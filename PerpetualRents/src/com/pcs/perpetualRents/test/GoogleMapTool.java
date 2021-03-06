package com.pcs.perpetualRents.test;

import java.io.InputStream;
import java.net.URL;
import java.net.URLEncoder;

import org.json.JSONArray;
import org.json.JSONObject;


public class GoogleMapTool {

	public JSONArray findAddress(String fromAddress, String toAddress) {
		JSONArray jArray = new JSONArray();
		
		try {
			
		String requestUrl = new String();

		// Build the url
		//http://maps.googleapis.com/maps/api/distancematrix/xml?
		//origins=15+Bishop+Hall+Crescent+Bromsgrove+England+b603jr
		//&destinations=5+Bishop+Hall+Crescent+Bromsgrove+England+b603jr
		//&mode=bicycling
		//&language=fr-FR
		//&sensor=false
		
		requestUrl = "http://maps.googleapis.com/maps/api/distancematrix/json?";
		requestUrl += "&origins=" + fromAddress;
		requestUrl += "&destinations=" + toAddress;
		//requestUrl += "&mode=bicycling";
		requestUrl += "&language=en-US";
		requestUrl += "&sensor=false";
		
		System.out.println("Raw Value :" + requestUrl);
		
		requestUrl = "http://maps.googleapis.com/maps/api/distancematrix/json?";
		requestUrl += "&origins=" + URLEncoder.encode(fromAddress, "utf-8");
		requestUrl += "&destinations=" + URLEncoder.encode(toAddress, "utf-8");
		//requestUrl += "&mode=bicycling";
		requestUrl += "&language=en-US";
		requestUrl += "&sensor=false";
		
		System.out.println(requestUrl);
		
		URL url = new URL(requestUrl);
		InputStream iStream = url.openStream();
		
		byte[] buffer = new byte[iStream.available()];
		iStream.read(buffer);
		iStream.close();
		
		String str = new String(buffer);
		
		JSONObject jObj = new JSONObject(str);
		//System.out.println(jObj);
		
		JSONArray rows =  (JSONArray) jObj.get("rows");
		
		if(rows.length() != 0) {
			//System.out.println(rows);
			JSONObject rowsarrayelementObj =(JSONObject) rows.get(0);
			JSONArray elements = (JSONArray) rowsarrayelementObj.get("elements");
			JSONObject elementsarrayObj =  (JSONObject) elements.get(0);
			JSONObject distanceObj =  (JSONObject) elementsarrayObj.get("distance");
			Integer distanceValue = (Integer) distanceObj.get("value");
			
			JSONObject durationObj =  (JSONObject) elementsarrayObj.get("duration");
			Integer durationValue = (Integer) durationObj.get("value");
			
			System.out.println("from :" + fromAddress);
			System.out.println("to :" + toAddress);
			System.out.println("distanceValue :" + distanceValue);
			System.out.println("durationValue :" + durationValue);
			
			jArray.put(new JSONObject().put("distance", distanceValue).put("duration", durationValue));
		} else {
			System.out.println("no result");
		}
		
		} catch (Exception e) { System.out.println("unable to parse response"); }
		return jArray;
	}
	
	public static void main(String[] args) {
		JSONArray jArray = new GoogleMapTool().findAddress("Noida UP India", "ghaziabad UP India");
		System.out.println(jArray);
	}


}
