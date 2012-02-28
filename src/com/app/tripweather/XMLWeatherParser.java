package com.app.tripweather;

/**
 * Parse WeatherBug in JSON.
 *
 * @author Karthik Kumar
 * @version 2011.01.05
 */

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XMLWeatherParser {

	private String result;
	private Location location;
//	private String locationResult;
	private ArrayList <Weather> forecast;

	private final String API_KEY = "A5358941562";
	private static byte[] sBuffer = new byte[512];

	/**
	 * Constructor. Sets up the URL to communicate with WeatherBug with given
	 * route information. Attempts to parse the URL, throws exception if unsuccessful.
	 * 
	 * @param orig the starting point of the trip.
	 * @param dest the ending point of the trip.
	 */
	public XMLWeatherParser(double lat, double lng)
	{
		result = "http://" + API_KEY + ".api.wxbug.net/getForecastRSS.aspx?ACode=";
		//result = "http://i.wxbug.net/REST/Direct/GetForecastHourly.ashx?";
		//		locationResult = "http://i.wxbug.net/REST/Direct/GetLocation.ashx?";

		StringBuilder str = new StringBuilder(result);
		str.append(API_KEY + "&");
		str.append("lat=").append(lat);
		str.append("&long=").append(lng);
		str.append("&UnitType=0").append("OutputType=1");
		//		str.append("&=i&").append("ht=d&").append("ht=fl&").append("ht=t&").append("ht=cp&api_key=").append(API_KEY);	
		result = str.toString();

		//		StringBuilder string = new StringBuilder(locationResult);
		//		string.append("la=").append(lat).append("&lo=").append(lng);
		//		string.append("&api_key=").append(API_KEY);
		//		locationResult = string.toString();

		forecast = new ArrayList<Weather> (7);
		
		try 
		{
			parseWeather();
		} catch (Exception e) {
			e.printStackTrace();
		}
		//		try 
		//		{
		//			parseLocation();
		//		} catch (Exception e) {
		//			e.printStackTrace();
		//		}
	}

	/**
	 * Parses a url pointing to a WeatherBug JSON object to a Weather object.
	 * @throws Exception
	 */
	private void parseWeather() throws Exception 
	{		
		String response = getUrlContent(result);	
		InputStream stream = new ByteArrayInputStream(response.getBytes());

		location = new Location();

		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document doc = db.parse(stream);
		Element root = doc.getDocumentElement();
		NodeList items = root.getElementsByTagName("aws:weather");
		for (int i = 0; i < items.getLength(); i++) {
			Node property = items.item(i);
			NodeList weatherItems = property.getChildNodes();
			for (int j = 0; j < weatherItems.getLength(); j++) {
				Node n = weatherItems.item(j);
				String nm = n.getNodeName();
				if (nm.equals("aws:forecasts")) {
					NodeList actualInfo = n.getChildNodes();
					for (int k = 0; k < actualInfo.getLength(); k++) {
						Node pr = actualInfo.item(k);
						String testing = pr.getNodeName();
						if (testing.equals("aws:location")) {
							NodeList locationInfo = pr.getChildNodes();
							location.setCity(locationInfo.item(0).getTextContent());
							location.setState(locationInfo.item(1).getTextContent());
							location.setZipCode(Integer.valueOf(locationInfo.item(2).getTextContent()));
							location.setUs(true);
						}
						else if (testing.equals("aws:forecast")) {
							Weather weat = new Weather();
							NodeList listOfForecasts = pr.getChildNodes();
//							for (int m = 0; m < actualInfo.getLength(); m++) {
								String day = listOfForecasts.item(0).getTextContent();
								String shortPrediction = listOfForecasts.item(1).getTextContent();
								String icon = listOfForecasts.item(2).getTextContent();
								String prediction = listOfForecasts.item(4).getTextContent();
								String high = listOfForecasts.item(5).getTextContent();
								String low = listOfForecasts.item(6).getTextContent();
								weat.setDescription(prediction);
								weat.setDay(day);
								int h, l = 0;
								try {
									h = Integer.valueOf(high);
								} catch (Exception e) {
									h = -1;
								}
								try {
									l = Integer.valueOf(low);
								} catch (Exception e) {
									l = -1;
								}
								weat.setHigh(h);
								weat.setLow(l);
								weat.setIcon(icon);
								weat.setShortPrediction(shortPrediction);
								forecast.add(weat);
//							}
						}
						System.out.println(testing);
					}
				}
			}
			String name = property.getNodeName();
			System.out.println(name);
		}
		//		JSONObject jObject = new JSONObject(response);
		//		JSONObject forecast = jObject.getJSONArray("forecastHourlyList").getJSONObject(0);
		//		
		//		weather = new Weather();
		//		weather.setIcon(forecast.getString("icon"));
		//		int precip = Integer.valueOf(forecast.getString("chancePrecip"));
		//		weather.setChanceOfPrecipatation(precip);
		//		weather.setDescription(forecast.getString("desc"));
		//		weather.setFeelsLike(Integer.valueOf(forecast.getString("feelsLike")));
		//		weather.setTemperature(Integer.valueOf(forecast.getString("temperature")));
	}

	/**
	 * Parses a url pointing to a WeatherBug location JSON object to a Location object.
	 * @throws Exception
	 */
//	private void parseLocation() throws Exception 
//	{		
//		String response = getUrlContent(locationResult);	
//
//		JSONObject jObject = new JSONObject(response);
//		JSONObject jLocation = jObject.getJSONObject("location");
//
//		location = new Location();
//		location.setCity(jLocation.getString("city"));
//		location.setState(jLocation.getString("state"));
//		location.setUs(jLocation.getBoolean("isUs"));
//		location.setZipCode(jLocation.getInt("zipCode"));
//	}

	/**
	 * Returns the location of this step.
	 * @return Location the steps in this trip.
	 */
	public Location getLocation()
	{
		return location;
	}
	/**



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
	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public void setForecast(ArrayList<Weather> forecast) {
		this.forecast = forecast;
	}
	
	public ArrayList<Weather> getForecast() {
		return forecast;
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
