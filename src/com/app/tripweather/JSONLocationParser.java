package com.app.tripweather;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

public class JSONLocationParser {

	private Location location;
	private static byte[] sBuffer = new byte[512];
	private String locationResult;
	private final String API_KEY = "2g5jr73c2qcb6697dzgeagtf";
	
	public JSONLocationParser() {
		
	}
	
	public Location locate (double lat, double lng) {

		locationResult = "http://i.wxbug.net/REST/Direct/GetLocation.ashx?";

		StringBuilder string = new StringBuilder(locationResult);
		string.append("la=").append(lat).append("&lo=").append(lng);
		string.append("&api_key=").append(API_KEY);
		locationResult = string.toString();
		
		try 
		{
			parseLocation();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return getLocation();
	}
	/**
	 * Parses a url pointing to a WeatherBug location JSON object to a Location object.
	 * @throws Exception
	 */
	private void parseLocation() throws Exception 
	{		
		String response = getUrlContent(locationResult);	

		JSONObject jObject = new JSONObject(response);
		JSONObject jLocation = jObject.getJSONObject("location");

		location = new Location();
		location.setCity(jLocation.getString("city"));
		location.setState(jLocation.getString("state"));
		location.setUs(jLocation.getBoolean("isUs"));
		location.setZipCode(jLocation.getInt("zipCode"));
	}
	/**
	 * Uses a HTTP client to get the content from WeatherBug
	 * @param url the link to get conditions.
	 * @return String conditions for this trip 
	 * @throws ApiException the exception thrown if any problem occurs.
	 */
	private String getUrlContent(String url) throws ApiException {

		// Create client and set our specific user-agent string
		HttpClient client = new DefaultHttpClient();
		HttpGet request = new HttpGet(url);
		//request.setHeader("User-Agent", sUserAgent);

		try {
			HttpResponse response = client.execute(request);

			// Pull content stream from response
			HttpEntity entity = response.getEntity();
			InputStream inputStream = entity.getContent();

			ByteArrayOutputStream content = new ByteArrayOutputStream();

			// Read response into a buffered stream
			int readBytes = 0;
			while ((readBytes = inputStream.read(sBuffer)) != -1) {
				content.write(sBuffer, 0, readBytes);
			}

			// Return result from buffered stream
			return new String(content.toByteArray());
		} catch (IOException e) {
			throw new ApiException("Problem communicating with API", e);
		}
	}
	/**
	 * Exception thrown when there is an error with getting info from API.
	 *
	 */
	@SuppressWarnings("serial")
	public static class ApiException extends Exception 
	{
		public ApiException(String detailMessage, Throwable throwable) 
		{
			super(detailMessage, throwable);
		}
		public ApiException(String detailMessage) 
		{
			super(detailMessage);
		}
	}
	/**
	 * Returns the location of this step.
	 * @return Location the steps in this trip.
	 */
	public Location getLocation()
	{
		return location;
	}
}
