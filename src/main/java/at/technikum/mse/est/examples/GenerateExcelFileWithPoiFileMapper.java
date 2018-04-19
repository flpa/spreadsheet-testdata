package at.technikum.mse.est.examples;

import at.technikum.mse.est.FileMapper;
import at.technikum.mse.est.poc.TestClass;
import at.technikum.mse.est.poi.PoiFileMapper;

import java.io.File;

public class GenerateExcelFileWithPoiFileMapper {
    public static void main(String[] args) {
        String filename = "./example.xlsx";

        FileMapper fileMapper = new PoiFileMapper();
        fileMapper.createTemplate(new File(filename), TestClass.class);
    }
}
