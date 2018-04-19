package at.technikum.mse.est.poi;

import at.technikum.mse.est.TypeMapper;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataValidation;
import org.apache.poi.ss.usermodel.DataValidationConstraint;
import org.apache.poi.ss.usermodel.DataValidationHelper;
import org.apache.poi.ss.util.CellRangeAddressList;

public class PoiFloatTypeMapper implements TypeMapper<Float, PoiContext> {

	@Override
	public void createColumn(PoiContext context, int index) {
		// style
		CellStyle style = context.getWorkbook().createCellStyle();
		style.setDataFormat(PoiConstants.EXCEL_CELL_STYLE_DATA_FORMAT_DECIMAL);
		context.getSheet().setDefaultColumnStyle(index, style);

		// validation
		DataValidationHelper validationHelper = context.getSheet().getDataValidationHelper();
		DataValidationConstraint constraint = validationHelper.createDecimalConstraint(DataValidationConstraint.OperatorType.BETWEEN, Float.toString(-Float.MAX_VALUE), Float.toString(Float.MAX_VALUE));
		CellRangeAddressList addressList = new CellRangeAddressList(1, PoiConstants.EXCEL_MAX_ROW, index, index);
		DataValidation validation = validationHelper.createValidation(constraint, addressList);

		validation.createPromptBox("Float", "Only float values allowed");
		validation.setShowPromptBox(true);

		validation.setErrorStyle(DataValidation.ErrorStyle.STOP);
		validation.createErrorBox("Only float values allowed", "Only float values are allowed!\nRange: " + -Float.MAX_VALUE + " to " + Float.MAX_VALUE);
		validation.setShowErrorBox(true);

		context.getSheet().addValidationData(validation);
	}

	@Override
	public Float readValue(PoiContext context, int row, int column) {
		// TODO Auto-generated method stub
		return null;
	}

}
