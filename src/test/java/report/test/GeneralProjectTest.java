package report.test;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.Test;

import report.utils.ReportProperties;

public class GeneralProjectTest {

	@Test
	public void test() {
		try {
			ReportProperties properties = new ReportProperties();
			assertEquals(properties.getProperty("tokenName"),"Authorization");
			assertEquals(properties.getUrl("getKpis"),"https://data.api.andfrankly.com/v1/kpis");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
