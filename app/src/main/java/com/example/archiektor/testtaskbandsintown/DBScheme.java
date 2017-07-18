package com.example.archiektor.testtaskbandsintown;


public final class DBScheme {

    public static final class DbTable {
        public static final String TABLE_NAME = "events";
    }

    /* Inner class that defines the table contents */

    public static final class Cols {
        public static final String ID = "id";
        public static final String TITLE = "title";
        public static final String DATE = "date";
        public static final String TICKET_URL = "ticketURL";
        public static final String COUNTRY = "country";
        public static final String COORDS = "coords";
    }
}
