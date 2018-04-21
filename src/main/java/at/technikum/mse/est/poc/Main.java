package at.technikum.mse.est.poc;

import java.io.File;
import java.util.List;

import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import at.technikum.mse.est.FileMapper;
import at.technikum.mse.est.poi.PoiFileMapper;
import dtos.Student;

public class Main {
	private static final Logger LOG = LoggerFactory.getLogger(Main.class);
	
	public static void main(String[] args) throws Exception {
		LOG.info("Starting");
		
		FileMapper fileMapper = new PoiFileMapper();
        List<Student> list = fileMapper.read(new File("resources/Students.xlsx"), Student.class, 0);
        
        for(Student student : list) {
        	System.out.println(student.toString());
        }
	}
}
