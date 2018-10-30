package report.main;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import report.controller.RestController;
import report.model.ReportData;
import report.utils.AccessJson;
import report.utils.ProjectProperties;

public class Main {	

	public static void main(String[] args) {
		try {
			//Set properties
			ProjectProperties properties = new ProjectProperties();

			//Retrieve data and generate report
			JSONArray groups = RestController.getGroups(properties);
			JSONArray kpis = RestController.getKpis(properties);
			JSONObject selectedGroup = AccessJson.getInnerObjectFromValue(groups, "name", properties.getInput("groupName"));
			JSONObject selectedKpi = AccessJson.getInnerObjectFromValue(kpis, "name", properties.getInput("kpiName"));
			JSONArray kpiGroups = RestController.getKpiGroup(properties, selectedKpi.get("id").toString(), selectedGroup.get("id").toString());
			JSONArray responseRates = RestController.getResponseRates(properties, selectedGroup.get("id").toString());
			List<ReportData> reporteFinal = RestController.generateReport(selectedGroup,groups,kpiGroups,responseRates);

			//Export generated report
			RestController.exportReport(properties, reporteFinal);
			//RestController.writeDataLineByLine(properties.getProperty("outputFilePath"));

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (Exception e){
			e.printStackTrace();
		}
	}



}
