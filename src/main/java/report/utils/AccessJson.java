package report.utils;

import java.io.IOException;
import java.net.MalformedURLException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

public class AccessJson {
	
	public static JSONArray getListValue(JSONObject json,String[] path) throws MalformedURLException, IOException, ParseException {
		Object ret = getValue(json,path);
		return returnJsonArray(ret);
	}

	public static String getStringValue(JSONObject json,String[] path) {
		return (String)getValue(json,path);
	}

	public static JSONObject getInnerObject(JSONObject json, String[] path) {
		return (JSONObject)getValue(json,path);
	}

	public static Object getValue(JSONObject json,String[] path) {
		if(path.length==1) {
			return json.get(path[0]);
		} else {
			int i=0;
			JSONObject aux=null;
			while (i < path.length - 1) {
				aux = (JSONObject)json.get(path[i]);
				i++;
			}
			return aux.get(path[path.length-1]);
		}
	}

	/**
	 * Usage: 
	 * inputhPath is a string array leading to the location of the input field we want to evaluate
	 * inputValue is the value we are looking for on the inputPath
	 * outputField is the name of the output field whose value we want to retrieve
	 **/
	public static Object getValueFromValue(JSONArray json, String[] inputPath, Object inputValue,String outputField) {
		for(int i=0;i<json.size();i++){
			JSONObject jo = (JSONObject)json.get(i);
			Object key = getValue(jo, inputPath);
			if(key !=null && (key==inputValue || key.equals(inputValue))) {
				JSONObject innerObject; 
				if(inputPath.length==1) {
					innerObject = jo;
				} else {
					String[] auxPath;
					auxPath = new String[inputPath.length-1];
					for(int j=0;j<auxPath.length;j++) {
						auxPath[j]=inputPath[j];
					}
					innerObject = getInnerObject(jo, auxPath);
				}
				return innerObject.get(outputField);
			}
		}
		return null;
	}
	
	public static String getStringFromValue(JSONArray json, String[] inputPath, Object inputValue,String outputField) {
		return getValueFromValue(json, inputPath, inputValue, outputField).toString();		
	}
	
	public static Long getLongFromValue(JSONArray json, String[] inputPath, Object inputValue,String outputField) {
		return (Long)getValueFromValue(json, inputPath, inputValue, outputField);		
	}
	
	@SuppressWarnings("unchecked")
	public static JSONArray returnJsonArray(Object object) throws MalformedURLException, IOException, ParseException {
		JSONArray ret = null;
		try {
			ret = (JSONArray)object;
		} catch (Exception e) {
			ret = new JSONArray();
			ret.add((JSONObject)object);
		}
		return ret;
	}

}
