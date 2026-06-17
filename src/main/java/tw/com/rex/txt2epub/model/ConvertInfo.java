package tw.com.rex.txt2epub.model;

import lombok.Builder;
import lombok.Getter;
import tw.com.rex.txt2epub.model.css.DisplayStyle;

@Builder
@Getter
public class ConvertInfo {

    private String txtPath;
    private String outputPath;
    private String coverPath;
    private String author;
    private String publisher;
    private String chapterFinderType;
    private String chapterFinder;
    private DisplayStyle style;
    private boolean isConvertSimplified;

}
