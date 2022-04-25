package tw.com.rex.txt2epub.model;

import lombok.Getter;

import java.nio.file.Path;

@Getter
public class ConvertInfo {

    private final Book book;
    private final Path output;
    private final TempDirectory tempDirectory;
    private final Style style;

    public ConvertInfo(Book book, Path output, Style style) {
        this.book = book;
        this.output = output;
        this.style = style;
        tempDirectory = new TempDirectory(book.getName());
    }

}
