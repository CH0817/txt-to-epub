package tw.com.rex.txt2epub.model;

import lombok.Getter;
import org.apache.commons.lang3.StringUtils;
import tw.com.rex.txt2epub.frame.MainFrame;
import tw.com.rex.txt2epub.service.TxtHandlerService;

import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;

@Getter
public class Book implements Serializable {

    private final String UNKNOWN = "未知";

    private final String name;
    private final String author;
    private final String publisher;
    private final List<TxtContent> txtContentList;
    private final Path cover;

    public Book(MainFrame frame) {
        this.name = getBookName(frame);
        this.cover = Paths.get(frame.getCoverPath().getText());
        this.author = frame.getAuthorField().getText();
        this.publisher = frame.getPublishingHouseField().getText();
        this.txtContentList = getTxtContentList(frame);
    }

    private String getBookName(MainFrame frame) {
        String fileName = Paths.get(frame.getSelectedTxtLabel().getText()).toAbsolutePath().getFileName().toString();
        return fileName.substring(0, fileName.lastIndexOf("."));
    }

    private List<TxtContent> getTxtContentList(MainFrame frame) {
        return new TxtHandlerService(frame.getSelectedTxtLabel().getText()).getTxtContentList();
    }

    public String getAuthor() {
        return StringUtils.isNotBlank(author) ? author : UNKNOWN;
    }

    public String getPublisher() {
        return StringUtils.isNotBlank(publisher) ? publisher : UNKNOWN;
    }

    public boolean hasCover() {
        return Objects.nonNull(cover) && Files.exists(cover) && Files.isRegularFile(cover);
    }

}
