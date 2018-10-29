package report.service;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import report.utils.ReportProperties;


public class RestService {

	public static JSONArray getValues(String urlString, ReportProperties properties, String values) throws MalformedURLException, IOException, ParseException {
		URL url = new URL(urlString+values);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.setRequestProperty(properties.getProperty("contentName"), properties.getProperty("contentValue"));
		conn.setRequestProperty(properties.getProperty("tokenName"),properties.getProperty("tokenValue"));
		if (conn.getResponseCode() != 200) {
			throw new RuntimeException("Failed : HTTP error code : "+ conn.getResponseCode());
		}
		InputStreamReader isr = new InputStreamReader(conn.getInputStream(),properties.getProperty("outputEncoding"));
		JSONArray jsonArray = parseJson(isr);		
		isr.close();
		conn.disconnect();		
		return jsonArray;
	}

	private static JSONArray parseJson(InputStreamReader isr) throws IOException, ParseException {
		JSONParser jsonParser = new JSONParser();
		JSONArray jsonObject = (JSONArray)jsonParser.parse(isr);
		return jsonObject;
	}




}
