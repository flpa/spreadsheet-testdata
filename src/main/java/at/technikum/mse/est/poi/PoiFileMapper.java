package at.technikum.mse.est.poi;

import at.technikum.mse.est.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.*;

public class PoiFileMapper implements FileMapper {

    private Map<Class, TypeMapper> typeMappers;

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

        Context poiContext = new PoiContext(workbook, sheet, row);

        Field[] fields = clazz.getDeclaredFields();
        FieldLabelBuilder fieldLabelBuilder = new FieldLabelBuilder();
        for (Field field : fields) {
            TypeMapper typeMapper = typeMappers.get(field.getType());
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
	public <T> List<T> read(File source, Class<T> clazz) {
		// TODO Auto-generated method stub
		return Collections.emptyList();
	}

    @Override
    public <S> void registerTypeMapper(TypeMapper typeMapper, Class<S> type) {
        this.typeMappers.put(type, typeMapper);
    }

}
