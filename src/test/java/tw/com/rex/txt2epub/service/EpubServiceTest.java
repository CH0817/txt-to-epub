package tw.com.rex.txt2epub.service;

import org.junit.AfterClass;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.stream.Stream;

import static org.junit.Assert.assertTrue;

public class EpubServiceTest {

    private final static Path directoryPath = Paths.get(System.getProperty("java.io.tmpdir"), "曹賊");

    @Test
    public void process() {
        Path coverPath = Paths.get("src/test/resources/cover.jpeg");
        EpubService service = new EpubService(coverPath.toAbsolutePath().toString());
        Path metaInfPath = directoryPath.resolve("META-INF").resolve("container.xml");
        Path mineTypePath = directoryPath.resolve("mimetype");
        Path filePath = directoryPath.resolve("曹賊.epub");
        Path coverImage = directoryPath.resolve("item").resolve("image");
        service.process();
        assertTrue(Files.exists(metaInfPath));
        assertTrue(Files.exists(mineTypePath));
        assertTrue(Files.exists(filePath));
        assertTrue(Files.exists(coverImage));
    }

    @AfterClass
    public static void deleteTempFile() throws IOException {
        if (Files.exists(directoryPath)) {
            try (Stream<Path> walk = Files.walk(directoryPath)) {
                walk.sorted(Comparator.reverseOrder())
                    .map(Path::toFile)
                    .forEach(File::delete);
            }
        }
    }

}
