package at.technikum.mse.est.examples;

import java.io.File;

import at.technikum.mse.est.poc.TestClass;
import at.technikum.mse.est.poi.PoiFileMapper;

public class GenerateExcelFileWithPoiFileMapper {
    public static void main(String[] args) {
        String filename = "./example.xlsx";

        PoiFileMapper fileMapper = new PoiFileMapper();
        fileMapper.createTemplate(new File(filename), TestClass.class);
    }
}
