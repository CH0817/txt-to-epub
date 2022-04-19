package tw.com.rex.txt2epub.model;

import lombok.Data;

import java.io.File;
import java.io.Serializable;
import java.nio.file.Path;
import java.util.List;

@Data
public class Book implements Serializable {

    // 書名
    private String name;
    // 作者
    private String author;
    // 內文
    private List<TxtContent> txtContentList;
    // 封面
    private Path cover;

}
