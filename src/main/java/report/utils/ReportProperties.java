package report.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ReportProperties {
	
	Map<String,String> properties;
	Map<String,String> urls;
	
	public ReportProperties() throws IOException {
		properties = readFile("src\\main\\resources\\params.properties");
		urls = readFile("src\\main\\resources\\urls.properties");
	}
	
	public String getProperty(String name) {
		return properties.get(name);
	}
	
	public String getUrl(String name) {
		return urls.get("baseUrl")+urls.get("apiVersion")+urls.get(name);
	}

	public static Map<String,String> readFile(String relativePath) throws IOException 
	{
		File file;
		BufferedReader br;
		Map<String,String> properties = new HashMap<String,String>();
		
		try {
			file = new File(relativePath); 
			br = new BufferedReader(new FileReader(file)); 
		} catch (Exception e) {
			file = new File (new File("").getAbsolutePath()+relativePath);
			br = new BufferedReader(new FileReader(file)); 
		}
	
		String st; 
		while ((st = br.readLine()) != null) {
			String[] pair = st.split("\\=");
			properties.put(pair[0], pair[1]);
		}
		
		br.close();
		return properties;
	} 


}
