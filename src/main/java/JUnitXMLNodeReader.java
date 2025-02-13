import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;

public class JUnitXMLNodeReader {
    public static void main(String[] args) {
        try {
            ObjectMapper mapper = new ObjectMapper();

            File xmlFile = getFileFromResources("example.xml");
            File xsdFile = getFileFromResources("schema.xsd");

            validateXMLSchema(xsdFile, xmlFile);

            XmlMapper xmlMapper = new XmlMapper();
            JsonNode rootNode = xmlMapper.readTree(xmlFile);

            System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(rootNode));
        } catch (IOException | URISyntaxException | SAXException e) {
            e.printStackTrace();
        }
    }

    private static File getFileFromResources(String fileName) throws URISyntaxException {
        ClassLoader classLoader = JUnitXmlReader.class.getClassLoader();
        URL resource = classLoader.getResource(fileName);
        if (resource == null) {
            throw new IllegalArgumentException("Plik nie znaleziony: " + fileName);
        } else {
            return Paths.get(resource.toURI()).toFile();
        }
    }

    private static void validateXMLSchema(File xsdFile, File xmlFile) throws SAXException, IOException {
        SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        Schema schema = factory.newSchema(xsdFile);
        Validator validator = schema.newValidator();
        Source source = new StreamSource(xmlFile);
        validator.validate(source);
        System.out.println("XML validation successful.");
    }
}
