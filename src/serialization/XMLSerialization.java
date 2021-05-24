package serialization;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import model.hierarchy.*;
import model.hierarchy.cavalry.CavalrySpearman;

import java.util.Arrays;
import java.util.LinkedList;

public class XMLSerialization {
    private static XMLSerialization xmlSerialization;

    private XMLSerialization() {
    }

    public static XMLSerialization getInstance() {
        if (xmlSerialization == null) {
            xmlSerialization = new XMLSerialization();
        }
        return xmlSerialization;
    }

    public String serializeToString(LinkedList<CavalrySpearman> list) {
        CavalrySpearman[] arr = new CavalrySpearman[list.size()];
        Arrays.setAll(arr, list::get);
        XmlMapper xmlMapper = new XmlMapper();
        try {
            return xmlMapper.writeValueAsString(arr);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
