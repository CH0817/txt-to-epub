package tw.com.rex.txt2epub.css;

import org.apache.commons.lang3.StringUtils;
import tw.com.rex.txt2epub.model.Style;

public class VerticalStyle extends Style {

    public VerticalStyle() {
        this.typesettingClass = "vrtl";
        this.contentH2Class = "gfont p-top-2em color-dimgray";
        this.contentFirstParagraphClass = "m-right-5em";
        this.contentParagraphClass = StringUtils.EMPTY;
        this.tocParagraphClass = "m-top-2em";
        this.mainStyleFolder = "vertical";
    }

}
