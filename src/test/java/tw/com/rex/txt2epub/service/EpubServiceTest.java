package tw.com.rex.txt2epub.service;

import org.junit.Before;
import org.junit.Test;
import tw.com.rex.txt2epub.model.Book;

import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.Assert.assertEquals;

public class EpubServiceTest {

    private Book book;
    private final Path outputPath = Paths.get("D:/Temp/曹賊");

    @Before
    public void setUp() {
        book = new Book();
        book.setName("曹賊");
        book.setAuthor("庚新");
        TxtHandlerService txtHandlerService = new TxtHandlerService(Paths.get("src/test/resources/曹賊.txt")
                                                                         .toAbsolutePath()
                                                                         .toString());
        book.setTxtContentList(txtHandlerService.getTxtContentList());
        book.setCover(Paths.get("src/test/resources/cover.jpeg"));
    }

    @Test
    public void process() throws Exception {
        EpubService service = new EpubService(book, outputPath);
        assertEquals(outputPath.resolve("曹賊.epub").toAbsolutePath().toString(), service.process());
    }

}
