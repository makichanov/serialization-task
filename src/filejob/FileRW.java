package filejob;

public class FileRW {

    private static FileJob fileJob;

    public static FileJob getFileJob() {
        return fileJob;
    }

    public static void setFileJob(FileJob fileJob) {
        FileRW.fileJob = fileJob;
    }
}
