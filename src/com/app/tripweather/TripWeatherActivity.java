package com.app.tripweather;

/**
 * Main screen. Receives trip information. 
 *
 * @author Karthik Kumar
 * @version 2011.01.05
 */

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class TripWeatherActivity extends Activity {

	private String startPoint;
	private String endPoint;
	private Button button;
	Intent i;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		button = (Button) findViewById(R.id.calcButton);
		button.setOnClickListener(new OnClickListener() {
			/**
			 * Internal listener for button. When button is clicked, checks
			 * whether the fields have been filled in and launches the list of
			 * directions activity.
			 */
			public void onClick(View v) {
				if (!isNetworkAvailable()) {
					Toast.makeText(TripWeatherActivity.this,
							"No Network Connection. Please try again later",
							Toast.LENGTH_LONG).show();
				} else {
					EditText start = (EditText) findViewById(R.id.start);
					EditText end = (EditText) findViewById(R.id.end);
					startPoint = start.getText().toString();
					endPoint = end.getText().toString();

					if (startPoint.length() == 0) {
						Toast.makeText(TripWeatherActivity.this,
								"Invalid start point", Toast.LENGTH_SHORT)
								.show();
					} else if (endPoint.length() == 0) {
						Toast.makeText(TripWeatherActivity.this,
								"Invalid end point", Toast.LENGTH_SHORT).show();
					} else if (startPoint.length() != 0
							&& endPoint.length() != 0) {
						getMoreInfo();
					}
				}
			}
		});

	}

	/**
	 * Getter for the origin.
	 * 
	 * @return String starting point for the trip.
	 */
	public String getStart() {
		return startPoint.trim().replaceAll(" ", "%20");

	}

	/**
	 * Getter for the destination.
	 * 
	 * @return String ending point for the trip.
	 */
	public String getEnd() {
		return endPoint.trim().replaceAll(" ", "%20");

	}

	/**
	 * Private method that fires an intent and opens the next activity.
	 */
	private void getMoreInfo() {
		Intent i = new Intent(this, NewDirectionsActivity.class);
		String[] arr = new String[2];
		arr[0] = getStart();
		arr[1] = getEnd();
		i.putExtra("request", arr);
		startActivity(i);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.dir_menu, menu);
		return true;
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
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
		Intent i = new Intent(TripWeatherActivity.this,
				DetailedDirectionsActivity.class);
		startActivity(i);
	}

	private void showAbout() {
		Intent i = new Intent(TripWeatherActivity.this,
				DisplayAboutInfoActivity.class);
		startActivity(i);
	}

	public boolean isNetworkAvailable() {
		ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = cm.getActiveNetworkInfo();
		// if no network is available networkInfo will be null, otherwise check
		// if we are connected
		if (networkInfo != null && networkInfo.isConnected()) {
			return true;
		}
		return false;
	}
}