package com.android.cashr.tasks;

import android.content.Context;
import android.os.AsyncTask;

import com.android.cashr.R;
import com.android.cashr.activities.CreateAccountActivity;
import com.android.cashr.factories.AlertFactory;
import com.android.cashr.factories.LogFactory;
import com.android.cashr.factories.NetworkFactory;
import com.android.cashr.factories.URLFactory;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import org.json.JSONObject;

import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class VerifyUserAndMakeAccount extends AsyncTask<String[], Integer, Integer> {
	private final boolean DEBUG = true;
    private boolean usernameTaken = false, userNameDecided = false;
    private Lock lock = new ReentrantLock();
    private Condition cv = lock.newCondition();

	private CreateAccountActivity mCreateAccountActivity;

	public VerifyUserAndMakeAccount(CreateAccountActivity locationsActivity) {
		LogFactory.log(DEBUG);
		mCreateAccountActivity = locationsActivity;
	}

	@Override
	protected void onPreExecute() {
		LogFactory.log(DEBUG);
	}

	@Override
	protected Integer doInBackground(String[]... params) {
		LogFactory.log(DEBUG);

		JSONObject jsonObject = URLFactory.getCustomerInfo(params[0][1]);
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

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Member");
        query.whereEqualTo("username", params[0][0]);

        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> commentList, ParseException e) {
                if(e != null) {
                    e.printStackTrace();
                }

                lock.lock();
                if(commentList != null) {
                    usernameTaken = !commentList.isEmpty();
                }
                userNameDecided = true;
                cv.signal();
                lock.unlock();
            }
        });

        lock.lock();
        while(!userNameDecided) {
            try {
                cv.await();
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }
        if(usernameTaken) {
            return 5;
        }
        lock.unlock();

        ParseObject newMember = new ParseObject("Member");
        newMember.put("username", params[0][0]);
        newMember.put("accountID", params[0][1]);
        newMember.put("password", params[0][2]);
        newMember.saveInBackground();
 		
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
