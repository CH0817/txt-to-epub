package tw.com.rex.txt2epub.utils;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

import org.apache.commons.lang3.StringUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FileUtil {

    private static Charset[] charsets = { StandardCharsets.UTF_8,
            Charset.forName("Big5"),
            Charset.forName("GBK"),
            StandardCharsets.UTF_16 };

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
                handlerIOException(e, path.toAbsolutePath() + " create directory failure!");
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
            handlerIOException(e, "write to " + path.toAbsolutePath() + " failure!");
        }
    }

    public static void copy(Path source, Path target) {
        assert Objects.nonNull(source);
        assert Objects.nonNull(target);
        try {
            Files.copy(source, target, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            handlerIOException(e, "copy " + source.toAbsolutePath() + " to " + target.toAbsolutePath() + " failure!");
        }
    }

    public static void copy(InputStream input, Path target) {
        assert Objects.nonNull(input);
        assert Objects.nonNull(target);
        try (OutputStream output = new FileOutputStream(target.toString())) {
            input.transferTo(output);
            output.flush();
        } catch (IOException e) {
            handlerIOException(e, "copy InputStream to " + target.toAbsolutePath() + " failure!");
        }
    }

    public static void copyAll(Path source, Path target) {
        assert Objects.nonNull(source);
        assert Objects.nonNull(target);
        try (Stream<Path> walk = Files.walk(source)) {
            walk.filter(Files::isRegularFile)
                    .forEach(p -> copy(p, target.resolve(p.getFileName())));
        } catch (IOException e) {
            handlerIOException(e,
                    "copy files in " + source.toAbsolutePath() + " to " + target.toAbsolutePath() + " failure!");
        }
    }

    public static void move(Path source, Path target) {
        assert Objects.nonNull(source);
        assert Objects.nonNull(target);
        try {
            createDirectory(target.getParent());
            Files.move(source, target, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            handlerIOException(e,
                    "move file in " + source.toAbsolutePath() + " to " + target.toAbsolutePath() + " failure!");
        }
    }

    public static void deleteAll(Path path) {
        assert Objects.nonNull(path);
        if (Files.exists(path)) {
            try (Stream<Path> pathStream = Files.walk(path)) {
                pathStream.sorted(Comparator.reverseOrder()).forEach(FileUtil::delete);
            } catch (IOException e) {
                handlerIOException(e, "remove files in " + path.toAbsolutePath() + " failure!");
            }
        }
    }

    public static void delete(Path path) {
        assert Objects.nonNull(path);
        try {
            Files.deleteIfExists(path);
        } catch (IOException e) {
            handlerIOException(e, "remove file " + path.toAbsolutePath() + " failure!");
        }
    }

    public static String readTxtString(String path) {
        for (Charset charset : charsets) {
            try {
                return Files.readString(Paths.get(path), charset);
            } catch (IOException e) {
                log.warn("{} 編碼取 txt 內容失敗", charset);
            }
        }

        throw new RuntimeException("讀取 txt 內容失敗");
    }

    public static List<String> readTxtLines(String path) {
        for (Charset charset : charsets) {
            try {
                return Files.readAllLines(Paths.get(path), charset);
            } catch (IOException e) {
                log.warn("{} 編碼取 txt 內容失敗", charset);
            }
        }

        throw new RuntimeException("讀取 txt 內容失敗");
    }

    private static void handlerIOException(Exception e, String message) {
        e.printStackTrace();
        throw new RuntimeException(message);
    }

}
