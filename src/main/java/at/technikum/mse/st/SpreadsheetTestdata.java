package at.technikum.mse.st;

import at.technikum.mse.st.poi.PoiFileMapper;

import java.io.File;
import java.util.List;

/**
 * Spreadsheet Testdata API.
 *
 * Spreadsheet Testdata is a library that can generate template spreadsheet files for testdata classes and
 * read the contents as a list of objects of a testdata class.
 */
public class SpreadsheetTestdata {
    private FileMapper<?> fileMapper;

    /**
     * Constructor.
     *
     * Initializes the SpreadsheetTestdata API with a default {@link PoiFileMapper}.
     */
    public SpreadsheetTestdata() {
        this.fileMapper = new PoiFileMapper();
    }

    /**
     * Generates a template file for the given testdata class.
     *
     * Generates a template at the given target {@link File} for the given testdata class.
     *
     * @param target    target {@link File}.
     * @param clazz     testdata class the template is generated for.
     * @throws StException if an error occurs during template generation.
     */
    public <T> void createTemplate(File target, Class<T> clazz) throws StException {
        fileMapper.createTemplate(target, clazz);
    }

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
    public <T> List<T> read(File source, Class<T> clazz) throws StException {
        return fileMapper.read(source, clazz);
    }

    /**
     * Registers a custom {@link FileMapper}.
     *
     * Registers a custom {@link FileMapper} that is used instead of the built-in {@link PoiFileMapper}.
     * Removes any registered custom {@link TypeMapper}.
     *
     * @param fileMapper    {@link FileMapper} to use instead of the built-in {@link PoiFileMapper}.
     */
    public void registerFileMapper(FileMapper fileMapper) {
        this.fileMapper = fileMapper;
    }

    /**
     * Registers a custom {@link TypeMapper}.
     *
     * Registers a custom {@link TypeMapper}, that is used next to or instead of the default ones.
     *
     * @param typeMapper    {@link TypeMapper} to register.
     * @param types         types that are mapped by the {@link TypeMapper}.
     */
    public <S> void registerTypeMapper(TypeMapper typeMapper, Class<S>... types) {
        fileMapper.registerTypeMapper(typeMapper, types);
    }
}