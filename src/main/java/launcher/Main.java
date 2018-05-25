package launcher;

import architecture.NoC;
import simulation_gen.ConfigParse;
import simulation_gen.ScenarioParse;
import simulation.EventList;
import simulation_gen.Simulator;
import simulation_gen.Trace;

public class Main {

    public static void main(String[] args) {

        // Parsing config file
        ConfigParse configParse = new ConfigParse();

        // NoC initialisation
        NoC noc = new NoC("Network-On-Chip",
                configParse.getDimension(),
                configParse.getNumberOfVC(),
                configParse.getVCBufferSize());

        // EventList initialisation
        Simulator.eventList = new EventList();

        // Parsing scenario file
        ScenarioParse scenarioParse = new ScenarioParse(noc);

        System.out.println(Simulator.eventList);

        Simulator s = new Simulator(configParse.getPeriod());
        s.simulate();

        // Traces Printing
        System.out.println("- - - TRACE - - -");
        for (Trace t : Simulator.traceList) {
            System.out.println(t);
        }
    }
}
