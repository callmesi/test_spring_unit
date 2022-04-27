package com.example.sweater.testutils;

import java.io.File;
import java.lang.reflect.Type;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class ResourceAccessor {

    private static final String DEFAULT_RESOURCES_PATH = "src/test/resources";
    private final String resourcesPath;
    private final Class aClass;

    public ResourceAccessor(Class aClass) {
        this.resourcesPath = DEFAULT_RESOURCES_PATH;
        this.aClass = aClass;
    }

    public <T> List<T> readObjectsFromFilePath(Class<T> type, String path) {
        return Utils.readObjectsFromFilePath(type, getPath(path));
    }

    public <T> List<T> readObjectsFromFilePath(Class<T> type, String... paths) {
        return Utils.readObjectsFromFilePath(type, getPath(paths));
    }

    public <T> T readObjectFromFilePath(Class<T> type, String path) {
        System.out.println(getPath(path));
        return Utils.readObjectFromFilePath(type, getPath(path));
    }

    public <T> T readObjectFromFilePath(Type type, String path) {
        return Utils.readObjectFromFilePath(type, getPath(path));
    }

    public <T> T readObjectFromFilePath(Class<T> type, String... paths) {
        return Utils.readObjectFromFilePath(type, getPath(paths));
    }

    public <T> T readObjectFromFilePath(Type type, String... paths) {
        return Utils.readObjectFromFilePath(type, getPath(paths));
    }

    public <T> T deserializeObjectFromBytesByFilePath(Class<T> type, String path) {
        return Utils.deserializeObjectFromBytesByFilePath(type, getPath(path));
    }

    public byte[] readBytesFromFilePath(String path) {
        return Utils.readBytesFromFile(getPath(path));
    }

    public byte[] readBytesFromFilePath(String... paths) {
        return Utils.readBytesFromFile(getPath(paths));
    }

    public String getFilePath(String... path) {
        return getFile(path).getPath();
    }

    public File getFile(String... path) {
        return getPath(path).toFile();
    }

    public Path getPath(String... path) {
        return Paths.get(getRootFilePath(), path);
    }

    public String getRootFilePath() {
        return getRootFile().getPath();
    }

    public File getRootFile() {
        return getRootPath().toFile();
    }

    public Path getRootPath() {
        String[] allSegments = aClass.getName()
                .split("[.]");
        return Paths.get(resourcesPath, allSegments);
    }
}
