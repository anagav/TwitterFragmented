package com.personal.apps.adapter;

import android.content.Context;
import android.graphics.Color;
import android.text.util.Linkify;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.codepath.apps.restclienttemplate.R;
import com.personal.apps.model.TwitterModel;
import com.squareup.picasso.Picasso;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by ashishn on 2/2/14.
 */
public class ProfileAdapter extends BaseAdapter {

    private ArrayList<TwitterModel> _data = null;
    Context _c;


    public TwitterModel setdata(String s) {
        if (this._data == null) {
            _data = new ArrayList<TwitterModel>();
        }

        this._data.addAll(TwitterModel.gettweets(s));

        this.notifyDataSetChanged();
        return this._data.get(0);

    }

    public ArrayList<TwitterModel> getData() {
        return this._data;
    }


    public ProfileAdapter(ArrayList<TwitterModel> data, Context c) {
        _data = data;
        _c = c;
    }

    public int getCount() {
        // TODO Auto-generated method stub
        return _data.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return _data.get(position);
    }


    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        View v = convertView;
        if (v == null) {
            LayoutInflater vi = (LayoutInflater) _c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.profilelayout, null);
        }

        assert v != null;
        ImageView image = (ImageView) v.findViewById(R.id.displayImage);
        TextView fromView = (TextView) v.findViewById(R.id.userName);
        TwitterModel tweet = _data.get(position);
        //TextView subView = (TextView)v.findViewById(R.id.subject);
        TextView descView = (TextView) v.findViewById(R.id.tweet);
        Linkify.addLinks(descView, Linkify.ALL);
        descView.setLinksClickable(true);
        descView.setLinkTextColor(Color.BLUE);


        //TextView timeView = (TextView)v.findViewById(R.id.time);


        URL url = null;
        try {
            url = new URL(tweet.user.profile_image_url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        assert url != null;


        Picasso.with(_c).load(tweet.user.profile_image_url).into(image);

        //image.setImageResource(R.drawable.ic_launcher);

        fromView.setText(tweet.user.name);


        descView.setText(tweet.text);


        return v;
    }
}
