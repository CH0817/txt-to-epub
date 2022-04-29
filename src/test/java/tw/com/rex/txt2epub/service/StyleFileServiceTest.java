package tw.com.rex.txt2epub.service;

import org.junit.After;
import org.junit.Test;
import tw.com.rex.txt2epub.model.ConvertInfo;
import tw.com.rex.txt2epub.model.TempDirectory;
import tw.com.rex.txt2epub.utils.TestUtil;

import java.nio.file.Files;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.junit.Assert.assertTrue;

public class StyleFileServiceTest {

    private final ConvertInfo convertInfo = TestUtil.createConvertInfo();

    @Test
    public void testCopy() {
        String[] cssNames = {"book-style.css", "style-advance.css", "style-check.css", "style-reset.css", "style-standard.css"};

        IntStream.range(0, convertInfo.getTempDirectories().length)
                 .forEach(i -> new StyleFileService(convertInfo, i).copy());

        Stream.of(convertInfo.getTempDirectories())
              .map(TempDirectory::getStylePath)
              .flatMap(p -> Stream.of(cssNames).map(p::resolve))
              .forEach(p -> assertTrue(Files.exists(p)));
    }

    @After
    public void cleanUp() {
        TestUtil.cleanUp(convertInfo);
    }

}