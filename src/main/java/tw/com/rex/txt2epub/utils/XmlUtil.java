package tw.com.rex.txt2epub.utils;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;

public class XmlUtil {

    public static void convertToXmlFile(Object obj, Path outputPath) {
        try {
            StringWriter writer = new StringWriter();
            JAXBContext jaxbContext = JAXBContext.newInstance(obj.getClass());
            Marshaller marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            marshaller.setProperty(Marshaller.JAXB_ENCODING, StandardCharsets.UTF_8.toString());
            marshaller.marshal(obj, writer);
            FileUtil.write(outputPath, writer.toString());
        } catch (JAXBException e) {
            e.printStackTrace();
            throw new RuntimeException("covert " + obj + " to xml file failure!");
        }
    }

}
