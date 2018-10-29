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
			
			Long groupIdL = AccessJson.getLongFromValue(groups, new String[]{"name"}, properties.getInput("groupName"), "id");
			String groupId = groupIdL.toString();
			String kpiId = AccessJson.getStringFromValue(kpis, new String[]{"name"}, properties.getInput("kpiName"), "id");
						
			JSONArray kpiGroups = RestController.getKpiGroup(properties, kpiId, groupId);
			JSONArray responseRates = RestController.getResponseRates(properties, groupId);

			List<ReportData> reporteFinal = new ArrayList<ReportData>();
			
			for(int i=0;i<kpiGroups.size();i++){
				JSONObject kpiGroup = (JSONObject)kpiGroups.get(i);
				JSONArray kpiValues = AccessJson.getListValue(kpiGroup, new String[]{"values"});
				for(int j=0;j<kpiValues.size();j++) {
					ReportData register = new ReportData();
					JSONObject kpiValue = (JSONObject)kpiValues.get(j);
					Long registerGroupId = Long.parseLong(kpiValue.get("groupId").toString());
					register.setGroupName(AccessJson.getStringFromValue(groups, new String[] {"id"}, registerGroupId, "name"));
					register.setGroupDescription(AccessJson.getStringFromValue(groups, new String[] {"id"}, registerGroupId, "description"));
					register.setYear(kpiValue.get("yearWeek")!=null?kpiValue.get("date").toString():"");
					register.setWeek(kpiValue.get("yearWeek")!=null?kpiValue.get("yearWeek").toString():"");
					register.setKpiValue(kpiValue.get("value")!=null?kpiValue.get("value").toString():"");
					reporteFinal.add(register);
				}
			}
			
			
			String separador = "				";
			
			for(ReportData registro : reporteFinal) {
				System.out.println(registro.getGroupName()+separador+registro.getGroupDescription()+separador+registro.getYear()+separador+registro.getWeek()+separador+registro.getKpiValue());
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
