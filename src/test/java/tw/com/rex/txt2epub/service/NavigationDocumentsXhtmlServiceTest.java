package tw.com.rex.txt2epub.service;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import tw.com.rex.txt2epub.model.ConvertInfo;
import tw.com.rex.txt2epub.utils.TestUtil;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.stream.IntStream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.io.FileMatchers.anExistingFileOrDirectory;

public class NavigationDocumentsXhtmlServiceTest {

    private static final ConvertInfo convertInfo = TestUtil.createConvertInfo();

    @Test
    public void generate() {
        IntStream.range(0, convertInfo.getTempDirectories().length)
                .forEach(i -> new NavigationDocumentsXhtmlService(convertInfo, i).generate());

        Arrays.stream(convertInfo.getTempDirectories())
                .map(tempDirectory -> tempDirectory.getItemPath().resolve("navigation-documents.xhtml"))
                .map(Path::toFile)
                .forEach(f -> assertThat(f, anExistingFileOrDirectory()));
    }

    @AfterAll
    public static void cleanUp() {
        TestUtil.cleanUp(convertInfo);
    }

}