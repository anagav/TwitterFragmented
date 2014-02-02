package com.personal.apps.twitterdemo;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import com.codepath.apps.restclienttemplate.R;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.personal.apps.adapter.TwitterAdapter;
import com.personal.apps.fragment.FragmentFactory;
import com.personal.apps.model.TwitterModel;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by ashishn on 1/27/14.
 */
public class MyTimeLineActivity extends FragmentActivity {

    TwitterAdapter adapter;

    final String HOME_STRING = "Home";

    final String MENTION_STRING = "Mentions";


    public enum CONTEXT {
        TIMELINE, MENTION;
    }


   /* @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("data",adapter.getData());
    }*/



    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final ActionBar actionBar = getActionBar();


        assert actionBar != null;

        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);


        ActionBar.TabListener tabListener = new ActionBar.TabListener() {
            public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
                String s = (String) tab.getTag();
                if (s.equals(HOME_STRING)) {
                    ft.replace(R.id.ContentPlaceholder, new FragmentFactory(new TwitterAdapter(new ArrayList<TwitterModel>(),
                            MyTimeLineActivity.this), CONTEXT.TIMELINE));

                } else if (s.equals(MENTION_STRING)) {
                    ft.replace(R.id.ContentPlaceholder, new FragmentFactory(new TwitterAdapter(new ArrayList<TwitterModel>(),
                            MyTimeLineActivity.this), CONTEXT.MENTION));
                }
            }

            public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {
                // hide the given tab
            }

            public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {
                // probably ignore this event
            }
        };

        actionBar.addTab(
                actionBar.newTab()
                        .setText(HOME_STRING).setTag(HOME_STRING)
                        .setTabListener(tabListener));

        actionBar.addTab(
                actionBar.newTab()
                        .setText(MENTION_STRING).setTag(MENTION_STRING)
                        .setTabListener(tabListener));

        setContentView(R.layout.timelinefragmentactivity);



       /*


        setContentView(R.layout.timelineactivity);


        ListView tweetlist = (ListView) findViewById(R.id.listView);

        tweetlist.setAdapter(adapter);

        tweetlist.setOnScrollListener(new EndlessScrollListener() {
            @Override
            public void onLoadMore(final int page, int totalItemsCount) {
                TwitterClientApp.getRestClient().getHomeTimeLine(page, new JsonHttpResponseHandler() {
                    @Override
                    public void onFailure(Throwable throwable, JSONArray jsonArray) {
                        super.onFailure(throwable, jsonArray);
                        System.out.println("Error:" + jsonArray);
                        System.out.println("Error:" + throwable.toString());
                    }

                    @Override
                    public void onSuccess(JSONArray jsonArray) {
                        Toast.makeText(getBaseContext(), "loading more for page" + page, Toast.LENGTH_SHORT).show();
                        super.onSuccess(jsonArray);
                        adapter.setdata(jsonArray.toString());
                    }
                });

            }
        });


        TwitterClientApp.getRestClient().getHomeTimeLine(1, new JsonHttpResponseHandler() {
            @Override
            public void onFailure(Throwable throwable, JSONArray jsonArray) {
                super.onFailure(throwable, jsonArray);
                System.out.println("Error:" + jsonArray);
                System.out.println("Error:" + throwable.toString());

            }

            @Override
            public void onFailure(Throwable throwable, JSONObject jsonObject) {
                super.onFailure(throwable, jsonObject);
                System.out.println("Error:" + jsonObject);
                System.out.println("Error:" + throwable.toString());
            }

            @Override
            public void onSuccess(JSONArray jsonArray) {
                super.onSuccess(jsonArray);
                adapter.setdata(jsonArray.toString());
            }
        });*/


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_activity_actions, menu);
        return super.onCreateOptionsMenu(menu);
    }


    public void loadCompose(MenuItem item) {
        Intent i = new Intent(MyTimeLineActivity.this, Compose.class);
        final int result = 2;
        startActivityForResult(i, result);
    }

    public void loadProfile(MenuItem item) {
        Intent i = new Intent(MyTimeLineActivity.this, ProfileView.class);
        startActivity(i);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, final Intent data) {

        TwitterClientApp.getRestClient().postTweet(data.getStringExtra("tweet"), new JsonHttpResponseHandler() {
            @Override
            public void onFailure(Throwable throwable, JSONArray jsonArray) {
                throwable.printStackTrace();
                System.out.println(jsonArray);
            }

            @Override
            public void onSuccess(JSONObject json) {
                Toast.makeText(getBaseContext(), "new tweet posted", Toast.LENGTH_SHORT).show();
                adapter.newTweet(json.toString());
            }
        });

    }


    public void displayProfile(View view) {
        Intent i = new Intent(this,ProfileView.class);
        i.putExtra("user_id",view.findViewById(R.id.displayImage).getTag().toString());
        startActivity(i);
    }


}