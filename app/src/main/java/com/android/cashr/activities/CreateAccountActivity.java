package com.android.cashr.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.cashr.R;
import com.android.cashr.tasks.VerifyUser;

public class CreateAccountActivity extends AbstractActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_account);

		EditText textUsername = (EditText) findViewById(R.id.et_create_account_username);
		final EditText textUserID   = (EditText) findViewById(R.id.et_create_account_user_id);
		EditText textPassword = (EditText) findViewById(R.id.et_create_account_password);

		Button btnSubmit = (Button) findViewById(R.id.btn_create_account);
		btnSubmit.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
//				String userID = textUserID.getText().toString();
				String userID = "54b604dfa520e02948a0f5f3";
				new VerifyUser(CreateAccountActivity.this).execute(userID);
			}
		});
	}
}
