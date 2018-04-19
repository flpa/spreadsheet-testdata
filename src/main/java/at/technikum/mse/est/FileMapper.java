package at.technikum.mse.est;

import java.io.File;
import java.util.List;

public interface FileMapper {
	<T> void createTemplate(File target, Class<T> clazz);

	<T> List<T> read(File source, Class<T> clazz);

	<S> void registerTypeMapper(TypeMapper typeMapper, Class<S> type);

	default <S> void registerTypeMapper(TypeMapper typeMapper, List<Class<S>> types) {
		types.forEach(type -> registerTypeMapper(typeMapper, type));
	}
}