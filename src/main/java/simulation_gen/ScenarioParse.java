package simulation_gen;

import architecture.NoC;
import communication.Message;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import simulation.Event;
import simulation.EventType;
import simulation_gen.Simulator;

import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;

public class ScenarioParse {

    private JSONObject jsonObjectL1;
    private JSONObject jsonObjectL2;
    private JSONObject jsonObjectSrc;
    private JSONObject jsonObjectDest;
    private JSONArray jsonArrayL1;

    public ScenarioParse(NoC noc, String scenarioPath) {

        JSONParser parser = new JSONParser();

        Object obj = null;
        try {
            // File parsing
            obj = parser.parse(new FileReader(scenarioPath));

            // Object level 1 (whole)
            jsonObjectL1 = (JSONObject) obj;

            // Object level 2 (+1 deeper)
            jsonArrayL1 = (JSONArray) jsonObjectL1.get("scenario");

            // Read ARRAY List
            Iterator<?> iteratorscroll = jsonArrayL1.iterator();
            while (iteratorscroll.hasNext()) {
                jsonObjectL2 = (JSONObject) iteratorscroll.next();

                jsonObjectSrc = (JSONObject) jsonObjectL2.get("src");
                jsonObjectDest = (JSONObject) jsonObjectL2.get("dest");

                // Event Creation
                int period = ((Long) jsonObjectL2.get("period")).intValue();
                int message = ((Long) jsonObjectL2.get("message")).intValue();
                int srx_x = ((Long) jsonObjectSrc.get("x")).intValue();
                int srx_y = ((Long) jsonObjectSrc.get("y")).intValue();
                int dest_x = ((Long) jsonObjectDest.get("x")).intValue();
                int dest_y = ((Long) jsonObjectDest.get("y")).intValue();

                // Periodic communication event creation
                int count = 0, instance = 0;
                while (count < Simulator.simulationPeriod) {
                    Event ev = new Event(EventType.MESSAGE_SEND,
                            count,
                            noc.getRouter(srx_x, srx_y),
                            new int[]{dest_x, dest_y},
                            message,
                            instance,
                            Message.messageNum);

                    // Event pushing
                    Simulator.eventList.push(ev);

                    count += period; instance++;
                }
                // Increment messageID
                Message.messageNum++;
            }


        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }
}
