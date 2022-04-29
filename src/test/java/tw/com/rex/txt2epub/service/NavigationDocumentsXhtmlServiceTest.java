package tw.com.rex.txt2epub.service;

import org.junit.After;
import org.junit.Test;
import tw.com.rex.txt2epub.model.ConvertInfo;
import tw.com.rex.txt2epub.utils.TestUtil;

import java.nio.file.Files;
import java.util.Arrays;
import java.util.stream.IntStream;

import static org.junit.Assert.assertTrue;

public class NavigationDocumentsXhtmlServiceTest {

    private final ConvertInfo convertInfo = TestUtil.createConvertInfo();

    @Test
    public void generate() {
        IntStream.range(0, convertInfo.getTempDirectories().length)
                 .forEach(i -> new NavigationDocumentsXhtmlService(convertInfo, i).generate());

        Arrays.stream(convertInfo.getTempDirectories())
              .map(tempDirectory -> tempDirectory.getItemPath().resolve("navigation-documents.xhtml"))
              .forEach(p -> assertTrue(Files.exists(p)));
    }

    @After
    public void cleanUp() {
        TestUtil.cleanUp(convertInfo);
    }

}