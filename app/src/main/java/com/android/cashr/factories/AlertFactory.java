package com.android.cashr.factories;

import android.app.Activity;

import com.github.mrengineer13.snackbar.SnackBar;

public class AlertFactory {
	private static final boolean DEBUG = false;

	public static void snackbar(Activity activity, String message) {
		LogFactory.log(DEBUG, message);

		new SnackBar.Builder(activity)
				.withMessage(message)
				.withDuration(SnackBar.SHORT_SNACK)
				.show();
	}
}
