package at.technikum.mse.est.poi;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import at.technikum.mse.est.LibraryApi;

public class PoiFileMapperComplexTypesTest {

	@Rule
	public TemporaryFolder tmpFolder = new TemporaryFolder();

	@Ignore
	@Test
	public void testFull() throws Exception {
		File file = tmpFolder.newFile("test.xlsx");

		LibraryApi libraryApi = new LibraryApi();
		libraryApi.createTemplate(file, ContainerMultipleChildren.class);
		
		System.out.println("Wrote " + file.getAbsolutePath());

		System.out.println("Press enter to continue");
		try (Scanner scanner = new Scanner(System.in)) {
			scanner.nextLine();
		}

		List<ContainerMultipleChildren> list = libraryApi.read(file, ContainerMultipleChildren.class);

		System.out.println("before");
		list.forEach(System.out::println);
		System.out.println("after");
	}
	
	@Test
	public void createTemplate_shouldSupportComplexType() throws Exception {
		File file = tmpFolder.newFile("test.xlsx");
		LibraryApi libraryApi = new LibraryApi();
		libraryApi.createTemplate(file, ContainerSingleChild.class);
		
		assertThat(countColumns(file)).isEqualTo(2);
	}
	
	@Test
	public void createTemplate_shouldSupportComplexTypeWithSameChildMultipleTimes() throws Exception {
		File file = tmpFolder.newFile("test.xlsx");
		LibraryApi libraryApi = new LibraryApi();
		libraryApi.createTemplate(file, ContainerMultipleChildren.class);
		
		assertThat(countColumns(file)).isEqualTo(4);
	}
	
	@Test(expected = CyclicalDependencyException.class)
	public void createTemplate_shouldPreventStackoverflowForCyclicalDependency() throws Exception {
		File file = tmpFolder.newFile("test.xlsx");
		LibraryApi libraryApi = new LibraryApi();
		libraryApi.createTemplate(file, CyclicalContainer.class);
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
