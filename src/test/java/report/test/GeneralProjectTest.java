package report.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.parser.ParseException;
import org.junit.Test;

import report.controller.RestController;
import report.utils.AccessJson;
import report.utils.ProjectProperties;

public class GeneralProjectTest {

	@Test
	public void readPropertiesTest() {
		try {
			ProjectProperties properties = new ProjectProperties();
			assertEquals(properties.getProperty("tokenName"),"Authorization");
			assertEquals(properties.getUrl("getKpis"),"https://data.api.andfrankly.com/v1/kpis");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void getKpiIdTest() {
		try {
			ProjectProperties properties = new ProjectProperties();
			JSONArray kpis = RestController.getKpis(properties);
			String id = AccessJson.getStringFromValue(kpis, new String[]{"name"}, properties.getInput("kpiName"), "id");
			assertEquals(id,"P-102");
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void getGroupIdTest() {
		try {
			ProjectProperties properties = new ProjectProperties();
			JSONArray groups = RestController.getGroups(properties);
			Long id = AccessJson.getLongFromValue(groups, new String[]{"name"}, properties.getInput("groupName"), "id");
			assertTrue(id==3939);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	

}
