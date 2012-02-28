package com.app.tripweather;

import java.util.ArrayList;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class NewDirectionsActivity extends ListActivity {

	static String[] information;
	private MyStringAdapter stringAdapter;
	private String origin;
	private String dest;
	static Route[] steps;
	private double latOfSelectedRoute;
	private double lngOfSelectedRoute;
	private String status;
	private JSONMapsParser response;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.directions);
		setOrigin(getIntent().getExtras().getStringArray("request")[0]);
		setDest(getIntent().getExtras().getStringArray("request")[1]);

		response = new JSONMapsParser(origin, dest);

		status = response.getStatus();
		steps = response.getRoutes();

		if (status == null || response.checkStatus(status) != 0) {
			// different status information, to display to the user.
			information = new String[1];
			if (status.equals("NOT_FOUND")) {
				information[0] = "At least one of the locations specified in the requests's origin or destination could "
						+ "not be geocoded. Please try again";
			} else if (status.equals("ZERO_RESULTS")) {
				information[0] = "No route could be found between the origin and destination. Please try again";
			} else if (status.equals("INVALID_REQUEST")) {
				information[0] = "The provided request was invalid. Please try again";
			} else if (status.equals("OVER_QUERY_LIMIT")) {
				information[0] = "The service has received too many requests from your "
						+ "application within the allowed time period. Please try again later";
			} else if (status.equals("REQUEST_DENIED")) {
				information[0] = "Service denied use of the directions service by your application. Please try again.";
			} else {
				information[0] = "Directions request could not be processed due to a server error. The request may succeed if you try again.";
			}
		} else {
			// if the directions available, get all routes and add to array of
			// directions
			information = new String[response.getRoutes().length + 1];
			for (int i = 0; i < response.getRoutes().length; i++) {
				information[i] = response.getRoutes()[i].info();
			}
			information[response.getRoutes().length] = response.getRoutes()[0]
					.getCopyright();
		}
		if (response.checkStatus(status) == 0) {
			Toast.makeText(this,
					"Click on a route to see weather information.",
					Toast.LENGTH_LONG).show();
			stringAdapter = new MyStringAdapter(this, R.layout.list_item,
					altDisplay(steps));

		} else {
			TextView t = (TextView) findViewById(R.id.trip_info);
			t.setText(information[0]);
			t.setTextSize(20);

		}
		setListAdapter(stringAdapter);
		// only attach a listener if the list of directions is available
		if (response.checkStatus(status) == 0) {
			ListView lv = getListView();
			lv.setTextFilterEnabled(true);
			lv.setOnItemClickListener(new OnItemClickListener() {
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					// only do something if item clicked on is not copyright
					// info.
					if (position != information.length - 1) {
						setLatOfSelectedRoute(steps[position].avgLatitude());
						setLngOfSelectedRoute(steps[position].avgLongitude());
						createWeather();
					}
				}
			});
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.dir_menu, menu);
		return true;
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		if (response.checkStatus(status) != 0) {
			menu.getItem(1).setVisible(false);
		}
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection
		switch (item.getItemId()) {
		case R.id.about:
			showAbout();
			return true;
		case R.id.detail_dirs:
			showDetailDirections();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	private void showDetailDirections() {
		Intent i = new Intent(NewDirectionsActivity.this,
				DetailedDirectionsActivity.class);
		startActivity(i);
	}

	private void showAbout() {
		Intent i = new Intent(NewDirectionsActivity.this,
				DisplayAboutInfoActivity.class);
		startActivity(i);
		// new AlertDialog.Builder(this)
		// .setTitle("About")
		// .setItems(new String[]{"About goes here"}, null).show();
	}

	private class MyStringAdapter extends ArrayAdapter<String> {
		private String[] strings;

		public MyStringAdapter(Context context, int textViewResourceId,
				String[] strings) {
			super(context, textViewResourceId, strings);
			this.strings = strings;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View v = convertView;
			if (v == null) {
				LayoutInflater vi = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				v = vi.inflate(R.layout.list_item, null);
			}
			TextView t = (TextView) v.findViewById(R.id.one_step);
			t.setText(strings[position]);

			return v;
		}
	}

	/**
	 * Setter for the destination field.
	 * 
	 * @param dest
	 *            the destination of the trip.
	 */
	public void setDest(String dest) {
		this.dest = dest;
	}

	/**
	 * Getter for the destination field.
	 * 
	 * @return String the destination of the trip.
	 */
	public String getDest() {
		return dest;
	}

	/**
	 * Setter for the origin field.
	 * 
	 * @param dest
	 *            the origin of the trip.
	 */
	public void setOrigin(String origin) {
		this.origin = origin;
	}

	/**
	 * Getter for the origin field.
	 * 
	 * @return String the origin of the trip.
	 */
	public String getOrigin() {
		return origin;
	}

	/**
	 * Private method that fires an intent to start the weather display activity
	 */
	private void createWeather() {
		Intent i = new Intent(this, NewWeatherDisplayActivity.class);
		double[] infoArr = new double[2];
		infoArr[0] = latOfSelectedRoute;
		infoArr[1] = lngOfSelectedRoute;
		i.putExtra("new_weather_request", infoArr);
		startActivity(i);
	}

	/**
	 * Sets the latitude of the selected route.
	 * 
	 * @param latOfSelectedRoute
	 *            the latOfSelectedRoute to set
	 */
	public void setLatOfSelectedRoute(double latOfSelectedRoute) {
		this.latOfSelectedRoute = latOfSelectedRoute;
	}

	/**
	 * Gets latitude of selected route.
	 * 
	 * @return the latOfSelectedRoute
	 */
	public double getLatOfSelectedRoute() {
		return latOfSelectedRoute;
	}

	/**
	 * Set longitude of selected route.
	 * 
	 * @param lngOfSelectedRoute
	 *            the lngOfSelectedRoute to set
	 */
	public void setLngOfSelectedRoute(double lngOfSelectedRoute) {
		this.lngOfSelectedRoute = lngOfSelectedRoute;
	}

	/**
	 * Gets the longitude of the route.
	 * 
	 * @return the lngOfSelectedRoute
	 */
	public double getLngOfSelectedRoute() {
		return lngOfSelectedRoute;
	}

	public String[] altDisplay(Route[] arr) {
		ArrayList<Route> newSteps = new ArrayList<Route>();
		ArrayList<Place> display = new ArrayList<Place>();
		JSONLocationParser parser = new JSONLocationParser();
		for (int k = 0; k < arr.length; k++) {
			Location loc = parser.locate(arr[k].avgLatitude(), arr[k].avgLongitude());
			String city = loc.getCity();
			String state = loc.getState();
			Place place = new Place(city, state);
			if (!display.contains(place)) {
				display.add(place);
				newSteps.add(arr[k]);
			}
		}
		String[] answer = new String[display.size()];
		for (int i = 0; i < answer.length; i++) {
			answer[i] = display.get(i).toString();;
		}
		Route[] newStepsArr = new Route[newSteps.size()];
		for (int j = 0; j < newStepsArr.length; j++) {
			newStepsArr[j] = newSteps.get(j);
		}
		setSteps(newStepsArr);
		return answer;
	}

	private void setSteps(Route[] newStepsArr) {
		System.arraycopy(newStepsArr, 0, steps, 0, newStepsArr.length);
	}

}
