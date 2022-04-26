package tw.com.rex.txt2epub.factory;

import tw.com.rex.txt2epub.model.css.HorizontalStyle;
import tw.com.rex.txt2epub.model.css.VerticalStyle;
import tw.com.rex.txt2epub.define.TypesettingEnum;
import tw.com.rex.txt2epub.model.css.Style;

public class StyleFactory {

    public static Style getStyle(String typesetting) {
        TypesettingEnum typesettingEnum = TypesettingEnum.valueOf(typesetting);
        switch (typesettingEnum) {
            case VERTICAL:
                return new VerticalStyle();
            case HORIZONTAL:
                return new HorizontalStyle();
            default:
                throw new RuntimeException("get style from " + typesetting + " failure!");
        }
    }

}
