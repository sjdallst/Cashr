package com.android.cashr.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.android.cashr.R;
import com.gc.materialdesign.views.ButtonFlat;

public class LoginActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		ButtonFlat button = (ButtonFlat) findViewById(R.id.btn_login_create);
		button.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(LoginActivity.this, CreateAccountActivity.class);
				startActivity(intent);
			}
		});
	}
}
