package com.android.cashr.factories;

import android.util.Log;

public class LogFactory {
	private static final String TAG = "Cashr";

	public static void log(boolean DEBUG) {
		if(DEBUG) {
			String method = new Exception().getStackTrace()[1].getClassName();
			method += "." + new Exception().getStackTrace()[1].getMethodName();

			Log.d(TAG, method + "()");
		}
	}

	public static void log(boolean DEBUG, String info) {
		if(DEBUG) {
			String method = new Exception().getStackTrace()[1].getClassName();
			method += "." + new Exception().getStackTrace()[1].getMethodName();

			Log.d(TAG, method + "():- " + info);
		}
	}

	public static void error(boolean DEBUG, Exception exception) {
		if(DEBUG) {
			String method = new Exception().getStackTrace()[1].getClassName();
			method += "." + new Exception().getStackTrace()[1].getMethodName();

			Log.e(TAG, method + "():- " + exception.toString());
		}
	}
}