package tw.com.rex.txt2epub.service;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;
import tw.com.rex.txt2epub.model.Container;
import tw.com.rex.txt2epub.utils.XmlUtil;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@NoArgsConstructor
@AllArgsConstructor
public class EpubService {

    private String selectedCover;
    private final Path directoryPath = Paths.get(System.getProperty("java.io.tmpdir"), "曹賊");

    public void process() {
        createCover();
        copyCss();
        Path filePath = directoryPath.resolve("曹賊.epub");
        createBasicFiles();
        if (!Files.exists(filePath)) {
            try {
                Files.createDirectories(filePath);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @SneakyThrows
    private void copyCss() {
        Path style = directoryPath.resolve("item").resolve("style");
        if (!Files.exists(style)) {
            Files.createDirectories(style);
        }
        try (Stream<Path> walk = Files.walk(Paths.get("src/main/resources"))) {
            walk.filter(Files::isRegularFile)
                .forEach(p -> {
                    Path targetPath = style.resolve(p.getFileName());
                    try {
                        Files.copy(p, targetPath);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
        }
    }

    @SneakyThrows
    private void createCover() {
        if (StringUtils.isNotBlank(selectedCover)) {
            Path imageDirectoryPath = directoryPath.resolve("item").resolve("image");
            if (!Files.exists(imageDirectoryPath)) {
                Files.createDirectories(imageDirectoryPath);
            }
            Path path = Paths.get(selectedCover);
            Files.copy(path, imageDirectoryPath.resolve(path.getFileName()));
        }
    }

    private void createBasicFiles() {
        createContainerXml();
        createMineType();
    }

    private void createContainerXml() {
        Path containerXmlPath = directoryPath.resolve("META-INF").resolve("container.xml");
        if (!Files.exists(containerXmlPath.getParent())) {
            try {
                Files.createDirectories(containerXmlPath.getParent());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        if (!Files.exists(containerXmlPath)) {
            try {
                Files.createFile(containerXmlPath);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        XmlUtil.convertToXmlFile(new Container(), containerXmlPath);
    }

    private void createMineType() {
        Path path = directoryPath.resolve("mimetype");
        if (!Files.exists(path)) {
            try {
                Files.createFile(path);
                Files.write(path, "application/epub+zip".getBytes(StandardCharsets.UTF_8));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
