package com.example.sweater.testutils;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

public final class Utils {

    public static <T> List<T> readObjectsFromFilePath(Class<T> type, Path path) {
        try {
            byte[] bytes = readBytesFromFile(path);
            ObjectMapper mapper = new ObjectMapper();
            JavaType javaType = mapper.getTypeFactory().constructCollectionType(List.class, type);
            return mapper.readValue(bytes, javaType);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T readObjectFromFilePath(Class<T> type, Path path) {
        try {
            byte[] bytes = readBytesFromFile(path);
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(bytes, type);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T readObjectFromFilePath(Type type, Path path) {
        try {
            byte[] bytes = readBytesFromFile(path);
            ObjectMapper mapper = new ObjectMapper();
            JavaType javaType = mapper.getTypeFactory().constructType(type);
            return mapper.readValue(bytes, javaType);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static byte[] readBytesFromFile(Path path) {
        try {
            return Files.readAllBytes(path);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void assertObjectsSerializedToBytes(Object expected, Object actual) {
        byte[] expectedSerializedBytes = serializeObjectToBytes(expected);
        byte[] actualSerializedBytes = serializeObjectToBytes(actual);
        assertArrayEquals(expectedSerializedBytes, actualSerializedBytes);
    }

    public static byte[] serializeObjectToBytes(Object object) {
        ByteArrayOutputStream arrayStream = new ByteArrayOutputStream();
        try (ObjectOutputStream objectStream = new ObjectOutputStream(arrayStream)) {
            objectStream.writeObject(object);
            return arrayStream.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T deserializeObjectFromBytesByFilePath(Class<T> type, Path path) {
        try {
            byte[] bytes = readBytesFromFile(path);
            return deserializeObjectFromBytes(type, bytes);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T deserializeObjectFromBytes(Class<T> type, byte[] bytes) {
        ByteArrayInputStream arrayStream = new ByteArrayInputStream(bytes);
        try (ObjectInputStream objectStream = new ObjectInputStream(arrayStream)) {
            return (T) objectStream.readObject();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
