package com.personal.apps.twitterdemo;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import com.codepath.apps.restclienttemplate.R;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.personal.apps.adapter.ProfileAdapter;
import com.personal.apps.model.TwitterModel;
import com.squareup.picasso.Picasso;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by ashishn on 2/2/14.
 */
public class ProfileView extends Activity {

    ListView profileInfo;
    ImageView profileImage;
    TextView profileName;
    TextView profileDesc;
    TextView followerDesc;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);
        profileInfo = (ListView) findViewById(R.id.profileList);
        profileImage = (ImageView) findViewById(R.id.profileImage);
        profileName = (TextView) findViewById(R.id.ProfileUserName);
        profileDesc = (TextView) findViewById(R.id.ProfileDescription);
        followerDesc = (TextView) findViewById(R.id.follwerCount);

        final ProfileAdapter adapter = new ProfileAdapter(new ArrayList<TwitterModel>(), this);

        profileInfo.setAdapter(adapter);


        TwitterClientApp.getRestClient().getUserTimeLine(new JsonHttpResponseHandler() {
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

                TwitterModel tweet = adapter.setdata(jsonArray.toString());

                System.out.println("result =" + tweet.toString());

                Picasso.with(ProfileView.this).load(tweet.user.profile_image_url).into(profileImage);

                profileName.setText(tweet.user.name);
                profileDesc.setText(tweet.user.description);
                followerDesc.setText(tweet.user.friends_count + " Following \t "+ tweet.user.followers_count + " Followers");

            }
        });




    }


}
