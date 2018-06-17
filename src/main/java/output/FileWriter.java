package output;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

public class FileWriter {

    private String logPath;
    private String resultPath;

    private static PrintWriter resultWriter;
    private static PrintWriter logWriter;

    public FileWriter(String logPath, String resultPath) {
        this.logPath = logPath;
        this.resultPath = resultPath;

        // Preparing Log Writer
        try {
            logWriter = new PrintWriter(logPath, "UTF-8");
        } catch (FileNotFoundException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        // Preparing Result Writer
        try {
            resultWriter = new PrintWriter(resultPath, "UTF-8");
        } catch (FileNotFoundException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public static void log(String str) {
        logWriter.println(str);
    }

    public static void result(String str) {
        resultWriter.println(str);
    }

    public void close() {
        logWriter.close();
        resultWriter.close();
    }
}
