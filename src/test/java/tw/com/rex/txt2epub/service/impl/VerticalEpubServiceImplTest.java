package tw.com.rex.txt2epub.service.impl;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import tw.com.rex.txt2epub.model.Book;
import tw.com.rex.txt2epub.model.ConvertInfo;
import tw.com.rex.txt2epub.service.EpubService;
import tw.com.rex.txt2epub.service.TxtHandlerService;
import tw.com.rex.txt2epub.utils.FileUtil;

import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.Assert.assertEquals;

public class VerticalEpubServiceImplTest {

    private ConvertInfo convertInfo;
    private final Path outputPath = Paths.get("D:/Temp/曹賊");

    @Before
    public void setUp() {
        Book book = new Book();
        book.setName("曹賊");
        book.setAuthor("庚新");
        book.setPublisher("典藏閣");
        TxtHandlerService txtHandlerService = new TxtHandlerService(Paths.get("src/test/resources/曹賊.txt")
                                                                         .toAbsolutePath()
                                                                         .toString());
        book.setTxtContentList(txtHandlerService.getTxtContentList());
        book.setCover(Paths.get("src/test/resources/cover.jpg"));

        convertInfo = new ConvertInfo(book, outputPath);
    }

    @Test
    public void process() throws Exception {
        EpubService service = new VerticalEpubServiceImpl(convertInfo);
        assertEquals(outputPath.resolve("曹賊.epub").toAbsolutePath().toString(), service.process());
    }

    @After
    public void cleanUp() {
        FileUtil.deleteAll(outputPath);
    }

}
