package tw.com.rex.txt2epub.model;

import lombok.Getter;

import java.nio.file.Path;

@Getter
public class ConvertInfo {

    private final Book book;
    private final Path output;
    private final TempDirectory tempDirectory;

    public ConvertInfo(Book book, Path output) {
        this.book = book;
        this.output = output;
        tempDirectory = new TempDirectory(book.getName());
    }

}
