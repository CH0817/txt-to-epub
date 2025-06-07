package tw.com.rex.txt2epub.model;

import lombok.Getter;
import tw.com.rex.txt2epub.panel.MainPanel;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;

@Getter
public class Book {

    private final String name;
    private final String author;
    private final String publisher;
    private final List<TxtContent> txtContentList;
    private final Path cover;

    public Book(MainPanel panel, String name, List<TxtContent> txtContentList) {
        this.name = name;
        this.cover = Paths.get(panel.getCoverPath());
        this.author = panel.getAuthorField().getText();
        this.publisher = panel.getPublishingHouseField().getText();
        this.txtContentList = txtContentList;
    }

    public boolean hasCover() {
        return Objects.nonNull(cover) && Files.exists(cover) && Files.isRegularFile(cover);
    }

}
