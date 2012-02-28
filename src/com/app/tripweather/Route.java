package com.app.tripweather;

/**
 * Represents a step or instruction in the trip.
 *
 * @author Karthik Kumar
 * @version 2011.01.05
 */


public class Route {

	private Location startLoc;
	private Location endLoc;
	private String instruction;
	private int duration;
	private String copyright;
	private int length;
	private String warning;

	/**
	 * Setter for starting location.
	 * @param startLoc starting location.
	 */
	public void setStartLoc(Location startLoc) 
	{
		this.startLoc = startLoc;
	}
	
	/**
	 * Getter for starting location.
	 * @return Location the starting Location
	 */
	public Location getStartLoc() 
	{
		return startLoc;
	}
	
	/**
	 * Setter for ending location.
	 * @param endLoc ending location.
	 */
	public void setEndLoc(Location endLoc) 
	{
		this.endLoc = endLoc;
	}
	
	/**
	 * Getter for ending location.
	 * @return Location the ending Location
	 */
	public Location getEndLoc() 
	{
		return endLoc;
	}
	
	/**
	 * Setter for the instruction field for this route.
	 * @param instruction the instruction to set.
	 */
	public void setInstruction(String instruction) 
	{
		this.instruction = instruction;
	}
	
	/**
	 * Getter for the instruction field for this route.
	 * @return String instruction.
	 */
	public String getInstruction() 
	{
		return instruction;
	}
	
	/**
	 * Sets the duration of this step.
	 * @param duration duration to set.
	 */
	public void setDuration(int duration) 
	{
		this.duration = duration;
	}
	
	/**
	 * Gets the duration of this step of the trip in seconds.
	 * @return int the number of seconds spent on this route.
	 */
	public int getDuration() 
	{
		return duration;
	}
	
	/**
	 * Returns the instruction formatted for readability
	 * @return String the formatted instruction.
	 */
	public String info()
	{
		return instruction.replaceAll("<(.*?)*>", "");
	}

	/**
	 * Returns the average latitude between the starting location and the ending location of this route.
	 * @return double average latitude of route.
	 */
	public double avgLatitude()
	{
		return (startLoc.getLatitude() + endLoc.getLatitude()) / 2;
	}
	
	/**
	 * Returns the average longitude between the starting location and the ending location of this route.
	 * @return double average longitude of route.
	 */
	public double avgLongitude()
	{
		return (startLoc.getLongitude() + endLoc.getLongitude()) / 2;
	}

	/**
	 * @param copyright the copyright to set
	 */
	public void setCopyright(String copyright) 
	{
		this.copyright = copyright;
	}

	/**
	 * @return the copyright
	 */
	public String getCopyright() 
	{
		return copyright;
	}

	/**
	 * @param length the length to set
	 */
	public void setLength(int length) 
	{
		this.length = length;
	}

	/**
	 * @return the length
	 */
	public int getLength() 
	{
		return length;
	}

	/**
	 * @param warning the warning to set
	 */
	public void setWarning(String warning) 
	{
		this.warning = warning;
	}

	/**
	 * @return the warning
	 */
	public String getWarning() 
	{
		return warning;
	}
}
