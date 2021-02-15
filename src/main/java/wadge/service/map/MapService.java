package wadge.service.map;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import io.github.cdimascio.dotenv.Dotenv;

public class MapService {
    private static final String KEY;
    private static Logger logger = Logger.getLogger(MapService.class.getName());
    
    static {
        Dotenv dotenv = Dotenv.load();
        KEY = dotenv.get("GOOGLE_API");
    }


    public void jsonToFile(String fileName, JSONObject json) { 
        try(FileWriter file = new FileWriter(fileName)) {
            file.write(json.toJSONString());
        } catch(IOException e) {
            logger.log(Level.FINE, e.getMessage(), e);
        } 
    }

    public JSONObject request() {
        String requestUrl = String.format("https://maps.googleapis.com/maps/api/place/findplacefromtext/json?input=naturalia&inputtype=textquery&fields=opening_hours,formatted_address,geometry&locationbias=circle:2000&key=%s", MapService.KEY);
        
        try {
            URL url = new URL(requestUrl);
            URLConnection connection = url.openConnection();
            BufferedReader in = new BufferedReader( new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuilder sb = new StringBuilder();

            while((inputLine = in.readLine()) != null) {
                sb.append(inputLine);
            }

            JSONParser parser = new JSONParser();
            return (JSONObject) parser.parse(sb.toString());
            
        } catch (IOException | ParseException e) {
            logger.log(Level.FINE, e.getMessage(), e);
        }
        
        return null;
    }

    public JSONArray parseJSON(JSONArray json) {
        JSONArray array = new JSONArray();

        json.forEach(place -> {
            JSONObject tmp = new JSONObject();
            tmp.put("formatted_address", ((JSONObject) place).get("formatted_address"));
            tmp.put("opening_hours", ((JSONObject) place).get("opening_hours"));
            JSONObject viewport = (JSONObject) ((JSONObject) place).get("geometry");
            tmp.put("location", (viewport).get("location"));

            array.add(tmp);
        });
        return array;
    }
}