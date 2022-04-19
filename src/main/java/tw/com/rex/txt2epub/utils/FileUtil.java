package tw.com.rex.txt2epub.utils;

import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Stream;

public class FileUtil {

    public static void createDirectories(Path... paths) {
        assert Objects.nonNull(paths) && paths.length > 0;
        Arrays.stream(paths).forEach(FileUtil::createDirectory);
    }

    public static void createDirectory(Path path) {
        assert Objects.nonNull(path);
        if (Files.notExists(path)) {
            try {
                Files.createDirectories(path);
            } catch (IOException e) {
                printStackTrace(e);
                throwRuntimeException(path.toAbsolutePath() + " create directory failure!");
            }
        }
    }

    public static void write(Path path, String content) {
        assert Objects.nonNull(path);
        assert StringUtils.isNotBlank(content);
        try {
            Files.write(path, content.getBytes(StandardCharsets.UTF_8), StandardOpenOption.CREATE,
                        StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            printStackTrace(e);
            throwRuntimeException("write to " + path.toAbsolutePath() + " failure!");
        }
    }

    public static void copy(Path source, Path target) {
        assert Objects.nonNull(source);
        assert Objects.nonNull(target);
        try {
            Files.copy(source, target, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            printStackTrace(e);
            throwRuntimeException("copy " + source.toAbsolutePath() + " to " + target.toAbsolutePath() + " failure!");
        }
    }

    public static void copyAll(Path source, Path target) {
        assert Objects.nonNull(source);
        assert Objects.nonNull(target);
        try (Stream<Path> walk = Files.walk(source)) {
            walk.filter(Files::isRegularFile)
                .forEach(p -> copy(p, target.resolve(p.getFileName())));
        } catch (IOException e) {
            printStackTrace(e);
            throwRuntimeException("copy files in " + source.toAbsolutePath() + " to " + target.toAbsolutePath() + " failure!");
        }
    }

    public static void move(Path source, Path target) {
        assert Objects.nonNull(source);
        assert Objects.nonNull(target);
        try {
            createDirectory(target.getParent());
            Files.move(source, target, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            printStackTrace(e);
            throwRuntimeException("move file in " + source.toAbsolutePath() + " to " + target.toAbsolutePath() + " failure!");
        }
    }

    private static void printStackTrace(Exception e) {
        e.printStackTrace();
    }

    private static void throwRuntimeException(String message) {
        throw new RuntimeException(message);
    }

}
