package tw.com.rex.txt2epub.model;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class CssClass implements Serializable {

    private String typesettingClass;
    private String contentH2Class;
    private String contentFirstParagraphClass;
    private String contentParagraphClass;
    private String tocParagraphClass;

}
