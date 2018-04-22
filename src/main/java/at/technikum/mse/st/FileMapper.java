package at.technikum.mse.st;

import java.io.File;
import java.util.List;

public interface FileMapper<C extends Context> {
	<T> void createTemplate(File target, Class<T> clazz) throws StException;

	<T> List<T> read(File source, Class<T> clazz) throws StException;

	<S> void registerTypeMapper(TypeMapper<?, C> typeMapper, Class<S>... types);
}