package report.utils;

import org.json.simple.JSONObject;

public class AccessJson {

	public static String getStringValue(JSONObject json,String[] path) {
		if(path.length==1) {
			return (String) json.get(path[0]);
		} else {
			int i=0;
			JSONObject aux=null;
			while (i < path.length - 1) {
				aux = (JSONObject)json.get(path[i]);
				i++;
			}
			return (String)aux.get(path[path.length-1]);
		}
	}

}
