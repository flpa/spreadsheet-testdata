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

public class PoiDoubleTypeMapper implements TypeMapper<Double, PoiContext> {
	
	private static final Logger LOG = LoggerFactory.getLogger(PoiDoubleTypeMapper.class);

	@Override
	public void createColumn(PoiContext context, int index) {
		// style
		CellStyle style = context.getWorkbook().createCellStyle();
		style.setDataFormat(PoiConstants.EXCEL_CELL_STYLE_DATA_FORMAT_DECIMAL);
		context.getSheet().setDefaultColumnStyle(index, style);

		// validation
		DataValidationHelper validationHelper = context.getSheet().getDataValidationHelper();
		DataValidationConstraint constraint = validationHelper.createDecimalConstraint(DataValidationConstraint.OperatorType.BETWEEN, "-" + PoiConstants.EXCEL_MAX_DOUBLE_VALUE, PoiConstants.EXCEL_MAX_DOUBLE_VALUE);
		CellRangeAddressList addressList = new CellRangeAddressList(1, PoiConstants.EXCEL_MAX_ROW, index, index);
		DataValidation validation = validationHelper.createValidation(constraint, addressList);

		validation.createPromptBox("Double", "Only double values allowed");
		validation.setShowPromptBox(true);

		validation.setErrorStyle(DataValidation.ErrorStyle.STOP);
		validation.createErrorBox("Only double values allowed", "Only double values are allowed!\nRange: -" + PoiConstants.EXCEL_MAX_DOUBLE_VALUE + " to " + PoiConstants.EXCEL_MAX_DOUBLE_VALUE);
		validation.setShowErrorBox(true);

		context.getSheet().addValidationData(validation);
		LOG.info("Validation: " + validation.getErrorBoxText() + "! for column " + index + " created");
		LOG.info("Column " + index + " created");
	}

	@Override
	public Double readValue(PoiContext context, int row, int column) {
		Cell cell = context.getSheet().getRow(row).getCell(column);
		return cell.getNumericCellValue();
	}

}
