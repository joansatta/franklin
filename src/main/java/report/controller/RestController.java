package report.controller;

import java.io.IOException;
import java.net.MalformedURLException;

import org.json.simple.JSONArray;
import org.json.simple.parser.ParseException;

import report.service.RestService;

public class RestController {
	
	public static JSONArray getKpis() throws MalformedURLException, IOException, ParseException {
		return RestService.getValues("https://data.api.andfrankly.com/v1/kpis");
	}

}
