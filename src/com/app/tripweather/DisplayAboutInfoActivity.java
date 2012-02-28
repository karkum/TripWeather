package com.app.tripweather;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class DisplayAboutInfoActivity extends Activity {
	private String about;
	private TextView txt;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialog);
		txt = (TextView)findViewById(R.id.dialog_text);
		about = "Map data ©2011 Google\n";
		about += "Weather data ©2011 WeatherBug\n";
		about += "Application built by Karthik Kumar.\n\n";
		about += "Please email kkumar9844@gmail.com for questions and concerns";
		txt.setText(about);
	}
}
