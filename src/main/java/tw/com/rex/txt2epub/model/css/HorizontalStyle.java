package tw.com.rex.txt2epub.model.css;

import org.apache.commons.lang3.StringUtils;

public class HorizontalStyle extends Style {

    public HorizontalStyle() {
        this.typesettingClass = "hltr";
        this.contentH2Class = "gfont align-center m-top-3em";
        this.contentFirstParagraphClass = "m-top-2em indent-2em";
        this.contentParagraphClass = StringUtils.SPACE + "class=\"indent-2em\"";
        this.tocParagraphClass = StringUtils.EMPTY;
        this.mainStyleFolder = "horizontal";
    }

}
