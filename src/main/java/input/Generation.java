package input;

import java.util.ArrayList;
import java.util.Random;

public class Generation {

    // Period Array
    int[] period_array = new int[]{6, 7, 10, 14, 15, 30};

    // TaskSet
    ArrayList<Task> tasks = new ArrayList<>();

    // Get utilization factors
    ArrayList<Double> utilizationArray = getUtilizationFactors();

    public ArrayList<Task> generateTasks() {

        // task Generation
        Task task;
        double utilization_factor;
        int msg_size, t, c, d;
        int lower_bound;
        // loop
        while (!utilizationArray.isEmpty()) {

            // generate parameters
            utilization_factor = getRandomUtilizFact();
            t = period_array[new Random().nextInt(period_array.length)];
            msg_size = (int) Math.ceil(t*utilization_factor);
            lower_bound = (int) (0.7*t);
            d = new Random().nextInt((t - lower_bound) + 1) + lower_bound;

            // TODO : define conflict factor
            // generate task
            task = new Task(0,0, 2, 2, t, 0, d, msg_size);
            tasks.add(task);
        }

        return tasks;
    }

    // Utilization Factors
    private ArrayList<Double> getUtilizationFactors() {

        ArrayList<Double> utilizationArr = new ArrayList<>();

        Unifast unifast = new Unifast(15, 32, 2);

        double[] utilization_factors = unifast.generateUtilizations();

        for (double utilization_factor : utilization_factors) {
            utilizationArr.add(utilization_factor);
        }

        return utilizationArr;
    }

    private double getRandomUtilizFact() {

        int index = new Random().nextInt(utilizationArray.size());
        double utilization_factor = utilizationArray.get(index);
        utilizationArray.remove(utilization_factor);

        return utilization_factor;
    }

    // HyperPeriod Computation
    private int getHyperPeriod() {
        int hyperperiod = 1;

        for (int period : period_array) {
            hyperperiod = lcm(hyperperiod, period);
        }

        return hyperperiod;
    }

    private int lcm(int a, int b) {
        if (a == 0 || b == 0) {
            return 0;
        }
        return (a * b) / gcd(a, b);
    }

    private int gcd(int a, int b) {
        int remainder = 0;
        do {
            remainder = a % b;
            a = b;
            b = remainder;
        } while (b != 0);

        return a;
    }

}
