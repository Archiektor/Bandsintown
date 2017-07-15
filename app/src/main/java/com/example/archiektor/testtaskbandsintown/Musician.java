package com.example.archiektor.testtaskbandsintown;

import java.io.Serializable;

public class Musician implements Serializable {

    private long id;
    private String name;
    private String image_url;
    private String thumb_url;
    private String facebook_tour_dates_url;
    private String facebook_page_url;
    private String mbid;
    private long tracker_count;
    private int upcoming_event_count;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getThumb_url() {
        return thumb_url;
    }

    public void setThumb_url(String thumb_url) {
        this.thumb_url = thumb_url;
    }

    public String getFacebook_tour_dates_url() {
        return facebook_tour_dates_url;
    }

    public void setFacebook_tour_dates_url(String facebook_tour_dates_url) {
        this.facebook_tour_dates_url = facebook_tour_dates_url;
    }

    public String getFacebook_page_url() {
        return facebook_page_url;
    }

    public void setFacebook_page_url(String facebook_page_url) {
        this.facebook_page_url = facebook_page_url;
    }

    public String getMbid() {
        return mbid;
    }

    public void setMbid(String mbid) {
        this.mbid = mbid;
    }

    public long getTracker_count() {
        return tracker_count;
    }

    public void setTracker_count(long tracker_count) {
        this.tracker_count = tracker_count;
    }

    public int getUpcoming_event_count() {
        return upcoming_event_count;
    }

    public void setUpcoming_event_count(int upcoming_event_count) {
        this.upcoming_event_count = upcoming_event_count;
    }



    public String toString() {
        return "name: " + getName() + "tracker_count: " + getTracker_count() + "upcoming: " + getUpcoming_event_count();
    }


}
