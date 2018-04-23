package at.technikum.mse.st.poi;

import at.technikum.mse.st.TypeMapper;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PoiStringTypeMapper implements TypeMapper<String, PoiContext> {
	
	private static final Logger LOG = LoggerFactory.getLogger(PoiStringTypeMapper.class);

	@Override
	public void createColumn(PoiContext context, int index) {
		// style
		CellStyle style = context.getWorkbook().createCellStyle();
		style.setDataFormat(PoiConstants.EXCEL_CELL_STYLE_DATA_FORMAT_TEXT);
		context.getSheet().setDefaultColumnStyle(index, style);
		LOG.info("Column " + index + " created");
	}

	@Override
	public String readValue(PoiContext context, int row, int column) {
		Cell cell = context.getSheet().getRow(row).getCell(column);
		return cell.getStringCellValue();
	}

}
