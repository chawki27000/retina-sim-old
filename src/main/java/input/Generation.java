package input;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class Generation {

    // Relative File Path
    String scenarioPath = System.getProperty("user.dir") + "/src/main/java/input/scenario.json";

    // Period Array
    int[] period_array = new int[]{6, 7, 10, 14, 15, 30};

    // TaskSet
    ArrayList<Task> tasks = new ArrayList<>();

    // Get utilization factors
    ArrayList<Double> utilizationArray = getUtilizationFactors();

    public void generateJsonScenario() {

        // Inner Method call
        generateTasks();

        // Initial Root object
        JSONObject obj = new JSONObject();



        // Add communication Array object
        JSONArray communications = new JSONArray();

        // loop
        for (Task ts : tasks) {
            // Communication object
            JSONObject comObj = new JSONObject();
            JSONObject srcObj = new JSONObject();
            JSONObject destObj = new JSONObject();

            srcObj.put("x", ts.getSrc_x());
            srcObj.put("y", ts.getSrc_y());

            destObj.put("x", ts.getDest_x());
            destObj.put("y", ts.getDest_y());

            comObj.put("src", srcObj);
            comObj.put("dest", destObj);
            comObj.put("message", ts.getMsg_size());
            comObj.put("time", ts.getT());

            communications.add(comObj);
        }

        // Assign root object
        obj.put("scenario", communications);

        // Writing Scenario JSON File
        try (FileWriter file = new FileWriter(scenarioPath)) {
            file.write(obj.toJSONString());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void generateTasks() {

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
            msg_size = (int) Math.ceil(t * utilization_factor);
            lower_bound = (int) (0.7 * t);
            d = new Random().nextInt((t - lower_bound) + 1) + lower_bound;

            // TODO : define conflict factor
            // generate task
            task = new Task(0, 0, 2, 2, t, 0, d, msg_size);
            tasks.add(task);
        }

    }

    public void taskPrint() {
        for (Task ts : tasks) {
            System.out.println(ts);
        }
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