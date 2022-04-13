package tw.com.rex.txt2epub.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TxtHandlerServiceTest {

    @DisplayName("txt 內容轉換成 TxtContent")
    @Test
    public void getContents() {
        Path filePath = Paths.get("src/test/resources/曹賊.txt");
        TxtHandlerService service = new TxtHandlerService(filePath.toAbsolutePath().toString());
        assertEquals(751, service.getTxtContentList().size());
    }

}
