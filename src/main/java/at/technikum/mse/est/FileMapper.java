package at.technikum.mse.est;

import java.io.File;
import java.util.List;

public interface FileMapper<C extends Context> {
	<T> void createTemplate(File target, Class<T> clazz) throws EstException;

	<T> List<T> read(File source, Class<T> clazz) throws EstException;

	<S> void registerTypeMapper(TypeMapper<?, C> typeMapper, Class<S> type);

	default <S> void registerTypeMapper(TypeMapper<?, C> typeMapper, List<Class<S>> types) {
		types.forEach(type -> registerTypeMapper(typeMapper, type));
	}
}