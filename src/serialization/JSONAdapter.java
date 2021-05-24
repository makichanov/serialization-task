package serialization;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import filejob.FileRW;
import model.hierarchy.*;
import model.hierarchy.cavalry.CavalrySpearman;

import java.io.*;
import java.util.Arrays;
import java.util.LinkedList;

public class JSONAdapter implements Serialization {
    private XMLSerialization adaptXML;
    private static JSONAdapter jsonAdapter;

    private JSONAdapter() {
        adaptXML = XMLSerialization.getInstance();
    }

    public static JSONAdapter getInstance() {
        if (jsonAdapter == null) {
            jsonAdapter = new JSONAdapter();
        }
        return jsonAdapter;
    }

    @Override
    public void serialize(File file, LinkedList<CavalrySpearman> list) {
        try {
            String temp = adaptXML.serializeToString(list);
            XmlMapper xmlMapper = new XmlMapper();
            CavalrySpearman[] spearmans = xmlMapper.readValue(temp, CavalrySpearman[].class);
            ObjectMapper objectMapper = new ObjectMapper();
            FileRW.getFileJob().write(file, objectMapper.writeValueAsString(spearmans));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public LinkedList<CavalrySpearman> deserialize(File file) {
        String readStrings = FileRW.getFileJob().read(file);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.USE_JAVA_ARRAY_FOR_JSON_ARRAY, true);
        CavalrySpearman[] arr = new CavalrySpearman[0];
        try {
            arr = objectMapper.reader().forType(CavalrySpearman[].class).readValue(readStrings);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new LinkedList<>(Arrays.asList(arr));
    }
}
