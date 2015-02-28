package com.android.cashr.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

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


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_splash_screen, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();

		//noinspection SimplifiableIfStatement
		if(id == R.id.action_settings) {
			return true;
		}

		return super.onOptionsItemSelected(item);
	}
}
