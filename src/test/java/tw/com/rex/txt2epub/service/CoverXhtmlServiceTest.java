package tw.com.rex.txt2epub.service;

import org.junit.After;
import org.junit.Test;
import tw.com.rex.txt2epub.model.ConvertInfo;
import tw.com.rex.txt2epub.utils.TestUtil;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.Assert.assertTrue;

public class CoverXhtmlServiceTest {

    private final ConvertInfo convertInfo = TestUtil.createConvertInfo();

    @Test
    public void generate() {
        IntStream.range(0, convertInfo.getTempDirectories().length)
                 .forEach(i -> new CoverXhtmlService(convertInfo, i).generate());

        getCoverPaths().forEach(p -> assertTrue(Files.exists(p)));
    }

    private List<Path> getCoverPaths() {
        return Arrays.stream(convertInfo.getBooks())
                     .map(b -> Paths.get(System.getProperty("java.io.tmpdir"), b.getName())
                                    .resolve("item")
                                    .resolve("image")
                                    .resolve(b.getCover().getFileName()))
                     .collect(Collectors.toList());
    }

    @After
    public void cleanUp() {
        TestUtil.cleanUp(convertInfo);
    }

}