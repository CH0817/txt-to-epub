package tw.com.rex.txt2epub.model.css;

import lombok.Getter;

import java.io.Serializable;

@Getter
public class Style implements Serializable {

    protected String typesettingClass;
    protected String contentH2Class;
    protected String contentFirstParagraphClass;
    protected String contentParagraphClass;
    protected String tocParagraphClass;
    protected String mainStyleFolder;

}
