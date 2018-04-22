package at.technikum.mse.st;

import java.io.File;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class SpreadsheetTestdataTest {
	private SpreadsheetTestdata impl = new SpreadsheetTestdata();
	private FileMapper fileMapper = Mockito.mock(FileMapper.class);

	@Before
	public void setUp() {
		impl.registerFileMapper(fileMapper);
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testCreateTemplate() throws Exception {
		File file = new File("foobar");
		Class<?> clazz = getClass();
		
		impl.createTemplate(file, clazz);
		
		Mockito.verify(fileMapper).createTemplate(file, clazz);
	}

	@Test
	public void testRead() throws Exception {
		File file = new File("foobar");
		Class<?> clazz = getClass();
		
		impl.read(file, clazz);
		
		Mockito.verify(fileMapper).read(file, clazz);
	}

	@Test
	public void testRegisterTypeMapper() throws Exception {
		TypeMapper tm = new TypeMapper<Object, Context>() {

			@Override
			public void createColumn(Context context, int index) {
			}

			@Override
			public Object readValue(Context context, int row, int column) {
				return null;
			}
		};
		
		impl.registerTypeMapper(tm, Object.class);
		
		Mockito.verify(fileMapper).registerTypeMapper(tm, Object.class);
	}

}
