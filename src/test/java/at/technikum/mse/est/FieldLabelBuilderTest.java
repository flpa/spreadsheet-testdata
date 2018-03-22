package at.technikum.mse.est;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Ignore;
import org.junit.Test;

public class FieldLabelBuilderTest {

	@SuppressWarnings("unused")
	private class TestClass {
		private int number;
		@Label("age")
		private int number2;
		private String lastName;
	}

	@Test
	public void fieldWithoutAnnotation_ShouldReturnFieldname() throws NoSuchFieldException {
		test("number", "Number");
	}

	@Test
	public void fieldWithAnnotation_ShouldReturnAnnotationValue() throws NoSuchFieldException {
		test("number2", "age");
	}

	@Ignore
	// TODO
	@Test
	public void fieldWithCamelCase_ShouldReturnSentenceCaseString() throws NoSuchFieldException {
		test("lastName", "Last Name");
	}

	private void test(String fieldName, String expected) throws NoSuchFieldException {
		assertThat(new FieldLabelBuilder().build(TestClass.class.getDeclaredField(fieldName))).isEqualTo(expected);
	}

}
