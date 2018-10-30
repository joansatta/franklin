package report.controller;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import report.model.ReportData;
import report.service.RestService;
import report.utils.AccessJson;
import report.utils.ProjectProperties;

public class RestController {
	
	public static JSONArray getKpis(ProjectProperties properties) throws MalformedURLException, IOException, ParseException {
		String url = properties.getUrl("getKpis");
		return RestService.getJsonArray(url,properties);
	}
	
	public static JSONArray getKpiGroup(ProjectProperties properties,String kpiId,String groupId) throws MalformedURLException, IOException, ParseException {
		String url = properties.getUrl("getKpiGroup").replace("{kpiId}",kpiId).replace("{groupId}",groupId);
		return RestService.getJsonArray(url, properties);
	}

	public static JSONArray getGroups(ProjectProperties properties) throws MalformedURLException, IOException, ParseException {
		String url = properties.getUrl("getGroups");
		return RestService.getJsonArray(url, properties);
	}
	
	public static JSONArray getResponseRates(ProjectProperties properties,String groupId) throws MalformedURLException, IOException, ParseException {
		String url = properties.getUrl("getResponseRates").replace("{groupId}",groupId);
		return RestService.getJsonArray(url, properties);
	}
	
	public static List<ReportData> generateReport(JSONObject selectedGroup, JSONArray groups, JSONArray kpiGroups, JSONArray responseRates) throws MalformedURLException, IOException, ParseException {
		List<ReportData> reporteFinal = new ArrayList<ReportData>();
		if(selectedGroup != null){
			//Load group register
			createAndAddRegister(selectedGroup, kpiGroups, responseRates, reporteFinal);
			//Load subgroups registers
			JSONArray values = AccessJson.getListValue(selectedGroup, "subgroups");
			if(values!=null){
				for(int i=0;i<values.size();i++){
					Long subgroupId = Long.parseLong(values.get(i).toString());
					JSONObject subgroup = AccessJson.getInnerObjectFromValue(groups, "id", subgroupId);
					createAndAddRegister(subgroup, kpiGroups, responseRates, reporteFinal);
				}
			}
		}
		return reporteFinal;
	}

	private static void createAndAddRegister(JSONObject groupOrSubgroup, JSONArray kpiGroups, JSONArray responseRates, List<ReportData> reporteFinal) throws MalformedURLException, IOException, ParseException {
		ReportData register = new ReportData();
		register.setGroupName(groupOrSubgroup.get("name")!=null?groupOrSubgroup.get("name").toString():"");
		register.setGroupDescription(groupOrSubgroup.get("description")!=null?groupOrSubgroup.get("description").toString():"");
		register.setGroupId(groupOrSubgroup.get("id")!=null?groupOrSubgroup.get("id").toString():"");
		addKpiInfo(kpiGroups,register);
		addResponseRateInfo(responseRates,register);
		reporteFinal.add(register);
	}
	
	private static void addKpiInfo(JSONArray kpiGroups, ReportData register) throws MalformedURLException, IOException, ParseException {
		JSONObject kpiGroup = (JSONObject)kpiGroups.get(0);
		JSONArray kpiValues = AccessJson.getListValue(kpiGroup, "values");
		JSONObject kpiValue = AccessJson.getInnerObjectFromValue(kpiValues, "groupId", register.getGroupId());
		if(kpiValue!=null){
			register.setYearWeek(kpiValue.get("yearWeek")!=null?kpiValue.get("yearWeek").toString():"");
			register.setFullDate(kpiValue.get("date")!=null?kpiValue.get("date").toString():"");
			register.setKpiValue(kpiValue.get("value")!=null?kpiValue.get("value").toString():"");
		}
	}

	private static void addResponseRateInfo(JSONArray responseRates, ReportData register) throws MalformedURLException, IOException, ParseException {
		JSONObject responseRate = (JSONObject)responseRates.get(0);
		JSONArray responseRateValues = AccessJson.getListValue(responseRate, "values");
		JSONObject responseRateValue = AccessJson.getInnerObjectFromValue(responseRateValues, "groupId", register.getGroupId());
		if(responseRateValue!=null){
			register.setResponseRate(responseRateValue.get("value")!=null?responseRateValue.get("value").toString():"");
		}
	}

}
