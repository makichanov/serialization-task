package serialization;

import model.hierarchy.*;
import model.hierarchy.cavalry.CavalrySpearman;

import java.io.File;
import java.util.LinkedList;

public interface Serialization {
    void serialize(File file, LinkedList<CavalrySpearman> list);
    LinkedList<CavalrySpearman> deserialize(File file);

}
