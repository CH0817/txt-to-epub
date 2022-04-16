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

    private final EpubService service = new EpubService();
    private final static Path directoryPath = Paths.get(System.getProperty("java.io.tmpdir"), "曹賊");

    @Test
    public void process() {
        Path metaInfPath = Paths.get(directoryPath.toAbsolutePath().toString(), "META-INF", "container.xml");
        Path mineTypePath = Paths.get(directoryPath.toAbsolutePath().toString(), "mimetype");
        Path filePath = Paths.get(directoryPath.toAbsolutePath().toString(), "曹賊.epub");
        service.process();
        assertTrue(Files.exists(metaInfPath));
        assertTrue(Files.exists(mineTypePath));
        assertTrue(Files.exists(filePath));
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
