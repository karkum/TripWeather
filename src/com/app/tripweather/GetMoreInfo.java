package com.app.tripweather;

import java.util.Calendar;
import java.util.Date;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

public class GetMoreInfo extends Activity {

	private DatePicker datePicker;
	private TimePicker timePicker;
	private Button button;
	private String [] infoArr;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.date_time_info);
		infoArr = new String[3];
		infoArr[0] = getIntent().getStringArrayExtra("request")[0];
		infoArr[1] = getIntent().getStringArrayExtra("request")[1];
		button = (Button) findViewById(R.id.calcButton);
		button.setOnClickListener(new OnClickListener() {
			/**
			 * Internal listener for button. When button is clicked, checks
			 * whether the fields have been filled in and launches the list of
			 * directions activity.
			 */
			public void onClick(View v) {
				datePicker = (DatePicker) findViewById(R.id.datePicker1);
				timePicker = (TimePicker) findViewById(R.id.timePicker1);
				int year = datePicker.getYear();
				Date time = new Date(year, datePicker.getMonth(), datePicker
						.getDayOfMonth(), timePicker.getCurrentHour(),
						timePicker.getCurrentMinute());
				Date current = Calendar.getInstance().getTime();
				current.setDate(current.getDate() + 7);
				time.setYear(time.getYear() - 1900);
				if (time.after(current)) {
					Toast.makeText(
							GetMoreInfo.this,
							"Invalid start date (Must be within 7 seven days "
							+ "from today)", Toast.LENGTH_SHORT).show();
				}
				current.setDate(current.getDate() - 14);
				if (time.before(current)) {
					Toast.makeText(GetMoreInfo.this, "Invalid start date (Must be within 7 seven days "
							+ "from today)",
							Toast.LENGTH_SHORT).show();
				}
				else 
				{
					infoArr[2] = time.toString();
					createDirections();
				}
			}
		});

		// System.out.print(time.toLocaleString());
		
	}
	private void createDirections() {
		Intent i = new Intent(this, NewDirectionsActivity.class);
		i.putExtra("actual_request", infoArr);
		startActivity(i);
	}
	
}
