package input;

import architecture.NoC;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;

public class ScenarioParse {

    private String absPath = System.getProperty("user.dir") + "/src/main/java/input/scenario.json";

    private JSONObject jsonObjectL1;
    private JSONObject jsonObjectL2;
    private JSONArray jsonArrayL1;


    public ScenarioParse(NoC noc) {

        JSONParser parser = new JSONParser();

        Object obj = null;
        try {
            // File parsing
            obj = parser.parse(new FileReader(absPath));

            // Object level 1 (whole)
            jsonObjectL1 = (JSONObject) obj;

            // Object level 2 (+1 deeper)
            jsonArrayL1 = (JSONArray) jsonObjectL1.get("scenario");

            // Read ARRAY List
            Iterator<?> iteratorscroll = jsonArrayL1.iterator();
            while (iteratorscroll.hasNext()) {
                jsonObjectL2 = (JSONObject) iteratorscroll.next();
                System.out.println(String.valueOf(jsonObjectL2));
            }


        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }
}
