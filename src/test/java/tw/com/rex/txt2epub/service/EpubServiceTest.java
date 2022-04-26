package tw.com.rex.txt2epub.service;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import tw.com.rex.txt2epub.define.TypesettingEnum;
import tw.com.rex.txt2epub.factory.StyleFactory;
import tw.com.rex.txt2epub.model.Book;
import tw.com.rex.txt2epub.model.ConvertInfo;
import tw.com.rex.txt2epub.utils.FileUtil;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.Assert.assertTrue;

public class EpubServiceTest {

    private ConvertInfo horizontalConvertInfo;
    private ConvertInfo verticalConvertInfo;
    private static final Path outputPath = Paths.get("D:/Temp/曹賊");
    private final String FINAL_FILE_NAME = "曹賊.epub";

    @Before
    public void setUp() {
        Book book = getBook();
        horizontalConvertInfo = new ConvertInfo(book,
                                                outputPath,
                                                StyleFactory.getStyle(TypesettingEnum.HORIZONTAL.toString()));
        verticalConvertInfo = new ConvertInfo(book,
                                              outputPath,
                                              StyleFactory.getStyle(TypesettingEnum.VERTICAL.toString()));
    }

    private static Book getBook() {
        Book book = new Book();
        book.setName("曹賊");
        book.setAuthor("庚新");
        book.setPublisher("典藏閣");
        TxtHandlerService txtHandlerService = new TxtHandlerService(Paths.get("src/test/resources/曹賊.txt")
                                                                         .toAbsolutePath()
                                                                         .toString());
        book.setTxtContentList(txtHandlerService.getTxtContentList());
        book.setCover(Paths.get("src/test/resources/cover.jpg"));
        return book;
    }

    @Test
    public void horizontalProcess() throws Exception {
        new EpubService(horizontalConvertInfo).process();
        assertTrue(Files.exists(outputPath.resolve(FINAL_FILE_NAME)));
    }

    @Test
    public void verticalProcess() throws Exception {
        new EpubService(verticalConvertInfo).process();
        assertTrue(Files.exists(outputPath.resolve(FINAL_FILE_NAME)));
    }

    @Test
    public void processNoCover() throws Exception {
        horizontalConvertInfo.getBook().setCover(Path.of(""));
        new EpubService(horizontalConvertInfo).process();
        assertTrue(Files.exists(outputPath.resolve(FINAL_FILE_NAME)));
    }

    @After
    public void cleanUp() {
        FileUtil.deleteAll(outputPath);
    }

}
