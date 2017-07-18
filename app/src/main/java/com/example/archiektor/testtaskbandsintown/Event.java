package com.example.archiektor.testtaskbandsintown;


class Event {

    private String title;
    private String formattedDatetime;
    private String ticketUrl;

    private String country;
    private String latitude;
    private String longitude;

    Event() {
    }

    public Event(String title, String formattedDatetime) {
        this.title = title;
        this.formattedDatetime = formattedDatetime;
    }

    public Event(String title, String formattedDatetime, String ticketUrl, String country, String latitude, String longitude) {
        this.title = title;
        this.formattedDatetime = formattedDatetime;
        this.ticketUrl = ticketUrl;
        this.country = country;
        this.latitude = latitude;
        this.longitude = longitude;
    }


    String getTitle() {
        return title;
    }

    void setTitle(String title) {
        this.title = title;
    }

    String getFormattedDatetime() {
        return formattedDatetime;
    }

    void setFormattedDatetime(String formattedDatetime) {
        this.formattedDatetime = formattedDatetime;
    }

    String getTicketUrl() {
        return ticketUrl;
    }

    void setTicketUrl(String ticketUrl) {
        this.ticketUrl = ticketUrl;
    }

    String getCountry() {
        return country;
    }

    void setCountry(String country) {
        this.country = country;
    }

    String getLatitude() {
        return latitude;
    }

    void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    String getLongitude() {
        return longitude;
    }

    void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    @Override
    public String toString() {
        return "// datatime: " + getFormattedDatetime() + "// country: " + getCountry() + "// coord: " + getLatitude() + ";" + getLongitude();
    }

}
