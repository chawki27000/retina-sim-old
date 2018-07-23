package output;

import communication.Message;
import communication.MessageInstance;
import simulation_gen.Simulator;
import simulation_gen.Trace;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
                fileWriter.append(String.valueOf(trace.getFlit().getPacket().getMessage().getE2ELatencyByAnalysis()));
                fileWriter.append(COMMA_DELIMITER);

                // Message Instance Array Fetching
                for (MessageInstance instance : Simulator.messageInstancesList) {
                    // Put L1
                    if (instance.getInstNumber() == 0 && instance == trace.getFlit().getPacket().getMessage()) {
                        fileWriter.append(String.valueOf(instance.latencyAnalysis()));
                        fileWriter.append(COMMA_DELIMITER);

                    } else {
                    }

                    // Put L2
                    if (instance.getInstNumber() == 1 && instance == trace.getFlit().getPacket().getMessage()) {
                        fileWriter.append(String.valueOf(instance.latencyAnalysis()));
                        fileWriter.append(COMMA_DELIMITER);
                    } else {
                        fileWriter.append(String.valueOf(0));
                        fileWriter.append(COMMA_DELIMITER);
                    }

                    // Put L3
                    if (instance.getInstNumber() == 2 && instance == trace.getFlit().getPacket().getMessage()) {
                        fileWriter.append(String.valueOf(instance.latencyAnalysis()));
                        fileWriter.append(COMMA_DELIMITER);
                    } else {
                        fileWriter.append(String.valueOf(0));
                        fileWriter.append(COMMA_DELIMITER);
                    }

                    // Put L4
                    if (instance.getInstNumber() == 3 && instance == trace.getFlit().getPacket().getMessage()) {
                        fileWriter.append(String.valueOf(instance.latencyAnalysis()));
                    } else {
                        fileWriter.append(String.valueOf(0));
                    }
                }


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
