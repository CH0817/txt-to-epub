package tw.com.rex.txt2epub.service;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import tw.com.rex.txt2epub.model.Book;
import tw.com.rex.txt2epub.model.ConvertInfo;
import tw.com.rex.txt2epub.model.TempDirectory;
import tw.com.rex.txt2epub.utils.TestUtil;

import java.nio.file.Path;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.io.FileMatchers.anExistingFileOrDirectory;

public class ContentXhtmlServiceTest {

    private final static ConvertInfo convertInfo = TestUtil.createConvertInfo();

    @Test
    public void generate() {
        IntStream.range(0, convertInfo.getTempDirectories().length)
                .forEach(i -> new ContentXhtmlService(convertInfo, i).generate());

        IntStream.range(0, convertInfo.getBooks().length)
                .mapToObj(this::getAllPathStream)
                .flatMap(s -> s)
                .map(Path::toFile)
                .forEach(f -> assertThat(f, anExistingFileOrDirectory()));
    }

    private Stream<Path> getAllPathStream(int index) {
        Book book = convertInfo.getBooks()[index];
        TempDirectory tempDirectory = convertInfo.getTempDirectories()[index];
        return getPathStream(book, tempDirectory);
    }

    private Stream<Path> getPathStream(Book book, TempDirectory tempDirectory) {
        return book.getTxtContentList().stream().map(c -> tempDirectory.getXhtmlPath().resolve(c.getXhtmlName()));
    }

    @AfterAll
    public static void cleanUp() {
        TestUtil.cleanUp(convertInfo);
    }

}