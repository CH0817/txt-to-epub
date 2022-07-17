package tw.com.rex.txt2epub.service;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import tw.com.rex.txt2epub.model.ConvertInfo;
import tw.com.rex.txt2epub.utils.TestUtil;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.io.FileMatchers.anExistingFileOrDirectory;

public class CoverXhtmlServiceTest {

    private static final ConvertInfo convertInfo = TestUtil.createConvertInfo();

    @Test
    public void generate() {
        IntStream.range(0, convertInfo.getTempDirectories().length)
                 .forEach(i -> new CoverXhtmlService(convertInfo, i).generate());

        getCoverPaths().stream().map(Path::toFile).forEach(f -> assertThat(f, anExistingFileOrDirectory()));
    }

    private List<Path> getCoverPaths() {
        return Arrays.stream(convertInfo.getBooks())
                     .map(b -> Paths.get(System.getProperty("java.io.tmpdir"), b.getName())
                                    .resolve("item")
                                    .resolve("image")
                                    .resolve(b.getCover().getFileName()))
                     .collect(Collectors.toList());
    }

    @AfterAll
    public static void cleanUp() {
        TestUtil.cleanUp(convertInfo);
    }

}