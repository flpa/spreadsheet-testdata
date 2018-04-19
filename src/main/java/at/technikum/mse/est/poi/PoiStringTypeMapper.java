package at.technikum.mse.est.poi;

import at.technikum.mse.est.TypeMapper;
import org.apache.poi.ss.usermodel.CellStyle;

public class PoiStringTypeMapper implements TypeMapper<String, PoiContext> {

	@Override
	public void createColumn(PoiContext context, int index) {
		// style
		CellStyle style = context.getWorkbook().createCellStyle();
		style.setDataFormat(PoiConstants.EXCEL_CELL_STYLE_DATA_FORMAT_TEXT);
		context.getSheet().setDefaultColumnStyle(index, style);
	}

	@Override
	public String readValue(PoiContext context, int row, int column) {
		// TODO Auto-generated method stub
		return null;
	}

}
