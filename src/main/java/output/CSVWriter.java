package output;

import simulation_gen.Trace;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class CSVWriter {

    //Delimiter used in CSV file
    private static final String COMMA_DELIMITER = ",";
    private static final String NEW_LINE_SEPARATOR = "\n";

    //CSV file header
    private static final String FILE_HEADER = "i,WCLA,L_1,L_2,L_3,L_4";


    public static void writeCsvFile(String fileName, ArrayList<Trace> traceList) {

        // Message Restriction Array
        ArrayList<Integer> messageList = new ArrayList<>();

        FileWriter fileWriter = null;

        try {
            fileWriter = new FileWriter(fileName);

            //Write the CSV file header
            fileWriter.append(FILE_HEADER);

            //Add a new line separator after the header
            fileWriter.append(NEW_LINE_SEPARATOR);

            //Write a new trace object list to the CSV file
            for (Trace trace : traceList) {

                if (!messageList.isEmpty() &&
                        messageList.contains(trace.getFlit().getPacket().getMessage().getId()))
                    continue;

                // Restriction
                messageList.add(trace.getFlit().getPacket().getMessage().getId());

                // Put ID
                fileWriter.append(String.valueOf(trace.getFlit().getPacket().getMessage().getId()));
                fileWriter.append(COMMA_DELIMITER);

                // Put WCLA
                fileWriter.append(String.valueOf(trace.getFlit().getPacket().getMessage().getE2ELatency()));
                fileWriter.append(COMMA_DELIMITER);

                // Put L1
                fileWriter.append(String.valueOf(1));
                fileWriter.append(COMMA_DELIMITER);

                // Put L2
                fileWriter.append(String.valueOf(2));
                fileWriter.append(COMMA_DELIMITER);

                // Put L3
                fileWriter.append(String.valueOf(3));
                fileWriter.append(COMMA_DELIMITER);

                // Put L4
                fileWriter.append(String.valueOf(4));

                // New line
                fileWriter.append(NEW_LINE_SEPARATOR);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                fileWriter.flush();
                fileWriter.close();
            } catch (IOException e) {
                System.out.println("Error while flushing/closing fileWriter !!!");
                e.printStackTrace();
            }
        }
    }
}
