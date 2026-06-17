package tw.com.rex.txt2epub.model;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;

import lombok.Getter;

@Getter
public class Book {

    private final String name;
    private final String author;
    private final String publisher;
    private final List<TxtContent> txtContentList;
    private final Path cover;

    public Book(ConvertInfo convertInfo, String name, List<TxtContent> txtContentList) {
        this.name = name;
        this.cover = Paths.get(convertInfo.getCoverPath());
        this.author = convertInfo.getAuthor();
        this.publisher = convertInfo.getPublisher();
        this.txtContentList = txtContentList;
    }

    public boolean hasCover() {
        return Objects.nonNull(cover) && Files.exists(cover) && Files.isRegularFile(cover);
    }

}
