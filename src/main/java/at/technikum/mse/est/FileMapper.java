package at.technikum.mse.est;

import java.io.File;
import java.util.List;

public interface FileMapper {
	<T> void createTemplate(File Mapper, Class<T> clazz);

	<T> List<T> read(File source, Class<T> clazz);
}