import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class Main2 {
    public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException {

        List<Employee> list = parseXML("data.xml");
        String json = listToJson(list);
        writeString(json);

    }

    private static List<Employee> parseXML(String s) throws ParserConfigurationException, IOException, SAXException {

        List<Employee> employees = null;
        List<String> valueAttr = new ArrayList<>();
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(new File(s));

        Node root = doc.getDocumentElement();
        NodeList nodeList = root.getChildNodes();

        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node_ = nodeList.item(i);
            if (Node.ELEMENT_NODE == node_.getNodeType()) {
                Element element = (Element) node_;
                String textContent = element.getTextContent();
                valueAttr.add(textContent);
            } else {
                     continue;       //рекурсия
            }
            Object[] objects = valueAttr.toArray();
            Employee employee = new Employee((long)(objects[0]), (String)(objects[1]), (String)(objects[2]), (String)(objects[3]), (int)(objects[4]));
            employees.add(employee);
        }
        return employees;
    }

    private static <T> String listToJson(List<Employee> list) {
        Type listType = new TypeToken<List<T>>() {
        }.getType();
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();
        Gson gson = builder.create();
        String json = gson.toJson(list, listType);
        return json;
    }

    private static void writeString(String json) {
        try (FileWriter fileWriter = new FileWriter("data2.json")) {

            fileWriter.write(json);
            fileWriter.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
