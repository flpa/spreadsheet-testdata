package at.technikum.mse.est.poi;

import at.technikum.mse.est.Context;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

public class PoiContext implements Context {
	/*
	 * flpa: Having the fields final makes the class immutable, of course we could
	 * also have them mutable to use one context for the whole process (i.e.
	 * modifying row while iterating through the rows)
	 */
	private final Workbook workbook;
	private final Sheet sheet;
	// flpa: not sure if this needs to be here?
	// private final XSSFDataValidationHelper validationHelper;

	public PoiContext(Workbook workbook, Sheet sheet) {
		this.workbook = workbook;
		this.sheet = sheet;
	}

	public Workbook getWorkbook() {
		return workbook;
	}

	public Sheet getSheet() {
		return sheet;
	}

	
}
