package at.technikum.mse.st;

import at.technikum.mse.st.poi.PoiFileMapper;

import java.io.File;
import java.util.List;

public class SpreadsheetTestdata {
    private FileMapper<?> fileMapper;

    public SpreadsheetTestdata() {
        this.fileMapper = new PoiFileMapper();
    }

    public <T> void createTemplate(File target, Class<T> clazz) throws StException {
        fileMapper.createTemplate(target, clazz);
    }

    public <T> List<T> read(File source, Class<T> clazz) throws Exception {
        return fileMapper.read(source, clazz);
    }

    public void registerFileMapper(FileMapper fileMapper) {
        this.fileMapper = fileMapper;
    }

    public <S> void registerTypeMapper(TypeMapper typeMapper, Class<S> type) {
        fileMapper.registerTypeMapper(typeMapper, type);
    }

    public <S> void registerTypeMapper(TypeMapper typeMapper, List<Class<S>> types) {
        fileMapper.registerTypeMapper(typeMapper, types);
    }
}
