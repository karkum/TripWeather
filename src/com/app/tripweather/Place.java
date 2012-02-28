package com.app.tripweather;

public class Place {
	String city;
	String state;
	
	public Place(String c, String s) {
		city = c;
		state = s;
	}

	/**
	 * @return the city
	 */
	public String getCity() {
		return city;
	}

	/**
	 * @param city the city to set
	 */
	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * @return the state
	 */
	public String getState() {
		return state;
	}

	/**
	 * @param state the state to set
	 */
	public void setState(String state) {
		this.state = state;
	}
	public boolean equals(Object other) {
		if (other != null) {
			if (other instanceof Place) {
				Place oth = (Place)other;
				if (this.city.equals(oth.getCity()) && this.state.equals(oth.getState()))
					return true;
			}
		}
		return false;
	}
	public String toString() {
		return city + ", " + state;
	}
}
