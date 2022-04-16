package tw.com.rex.txt2epub.model;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import java.io.Serializable;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
public class RootFile implements Serializable {

    @XmlAttribute(name = "full-path")
    private String fullPath = "item/standard.opf";
    @XmlAttribute(name = "media-type")
    private String mediaType = "application/oebps-package+xml";

}
