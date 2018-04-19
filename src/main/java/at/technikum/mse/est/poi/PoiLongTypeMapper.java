package at.technikum.mse.est.poi;

import at.technikum.mse.est.TypeMapper;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataValidation;
import org.apache.poi.ss.usermodel.DataValidationConstraint;
import org.apache.poi.ss.usermodel.DataValidationHelper;
import org.apache.poi.ss.util.CellRangeAddressList;

public class PoiLongTypeMapper implements TypeMapper<Long, PoiContext> {

	@Override
	public void createColumn(PoiContext context, int index) {
		// style
		CellStyle style = context.getWorkbook().createCellStyle();
		style.setDataFormat(PoiConstants.EXCEL_CELL_STYLE_DATA_FORMAT_INTEGER);
		context.getSheet().setDefaultColumnStyle(index, style);

		// validation
		DataValidationHelper validationHelper = context.getSheet().getDataValidationHelper();
		DataValidationConstraint constraint = validationHelper.createIntegerConstraint(DataValidationConstraint.OperatorType.BETWEEN, Long.toString(Long.MIN_VALUE), Long.toString(Long.MAX_VALUE));
		CellRangeAddressList addressList = new CellRangeAddressList(1, PoiConstants.EXCEL_MAX_ROW, index, index);
		DataValidation validation = validationHelper.createValidation(constraint, addressList);

		validation.createPromptBox("Long", "Only long values allowed");
		validation.setShowPromptBox(true);

		validation.setErrorStyle(DataValidation.ErrorStyle.STOP);
		validation.createErrorBox("Only long values allowed", "Only long values are allowed!\nRange: " + Long.MIN_VALUE + " to " + Long.MAX_VALUE);
		validation.setShowErrorBox(true);

		context.getSheet().addValidationData(validation);
	}

	@Override
	public Long readValue(PoiContext context, int row, int column) {
		// TODO Auto-generated method stub
		return null;
	}

}
