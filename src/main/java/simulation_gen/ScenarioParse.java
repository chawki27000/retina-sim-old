package simulation_gen;

import architecture.NoC;
import communication.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import simulation.Event;
import simulation.EventType;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class ScenarioParse {

    private JSONObject jsonObjectL1;
    private JSONObject jsonObjectL2;
    private JSONObject jsonObjectSrc;
    private JSONObject jsonObjectDest;
    private JSONArray jsonArrayL1;

    private ArrayList<Integer> periods_array;

    public ScenarioParse(NoC noc, String scenarioPath) {

        periods_array = new ArrayList<Integer>();
        JSONParser parser = new JSONParser();

        Object obj = null;
        int id = 0;
        MessageSet mset = new MessageSet();
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

                int MessageSize = ((Long) jsonObjectL2.get("message")).intValue();
                int src_x = ((Long) jsonObjectSrc.get("x")).intValue();
                int src_y = ((Long) jsonObjectSrc.get("y")).intValue();
                int dst_x = ((Long) jsonObjectDest.get("x")).intValue();
                int dst_y = ((Long) jsonObjectDest.get("y")).intValue();

                Packet p = new Packet(0, MessageSize);
                Message m = new Message(id, period, p, new coordinates(src_x, src_y), new coordinates(dst_x, dst_y));
                p.setMessage(m);
                mset.addMessage(m);
                id++;
            }

        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

        create_and_push_periodic_events(noc, mset);

    }

    void create_and_push_periodic_events(NoC noc, MessageSet mset) {
        int hyper = mset.getHyperPeriod();
        for (Message m : mset.getM_list()) {
            for (int i = 0; i < hyper; i++) {
                MessageInstance inst_m = new MessageInstance(m, i, i * m.getPeriod());
				Simulator.messageInstancesList.add(inst_m);
                Event ev = new Event(EventType.MESSAGE_SEND, inst_m.getArrivalTime(), noc.getRouter(m.getSrc_coor()),
                        noc.getRouter(m.getDst_coor()), inst_m);
                Simulator.eventList.push(ev);
            }
        }

    }
}
