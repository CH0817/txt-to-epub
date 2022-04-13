package tw.com.rex.txt2epub.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@AllArgsConstructor
@Data
public class TxtContent implements Serializable {

    private String title;
    private List<String> contentList;

}
