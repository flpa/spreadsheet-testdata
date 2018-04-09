package at.technikum.mse.est.poi;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import at.technikum.mse.est.Context;

public class PoiContext implements Context {
	/*
	 * flpa: Having the fields final makes the class immutable, of course we could
	 * also have them mutable to use one context for the whole process (i.e.
	 * modifying row while iterating through the rows)
	 */
	private final XSSFWorkbook workbook;
	private final XSSFSheet sheet;
	// flpa: not sure if this needs to be here?
	// private final XSSFDataValidationHelper validationHelper;
	private final Row row;

	public PoiContext(XSSFWorkbook workbook, XSSFSheet sheet, Row row) {
		this.workbook = workbook;
		this.sheet = sheet;
		this.row = row;
	}

	public XSSFWorkbook getWorkbook() {
		return workbook;
	}

	public XSSFSheet getSheet() {
		return sheet;
	}

	public Row getRow() {
		return row;
	}
}
