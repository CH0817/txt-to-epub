package tw.com.rex.txt2epub.model;

import lombok.Data;

import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.util.List;

@Data
@XmlRootElement(name = "container")
@XmlAccessorType(XmlAccessType.FIELD)
public class Container implements Serializable {

    @XmlAttribute
    private String version = "1.0";
    @XmlAttribute
    private String xmlns = "urn:oasis:names:tc:opendocument:xmlns:container";
    @XmlElement(name = "rootfiles")
    private List<RootFile> rootFiles;

}
