package com.app.tripweather;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Third activity. Displays the weather conditions for a selected route.
 *
 * @author Karthik Kumar
 * @version 2011.01.05
 */

public class NewWeatherDisplayActivity extends Activity
{
	/* The TextView for placing our temperature information */
	private TextView day1;
	private TextView day2;
	private TextView day3;
	private TextView day4;
	private TextView day5;
	private TextView day6;
	private TextView day7;

	private ImageView icon1;
	private ImageView icon2;
	private ImageView icon3;
	private ImageView icon4;
	private ImageView icon5;
	private ImageView icon6;
	private ImageView icon7;

	private TextView day1high;
	private TextView day2high;
	private TextView day3high;
	private TextView day4high;
	private TextView day5high;
	private TextView day6high;
	private TextView day7high;

	private TextView day1low;
	private TextView day2low;
	private TextView day3low;
	private TextView day4low;
	private TextView day5low;
	private TextView day6low;
	private TextView day7low;



	/* The location of our requested information */
	private Location location;

	/* The URL for opening the weather bug mobile website */
	private String webUrl = "http://weather-mobile.weatherbug.com/";

	/* The degree symbol in unicode */
	private final String DEGREE_SYMBOL = "\u00B0";

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.forecast);

		XMLWeatherParser response = new XMLWeatherParser(getIntent().getDoubleArrayExtra("new_weather_request")[0], 
				getIntent().getDoubleArrayExtra("new_weather_request")[1]);
		ArrayList <Weather> forecast = response.getForecast();
		StringBuilder url = new StringBuilder(webUrl);
		location = response.getLocation();
		url.append(location.getState()).append("/").append(location.getCity()).append("local-forecast/detailed-forecast.aspx?ftype=1&fcurr=0&cid=0");
		webUrl = url.toString();
		TextView infoText = (TextView) findViewById(R.id.forecastInfo);
		infoText.setText("Forecast for " + location.getCity() + ", " + location.getState() + ":");
		{
			day1 = (TextView) findViewById(R.id.day_1);
			day1.setText(forecast.get(0).getDay().toUpperCase());
			icon1 = (ImageView) findViewById(R.id.day_1_icon);

			icon1.setOnClickListener(new OnClickListener(){
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
			Drawable icon = downloadFile(forecast.get(0).getIcon());

			if (icon != null)
			{
				icon1.setImageDrawable(icon);
				
			}
			
				
			day1high = (TextView) findViewById(R.id.day_1_high);
			if (forecast.get(0).getHigh() == -1)
				day1high.setText("--");
			else
				day1high.setText(forecast.get(0).getHigh() + DEGREE_SYMBOL);			
			day1low = (TextView) findViewById(R.id.day_1_low);
			day1low.setText(forecast.get(0).getLow() + DEGREE_SYMBOL);
		}
		{
			day2 = (TextView) findViewById(R.id.day_2);
			day2.setText(forecast.get(1).getDay().toUpperCase());
			icon2 = (ImageView) findViewById(R.id.day_2_icon);

			icon2.setOnClickListener(new OnClickListener(){
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
			Drawable icon = downloadFile(forecast.get(1).getIcon());

			if (icon != null)
			{
				icon2.setImageDrawable(icon);
			}
			
			day2high = (TextView) findViewById(R.id.day_2_high);
			day2high.setText(forecast.get(1).getHigh() + DEGREE_SYMBOL);
			
			day2low = (TextView) findViewById(R.id.day_2_low);
			day2low.setText(forecast.get(1).getLow() + DEGREE_SYMBOL);
		}
		{
			day3 = (TextView) findViewById(R.id.day_3);
			day3.setText(forecast.get(2).getDay().toUpperCase());
			icon3 = (ImageView) findViewById(R.id.day_3_icon);

			icon3.setOnClickListener(new OnClickListener(){
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
			Drawable icon = downloadFile(forecast.get(2).getIcon());

			if (icon != null)
			{
				icon3.setImageDrawable(icon);
			}
			
			day3high = (TextView) findViewById(R.id.day_3_high);
			day3high.setText(forecast.get(2).getHigh() + DEGREE_SYMBOL);
			
			day3low = (TextView) findViewById(R.id.day_3_low);
			day3low.setText(forecast.get(2).getLow() + DEGREE_SYMBOL);
		}

		{
			day4 = (TextView) findViewById(R.id.day_4);
			day4.setText(forecast.get(3).getDay().toUpperCase());
			icon4 = (ImageView) findViewById(R.id.day_4_icon);

			icon4.setOnClickListener(new OnClickListener(){
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
			Drawable icon = downloadFile(forecast.get(3).getIcon());

			if (icon != null)
			{
				icon4.setImageDrawable(icon);
			}
			
			day4high = (TextView) findViewById(R.id.day_4_high);
			day4high.setText(forecast.get(3).getHigh() + DEGREE_SYMBOL);
			
			day4low = (TextView) findViewById(R.id.day_4_low);
			day4low.setText(forecast.get(3).getLow() + DEGREE_SYMBOL);
		}

		{
			day5 = (TextView) findViewById(R.id.day_5);
			day5.setText(forecast.get(4).getDay().toUpperCase());
			icon5 = (ImageView) findViewById(R.id.day_5_icon);

			icon5.setOnClickListener(new OnClickListener(){
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
			Drawable icon = downloadFile(forecast.get(4).getIcon());

			if (icon != null)
			{
				icon5.setImageDrawable(icon);
			}
			
			day5high = (TextView) findViewById(R.id.day_5_high);
			day5high.setText(forecast.get(4).getHigh() + DEGREE_SYMBOL);
			
			day5low = (TextView) findViewById(R.id.day_5_low);
			day5low.setText(forecast.get(4).getLow() + DEGREE_SYMBOL);
		}
		{
			day6 = (TextView) findViewById(R.id.day_6);
			day6.setText(forecast.get(5).getDay().toUpperCase());
			icon6 = (ImageView) findViewById(R.id.day_6_icon);

			icon6.setOnClickListener(new OnClickListener(){
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
			Drawable icon = downloadFile(forecast.get(5).getIcon());

			if (icon != null)
			{
				icon6.setImageDrawable(icon);
			}
			
			day6high = (TextView) findViewById(R.id.day_6_high);
			day6high.setText(forecast.get(5).getHigh() + DEGREE_SYMBOL);
			
			day6low = (TextView) findViewById(R.id.day_6_low);
			day6low.setText(forecast.get(5).getLow() + DEGREE_SYMBOL);
		}

		{
			day7 = (TextView) findViewById(R.id.day_7);
			day7.setText(forecast.get(6).getDay().toUpperCase());
			icon7 = (ImageView) findViewById(R.id.day_7_icon);

			icon7.setOnClickListener(new OnClickListener(){
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
			Drawable icon = downloadFile(forecast.get(6).getIcon());

			if (icon != null)
			{
				icon7.setImageDrawable(icon);
			}
			
			day7high = (TextView) findViewById(R.id.day_7_high);
			day7high.setText(forecast.get(6).getHigh() + DEGREE_SYMBOL);
			
			day7low = (TextView) findViewById(R.id.day_7_low);
			if (forecast.get(6).getLow() == -1)
				day7low.setText("--");
			else
				day7low.setText(forecast.get(6).getLow()+ DEGREE_SYMBOL);
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

