package at.technikum.mse.est.poi;

import at.technikum.mse.est.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class PoiFileMapper implements FileMapper<PoiContext> {

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
	public <T> void createTemplate(File target, Class<T> clazz) {
		Workbook workbook = new XSSFWorkbook();
		Sheet sheet = workbook.createSheet(clazz.getSimpleName());

        // header
        Row headerRow = sheet.createRow(0);
        int colNumber = 0;
        CellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setDataFormat(PoiConstants.EXCEL_CELL_STYLE_DATA_FORMAT_TEXT);
        sheet.createFreezePane(0, 1);

        PoiContext poiContext = new PoiContext(workbook, sheet);

        FieldLabelBuilder fieldLabelBuilder = new FieldLabelBuilder();
        writeClass(clazz, poiContext, fieldLabelBuilder, headerRow, headerStyle, colNumber);

		try (FileOutputStream outputStream = new FileOutputStream(target)) {
			workbook.write(outputStream);
			workbook.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	private <T> void writeClass(Class<T> clazz, PoiContext poiContext, FieldLabelBuilder fieldLabelBuilder,
			Row headerRow, CellStyle headerStyle, int colNumber) {
		Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            TypeMapper<?, PoiContext> typeMapper = typeMappers.get(field.getType());
            if (typeMapper == null) {
                throw new TypeMapperNotFoundException(field.getType());
            }
            typeMapper.createColumn(poiContext, colNumber);

            // header
            Cell headerCell = headerRow.createCell(colNumber);
            headerCell.setCellStyle(headerStyle);
            headerCell.setCellValue(fieldLabelBuilder.build(field));

            colNumber++;
        }
	}

	@Override
	public <T> List<T> read(File source, Class<T> clazz) throws Exception {
		Workbook workbook = WorkbookFactory.create(source);
		Sheet sheet = workbook.getSheet(clazz.getSimpleName());
        ArrayList<T> objectsList = new ArrayList<>(); 
        Field[] fields = clazz.getDeclaredFields();

        Object[] args = new Object[fields.length];

        HashMap<String, Integer> hashMap = getExcelFieldNames(sheet);

        for (int row = 1; row < sheet.getPhysicalNumberOfRows(); row++) {
            for (int i = 0; i < clazz.getDeclaredFields().length; i++) {
            	int currentColumn = hashMap.get(new FieldLabelBuilder().build(fields[i]));
            	TypeMapper<?, PoiContext> typeMapper = typeMappers.get(fields[i].getType());
                args[i] = typeMapper.readValue(new PoiContext(workbook, sheet), row, currentColumn);
            }
            objectsList.add(newInstance(clazz, args));
        }
        return objectsList;
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
