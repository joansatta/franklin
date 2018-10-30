package report.main;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import report.controller.RestController;
import report.model.ReportData;
import report.utils.AccessJson;
import report.utils.ReportProperties;

public class Main {	

	public static void main(String[] args) {
		try {

			ReportProperties properties = new ReportProperties();
			JSONArray groups = RestController.getGroups(properties);
			JSONArray kpis = RestController.getKpis(properties);
			JSONObject selectedGroup = AccessJson.getInnerObjectFromValue(groups, new String[]{"name"}, properties.getInput("groupName"));
			JSONObject selectedKpi = AccessJson.getInnerObjectFromValue(kpis, new String[]{"name"}, properties.getInput("kpiName"));

			Long groupIdL = Long.parseLong(selectedGroup.get("id").toString());
			String groupId = groupIdL.toString();
			String kpiId = selectedKpi.get("id").toString();

			JSONArray kpiGroups = RestController.getKpiGroup(properties, kpiId, groupId);
			JSONArray responseRates = RestController.getResponseRates(properties, groupId);

			List<ReportData> reporteFinal = generateReport(selectedGroup,groups,kpiGroups,responseRates);

			String separador = "		";

			for(ReportData registro : reporteFinal) {
				System.out.println(registro.getGroupName()+separador+registro.getGroupDescription()+separador+registro.getYear()+separador+registro.getWeek()+separador+registro.getKpiFormattedValue()+separador+registro.getResponseRateFormattedValue());
			}


		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	private static List<ReportData> generateReport(JSONObject selectedGroup, JSONArray groups, JSONArray kpiGroups, JSONArray responseRates) throws MalformedURLException, IOException, ParseException {
		List<ReportData> reporteFinal = new ArrayList<ReportData>();
		if(selectedGroup != null){
			//Load group register
			createAndAddRegister(selectedGroup, kpiGroups, responseRates, reporteFinal);
			JSONArray values = AccessJson.getListValue(selectedGroup, new String[]{"subgroups"});
			if(values!=null){
				for(int i=0;i<values.size();i++){
					//Load subgroups registers
					Long subgroupId = Long.parseLong(values.get(i).toString());
					JSONObject subgroup = AccessJson.getInnerObjectFromValue(groups, new String[]{"id"}, subgroupId);
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
		addResponseRatesInfo(responseRates,register);
		reporteFinal.add(register);
	}
	
	private static void addKpiInfo(JSONArray kpiGroups, ReportData register) throws MalformedURLException, IOException, ParseException {
		JSONObject kpiGroup = (JSONObject)kpiGroups.get(0);
		JSONArray kpiValues = AccessJson.getListValue(kpiGroup, new String[]{"values"});
		JSONObject kpiValue = AccessJson.getInnerObjectFromValue(kpiValues, new String[]{"groupId"}, register.getGroupId());
		if(kpiValue!=null){
			register.setYearWeek(kpiValue.get("yearWeek")!=null?kpiValue.get("yearWeek").toString():"");
			register.setFullDate(kpiValue.get("date")!=null?kpiValue.get("date").toString():"");
			register.setKpiValue(kpiValue.get("value")!=null?kpiValue.get("value").toString():"");
		}
	}

	private static void addResponseRatesInfo(JSONArray responseRates, ReportData register) throws MalformedURLException, IOException, ParseException {
		JSONObject responseRate = (JSONObject)responseRates.get(0);
		JSONArray responseRateValues = AccessJson.getListValue(responseRate, new String[]{"values"});
		JSONObject responseRateValue = AccessJson.getInnerObjectFromValue(responseRateValues, new String[]{"groupId"}, register.getGroupId());
		if(responseRateValue!=null){
			register.setResponseRate(responseRateValue.get("value")!=null?responseRateValue.get("value").toString():"");
		}
	}

}
