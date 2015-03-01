package com.android.cashr.factories;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.android.cashr.factories.LogFactory;

public class NetworkFactory {
	private static final boolean DEBUG = false;

	public static boolean isConnected(Context context) {
		LogFactory.log(DEBUG);

		boolean connectedToWifi = false;
		boolean connectedToNetwork = false;

		ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo[] netInfo = cm.getAllNetworkInfo();

		for (NetworkInfo ni : netInfo) {
			if (ni.getTypeName().equalsIgnoreCase("WIFI")) {
				if (ni.isConnected()) { connectedToWifi = true; }
			}
			if (ni.getTypeName().equalsIgnoreCase("MOBILE")) {
				if (ni.isConnected()) { connectedToNetwork = true; }
			}
		}

		if(connectedToWifi) {
			LogFactory.log(DEBUG, "Connected to WiFi");
		}
		if(connectedToNetwork) {
			LogFactory.log(DEBUG, "Connected to Cellular Network");
		}

		return connectedToWifi || connectedToNetwork;
	}
}
