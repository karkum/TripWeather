package com.app.tripweather;

/**
 * Second activity. Displays the list of directions that must be traveled on the trip.
 *
 * @author Karthik Kumar
 * @version 2011.01.05
 */

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class DetailedDirectionsActivity extends ListActivity
{
	private Route[] steps;
	private double lngOfSelectedRoute;
	private double latOfSelectedRoute;

	/**
	 * Called when the activity is created. This method gets the trip information from
	 * the previous activity, checks whether the information for Google Maps is valid. If 
	 * it is, gets route information and displays via a list adapter.
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setSteps(NewDirectionsActivity.steps);

		setListAdapter(new ArrayAdapter<String>(this, R.layout.list_item, 
				NewDirectionsActivity.information));


		ListView lv = getListView();

		lv.setOnItemClickListener(new OnItemClickListener() 
		{
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) 
			{
				//only do something if item clicked on is not copyright info.
				if (position != NewDirectionsActivity.information.length - 1)
				{
					setLatOfSelectedRoute(steps[position].avgLatitude());
					setLngOfSelectedRoute(steps[position].avgLongitude());
					createWeather();
				}
			}

		});
	}


	private void setSteps(Route[] stringArray) {
		steps = stringArray;
	}

	/**
	 * Private method that fires an intent to start the weather display activity
	 */
	private void createWeather()
	{
		Intent i = new Intent(DetailedDirectionsActivity.this, NewWeatherDisplayActivity.class);
		double [] infoArr = new double[2];
		infoArr[0] = latOfSelectedRoute;
		infoArr[1] = lngOfSelectedRoute;
		i.putExtra("new_weather_request", infoArr);
		startActivity(i);
	}
	private void setLatOfSelectedRoute(double avgLatitude) {
		latOfSelectedRoute = avgLatitude;
	}
	private void setLngOfSelectedRoute(double avgLongitude) {
		lngOfSelectedRoute = avgLongitude;
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.dir_menu, menu);
	    return true;
	}
	@Override
	public boolean onPrepareOptionsMenu (Menu menu) {
	    menu.getItem(1).setVisible(false);
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
		Intent i = new Intent(DetailedDirectionsActivity.this, DetailedDirectionsActivity.class);
		startActivity(i);
	}
	private void showAbout() {
		Intent i = new Intent(DetailedDirectionsActivity.this, DisplayAboutInfoActivity.class);
		startActivity(i);
	}
}
