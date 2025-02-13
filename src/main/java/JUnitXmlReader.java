import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;

public class JUnitXmlReader {
    public static void main(String[] args) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            XmlMapper xmlMapper = new XmlMapper();
            File file = getFileFromResources("example.xml");
            TestSuite testSuite = xmlMapper.readValue(file, TestSuite.class);
            System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(testSuite));
        } catch (IOException | URISyntaxException e) {
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
}
