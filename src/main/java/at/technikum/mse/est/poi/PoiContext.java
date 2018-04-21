package at.technikum.mse.est.poi;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import at.technikum.mse.est.Context;

public class PoiContext implements Context {
	/*
	 * flpa: Having the fields final makes the class immutable, of course we could
	 * also have them mutable to use one context for the whole process (i.e.
	 * modifying row while iterating through the rows)
	 */
	private final Workbook workbook;
	private final Sheet sheet;

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