package com.example.archiektor.testtaskbandsintown;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

public class MainActivity extends Activity {

    public static final String EXTRA_MESSAGE = "com.example.archiektor.MESSAGE";
    private static final String SCREEN_ORIENTATION = "com.example.archiektor.SCREEN";
    static boolean hasText;

    private Musician group;
    private static final Musician savedGroup = new Musician();

    private EditText editText;
    private ImageButton imageButton;
    private static String nameOfMusician;
    private static String artistTitle;
    private TextView artist;
    private TextView countTours;
    private TextView url;
    private TextView toursAva;
    private TextView facebookAva;

    private ImageView imageView;

    private static final String STARTPOINT = "http://api.bandsintown.com/artists/";

    private static String imagePath;
    private static String countForCond;
    private static String urlBook;


    private Intent intent;
    private Intent intentWeb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        artist = (TextView) findViewById(R.id.group);
        toursAva = (TextView) findViewById(R.id.tours);
        countTours = (TextView) findViewById(R.id.numberOfTours);
        facebookAva = (TextView) findViewById(R.id.facebook);
        url = (TextView) findViewById(R.id.url);

        editText = (EditText) findViewById(R.id.editText);

        imageButton = (ImageButton) findViewById(R.id.imageButton);
        updateAlpha(imageButton, 0.35f);
        imageView = (ImageView) findViewById(R.id.goToList);

        if (savedInstanceState != null && hasText) {
            Log.i("start", "in savedInstanceState != null");
            //if (!editText.getText().toString().isEmpty()) {
            nameOfMusician = savedInstanceState.getString("artist");
            countForCond = savedInstanceState.getString("countTours");
            urlBook = savedInstanceState.getString("facebookSite");
            imagePath = savedInstanceState.getString("image");
            String invertedEditText = savedInstanceState.getString("editText");
            editText.setText(invertedEditText);

            check();

        } else {
            artist.setVisibility(View.GONE);
            toursAva.setVisibility(View.GONE);
            countTours.setVisibility(View.GONE);
            facebookAva.setVisibility(View.GONE);
            url.setVisibility(View.GONE);
        }

    }

    public void search(View view) {
        if (isOnline()) {
            nameOfMusician = editText.getText().toString().replaceAll("\\s", "");
            hasText = editText.getText().toString().length() > 0;
            if (nameOfMusician.matches("")) {
                //imageButton.setEnabled(false);
                Toast.makeText(this, "You didn't enter artist", Toast.LENGTH_SHORT).show();
                return;
            }
            updateAlpha(imageButton, 1f);
            String endPoint = STARTPOINT + nameOfMusician + ".json?api_version=2.0&app_id=YOUR_APP_ID";

            RequestQueue requestQueue = Volley.newRequestQueue(this);

            JsonObjectRequest obreq = new JsonObjectRequest(Request.Method.GET, endPoint, null,
                    new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {

                            if (response != null) {
                                artist.setVisibility(View.VISIBLE);
                                countTours.setVisibility(View.VISIBLE);
                                url.setVisibility(View.VISIBLE);
                                toursAva.setVisibility(View.VISIBLE);
                                facebookAva.setVisibility(View.VISIBLE);
                            } else {
                                Toast.makeText(getApplicationContext(), "There's no info about such artist !", Toast.LENGTH_LONG).show();
                            }

                            Gson gson = new Gson();


                            group = gson.fromJson(response.toString(), Musician.class);

                            artistTitle = group.getName();
                            savedGroup.setName(artistTitle);
                            countForCond = String.valueOf(group.getUpcoming_event_count());
                            savedGroup.setUpcoming_event_count(Integer.valueOf(countForCond));


                            if (group.getFacebook_page_url() != null) {
                                urlBook = group.getFacebook_page_url();
                                savedGroup.setFacebook_page_url(urlBook);
                            } else {
                                urlBook = "Group don't have page";
                            }
                            imagePath = group.getImage_url();
                            savedGroup.setImage_url(imagePath);

                            check();

                        }
                    },

                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.e("Volley", "Error");
                        }
                    }
            );

            requestQueue.add(obreq);

        } else {
            Toast toast = Toast.makeText(getApplicationContext(),
                    "Check Your Internet Connection", Toast.LENGTH_LONG);
            toast.show();
        }
    }

    private void setClicable(String string, int start, int end, final Intent intent, TextView textView) {
        SpannableString ss = new SpannableString(string);
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View textView) {
                startActivity(intent);
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setUnderlineText(false);
            }
        };
        ss.setSpan(clickableSpan, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        textView.setText(ss);
        textView.setMovementMethod(LinkMovementMethod.getInstance());
        textView.setHighlightColor(Color.TRANSPARENT);
    }

    void updateAlpha(View v, float value) {
        v.setAlpha(value);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if (!editText.getText().toString().isEmpty()) {
            Log.i("hasText", "" + hasText);
            outState.putString("artist", savedGroup.getName());
            outState.putString("countTours", String.valueOf(savedGroup.getUpcoming_event_count()));
            outState.putString("facebookSite", savedGroup.getFacebook_page_url());
            outState.putString("image", savedGroup.getImage_url());
            outState.putString("editText", editText.getText().toString());
        }
    }

    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    private void check() {
        if (countForCond.equals("0")) {
            artist.setTextColor(Color.parseColor("#b21007"));
            artist.setText(artistTitle);
            countTours.setText(countForCond);
            url.setText(urlBook);
        } else {
            artist.setTextColor(Color.parseColor("#52d036"));
            artist.setText(artistTitle);
            countTours.setText(countForCond);

            intent = new Intent(getApplicationContext(), SecondActivity.class);
            intent.putExtra(EXTRA_MESSAGE, artistTitle);
            intent.putExtra(SCREEN_ORIENTATION, false);
            setClicable((countForCond), 0, countForCond.length(), intent, countTours);

            url.setText(urlBook);
            Intent intentWeb = new Intent(Intent.ACTION_VIEW, Uri.parse(urlBook));
            setClicable((urlBook), 0, urlBook.length(), intentWeb, url);
        }


        Picasso.with(getApplicationContext()).load(imagePath).resize(720, 570).placeholder(R.drawable.ic_wait_download).error(R.drawable.ic_error_fallback).into(imageView);


        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!countForCond.equals("0")) {
                    intent = new Intent(getApplicationContext(), SecondActivity.class);
                    intent.putExtra(EXTRA_MESSAGE, nameOfMusician);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "No tours available", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

}

