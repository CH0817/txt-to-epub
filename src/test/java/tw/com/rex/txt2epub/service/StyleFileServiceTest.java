package tw.com.rex.txt2epub.service;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import tw.com.rex.txt2epub.model.ConvertInfo;
import tw.com.rex.txt2epub.model.TempDirectory;
import tw.com.rex.txt2epub.utils.TestUtil;

import java.nio.file.Path;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.io.FileMatchers.anExistingFileOrDirectory;

public class StyleFileServiceTest {

    private static final ConvertInfo convertInfo = TestUtil.createConvertInfo();

    @Test
    public void testCopy() {
        String[] cssNames = {"book-style.css", "style-advance.css", "style-check.css", "style-reset.css", "style-standard.css"};

        IntStream.range(0, convertInfo.getTempDirectories().length)
                .forEach(i -> new StyleFileService(convertInfo, i).copy());

        Stream.of(convertInfo.getTempDirectories())
                .map(TempDirectory::getStylePath)
                .flatMap(p -> Stream.of(cssNames).map(p::resolve))
                .map(Path::toFile)
                .forEach(f -> assertThat(f, anExistingFileOrDirectory()));
    }

    @AfterAll
    public static void cleanUp() {
        TestUtil.cleanUp(convertInfo);
    }

}