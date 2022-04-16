package tw.com.rex.txt2epub.service;

import tw.com.rex.txt2epub.model.Container;
import tw.com.rex.txt2epub.utils.XmlUtil;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class EpubService {

    private final Path directoryPath = Paths.get(System.getProperty("java.io.tmpdir"), "曹賊");

    public void process() {
        Path filePath = Paths.get(directoryPath.toAbsolutePath().toString(), "曹賊.epub");
        createBasicFiles();
        if (!Files.exists(filePath)) {
            try {
                Files.createDirectories(filePath);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void createBasicFiles() {
        createContainerXml();
        createMineType();
    }

    private void createContainerXml() {
        Path containerXmlPath = Paths.get(directoryPath.toAbsolutePath().toString(), "META-INF", "container.xml");
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
        Path path = Paths.get(directoryPath.toAbsolutePath().toString(), "mimetype");
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
