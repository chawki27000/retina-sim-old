package config;

import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Map;

public class Parse {

    public String absPath = System.getProperty("user.dir") + "/src/main/java/config/config.yml";
    private int dimension;
    private int numberOfVC;
    private int VCBufferSize;
    private int period;

    public Parse() {

        Yaml yaml = new Yaml();

        Map<String, Map<String, Integer>> values = null;
        try {
            values = yaml.load(new FileInputStream(new File(absPath)));

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

    public int getDimension() {
        return dimension;
    }

    public int getNumberOfVC() {
        return numberOfVC;
    }

    public int getVCBufferSize() {
        return VCBufferSize;
    }

    public int getPeriod() {
        return period;
    }
}
