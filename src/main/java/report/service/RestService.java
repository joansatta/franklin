package report.service;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.simple.JSONArray;
import org.json.simple.parser.ParseException;

import report.utils.AccessJson;
import report.utils.ProjectProperties;


public class RestService {


	public static JSONArray getJsonArray(String urlString, ProjectProperties properties) throws MalformedURLException, IOException, ParseException {
		Object ret = getValues(urlString, properties);
		return AccessJson.returnJsonArray(ret);		
	}

	public static Object getValues(String urlString, ProjectProperties properties) throws MalformedURLException, IOException, ParseException {
		URL url = new URL(urlString);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.setRequestProperty(properties.getProperty("contentName"), properties.getProperty("contentValue"));
		conn.setRequestProperty(properties.getProperty("tokenName"),properties.getProperty("tokenValue"));
		if (conn.getResponseCode() != 200) {
			throw new RuntimeException("Failed : HTTP error code : "+ conn.getResponseCode());
		}
		InputStreamReader isr = new InputStreamReader(conn.getInputStream(),properties.getProperty("outputEncoding"));
		Object json = AccessJson.parseJson(isr);		
		isr.close();
		conn.disconnect();		
		return json;
	}


}
