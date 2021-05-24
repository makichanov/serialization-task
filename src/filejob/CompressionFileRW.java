package filejob;

import compression.Compression;

import java.io.*;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class CompressionFileRW implements FileJob {
    private static final String COMPRESSION_PATH = "..\\serialization-task\\compression";
    public static List<Class> compressionClasses = loadPlugins(COMPRESSION_PATH);
    private Compression compression;

    public CompressionFileRW(Compression compression) {
        this.compression = compression;
    }

    private static List<Class> loadPlugins(String parentPackage) {
        List<Class> classes = new ArrayList<>();
        File pluginDir = new File(parentPackage);
        File[] jars = pluginDir.listFiles(file -> file.isFile() && file.getName().endsWith(".jar"));
        if (jars == null) {
            throw new IllegalArgumentException("Plugins not found");
        }
        for (File jar : jars) {
            JarFile jarFile;
            URL path = null;
            Enumeration<JarEntry> entries = null;
            try {
                jarFile = new JarFile(jar);
                entries = jarFile.entries();
                path = jar.toURI().toURL();
            } catch (IOException e) {
                e.printStackTrace();
            }

            URLClassLoader classLoader = new URLClassLoader(new URL[]{path});
            while (entries.hasMoreElements()) {
                JarEntry entry = entries.nextElement();
                String name = entry.getName();
                if (entry.isDirectory() || (!name.endsWith(".class"))) {
                    continue;
                }
                String className = name.substring(0, name.length() - 6).replace('/', '.');
                Class<?> anotherClass = null;
                try {
                    anotherClass = classLoader.loadClass(className);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                classes.add(anotherClass);
            }
        }
        return classes;
    }

    @Override
    public String read(File file) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }
            return compression.decompress(stringBuilder.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void write(File file, String text) {
        try {
            FileWriter fw = new FileWriter(file);
            fw.write(compression.compress(text));
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
