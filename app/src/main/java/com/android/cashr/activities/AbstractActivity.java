package com.android.cashr.activities;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import com.android.cashr.R;
import com.android.cashr.factories.LogFactory;

public class AbstractActivity extends ActionBarActivity {
	private final boolean DEBUG = false;

	private int mStartCount = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		LogFactory.log(DEBUG);

		mStartCount = 1;

		if (savedInstanceState == null) {
			this.overridePendingTransition(
				R.anim.slide_in_left,
				R.anim.slide_out_left
			);
		} else {
			mStartCount = 2;
		}
	}

	@Override
	protected void onStart() {
		super.onStart();
		
		LogFactory.log(DEBUG);
		if (mStartCount > 1) {
			this.overridePendingTransition(
				R.anim.slide_in_right,
				R.anim.slide_out_right
			);
		} else if (mStartCount == 1) {
			mStartCount++;
		}

	}
}
