package com.android.cashr.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.cashr.R;
import com.android.cashr.tasks.VerifyUserAndMakeAccount;
import com.parse.Parse;

public class CreateAccountActivity extends AbstractActivity {

    private EditText textUsername, textUserID, textPassword;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_account);

		textUsername = (EditText) findViewById(R.id.et_create_account_username);
		textUserID   = (EditText) findViewById(R.id.et_create_account_user_id);
		textPassword = (EditText) findViewById(R.id.et_create_account_password);


		Button btnSubmit = (Button) findViewById(R.id.btn_create_account);
		btnSubmit.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
                String[] fieldValues = new String[3];

                fieldValues[0] = textUsername.getText().toString().trim();               //fieldValues[1] = textUsername.getText().toString();
                fieldValues[2] = textPassword.getText().toString().trim();
				fieldValues[1] = "54b604dfa520e02948a0f5f3";
				new VerifyUserAndMakeAccount(CreateAccountActivity.this).execute(fieldValues);
			}
		});
	}
}
