package com.app.tripweather;

/**
 * Parse WeatherBug in JSON.
 *
 * @author Karthik Kumar
 * @version 2011.01.05
 */

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

public class JSONWeatherParser {

	private String result;
	private Weather weather;
	private Location location;
	private String locationResult;
	private final String API_KEY = "2g5jr73c2qcb6697dzgeagtf";
	private static byte[] sBuffer = new byte[512];

	/**
	 * Constructor. Sets up the URL to communicate with WeatherBug with given
	 * route information. Attempts to parse the URL, throws exception if unsuccessful.
	 * 
	 * @param orig the starting point of the trip.
	 * @param dest the ending point of the trip.
	 */
	public JSONWeatherParser(double lat, double lng)
	{
		result = "http://i.wxbug.net/REST/Direct/GetForecastHourly.ashx?";
		locationResult = "http://i.wxbug.net/REST/Direct/GetLocation.ashx?";

		StringBuilder str = new StringBuilder(result);
		str.append("la=").append(lat);
		str.append("&lo=").append(lng);
		str.append("&ht=i&").append("ht=d&").append("ht=fl&").append("ht=t&").append("ht=cp&api_key=").append(API_KEY);	
		result = str.toString();

		StringBuilder string = new StringBuilder(locationResult);
		string.append("la=").append(lat).append("&lo=").append(lng);
		string.append("&api_key=").append(API_KEY);
		locationResult = string.toString();

		try 
		{
			parseWeather();
		} catch (Exception e) {
			e.printStackTrace();
		}
		try 
		{
			parseLocation();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Parses a url pointing to a WeatherBug JSON object to a Weather object.
	 * @throws Exception
	 */
	private void parseWeather() throws Exception 
	{		
		String response = getUrlContent(result);	

		JSONObject jObject = new JSONObject(response);
		JSONObject forecast = jObject.getJSONArray("forecastHourlyList").getJSONObject(0);

		weather = new Weather();
		weather.setIcon(forecast.getString("icon"));
		int precip = Integer.valueOf(forecast.getString("chancePrecip"));
		weather.setChanceOfPrecipatation(precip);
		weather.setDescription(forecast.getString("desc"));
		weather.setFeelsLike(Integer.valueOf(forecast.getString("feelsLike")));
		weather.setTemperature(Integer.valueOf(forecast.getString("temperature")));
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
	 * Returns the location of this step.
	 * @return Location the steps in this trip.
	 */
	public Location getLocation()
	{
		return location;
	}
	/**
	 * Returns the weather on this step.
	 * @return Weather[] the steps in this trip.
	 */
	public Weather getWeather()
	{
		return weather;
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
}
