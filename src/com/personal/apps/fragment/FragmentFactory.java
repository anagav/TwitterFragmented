package com.personal.apps.fragment;


import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Fragment;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import com.codepath.apps.restclienttemplate.R;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.personal.apps.adapter.TwitterAdapter;
import com.personal.apps.twitterdemo.MyTimeLineActivity;
import com.personal.apps.twitterdemo.TwitterClientApp;
import org.json.JSONArray;
import org.json.JSONObject;


@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class FragmentFactory extends Fragment {


    TwitterAdapter adapter;

    ListView tweetlist;

    MyTimeLineActivity.CONTEXT context;

    //String results;


    public FragmentFactory(BaseAdapter adapter, MyTimeLineActivity.CONTEXT context) {
        this.adapter = (TwitterAdapter) adapter;
        this.context = context;

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);


    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.timelineactivity, container, false);
        assert view != null;
        tweetlist = (ListView) view.findViewById(R.id.listView);
        tweetlist.setAdapter(adapter);
        return view;
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        saveInstance(outState);
    }

    private void saveInstance(Bundle outState) {
        outState.putString("data", adapter.toString());
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        System.out.println("======saved instance="+savedInstanceState);
        //if (savedInstanceState !=null && savedInstanceState.get("data") != null) {
            loadData(context);
           // return;
        //}
        //adapter.setdata(savedInstanceState.getString("data"));
    }


    private void loadData(MyTimeLineActivity.CONTEXT context) {

        switch (context) {

            case TIMELINE:

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
                        //System.out.println("timeline results="+jsonArray.toString());
                        adapter.setdata(jsonArray.toString());
                        //results = jsonArray.toString();
                    }
                });
                break;
            case MENTION:
                TwitterClientApp.getRestClient().getMentions(new JsonHttpResponseHandler() {
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
                        //System.out.println("results="+jsonArray.toString());
                        adapter.setdata(jsonArray.toString());
                        //results = jsonArray.toString();
                    }
                });
                break;
            default:
                System.out.println("invalid option");
                break;
        }
    }
}
