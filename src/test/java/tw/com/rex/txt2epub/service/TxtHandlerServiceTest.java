package tw.com.rex.txt2epub.service;

import org.junit.Test;

import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.Assert.assertEquals;

public class TxtHandlerServiceTest {

    @Test
    public void getContents() {
        Path filePath = Paths.get("src/test/resources/曹賊.txt");
        TxtHandlerService service = new TxtHandlerService(filePath.toAbsolutePath().toString());
        assertEquals(368, service.getTxtContentList().size());
    }

}
