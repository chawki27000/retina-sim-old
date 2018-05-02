package simulation_gen;

import architecture.NoC;

public class NocSim {

    // Active Object (Process)
    public static NoC noc;


    protected NocSim(String s) {

    }

    public static void main(String[] args) {


        // Network-On-Chip Instantiation
        noc = new NoC("NoC", 3, 1, 12);


    }

}
