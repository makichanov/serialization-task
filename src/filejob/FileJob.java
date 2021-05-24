package filejob;

import java.io.File;

public interface FileJob {
    String read(File file);
    void write(File file, String text);
}
