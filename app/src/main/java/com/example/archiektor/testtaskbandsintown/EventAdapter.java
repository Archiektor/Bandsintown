package com.example.archiektor.testtaskbandsintown;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

class EventAdapter extends RecyclerView.Adapter<EventAdapter.MyViewHolder> {

    private List<Event> mEvents;
    private Context mContext;

    public Context getmContext() {
        return mContext;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView titleTv;
        TextView dateTv;
        ImageButton mapBtn;
        ImageButton saveBtn;

        MyViewHolder(View itemView) {
            super(itemView);

            titleTv = (TextView) itemView.findViewById(R.id.title_info);
            dateTv = (TextView) itemView.findViewById(R.id.date_info);
            mapBtn = (ImageButton) itemView.findViewById(R.id.map_button);
            saveBtn = (ImageButton) itemView.findViewById(R.id.save_button);
        }
    }

    EventAdapter(Context context, List<Event> events) {
        mContext = context;
        mEvents = events;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View eventView = inflater.inflate(R.layout.item_event, parent, false);

        return new MyViewHolder(eventView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final Event event = mEvents.get(position);

        TextView textView1;
        TextView textView2;

        ImageButton btn1;
        ImageButton btn2;

        textView1 = holder.titleTv;
        String[] parts = (event.getTitle()).split("@");
        String title = parts[1];
        textView1.setText(title);
        textView2 = holder.dateTv;
        textView2.setText(event.getFormattedDatetime());

        btn1 = holder.mapBtn;
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Double latStr = Double.parseDouble(event.getLatitude());
                Double lngStr = Double.parseDouble(event.getLongitude());

                Intent intentMap = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:" + latStr + "," + lngStr + "?z=17"));
                intentMap.setPackage("com.google.android.apps.maps");
                mContext.startActivity(intentMap);
            }
        });
        btn2 = holder.saveBtn;
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper databaseHelper;
                databaseHelper = new DBHelper(mContext);

                SQLiteDatabase db = databaseHelper.getWritableDatabase();

                DBHelper.insertData(db, event.getTitle(), event.getFormattedDatetime(), event.getTicketUrl(), event.getCountry(), event.getLongitude() + ";" + event.getLatitude());

                db.close();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mEvents.size();
    }

}

