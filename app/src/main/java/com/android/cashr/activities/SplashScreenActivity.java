package com.android.cashr.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import com.android.cashr.R;
import com.android.cashr.factories.LogFactory;

public class SplashScreenActivity extends ActionBarActivity {
	private static final boolean DEBUG = true;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		LogFactory.log(DEBUG);

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash_screen);
		
		Thread startTimer = new Thread() {
			public void run() {
				LogFactory.log(DEBUG);

				try {
					sleep(5000);

					Intent intent = new Intent(
						SplashScreenActivity.this, 
						LoginActivity.class
					);
					startActivity(intent);
					finish();
				}
				catch(InterruptedException e) {
					LogFactory.error(DEBUG, e);
				}
			}
		};
		
		startTimer.start();
	}
}
