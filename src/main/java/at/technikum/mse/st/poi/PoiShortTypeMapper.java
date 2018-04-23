package at.technikum.mse.st.poi;

import at.technikum.mse.st.TypeMapper;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataValidation;
import org.apache.poi.ss.usermodel.DataValidationConstraint;
import org.apache.poi.ss.usermodel.DataValidationHelper;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PoiShortTypeMapper implements TypeMapper<Short, PoiContext> {
	
	private static final Logger LOG = LoggerFactory.getLogger(PoiShortTypeMapper.class);

	@Override
	public void createColumn(PoiContext context, int index) {
		// style
		CellStyle style = context.getWorkbook().createCellStyle();
		style.setDataFormat(PoiConstants.EXCEL_CELL_STYLE_DATA_FORMAT_INTEGER);
		context.getSheet().setDefaultColumnStyle(index, style);

		// validation
		DataValidationHelper validationHelper = context.getSheet().getDataValidationHelper();
		DataValidationConstraint constraint = validationHelper.createIntegerConstraint(DataValidationConstraint.OperatorType.BETWEEN,  Short.toString(Short.MIN_VALUE), Short.toString(Short.MAX_VALUE));
		CellRangeAddressList addressList = new CellRangeAddressList(1, PoiConstants.EXCEL_MAX_ROW, index, index);
		DataValidation validation = validationHelper.createValidation(constraint, addressList);

		validation.createPromptBox("Short", "Only short values allowed");
		validation.setShowPromptBox(true);

		validation.setErrorStyle(DataValidation.ErrorStyle.STOP);
		validation.createErrorBox("Only short values allowed", "Only short values are allowed!\nRange: " + Short.MIN_VALUE + " to " + Short.MAX_VALUE);
		validation.setShowErrorBox(true);

		context.getSheet().addValidationData(validation);
		LOG.info("Validation: " + validation.getErrorBoxText() + "! for column " + index + " created");
		LOG.info("Column " + index + " created");
	}

	@Override
	public Short readValue(PoiContext context, int row, int column) {
		Cell cell = context.getSheet().getRow(row).getCell(column);
		Double value = cell.getNumericCellValue();
		return value.shortValue();
	}

}
