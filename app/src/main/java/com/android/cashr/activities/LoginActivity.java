package com.android.cashr.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.cashr.R;
import com.android.cashr.factories.LogFactory;
import com.gc.materialdesign.views.ButtonFlat;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

public class LoginActivity extends Activity {
	private static final boolean DEBUG = true;
    private EditText usernameText, passwordText;
    private String username, password;
    public static final String LOGIN_PREFS = "com.android.cashr.loginPrefs";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		LogFactory.log(DEBUG);

		super.onCreate(savedInstanceState);
        SharedPreferences settings = getSharedPreferences(LOGIN_PREFS, 0);
        Log.e("HNNNNNNNNG", "JALR");
        if(settings.getBoolean("loggedIn", false) &&
                !(settings.getString("accountID", "").equals(""))) {
            Intent intent = new Intent(LoginActivity.this, AccountsListActivity.class);
            intent.putExtra("accountID", settings.getString("accountID", ""));
            startActivity(intent);
        }
		setContentView(R.layout.activity_login);

        usernameText = (EditText)findViewById(R.id.et_login_username);
        passwordText = (EditText)findViewById(R.id.et_login_password);

		ButtonFlat button = (ButtonFlat) findViewById(R.id.btn_login_create);
		button.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(LoginActivity.this, CreateAccountActivity.class);
				startActivity(intent);
			}
		});

        Button button1 = (Button) findViewById(R.id.btn_login);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                username = usernameText.getText().toString().trim();
                password = passwordText.getText().toString().trim();

                ParseQuery<ParseObject> query = ParseQuery.getQuery("Member");
                query.whereEqualTo("username", username);

                query.findInBackground(new FindCallback<ParseObject>() {
                    public void done(List<ParseObject> commentList, ParseException e) {
                        if((commentList != null) && !commentList.isEmpty()) {
                            if(commentList.get(0).getString("password").compareTo(password) == 0) {
                                SharedPreferences settings = getSharedPreferences(LOGIN_PREFS, Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = settings.edit();
                                editor.putBoolean("loggedIn", true);
                                editor.putString("accountID", commentList.get(0).getString("accountID"));
                                editor.commit();
                                Intent intent = new Intent(LoginActivity.this, AccountsListActivity.class);
                                intent.putExtra("accountID", commentList.get(0).getString("accountID"));
                                startActivity(intent);
                            }
                        }
                    }
                });
            }
        });
	}
}
