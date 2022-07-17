package tw.com.rex.txt2epub.service;

import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.nio.file.Paths;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class TxtHandlerServiceTest {

    @Test
    public void getContents() {
        Path filePath = Paths.get("src/test/resources/曹賊.txt");
        TxtHandlerService service = new TxtHandlerService(filePath.toAbsolutePath().toString());
        assertThat(368, equalTo(service.getTxtContentList().size()));
    }

}
