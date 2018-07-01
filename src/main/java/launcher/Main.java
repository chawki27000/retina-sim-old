package launcher;

import architecture.NoC;
import output.FileWriter;
import simulation_gen.ConfigParse;
import simulation_gen.ScenarioParse;
import simulation.EventList;
import simulation_gen.Simulator;
import simulation_gen.Trace;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

public class Main {

    public static void main(String[] args) {

        // Files Relative Path
        String configPath = System.getProperty("user.dir") + "/src/main/java/input/config.yml";
        String scenarioPath = System.getProperty("user.dir") + "/src/main/java/input/scenario.json";
        String logPath = System.getProperty("user.dir") + "/src/main/java/output/log.txt";
        String resultPath = System.getProperty("user.dir") + "/src/main/java/output/result.txt";

        // Parsing config file
        ConfigParse configParse = new ConfigParse(configPath);

        // NoC initialisation
        NoC noc = new NoC("Network-On-Chip",
                configParse.getDimension(),
                configParse.getNumberOfVC(),
                configParse.getVCBufferSize());

        // EventList initialisation
        Simulator.eventList = new EventList();

        // Parsing scenario file
        ScenarioParse scenarioParse = new ScenarioParse(noc, scenarioPath);

        // Output File Preparing
        FileWriter fileWriter = new FileWriter(logPath, resultPath);

        // START SIMULATING
        System.out.println("Simulation : Start");
        Simulator s = new Simulator(configParse.getPeriod());
        s.simulate();
        System.out.println("Simulation : End");

        // Traces Writing
        System.out.println("\n");
        System.out.println("- - - - - - - - - - - - TRACE - - - - - - - - - - - -");
        for (Trace t : Simulator.traceList) {
            System.out.println(t.toString());
        }

        // Close Output File Writer
        fileWriter.close();
    }
}
