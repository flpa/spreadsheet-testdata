package at.technikum.mse.est.poc;

import java.util.ArrayList;
import org.apache.poi.ss.usermodel.Workbook;

public interface Reader {
	
	public Workbook createWorkbook(String filePath) throws Exception;

	public ArrayList<Object> mapSheetToClass(Workbook wb, int sheetIndex, Class<?> c) throws Exception;

}
