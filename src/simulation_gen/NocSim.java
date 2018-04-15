package simulation_gen;

import architecture.NoC;
import architecture.Router;
import psimjava.Process;
import psimjava.Simulation;
import psimjava.Squeue;
import psimjava.StaticSync;

public class NocSim extends Process {

    // Active Object (Process)
    public static NoC noc;

    // Passive Object
    public static Squeue sending_queue;

    // Simulation Constant
    public static double simPeriod = 125d; // simulation period
    public static int sendingBufferSize = 15;

    private static Simulation sim;

    protected NocSim(String s) {
        super(s);
    }

    public static void main(String[] args) {

        // set-up sim
        sim = new Simulation("Network-on-Chip System Simulator");

        // Network-On-Chip Instantiation
        noc = new NoC("NoC",2,8, 12);

        // Initial sending
        noc.getRouter(0,0).setSendingInfo(1,1,128);

        // starting NoC Process
        noc.start();

        // Sending Queue Instantiation
        sending_queue = new Squeue("SendingQueue", sendingBufferSize);

        // Sender and Receiver coordinates
        int[] src = new int[]{0,0};
        int[] dest = new int[]{1,1};

    }

    @Override
    protected void Main_body() {

        sim.start_sim(simPeriod);

        System.out.println("Simulation clock: "+ StaticSync.get_clock());


        System.exit(0);
    }
}
