package simulation_gen;

import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Map;

public class ConfigParse {

    public static int dimension;
    public static int numberOfVC;
    public static int VCBufferSize;
    public static int period;

    public ConfigParse(String configPath) {

        Yaml yaml = new Yaml();

        Map<String, Map<String, Integer>> values = null;
        try {
            values = yaml.load(new FileInputStream(new File(configPath)));

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        for (String key : values.keySet()) {
            Map<String, Integer> subValues = values.get(key);

            for (String subValueKey : subValues.keySet()) {
                if (subValueKey.equals("dimension"))
                    dimension = subValues.get(subValueKey);
                else if (subValueKey.equals("numberOfVC"))
                    numberOfVC = subValues.get(subValueKey);
                else if (subValueKey.equals("VCBufferSize"))
                    VCBufferSize = subValues.get(subValueKey);
                else if (subValueKey.equals("period"))
                    period = subValues.get(subValueKey);
            }
        }
    }

}
