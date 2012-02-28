package com.app.tripweather;

public class Trip {
	private Route[] route;
	private int distance;
	private int duration;
	
	public Route[] getRoute() {
		return route;
	}

	public void setRoute(Route[] route) {
		this.route = route;
	}

	public int getDistance() {
		return distance;
	}

	public void setDistance(int distance) {
		this.distance = distance;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}
	
	
}
