package report.controller;

import java.io.IOException;
import java.net.MalformedURLException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import report.service.RestService;
import report.utils.ReportProperties;

public class RestController {
	
	public static JSONArray getKpis(ReportProperties properties) throws MalformedURLException, IOException, ParseException {
		String url = properties.getUrl("getKpis");
		return RestService.getJsonArray(url,properties);
	}
	
	public static JSONObject getKpiGroup(ReportProperties properties,String kpiId,String groupId) throws MalformedURLException, IOException, ParseException {
		String url = properties.getUrl("getKpiGroup").replace("{kpiId}",kpiId).replace("{groupId}",groupId);
		return RestService.getJsonObject(url, properties);
	}

}
