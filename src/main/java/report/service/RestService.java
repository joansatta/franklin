package report.service;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


public class RestService {

	public static JSONArray getValues(String urlString) throws MalformedURLException, IOException, ParseException {
		URL url = new URL(urlString);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.setRequestProperty("content-type", "application/json; charset=utf-8");
		conn.setRequestProperty("Authorization","eyJhbGciOiJIUzI1NiJ9.eyJjb21wYW55SWQiOjEsImlhdCI6MTUzNjIzNTIyM30.eX0t7TJ9mzhH1zDlzXxdDtWhFccA_t-55Q_h4SX7Bc8");

		if (conn.getResponseCode() != 200) {
			throw new RuntimeException("Failed : HTTP error code : "+ conn.getResponseCode());
		}

		InputStreamReader isr = new InputStreamReader(conn.getInputStream(),"UTF-8");

		JSONArray jsonArray = parseJson(isr);
		
		conn.disconnect();
		return jsonArray;
	}

	private static JSONArray parseJson(InputStreamReader isr) throws IOException, ParseException {
		JSONParser jsonParser = new JSONParser();
		JSONArray jsonObject = (JSONArray)jsonParser.parse(isr);
		return jsonObject;
	}




}
