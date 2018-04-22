package at.technikum.mse.st.poc;

import java.io.File;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import at.technikum.mse.st.poi.PoiFileMapper;
import dtos.Student;

public class Main {
	private static final Logger LOG = LoggerFactory.getLogger(Main.class);
	
	public static void main(String[] args) throws Exception {
		LOG.info("Starting");
		
		PoiFileMapper fileMapper = new PoiFileMapper();
        List<Student> list = fileMapper.read(new File("resources/Students.xlsx"), Student.class);
        
        for(Student student : list) {
        	System.out.println(student.toString());
        }
	}
}
