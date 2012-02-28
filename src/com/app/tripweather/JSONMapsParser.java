package com.app.tripweather;

/**
 * Parse Google Maps directions using JSON.
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
import org.json.JSONArray;
import org.json.JSONObject;

public class JSONMapsParser {

	private String result;
	private Route [] routes;
	private String status;
	private Trip trip;
	private static byte[] sBuffer = new byte[512];

	/**
	 * Constructor. Sets up the URL to communicate with Google Maps with given
	 * trip information. Attempts to parse the URL, throws exception if unsuccessful.
	 * 
	 * @param orig the starting point of the trip.
	 * @param dest the ending point of the trip.
	 */
	public JSONMapsParser(String orig, String dest)
	{
		result = "http://maps.googleapis.com/maps/api/directions/json?";

		StringBuilder str = new StringBuilder(result);
		str.append("origin=").append(orig);
		str.append("&destination=").append(dest);
		str.append("&sensor=false");	
		result = str.toString();

		try {
			parse();
		} catch (Exception e) {
			status = "NOT_FOUND";
		}
	}

	/**
	 * Parses a url pointing to a Google JSON object to a Route object.
	 * @throws Exception
	 */
	private void parse() throws Exception 
	{		
		String response = getUrlContent(result);	

		JSONObject jObject = new JSONObject(response);
		status = jObject.getString("status");

		//checks to make sure there are no errors in getting content.
		if (checkStatus(status) == 0)
		{
			//drills down the JSON object to find each individual step.
			JSONObject jsonRoute = jObject.getJSONArray("routes").getJSONObject(0);
			JSONObject leg = jsonRoute.getJSONArray("legs").getJSONObject(0);
			JSONObject tripDistObj = leg.getJSONObject("distance");
			JSONObject tripDurObj = leg.getJSONObject("duration");
			int distance = tripDistObj.getInt("value");
			int duration = tripDurObj.getInt("value");
			JSONArray steps = leg.getJSONArray("steps");
			routes = new Route [steps.length()];

			for (int i = 0; i < steps.length(); i++)
			{
				//updates more information about route for each step in trip in a array of routes.
				JSONObject step = steps.getJSONObject(i);
				routes[i] = new Route();
				routes[i].setInstruction(step.getString("html_instructions"));  
				routes[i].setDuration((step.getJSONObject("duration").getInt("value")));
				routes[i].setStartLoc(new Location(step.getJSONObject("start_location").getDouble("lat"),step.getJSONObject("start_location").getDouble("lng")));
				routes[i].setEndLoc(new Location(step.getJSONObject("end_location").getDouble("lat"),step.getJSONObject("end_location").getDouble("lng")));
				//Get google's copyright notice (requirement)
				routes[i].setCopyright(jsonRoute.getString("copyrights"));
				//Get the total length of this step.
				routes[i].setLength(step.getJSONObject("distance").getInt("value"));
				//Get any warnings provided (requirement)
				if (!jsonRoute.getJSONArray("warnings").isNull(0)) 
				{
					routes[i].setWarning(jsonRoute.getJSONArray("warnings").getString(0));
				}
			}
			trip = new Trip();
			trip.setRoute(routes);
			trip.setDuration(duration);
			trip.setDistance(distance);
		}
	}

	/**
	 * Returns the routes on this trip.
	 * @return Route[] the steps in this trip.
	 */
	public Route[] getRoutes()
	{
		return routes;
	}


	/**
	 * Uses a HTTP client to get the content from Google Maps
	 * @param url the link to get directions.
	 * @return String the route for this trip 
	 * @throws ApiException the exception thrown if any problem occurs.
	 */
	private String getUrlContent(String url) throws ApiException {

		// Create client and set our specific user-agent string
		HttpClient client = new DefaultHttpClient();
		HttpGet request = new HttpGet(url);
		//        request.setHeader("User-Agent", sUserAgent);

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
	@SuppressWarnings("serial")
	public static class ApiException extends Exception {
		public ApiException(String detailMessage, Throwable throwable) {
			super(detailMessage, throwable);
		}

		public ApiException(String detailMessage) {
			super(detailMessage);
		}
	}

	/**
	 * Checks to make sure the status is OK, so we can continue to drill JSON.
	 * @param status the status of the request.
	 * @return 0 if no problem in request, 1 otherwise.
	 */
	public int checkStatus(String status)
	{
		if (!(status.equals("NOT_FOUND") || status.equals("ZERO_RESULTS") || status.equals("INVALID_REQUEST") || 
				status.equals("OVER_QUERY_LIMIT") || status.equals("REQUEST_DENIED") || status.equals("UNKNOWN_ERROR")))
		{
			return 0;
		}
		else
		{
			return 1;
		}
	}

	/**
	 * Returns the status of the directions request.
	 * @return String the status message.
	 */
	public String getStatus()
	{
		return status;
	}
}
