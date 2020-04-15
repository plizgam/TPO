/**
 *
 *  @author Pliżga Miłosz S18536
 *
 */

package zad1;


import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;
import java.util.Map;

public class Tools {
    public static Options createOptionsFromYaml(String fileName) throws FileNotFoundException {
        Yaml yaml = new Yaml();
        Map<String,Object> yamlData = yaml.load(new FileReader(new File(fileName)));
        return new Options(
                yamlData.get("host").toString(),
                (int) yamlData.get("port"),
                (boolean) yamlData.get("concurMode"),
                (boolean) yamlData.get("showSendRes"),
                (Map<String, List<String>>) yamlData.get("clientsMap"));
    }
}
