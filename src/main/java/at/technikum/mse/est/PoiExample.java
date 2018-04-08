package at.technikum.mse.est;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.xssf.usermodel.*;

import java.io.FileOutputStream;
import java.io.IOException;

public class PoiExample {

    public static final int MAX_EXCEL_ROW = 1048575;
    public static final String MAX_EXCEL_DOUBLE_VALUE = "9.99999999999999E+307";

    public static void main(String[] args) {
        String filename = "./example.xlsx";

        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Test-Sheet");
        XSSFDataValidationHelper validationHelper = new XSSFDataValidationHelper(sheet);

        Cell cell;
        Row row;
        XSSFCellStyle style;
        DataValidationConstraint constraint;
        CellRangeAddressList addressList;
        DataValidation validation;

        row = sheet.createRow(0);
        int colNumber = 0;

        // style for header
        XSSFCellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setDataFormat((short)0x31);
        sheet.createFreezePane(0, 1);

        // String ------------------------------------------------------------------------------------------------------
        // style for column
        style = workbook.createCellStyle();
        style.setDataFormat((short)0x31);
        sheet.setDefaultColumnStyle(colNumber, style);

        // header
        cell = row.createCell(colNumber);
        cell.setCellStyle(headerStyle);
        cell.setCellValue("String");

        colNumber++;

        // Char --------------------------------------------------------------------------------------------------------
        // style for column
        style = workbook.createCellStyle();
        style.setDataFormat((short)0x31);
        sheet.setDefaultColumnStyle(colNumber, style);

        // header
        cell = row.createCell(colNumber);
        cell.setCellStyle(headerStyle);
        cell.setCellValue("Char");

        // validation
        constraint = validationHelper.createTextLengthConstraint(DataValidationConstraint.OperatorType.BETWEEN, "1", "1");
        addressList = new CellRangeAddressList(1, MAX_EXCEL_ROW, colNumber, colNumber);
        validation = validationHelper.createValidation(constraint, addressList);

        validation.createPromptBox("Only char values allowed", "Length: 1");
        validation.setShowPromptBox(true);

        validation.setErrorStyle(DataValidation.ErrorStyle.STOP);
        validation.createErrorBox("Only char values allowed", "Only char values are allowed!");
        validation.setShowErrorBox(true);

        sheet.addValidationData(validation);
        colNumber++;


        // Short -------------------------------------------------------------------------------------------------------
        // style for column
        style = workbook.createCellStyle();
        style.setDataFormat((short)1);
        sheet.setDefaultColumnStyle(colNumber, style);

        // header
        cell = row.createCell(colNumber);
        cell.setCellStyle(headerStyle);
        cell.setCellValue("Short");

        // validation
        constraint = validationHelper.createIntegerConstraint(DataValidationConstraint.OperatorType.BETWEEN, Short.toString(Short.MIN_VALUE), Short.toString(Short.MAX_VALUE));
        addressList = new CellRangeAddressList(1, MAX_EXCEL_ROW, colNumber, colNumber);
        validation = validationHelper.createValidation(constraint, addressList);

        validation.createPromptBox("Only short values allowed", "Range: " + Short.MIN_VALUE + " - " + Short.MAX_VALUE);
        validation.setShowPromptBox(true);

        validation.setErrorStyle(DataValidation.ErrorStyle.STOP);
        validation.createErrorBox("Only short values allowed", "Only short values are allowed!\nRange: " + Short.MIN_VALUE + " to " + Short.MAX_VALUE);
        validation.setShowErrorBox(true);

        sheet.addValidationData(validation);
        colNumber++;


        // Int ---------------------------------------------------------------------------------------------------------
        // style for column
        style = workbook.createCellStyle();
        style.setDataFormat((short)1);
        sheet.setDefaultColumnStyle(colNumber, style);

        // header
        cell = row.createCell(colNumber);
        cell.setCellStyle(headerStyle);
        cell.setCellValue("Int");

        // validation
        constraint = validationHelper.createIntegerConstraint(DataValidationConstraint.OperatorType.BETWEEN, Integer.toString(Integer.MIN_VALUE), Integer.toString(Integer.MAX_VALUE));
        addressList = new CellRangeAddressList(1, MAX_EXCEL_ROW, colNumber, colNumber);
        validation = validationHelper.createValidation(constraint, addressList);

        validation.createPromptBox("Only integer values allowed", "Range: " + Integer.MIN_VALUE + " - " + Integer.MAX_VALUE);
        validation.setShowPromptBox(true);

        validation.setErrorStyle(DataValidation.ErrorStyle.STOP);
        validation.createErrorBox("Only integer values allowed", "Only integer values are allowed!\nRange: " + Integer.MIN_VALUE + " to " + Integer.MAX_VALUE);
        validation.setShowErrorBox(true);

        sheet.addValidationData(validation);
        colNumber++;


        // Long --------------------------------------------------------------------------------------------------------
        // style for column
        style = workbook.createCellStyle();
        style.setDataFormat((short)1);
        sheet.setDefaultColumnStyle(colNumber, style);

        // header
        cell = row.createCell(colNumber);
        cell.setCellStyle(headerStyle);
        cell.setCellValue("Long");

        // validation
        constraint = validationHelper.createIntegerConstraint(DataValidationConstraint.OperatorType.BETWEEN, Long.toString(Long.MIN_VALUE), Long.toString(Long.MAX_VALUE));
        addressList = new CellRangeAddressList(1, MAX_EXCEL_ROW, colNumber, colNumber);
        validation = validationHelper.createValidation(constraint, addressList);

        validation.createPromptBox("Only long values allowed", "Range: " + Long.MIN_VALUE + " - " + Long.MAX_VALUE);
        validation.setShowPromptBox(true);

        validation.setErrorStyle(DataValidation.ErrorStyle.STOP);
        validation.createErrorBox("Only long values allowed", "Only long values are allowed!\nRange: " + Long.MIN_VALUE + " to " + Long.MAX_VALUE);
        validation.setShowErrorBox(true);

        sheet.addValidationData(validation);
        colNumber++;


        // Float -------------------------------------------------------------------------------------------------------
        // style for column
        style = workbook.createCellStyle();
        style.setDataFormat((short)2);
        sheet.setDefaultColumnStyle(colNumber, style);

        // header
        cell = row.createCell(colNumber);
        cell.setCellStyle(headerStyle);
        cell.setCellValue("Float");

        // validation
        constraint = validationHelper.createDecimalConstraint(DataValidationConstraint.OperatorType.BETWEEN, Float.toString(-Float.MAX_VALUE), Float.toString(Float.MAX_VALUE));
        addressList = new CellRangeAddressList(1, MAX_EXCEL_ROW, colNumber, colNumber);
        validation = validationHelper.createValidation(constraint, addressList);

        validation.createPromptBox("Only float values allowed", "Range: " + -Float.MAX_VALUE + " - " + Float.MAX_VALUE);
        validation.setShowPromptBox(true);

        validation.setErrorStyle(DataValidation.ErrorStyle.STOP);
        validation.createErrorBox("Only float values allowed", "Only float values are allowed!\nRange: " + -Float.MAX_VALUE + " to " + Float.MAX_VALUE);
        validation.setShowErrorBox(true);

        sheet.addValidationData(validation);
        colNumber++;


        // Double ------------------------------------------------------------------------------------------------------
        // style for column
        style = workbook.createCellStyle();
        style.setDataFormat((short)2);
        sheet.setDefaultColumnStyle(colNumber, style);

        // header
        cell = row.createCell(colNumber);
        cell.setCellStyle(headerStyle);
        cell.setCellValue("Double");

        // validation
        constraint = validationHelper.createDecimalConstraint(DataValidationConstraint.OperatorType.BETWEEN, "-" + MAX_EXCEL_DOUBLE_VALUE, MAX_EXCEL_DOUBLE_VALUE);
        addressList = new CellRangeAddressList(1, MAX_EXCEL_ROW, colNumber, colNumber);
        validation = validationHelper.createValidation(constraint, addressList);

        validation.createPromptBox("Only double values allowed", "Range: -" + MAX_EXCEL_DOUBLE_VALUE + " - " + MAX_EXCEL_DOUBLE_VALUE);
        validation.setShowPromptBox(true);

        validation.setErrorStyle(DataValidation.ErrorStyle.STOP);
        validation.createErrorBox("Only double values allowed", "Only double values are allowed!\nRange: -" + MAX_EXCEL_DOUBLE_VALUE + " to " + MAX_EXCEL_DOUBLE_VALUE);
        validation.setShowErrorBox(true);

        sheet.addValidationData(validation);
        colNumber++;


        // Boolean -----------------------------------------------------------------------------------------------------
        // style for column
        style = workbook.createCellStyle();
        style.setDataFormat((short)0x31);
        sheet.setDefaultColumnStyle(colNumber, style);

        // header
        cell = row.createCell(colNumber);
        cell.setCellStyle(headerStyle);
        cell.setCellValue("Boolean");

        // validation
        constraint = validationHelper.createExplicitListConstraint(new String[]{Boolean.TRUE.toString(), Boolean.FALSE.toString()});
        addressList = new CellRangeAddressList(1, MAX_EXCEL_ROW, colNumber, colNumber);
        validation = validationHelper.createValidation(constraint, addressList);

        validation.createPromptBox("Only boolean values allowed", "Allowed values: " + Boolean.TRUE.toString() + " or " + Boolean.FALSE.toString());
        validation.setShowPromptBox(true);

        validation.setErrorStyle(DataValidation.ErrorStyle.STOP);
        validation.createErrorBox("Only boolean values allowed", "Only boolean values are allowed!\nAllowed values: " + Boolean.TRUE.toString() + " or " + Boolean.FALSE.toString());
        validation.setShowErrorBox(true);

        sheet.addValidationData(validation);
        colNumber++;

        try {
            FileOutputStream outputStream = new FileOutputStream(filename);
            workbook.write(outputStream);
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
