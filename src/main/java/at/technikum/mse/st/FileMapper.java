package at.technikum.mse.st;

import java.io.File;
import java.util.List;

/**
 * FileMapper interface for implementing custom mapping functionality for different file types.
 *
 * In order to use a custom FileMapper, first implement this interface, a corresponding {@link Context}
 * and any needed {@link TypeMapper}.
 * Register any default {@link TypeMapper} in the constructor using {@link #registerTypeMapper(TypeMapper, Class[])}.
 * Then register the FileMapper by calling {@link SpreadsheetTestdata#registerFileMapper(FileMapper)}.
 *
 * @param <C> {@link Context} used by the FileMapper and any corresponding {@link TypeMapper}.
 */
public interface FileMapper<C extends Context> {

	/**
	 * Generates a template file for the given testdata class.
	 *
	 * Generates a template at the given target {@link File} for the given testdata class.
	 *
	 * @param target    target {@link File}.
	 * @param clazz     testdata class the template is generated for.
	 * @throws StException if an error occurs during template generation.
	 */
	<T> void createTemplate(File target, Class<T> clazz) throws StException;

	/**
	 * Reads the given testdata {@link File}.
	 *
	 * Reads the given testdata {@link File} and returns a {@link List} of objects of the given testdata class.
	 *
	 * @param source    source {@link File}.
	 * @param clazz     testdata class to generate from the given file.
	 * @return a {@link List} of objects of the given testdata class.
	 * @throws StException if an error occurs while reading the file.
	 */
	<T> List<T> read(File source, Class<T> clazz) throws StException;

	/**
	 * Registers a custom {@link TypeMapper}.
	 *
	 * @param typeMapper    {@link TypeMapper} to register.
	 * @param types         types that are mapped by the {@link TypeMapper}.
	 */
	<S> void registerTypeMapper(TypeMapper<?, C> typeMapper, Class<S>... types);
}