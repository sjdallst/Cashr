package com.android.cashr.tasks;

import android.content.Context;
import android.os.AsyncTask;

import com.android.cashr.activities.CreateAccountActivity;
import com.android.cashr.factories.AlertFactory;
import com.android.cashr.factories.LogFactory;
import com.android.cashr.factories.NetworkFactory;
import com.android.cashr.factories.URLFactory;

import org.json.JSONObject;

public class VerifyUser extends AsyncTask<String, Integer, Integer> {
	private final boolean DEBUG = true;

	private CreateAccountActivity mCreateAccountActivity;

	public VerifyUser(CreateAccountActivity locationsActivity) {
		LogFactory.log(DEBUG);
		mCreateAccountActivity = locationsActivity;
	}

	@Override
	protected void onPreExecute() {
		LogFactory.log(DEBUG);
	}

	@Override
	protected Integer doInBackground(String... params) {
		LogFactory.log(DEBUG);

		JSONObject jsonObject = URLFactory.getCustomerInfo(params[0]);
		if(jsonObject == null) {
			return 1;
		}
		
		if(jsonObject.optString("code").equals("400")) {
			return 2;
		}
		else if(jsonObject.optString("code").equals("403")) {
			return 3;
		}
		else if(jsonObject.optString("code").equals("404")) {
			return 4;
		}
 		
		return 0;
	}

	@Override
	protected void onPostExecute(Integer success) {
		LogFactory.log(DEBUG);

		Context context = mCreateAccountActivity.getApplicationContext();
		boolean haveNetworkConnection = NetworkFactory.isConnected(context);

		if (success == 0 && !haveNetworkConnection) {
			LogFactory.log(DEBUG, "No Network Connection");
			AlertFactory.snackbar(mCreateAccountActivity, "No Network Connection");
		}
		else if (success > 0) {
			LogFactory.log(DEBUG, "Error Loading Data");
			AlertFactory.snackbar(mCreateAccountActivity, "(" + success + ") Error Loading Data");
		}

		if (success == 0) {
			LogFactory.log(DEBUG, "User Successfully Created");
			AlertFactory.snackbar(mCreateAccountActivity, "You Are Ready to Login!");
		}
	}
}
