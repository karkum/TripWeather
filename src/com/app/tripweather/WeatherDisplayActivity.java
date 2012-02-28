package com.app.tripweather;

import java.io.InputStream;
import java.net.URL;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Third activity. Displays the weather conditions for a selected route.
 *
 * @author Karthik Kumar
 * @version 2011.01.05
 */

public class WeatherDisplayActivity extends Activity
{
	/* The TextView for placing our temperature information */
	private TextView tempInfo;

	/* The TextView for placing our weather description */
	private TextView descInfo;

	/* The TextView for placing our feels like information */
	private TextView flInfo;

	/* The TextView for placing our chance of precipitation information */
	private TextView cpInfo;

	/* The ImageView for placing our condition */
	private ImageView condition;

	/* The location of our requested information */
	private Location location;
	
	/* The copyright information from Weatherbug */
	private TextView info;

	/* The URL for getting the icon of the weather from Weather Bug*/
	private String imageURL = "http://img.weather.weatherbug.com/forecast/icons/localized/200x168/en/trans/";

	/* The URL for opening the weather bug mobile website */
	private String webUrl = "http://weather-mobile.weatherbug.com/";

	/* The degree symbol in unicode */
	private final String DEGREE_SYMBOL = "\u00B0";

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.weather_display);

		JSONWeatherParser response = new JSONWeatherParser(getIntent().getDoubleArrayExtra("new_weather_request")[0], 
				getIntent().getDoubleArrayExtra("new_weather_request")[1]);
		XMLWeatherParser test = new XMLWeatherParser(getIntent().getDoubleArrayExtra("new_weather_request")[0], 
				getIntent().getDoubleArrayExtra("new_weather_request")[1]);
		Weather weather = response.getWeather();
		location = response.getLocation();

		// makes sure the location is in the US, (weatherbug only valid for US)
		if (location.getIsUs())
		{
			tempInfo = (TextView) findViewById(R.id.tempInfo);
			tempInfo.setText(weather.getTemperature()+ DEGREE_SYMBOL + "F");

			descInfo = (TextView) findViewById(R.id.descInfo);
			descInfo.setText(weather.getDescription());

			flInfo = (TextView) findViewById(R.id.flInfo);
			flInfo.setText(weather.getFeelsLike() + DEGREE_SYMBOL + "F");

			cpInfo = (TextView) findViewById(R.id.cpInfo);
			cpInfo.setText(weather.getChanceOfPrecipatation() + "%");

			info = (TextView) findViewById(R.id.info);
			
			condition = (ImageView) findViewById(R.id.cond);
			StringBuilder str = new StringBuilder(imageURL);
			str.append(weather.getIcon()).append(".png");

			StringBuilder url = new StringBuilder(webUrl);
			url.append(location.getState()).append("/").append(location.getCity()).append("local-forecast/detailed-forecast.aspx?ftype=1&fcurr=0&cid=0");
			webUrl = url.toString();

			condition.setOnClickListener(new OnClickListener(){
				@Override
				/**
				 * Listens for clicks on the image, opens the weather bug website when pressed
				 * (Required by WeatherBug).
				 */
				public void onClick(View v) 
				{
					Uri uri = Uri.parse(webUrl);
					Intent intent = new Intent(Intent.ACTION_VIEW, uri);
					startActivity(intent);
				}
			});

			// Gets the weather condition icon.
			Drawable icon = downloadFile(str.toString());
			downloadFile(str.toString());

			if (icon != null)
			{
				condition.setImageDrawable(icon);
			}
			info.setText("Weather information for " + location.getCity() + ", "  + 
					location.getState() + "\n\u24D2" + "WeatherBug 2011");
		}
		else
		{
			Toast.makeText(this, "Cannot find weather.", Toast.LENGTH_LONG).show();
		}

	}

	/**
	 * This method downloads the weather icon from WeatherBug 
	 * @param fileUrl the url of the file
	 * @return
	 */
	private Drawable downloadFile(String fileUrl)
	{
		try
		{
			InputStream is = (InputStream) new URL(fileUrl).getContent();
			Drawable d = Drawable.createFromStream(is, "src");
			return d;
		}
		catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
}
