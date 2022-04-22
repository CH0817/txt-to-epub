package tw.com.rex.txt2epub.model;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.nio.file.Path;
import java.util.List;

@Data
public class Book implements Serializable {

    private final String UNKNOWN = "未知";

    private String name;
    private String author;
    private String publisher;
    private List<TxtContent> txtContentList;
    private Path cover;

    public String getAuthor() {
        return StringUtils.isNotBlank(author) ? author : UNKNOWN;
    }

    public String getPublisher() {
        return StringUtils.isNotBlank(publisher) ? publisher : UNKNOWN;
    }

}
