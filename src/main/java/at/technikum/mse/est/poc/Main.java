package at.technikum.mse.est.poc;

import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import dtos.Student;

public class Main {
	private static final Logger LOG = LoggerFactory.getLogger(Main.class);
	
	public static void main(String[] args) throws Exception {
		LOG.info("Starting");
		
		Reader reader = new ExcelReader();
		Workbook wb = reader.createWorkbook("resources/Students.xlsx");
		reader.mapSheetToClass(wb, 0, Student.class);
	}
}
