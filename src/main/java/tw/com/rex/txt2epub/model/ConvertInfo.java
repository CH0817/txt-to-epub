package tw.com.rex.txt2epub.model;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

import lombok.Getter;
import tw.com.rex.txt2epub.creator.BookCreator;
import tw.com.rex.txt2epub.model.css.DisplayStyle;
import tw.com.rex.txt2epub.view.EpubConvertView;

@Getter
public class ConvertInfo {

    private final Book[] books;
    private final Path output;
    private final TempDirectory[] tempDirectories;
    private final DisplayStyle style;

    public ConvertInfo(EpubConvertView view) {
        this.books = BookCreator.create(view);
        this.output = Paths.get(view.getOutputPath());
        this.style = view.getDisplayStyle();
        this.tempDirectories = createTempDirectories();
    }

    private TempDirectory[] createTempDirectories() {
        return Stream.of(books)
                .map(book -> new TempDirectory(book.getName()))
                .toArray(TempDirectory[]::new);
    }

}
