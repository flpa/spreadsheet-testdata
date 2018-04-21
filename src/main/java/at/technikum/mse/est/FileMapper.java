package at.technikum.mse.est;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

public interface FileMapper {
	<T> void createTemplate(File target, Class<T> clazz);

	<T> List<T> read(File source, Class<T> clazz, int sheetIndex) throws Exception;

	<S> void registerTypeMapper(TypeMapper typeMapper, Class<S> type);

	default <S> void registerTypeMapper(TypeMapper typeMapper, List<Class<S>> types) {
		types.forEach(type -> registerTypeMapper(typeMapper, type));
	}
}