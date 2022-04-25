package tw.com.rex.txt2epub.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@AllArgsConstructor
@Data
public class TxtContent implements Serializable {

    // 章節名稱
    private String title;
    // 章節內容
    private List<String> contentList;
    // 檔案名稱
    private String xhtmlName;

}
