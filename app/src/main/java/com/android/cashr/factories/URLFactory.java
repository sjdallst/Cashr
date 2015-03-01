package com.android.cashr.factories;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

public class URLFactory {
	private static final boolean DEBUG = true;
	
	private static final String customerKey = "CUST5207b274548a88b8d7b323a4e3c77016";
	private static final String apiURL = "http://api.reimaginebanking.com";
	private static final String keyURL = "?key=" + customerKey;

	private static JSONObject getJSONFromURL(String stringURL) {
		LogFactory.log(DEBUG);

		try {
			LogFactory.log(DEBUG, "Downloading From: " + stringURL);

			URL url = new URL(stringURL);
			InputStream inputStream = url.openStream();

			BufferedReader bufferedReader = new BufferedReader
					(new InputStreamReader(inputStream, "UTF-8"), 8);

			StringBuilder stringBuilder = new StringBuilder();

			String line;
			while ((line = bufferedReader.readLine()) != null) {
				stringBuilder.append(line).append("\n");
			}

			LogFactory.log(DEBUG, "Downloaded From: " + stringURL);

			return new JSONObject(stringBuilder.toString());
		}
		catch (MalformedURLException e) {
			LogFactory.log(DEBUG, "MalformedURLException: " + e.toString());
		}
		catch (IOException e) {
			LogFactory.log(DEBUG, "IOException: " + e.toString());
		}
		catch (JSONException e) {
			LogFactory.log(DEBUG, "JSONException: " + e.toString());
		}

		return null;
	}

	public static JSONObject getCustomerInfo(String userID) {
		return getJSONFromURL(apiURL + "/customers/" + userID + keyURL);
	}
}
