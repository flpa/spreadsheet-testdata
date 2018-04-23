package at.technikum.mse.st.poi;

import at.technikum.mse.st.*;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class PoiFileMapper implements FileMapper<PoiContext> {
	
	private static final Logger LOG = LoggerFactory.getLogger(PoiFileMapper.class);
	
	private final FieldLabelBuilder fieldLabelBuilder = new FieldLabelBuilder();
    private Map<Class<?>, TypeMapper<?,PoiContext>> typeMappers;

    public PoiFileMapper() {
        this.typeMappers = new HashMap<>();
        
        LOG.info("Registering the TypeMappers...");
        
        registerTypeMapper(new PoiStringTypeMapper(), String.class);
        registerTypeMapper(new PoiCharacterTypeMapper(), Character.class, char.class);
        registerTypeMapper(new PoiShortTypeMapper(), Short.class, short.class);
        registerTypeMapper(new PoiIntegerTypeMapper(), Integer.class, int.class);
        registerTypeMapper(new PoiLongTypeMapper(), Long.class, long.class);
        registerTypeMapper(new PoiFloatTypeMapper(), Float.class, float.class);
        registerTypeMapper(new PoiDoubleTypeMapper(), Double.class, double.class);
        registerTypeMapper(new PoiBooleanTypeMapper(), Boolean.class, boolean.class);
    }

	@Override
	public <T> void createTemplate(File target, Class<T> clazz) throws StException {
		LOG.info("Start creating the template...");
		Workbook workbook = new XSSFWorkbook();
		
		Sheet sheet = workbook.createSheet(clazz.getSimpleName());
		LOG.info("Sheet " + clazz.getSimpleName() + " created ");

        // header
        Row headerRow = sheet.createRow(0);
        int colNumber = 0;
        CellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setDataFormat(PoiConstants.EXCEL_CELL_STYLE_DATA_FORMAT_TEXT);
        sheet.createFreezePane(0, 1);

        PoiContext poiContext = new PoiContext(workbook, sheet);

        writeClass(clazz, poiContext, headerRow, headerStyle,"", colNumber, new ArrayDeque<>(), new HashSet<>());

		try (FileOutputStream outputStream = new FileOutputStream(target)) {
			workbook.write(outputStream);
			workbook.close();
		} catch (IOException e) {
			throw new StException("Error while writing workbook", e);
		}
		
		LOG.info("Template for " + clazz.getSimpleName() + " created");
	}

	private <T> int writeClass(Class<T> clazz, PoiContext poiContext,
			Row headerRow, CellStyle headerStyle, String headerPrefix, int colNumber, Deque<Class<?>> classStack, Set<String> labels) throws CyclicalDependencyException, FieldMappingException, DuplicateLabelException {
		if(classStack.contains(clazz)) {
			throw new CyclicalDependencyException("A cyclical dependency has been detected. This is currently not supported.");
		}
		classStack.push(clazz);
		LOG.info("Getting the fields for " + clazz.getSimpleName());
		Field[] fields = getClassFields(clazz);
		for (Field field : fields) {
			TypeMapper<?, PoiContext> typeMapper = typeMappers.get(field.getType());
			
			if (typeMapper != null) {
				LOG.info("For the field " + field.getName() + " using " + typeMapper.getClass().getSimpleName());
				typeMapper.createColumn(poiContext, colNumber);
				
				
				// header
                String label = headerPrefix + fieldLabelBuilder.build(field);
                if (labels.contains(label)) {
                    throw new DuplicateLabelException(field, label);
                }
                labels.add(label);
				Cell headerCell = headerRow.createCell(colNumber);
				headerCell.setCellStyle(headerStyle);
				headerCell.setCellValue(label);

				LOG.info("Header " + label + " for column " + colNumber + " created");
				
				colNumber++;
			} else if (field.getAnnotation(Flatten.class) != null) {
				colNumber = writeClass(field.getType(), poiContext, headerRow, headerStyle, buildFlattenedFieldPrefix(field), colNumber, classStack, labels);
			} else {
				throw new FieldMappingException(field);
			}
		}
		classStack.pop();
		return colNumber;
	}

	private <T> Field[] getClassFields(Class<T> clazz) {
		/*
		 * Jacoco Code Coverage injects these fields into our test classes but
		 * we do not have TypeMappers for them and they are not in the
		 * constructors
		 */
		return Arrays.stream(clazz.getDeclaredFields())
				.filter(field -> !field.getName().equalsIgnoreCase("$jacocoData"))//
				.toArray(Field[]::new);
	}

	@Override
	public <T> List<T> read(File source, Class<T> clazz) throws StException {
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
			throw new StException("Error while reading workbook", e);
		} catch (ReflectiveOperationException | IllegalArgumentException e) {
			throw new StException("Error while instantiating target class " + clazz, e);
		}
	}

	private <S> S readClass(Class<S> clazz, PoiContext context, HashMap<String, Integer> columnNumberByName,
			FieldLabelBuilder fieldLabelBuilder, String prefix, int row, Deque<Class<?>> classStack) throws ReflectiveOperationException, CyclicalDependencyException, FieldMappingException {
		if(classStack.contains(clazz)) {
			throw new CyclicalDependencyException("A cyclical dependency has been detected. This is currently not supported.");
		}
		classStack.push(clazz);
		
		Field[] fields = getClassFields(clazz);
		Object[] args = new Object[fields.length];
		for (int i = 0; i < fields.length; i++) {
			Field field = fields[i];
			
			TypeMapper<?, PoiContext> typeMapper = typeMappers.get(field.getType());
			if (typeMapper != null) {
				int currentColumn = columnNumberByName.get(prefix + fieldLabelBuilder.build(field));
				args[i] = typeMapper.readValue(context, row, currentColumn);
			} else if (field.getAnnotation(Flatten.class) != null) {
				args[i] = readClass(field.getType(), context, columnNumberByName, fieldLabelBuilder, buildFlattenedFieldPrefix(field), row, classStack);
			} else {
				throw new FieldMappingException(field);
			}
		}
		classStack.pop();
		return newInstance(clazz, args);
	}

	private String buildFlattenedFieldPrefix(Field field) {
		return fieldLabelBuilder.build(field) + " ";
	}

    @Override
    public <S> void registerTypeMapper(TypeMapper<?,PoiContext> typeMapper, Class<S>... types) {
    	for (int i = 0; i < types.length; i++) {
    		this.typeMappers.put(types[i], typeMapper);
		}
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
    private <T> T newInstance(Class clazz, final Object... args)
            throws NoSuchMethodException, InstantiationException, IllegalAccessException,
            IllegalArgumentException, InvocationTargetException {
        Field[] fields = getClassFields(clazz);
        Class[] types = new Class[fields.length];
        for (int i = 0; i < types.length; i++) {
            types[i] = fields[i].getType();
        }

        return (T)clazz.getConstructor(types).newInstance(args);
    }
}
