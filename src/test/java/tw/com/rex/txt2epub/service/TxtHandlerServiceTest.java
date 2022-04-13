package tw.com.rex.txt2epub.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TxtHandlerServiceTest {

    private final TxtHandlerService service = new TxtHandlerService("D:/Rex/Downloads/曹賊.txt");

    @DisplayName("txt 內容轉換成 TxtContent")
    @Test
    public void getContents() {
        assertEquals(751, service.getTxtContentList().size());
    }

}
