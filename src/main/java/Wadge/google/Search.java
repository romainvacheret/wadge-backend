package Wadge.google;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

import java.net.URL;
import java.net.URLConnection;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Search {
    private static final String KEY = System.getenv("GOOGLE_API");


    public void jsonToFile(String fileName, JSONObject json) { 
        try {
            FileWriter file = new FileWriter(fileName);
            file.write(json.toJSONString());
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        } 
    }

    public JSONObject request() {
        String requestUrl = new String("https://maps.googleapis.com/maps/api/place/findplacefromtext/json?input=naturalia&inputtype=textquery&fields=opening_hours,formatted_address,geometry&locationbias=circle:2000@48.8566,2.3522&key=" + Search.KEY);
        
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
            JSONObject json = (JSONObject) parser.parse(sb.toString());
            return json;
            
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        
        return null;
    }

    private JSONArray parseJSON(JSONArray json) {
        JSONArray array = new JSONArray();

        json.forEach(place -> {
            JSONObject tmp = new JSONObject();
            tmp.put("formatted_address", ((JSONObject) place).get("formatted_address"));
            tmp.put("opening_hours", ((JSONObject) place).get("opening_hours"));
            JSONObject viewport = (JSONObject) ((JSONObject) place).get("geometry");
            System.out.println(viewport);
            tmp.put("location", ((JSONObject) viewport).get("location"));

            array.add(tmp);
        });
        return array;
    }

    public static void main(String[] args) throws IOException, ParseException {
        Search obj = new Search();

        JSONObject json = obj.request();
        JSONObject tmp = new JSONObject();
        tmp.put("candidates", obj.parseJSON((JSONArray) json.get("candidates")));
        obj.jsonToFile("test4.json", tmp);

    }
}