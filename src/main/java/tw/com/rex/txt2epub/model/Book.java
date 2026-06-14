package tw.com.rex.txt2epub.model;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;

import lombok.Getter;
import tw.com.rex.txt2epub.view.EpubConvertView;

@Getter
public class Book {

    private final String name;
    private final String author;
    private final String publisher;
    private final List<TxtContent> txtContentList;
    private final Path cover;

    public Book(EpubConvertView view, String name, List<TxtContent> txtContentList) {
        this.name = name;
        this.cover = Paths.get(view.getCoverPath());
        this.author = view.getAuthor();
        this.publisher = view.getPublisher();
        this.txtContentList = txtContentList;
    }

    public boolean hasCover() {
        return Objects.nonNull(cover) && Files.exists(cover) && Files.isRegularFile(cover);
    }

}
