package at.technikum.mse.st.examples;

import at.technikum.mse.st.SpreadsheetTestdata;
import at.technikum.mse.st.StException;
import at.technikum.mse.st.poc.TestClass;

import java.io.File;

public class GenerateTemplateWithDefaultFileMapper {
    public static void main(String[] args) throws StException {
        String filename = "example.xlsx";
        SpreadsheetTestdata spreadsheetTestdata = new SpreadsheetTestdata();
        spreadsheetTestdata.createTemplate(new File(filename), TestClass.class);
    }
}
