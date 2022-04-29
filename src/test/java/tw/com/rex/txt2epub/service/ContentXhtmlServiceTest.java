package tw.com.rex.txt2epub.service;

import org.junit.After;
import org.junit.Test;
import tw.com.rex.txt2epub.model.Book;
import tw.com.rex.txt2epub.model.ConvertInfo;
import tw.com.rex.txt2epub.model.TempDirectory;
import tw.com.rex.txt2epub.utils.TestUtil;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.junit.Assert.assertTrue;

public class ContentXhtmlServiceTest {

    private final ConvertInfo convertInfo = TestUtil.createConvertInfo();

    @Test
    public void generate() {
        IntStream.range(0, convertInfo.getTempDirectories().length)
                 .forEach(i -> new ContentXhtmlService(convertInfo, i).generate());

        IntStream.range(0, convertInfo.getBooks().length)
                 .mapToObj(this::getAllPathStream)
                 .flatMap(s -> s)
                 .forEach(p -> assertTrue(Files.exists(p)));
    }

    private Stream<Path> getAllPathStream(int index) {
        Book book = convertInfo.getBooks()[index];
        TempDirectory tempDirectory = convertInfo.getTempDirectories()[index];
        return getPathStream(book, tempDirectory);
    }

    private Stream<Path> getPathStream(Book book, TempDirectory tempDirectory) {
        return book.getTxtContentList().stream().map(c -> tempDirectory.getXhtmlPath().resolve(c.getXhtmlName()));
    }

    @After
    public void cleanUp() {
        TestUtil.cleanUp(convertInfo);
    }

}