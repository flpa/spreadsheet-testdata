package at.technikum.mse.est;

import org.apache.poi.ss.usermodel.Workbook;

import dtos.Student;

public class Main {
	public static void main(String[] args) throws Exception {
		Reader reader = new ExcelReader();
		Workbook wb = reader.createWorkbook("resources/Students.xlsx");
		reader.mapSheetToClass(wb, 0, Student.class);
		
	}
}
