package at.technikum.mse.est.poi;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;
import java.io.IOException;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import at.technikum.mse.est.LibraryApi;

public class PoiFileMapperComplexTypesTest {

	@Rule
	public TemporaryFolder tmpFolder = new TemporaryFolder();

	@Test
	public void createTemplate_shouldSupportComplexType() throws Exception {
		File file = tmpFolder.newFile("test.xlsx");
		new PoiFileMapper().createTemplate(file, ContainerSingleChild.class);
		
		assertThat(countColumns(file)).isEqualTo(2);
	}
	
	@Test
	public void createTemplate_shouldSupportComplexTypeWithSameChildMultipleTimes() throws Exception {
		File file = tmpFolder.newFile("test.xlsx");
		new PoiFileMapper().createTemplate(file, ContainerMultipleChildren.class);
		
		assertThat(countColumns(file)).isEqualTo(4);
	}
	
	@Test(expected = CyclicalDependencyException.class)
	public void createTemplate_shouldPreventStackoverflowForCyclicalDependency() throws Exception {
		File file = tmpFolder.newFile("test.xlsx");
		new PoiFileMapper().createTemplate(file, CyclicalContainer.class);
	}
	
	@Test
	public void read_ShouldSupportComplexTypeWithSameChildMultipleTimes() throws Exception {
		new  LibraryApi().read(new File("src/test/resources/containerMultipleChildren.xlsx"), ContainerMultipleChildren.class);
	}

	private int countColumns(File file) throws IOException, InvalidFormatException {
		try(Workbook workbook = WorkbookFactory.create(file)) {
			return workbook.getSheetAt(0).getRow(0).getLastCellNum();
		}
	}
	
	static class ContainerSingleChild {
		private final String name;
		private final Child child;
		
		public ContainerSingleChild(String name, Child child) {
			this.name = name;
			this.child = child;
		}

		public String getName() {
			return name;
		}

		public Child getChild() {
			return child;
		}
	}

	static class ContainerMultipleChildren {
		private final String name;
		private final Child child1;
		private final Child child2;
		private final AnotherRelation anotherRelation;

		public ContainerMultipleChildren(String name, Child child1, Child child2, AnotherRelation anotherRelation) {
			this.name = name;
			this.child1 = child1;
			this.child2 = child2;
			this.anotherRelation = anotherRelation;
		}

		public String getName() {
			return name;
		}

		public Child getChild11() {
			return child1;
		}

		public Child getChild12() {
			return child2;
		}

		public AnotherRelation getChild2() {
			return anotherRelation;
		}
	}

	static class Child {
		private final String name;

		public Child(String name) {
			this.name = name;
		}

		public String getName() {
			return name;
		}
	}

	static class AnotherRelation {
		private final int length;

		public AnotherRelation(int length) {
			this.length = length;
		}

		public int getLength() {
			return length;
		}
	}
	
	static class CyclicalContainer {
		private final CyclicalContainer cycle;

		public CyclicalContainer(CyclicalContainer cycle) {
			this.cycle = cycle;
		}

		public CyclicalContainer getCycle() {
			return cycle;
		}
	}
}
