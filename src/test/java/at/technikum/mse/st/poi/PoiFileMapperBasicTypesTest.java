package at.technikum.mse.st.poi;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;
import java.io.IOException;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import at.technikum.mse.st.Label;

public class PoiFileMapperBasicTypesTest {

	@Rule
	public TemporaryFolder tmpFolder = new TemporaryFolder();

	@Test
	public void createTemplate_shouldSupportPrimitiveTypes() throws Exception {
		File file = tmpFolder.newFile("test.xlsx");
		new PoiFileMapper().createTemplate(file, TestClass.class);

		assertThat(countColumns(file)).isEqualTo(8);
	}

	private int countColumns(File file) throws IOException, InvalidFormatException {
		try (Workbook workbook = WorkbookFactory.create(file)) {
			return workbook.getSheetAt(0).getRow(0).getLastCellNum();
		}
	}

	@SuppressWarnings("unused")
	static class TestClass {
		@Label("TestStringLabel")
		private String testString;
		private char testChar;
		private short testShort;
		private int testInt;
		private long testLong;
		private float testFloat;
		private double testDouble;
		private boolean testBoolean;

		public TestClass(String testString, char testChar, short testShort, int testInt, long testLong,
				float testFloat, double testDouble, boolean testBoolean) {
			this.testString = testString;
			this.testChar = testChar;
			this.testShort = testShort;
			this.testInt = testInt;
			this.testLong = testLong;
			this.testFloat = testFloat;
			this.testDouble = testDouble;
			this.testBoolean = testBoolean;
		}
	}
}
