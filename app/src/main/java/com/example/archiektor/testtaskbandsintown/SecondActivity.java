package com.example.archiektor.testtaskbandsintown;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SecondActivity extends Activity {

    private static final String STARTPOINT = "http://api.bandsintown.com/artists/";
    private String nameOfArtist;
    private static Boolean screen = false;
    public static final String EXTRA_MESSAGE = "com.example.archiektor.MESSAGE";
    private static final String SCREEN_ORIENTATION = "com.example.archiektor.SCREEN";

    private List<Event> eventItems;

    private EventAdapter adapter;

    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        if (savedInstanceState != null) {
            String screen1 = savedInstanceState.getString("screen");
            if (screen1.equals("123")) {
                screen = false;
            }
        }

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            Log.i("SecondActivity", "THERE");
            nameOfArtist = extras.getString(EXTRA_MESSAGE);
            screen = extras.getBoolean(SCREEN_ORIENTATION);
            if (screen) {
                Log.i("SecondActivity", "Change screen orientation");
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            }
        }


        eventItems = new ArrayList<>();
        requestQueue = Volley.newRequestQueue(this);

        fetchPosts();

        RecyclerView rvEvents = (RecyclerView) findViewById(R.id.events);

        adapter = new EventAdapter(this, eventItems);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        VerticalSpaceItemDecoration itemDecoration = new VerticalSpaceItemDecoration(8);
        rvEvents.addItemDecoration(itemDecoration);

        layoutManager.scrollToPosition(0);
        rvEvents.setAdapter(adapter);
        rvEvents.setLayoutManager(layoutManager);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString("screen", "123");
    }

    private void fetchPosts() {
        String ENDPOINT = STARTPOINT + nameOfArtist + "/events.json?api_version=2.0&app_id=YOUR_APP_ID";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(ENDPOINT, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    if (response.length() > 0) {
                        eventItems.clear();
                        for (int i = 0; i < response.length(); i++) {
                            JSONObject jsonObject = response.getJSONObject(i);
                            Event event = new Event();
                            if (!jsonObject.isNull("title")) {
                                event.setTitle(jsonObject.getString("title"));
                            }
                            if (!jsonObject.isNull("formatted_datetime")) {
                                event.setFormattedDatetime(jsonObject.getString("formatted_datetime"));
                            }
                            if (!jsonObject.isNull("ticket_url")) {
                                event.setTicketUrl(jsonObject.getString("ticket_url"));
                            }
                            JSONObject venue = jsonObject.getJSONObject("venue");
                            if (!venue.isNull("country")) {
                                event.setCountry(venue.getString("country"));
                                event.setLatitude(venue.getString("latitude"));
                                event.setLongitude(venue.getString("longitude"));
                            }

                            //Log.i("event" + i + "= ", event.toString());
                            eventItems.add(i, event);
                            //Log.i("events = ", String.valueOf(eventItems.size()));
                        }
                        adapter.notifyDataSetChanged();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // do something
            }
        });

        requestQueue.add(jsonArrayRequest);

    }
}
