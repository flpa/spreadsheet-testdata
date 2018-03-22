package at.technikum.mse.est;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class DescriptionBuilderTest {

	private class TestClass {
		private int number;
	}

	@Test
	public void testBuild() throws Exception {
		assertThat(new DescriptionBuilder().build(TestClass.class.getDeclaredField("number"))).isEqualTo("number");
	}

}
