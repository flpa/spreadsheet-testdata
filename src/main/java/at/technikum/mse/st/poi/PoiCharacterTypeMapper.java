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

public class PoiCharacterTypeMapper implements TypeMapper<Character, PoiContext> {
	
	private static final Logger LOG = LoggerFactory.getLogger(PoiCharacterTypeMapper.class);

	@Override
	public void createColumn(PoiContext context, int index) {
		// style
		CellStyle style = context.getWorkbook().createCellStyle();
		style.setDataFormat(PoiConstants.EXCEL_CELL_STYLE_DATA_FORMAT_TEXT);
		context.getSheet().setDefaultColumnStyle(index, style);

		// validation
		DataValidationHelper validationHelper = context.getSheet().getDataValidationHelper();
		DataValidationConstraint constraint = validationHelper.createTextLengthConstraint(DataValidationConstraint.OperatorType.BETWEEN, "1", "1");
		CellRangeAddressList addressList = new CellRangeAddressList(1, PoiConstants.EXCEL_MAX_ROW, index, index);
		DataValidation validation = validationHelper.createValidation(constraint, addressList);

		validation.createPromptBox("Character", "Only single characters allowed");
		validation.setShowPromptBox(true);

		validation.setErrorStyle(DataValidation.ErrorStyle.STOP);
		validation.createErrorBox("Only character values allowed", "Only character values allowed");
		validation.setShowErrorBox(true);

		context.getSheet().addValidationData(validation);
		LOG.info("Validation: " + validation.getErrorBoxText() + "! for column " + index + " created");
		LOG.info("Column " + index + " created");
	}

	@Override
	public Character readValue(PoiContext context, int row, int column) {
		Cell cell = context.getSheet().getRow(row).getCell(column);
		String value = cell.getStringCellValue();
		if(value.length()==1) {
			return value.charAt(0);
		}
		return null;
	}

}
