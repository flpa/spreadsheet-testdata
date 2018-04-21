package at.technikum.mse.est.examples;

import at.technikum.mse.est.LibraryApi;
import at.technikum.mse.est.poc.TestClass;

import java.io.File;

public class GenerateTemplateWithDefaultFileMapper {
    public static void main(String[] args) {
        String filename = "example.xlsx";
        LibraryApi libraryApi = new LibraryApi();
        libraryApi.createTemplate(new File(filename), TestClass.class);
    }
}
