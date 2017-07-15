package com.example.archiektor.testtaskbandsintown;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
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

    private EditText editText;
    private ImageButton imageButton;
    //!!! should be without spaces !!!!!!!!!!!!!
    private String nameOfMusician;
    private TextView artist;
    private TextView countTours;
    private TextView url;

    private TextView count;

    private ImageView imageView;

    private static final String STARTPOINT = "http://api.bandsintown.com/artists/";
    private String endPoint;

    private String imagePath;

    private RequestQueue requestQueue;

    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        artist = (TextView) findViewById(R.id.group);
        artist.setVisibility(View.GONE);
        countTours = (TextView) findViewById(R.id.numberOfTours);
        countTours.setVisibility(View.GONE);


        url = (TextView) findViewById(R.id.url);
        url.setVisibility(View.GONE);

        count = (TextView) findViewById(R.id.numberOfTours);

        editText = (EditText) findViewById(R.id.editText);

        imageButton = (ImageButton) findViewById(R.id.imageButton);

        imageView = (ImageView) findViewById(R.id.goToList);

    }

    public void search(View view) {
        //проверка editext
        nameOfMusician = (String) editText.getText().toString();
        endPoint = STARTPOINT + nameOfMusician + ".json?api_version=2.0&app_id=YOUR_APP_ID";

        Log.i("start", "Open a method");
        requestQueue = Volley.newRequestQueue(this);

        JsonObjectRequest obreq = new JsonObjectRequest(Request.Method.GET, endPoint, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        if (response != null) {
                            artist.setVisibility(View.VISIBLE);
                            countTours.setVisibility(View.VISIBLE);
                            url.setVisibility(View.VISIBLE);
                        } else {
                            Toast.makeText(getApplicationContext(), "This is my Toast message!", Toast.LENGTH_LONG).show();
                        }

                        Gson gson = new Gson();

                        Musician group;
                        group = gson.fromJson(response.toString(), Musician.class);

                        String name = group.getName();
                        String count = String.valueOf(group.getUpcoming_event_count());
                        if (count == "0") {
                            countTours.setText("0");
                        }
                        String urlBook = group.getFacebook_page_url();
                        imagePath = group.getImage_url();

                        artist.setText(name);
                        countTours.setText(count);
                        intent = new Intent(getApplicationContext(), SecondActivity.class);
                        intent.putExtra(EXTRA_MESSAGE, name);
                        setClicable((count), 0, count.length(), intent, countTours);
                        //startActivity(intent);

                        url.setText(urlBook);

                        Intent intentWeb = new Intent(Intent.ACTION_VIEW, Uri.parse(urlBook));
                        setClicable((urlBook), 0, urlBook.length(), intentWeb, url);

                        Picasso.with(getApplicationContext()).load(imagePath).resize(850, 570).placeholder(R.drawable.ic_wait_download).error(R.drawable.ic_error_fallback).into(imageView);
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

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(getApplicationContext(), SecondActivity.class);
                intent.putExtra(EXTRA_MESSAGE, nameOfMusician);
                startActivity(intent);
            }
        });

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

}

