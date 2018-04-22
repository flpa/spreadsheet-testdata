package at.technikum.mse.st.poc;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;

import at.technikum.mse.st.FieldLabelBuilder;

public class ExcelReader implements Reader {

    @Override
    public Workbook createWorkbook(String filePath)
            throws EncryptedDocumentException, InvalidFormatException, IOException {
        return WorkbookFactory.create(new File(filePath));
    }

    @Override
    public ArrayList<Object> mapSheetToClass(Workbook wb, int sheetIndex, Class<?> c) throws Exception {
    	Sheet sheet = wb.getSheetAt(sheetIndex);
        ArrayList<Object> objectsList = new ArrayList<>();
        
        Field[] fields = c.getDeclaredFields();

        Object[] args = new Object[fields.length];

        HashMap<String, Integer> hashMap = getExcelFieldNames(sheet);

        for (int r = 1; r < sheet.getPhysicalNumberOfRows(); r++) {
            Row currentRow = sheet.getRow(r);
            for (int i = 0; i < c.getDeclaredFields().length; i++) {
            	Cell cell = currentRow.getCell(hashMap.get(new FieldLabelBuilder().build(fields[i])));
                if (cell.getCellTypeEnum().equals(CellType.STRING)) {
                    args[i] = cell.getStringCellValue();
                }
                if (cell.getCellTypeEnum().equals(CellType.NUMERIC)) {
                    args[i] = cell.getNumericCellValue();
                }
            }

            objectsList.add(newInstance(c.getName(), args));
        }

        return objectsList;

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
