package at.technikum.mse.est.poi;

import at.technikum.mse.est.*;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class PoiFileMapper implements FileMapper<PoiContext> {
	private final FieldLabelBuilder fieldLabelBuilder = new FieldLabelBuilder();
    private Map<Class<?>, TypeMapper<?,PoiContext>> typeMappers;

    public PoiFileMapper() {
        this.typeMappers = new HashMap<>();

        registerTypeMapper(new PoiStringTypeMapper(), String.class);
        registerTypeMapper(new PoiCharacterTypeMapper(), Arrays.asList(Character.class, char.class));
        registerTypeMapper(new PoiShortTypeMapper(), Arrays.asList(Short.class, short.class));
        registerTypeMapper(new PoiIntegerTypeMapper(), Arrays.asList(Integer.class, int.class));
        registerTypeMapper(new PoiLongTypeMapper(), Arrays.asList(Long.class, long.class));
        registerTypeMapper(new PoiFloatTypeMapper(), Arrays.asList(Float.class, float.class));
        registerTypeMapper(new PoiDoubleTypeMapper(), Arrays.asList(Double.class, double.class));
        registerTypeMapper(new PoiBooleanTypeMapper(), Arrays.asList(Boolean.class, boolean.class));
    }

	@Override
	public <T> void createTemplate(File target, Class<T> clazz) throws EstException {
		Workbook workbook = new XSSFWorkbook();
		Sheet sheet = workbook.createSheet(clazz.getSimpleName());

        // header
        Row headerRow = sheet.createRow(0);
        int colNumber = 0;
        CellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setDataFormat(PoiConstants.EXCEL_CELL_STYLE_DATA_FORMAT_TEXT);
        sheet.createFreezePane(0, 1);

        PoiContext poiContext = new PoiContext(workbook, sheet);

        writeClass(clazz, poiContext, headerRow, headerStyle,"", colNumber, new ArrayDeque<>());

		try (FileOutputStream outputStream = new FileOutputStream(target)) {
			workbook.write(outputStream);
			workbook.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private <T> int writeClass(Class<T> clazz, PoiContext poiContext,
			Row headerRow, CellStyle headerStyle, String headerPrefix, int colNumber, Deque<Class<?>> classStack) throws CyclicalDependencyException {
		if(classStack.contains(clazz)) {
			throw new CyclicalDependencyException("A cyclical dependency has been detected. This is currently not supported.");
		}
		classStack.push(clazz);
		Field[] fields = clazz.getDeclaredFields();
		for (Field field : fields) {
			TypeMapper<?, PoiContext> typeMapper = typeMappers.get(field.getType());
			if (typeMapper == null) {
				colNumber = writeClass(field.getType(), poiContext, headerRow, headerStyle, buildFlattenedFieldPrefix(field), colNumber, classStack);
			} else {
				typeMapper.createColumn(poiContext, colNumber);

				// header
				Cell headerCell = headerRow.createCell(colNumber);
				headerCell.setCellStyle(headerStyle);
				headerCell.setCellValue(headerPrefix + fieldLabelBuilder.build(field));

				colNumber++;
			}
		}
		classStack.pop();
		return colNumber;
	}

	@Override
	public <T> List<T> read(File source, Class<T> clazz) throws EstException {
		try (Workbook workbook = WorkbookFactory.create(source)) {

			Sheet sheet = workbook.getSheet(clazz.getSimpleName());
			ArrayList<T> objectsList = new ArrayList<>();

			HashMap<String, Integer> columnNumberByName = getExcelFieldNames(sheet);
			FieldLabelBuilder fieldLabelBuilder = new FieldLabelBuilder();
			PoiContext context = new PoiContext(workbook, sheet);

			for (int row = 1; row < sheet.getPhysicalNumberOfRows(); row++) {
				objectsList.add(readClass(clazz, context, columnNumberByName, fieldLabelBuilder, "", row, new ArrayDeque<>()));
			}
			return objectsList;
		} catch (EncryptedDocumentException | InvalidFormatException | IOException e) {
			throw new EstException("Error while reading workbook", e);
		} catch (ReflectiveOperationException | IllegalArgumentException e) {
			throw new EstException("Error while instantiating target class " + clazz);
		}
	}

	private <S> S readClass(Class<S> clazz, PoiContext context, HashMap<String, Integer> columnNumberByName,
			FieldLabelBuilder fieldLabelBuilder, String prefix, int row, Deque<Class<?>> classStack) throws ReflectiveOperationException, CyclicalDependencyException {
		if(classStack.contains(clazz)) {
			throw new CyclicalDependencyException("A cyclical dependency has been detected. This is currently not supported.");
		}
		classStack.push(clazz);
		
		Field[] fields = clazz.getDeclaredFields();
		Object[] args = new Object[fields.length];
		for (int i = 0; i < fields.length; i++) {
			Field field = fields[i];
			
			TypeMapper<?, PoiContext> typeMapper = typeMappers.get(field.getType());
			if (typeMapper == null) {
				args[i] = readClass(field.getType(), context, columnNumberByName, fieldLabelBuilder, buildFlattenedFieldPrefix(field), row, classStack);
			} else {
				int currentColumn = columnNumberByName.get(prefix + fieldLabelBuilder.build(field));
				args[i] = typeMapper.readValue(context, row, currentColumn);
			}
		}
		classStack.pop();
		return newInstance(clazz, args);
	}

	private String buildFlattenedFieldPrefix(Field field) {
		return fieldLabelBuilder.build(field) + " ";
	}

    @Override
    public <S> void registerTypeMapper(TypeMapper<?,PoiContext> typeMapper, Class<S> type) {
        this.typeMappers.put(type, typeMapper);
    }
    
    private HashMap<String, Integer> getExcelFieldNames(Sheet sheet) {

        HashMap<String, Integer> hashMap = new HashMap<>();

        Row row = sheet.getRow(0);
        for (int i = 0; i < row.getLastCellNum(); i++) {
            hashMap.put(row.getCell(i).getStringCellValue(), i);
        }
        return hashMap;
    }
    
    @SuppressWarnings("unchecked")
    private static <T> T newInstance(Class clazz, final Object... args)
            throws NoSuchMethodException, InstantiationException, IllegalAccessException,
            IllegalArgumentException, InvocationTargetException {
        Field[] fields = clazz.getDeclaredFields();
        Class[] types = new Class[fields.length];
        for (int i = 0; i < types.length; i++) {
            types[i] = fields[i].getType();
        }

        return (T)clazz.getConstructor(types).newInstance(args);
    }
}
