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
        Row row = sheet.createRow(0);
        Cell cell;
        int colNumber = 0;
        CellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setDataFormat(PoiConstants.EXCEL_CELL_STYLE_DATA_FORMAT_TEXT);
        sheet.createFreezePane(0, 1);

        PoiContext poiContext = new PoiContext(workbook, sheet);

        Field[] fields = clazz.getDeclaredFields();
        FieldLabelBuilder fieldLabelBuilder = new FieldLabelBuilder();
        for (Field field : fields) {
            TypeMapper<?, PoiContext> typeMapper = typeMappers.get(field.getType());
            if (typeMapper == null) {
                throw new TypeMapperNotFoundException(field.getType());
            }
            typeMapper.createColumn(poiContext, colNumber);

            // header
            cell = row.createCell(colNumber);
            cell.setCellStyle(headerStyle);
            cell.setCellValue(fieldLabelBuilder.build(field));

            colNumber++;
        }

		try (FileOutputStream outputStream = new FileOutputStream(target)) {
			workbook.write(outputStream);
			workbook.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public <T> List<T> read(File source, Class<T> clazz, int sheetIndex) throws Exception {
		Workbook workbook = WorkbookFactory.create(source);
		Sheet sheet = workbook.getSheetAt(sheetIndex);
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
            objectsList.add(newInstance(clazz.getName(), args));
        }
        return objectsList;
	}

    @Override
    public <S> void registerTypeMapper(TypeMapper<?,PoiContext> typeMapper, Class<S> type) {
        this.typeMappers.put(type, typeMapper);
    }
    
    public HashMap<String, Integer> getExcelFieldNames(Sheet sheet) {

        HashMap<String, Integer> hashMap = new HashMap<>();

        Row row = sheet.getRow(0);
        for (int i = 0; i < row.getLastCellNum(); i++) {
            hashMap.put(row.getCell(i).getStringCellValue(), i);
        }
        return hashMap;
    }
    
    @SuppressWarnings("unchecked")
    public static <T> T newInstance(final String className, final Object... args)
            throws ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException,
            IllegalArgumentException, InvocationTargetException {
        @SuppressWarnings("rawtypes")
        Class[] types = new Class[args.length];
        for (int i = 0; i < types.length; i++) {
            types[i] = args[i].getClass();
        }
        return (T)Class.forName(className).getConstructor(types).newInstance(args);
    }


}
