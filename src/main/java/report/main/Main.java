package report.main;

import java.io.IOException;
import java.net.MalformedURLException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import report.utils.RestClient;

public class Main {

	public static void main(String[] args) {
		try {
			JSONArray parsedJson = RestClient.getValues("https://data.api.andfrankly.com/v1/kpis");
			for(int i=0;i<parsedJson.size();i++){
				JSONObject jo = (JSONObject)parsedJson.get(i);
				System.out.println(jo.get("id"));
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

}
