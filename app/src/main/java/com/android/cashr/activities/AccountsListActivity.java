package com.android.cashr.activities;

import android.annotation.TargetApi;
import android.app.SearchManager;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.widget.DrawerLayout;
import android.os.Bundle;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.android.cashr.R;
import com.android.cashr.adaptors.AccountAdapter;
import com.android.cashr.animator.ReboundItemAnimator;
import com.android.cashr.factories.LogFactory;
import com.android.cashr.factories.URLFactory;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AccountsListActivity extends AbstractActivity {
    private List<String> mAccountsList;
    private AccountAdapter mAccountAdapter;
    private String accountID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cards);

        Intent intent = getIntent();
        accountID = intent.getStringExtra("accountID");

        // Handle Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Handle RecyclerView
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new ReboundItemAnimator());

        mAccountAdapter = new AccountAdapter(R.layout.card_account, this, new ArrayList<String>());
        recyclerView.setAdapter(mAccountAdapter);
        mAccountsList = new ArrayList<>();

        new GetAccountsTask().execute();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_cards, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void animateActivity(String accountInfo) {
        Intent intent = new Intent(this, AccountActivity.class);
        intent.putExtra("accountName", accountInfo);

        ActivityOptionsCompat transitionActivityOptions =
                ActivityOptionsCompat.makeSceneTransitionAnimation(this);
        startActivity(intent, transitionActivityOptions.toBundle());
    }

    private class GetAccountsTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            mAccountAdapter.clearDevices();
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            mAccountsList.clear();

//            ParseQuery<ParseObject> query = ParseQuery.getQuery("Member");
//            query.whereEqualTo("accountID", accountID);
//
//            query.findInBackground(new FindCallback<ParseObject>() {
//                public void done(List<ParseObject> commentList, ParseException e) {
//                    Log.e("DOIN THE PARSE: ", "YEAH!" + commentList.size() + commentList.get(0).getString("username"));
//                    for(ParseObject pObj: commentList) {
//                        mAccountsList.add(pObj.getString("username"));
//                        System.out.println("HARBAUGH@");
//                    }
//                    mAccountAdapter.addAccounts(mAccountsList);
//                }
//            });

            JSONObject jsonObject = URLFactory.getCustomerAccounts(accountID);
            if(jsonObject == null) {
                return null;
            }

            LogFactory.log(true, jsonObject.toString());

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            //mAccountAdapter.addAccounts(mAccountsList);
            super.onPostExecute(result);
        }
    }
}
