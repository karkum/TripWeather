package com.app.tripweather;

/**
 * Represents a location on a map with a longitude and latitude..
 *
 * @author Karthik Kumar
 * @version 2011.01.05
 */


public class Location {

	private double latitude;
	private double longitude;
	private int zipCode;
	private String state;
	private String city;
	private boolean isUs;
	
	/**
	 * Non-default Constructor.
	 * @param lat the latitude of the location.
	 * @param lng the longitude of the location.
	 */
	public Location (double lat, double lng)
	{
		latitude = lat;
		longitude = lng;
	}
	
	/**
	 * 
	 */
	public Location()
	{
		//Empty default constructor.
	}

	/**
	 * Setter for the latitude field
	 * @param latitude the latitude to set
	 */
	public void setLatitude(double latitude) 
	{
		this.latitude = latitude;
	}

	/**
	 * Getter for the latitude field
	 * @return double the latitude
	 */
	public double getLatitude() 
	{
		return latitude;
	}

	/**
	 * Setter for the longitude field
	 * @param longitude the longitude to set
	 */
	public void setLongitude(double longitude) 
	{
		this.longitude = longitude;
	}

	/**
	 * Getter for the longitude field
	 * return double the longitude
	 */
	public double getLongitude() 
	{
		return longitude;
	}

	/**
	 * @param zipCode the zipCode to set
	 */
	public void setZipCode(int zipCode)
	{
		this.zipCode = zipCode;
	}

	/**
	 * @return the zipCode
	 */
	public int getZipCode() 
	{
		return zipCode;
	}

	/**
	 * @param state the state to set
	 */
	public void setState(String state) 
	{
		this.state = state;
	}

	/**
	 * @return the state
	 */
	public String getState() 
	{
		return state;
	}

	/**
	 * @param city the city to set
	 */
	public void setCity(String city) 
	{
		this.city = city;
	}

	/**
	 * @return the city
	 */
	public String getCity() 
	{
		return city;
	}

	/**
	 * @param isUs the isUs to set
	 */
	public void setUs(boolean isUs) 
	{
		this.isUs = isUs;
	}

	/**
	 * @return the isUs
	 */
	public boolean getIsUs() 
	{
		return isUs;
	}
}
