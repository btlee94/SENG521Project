package com.example.seng521.seng521project;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class ActivityMain extends AppCompatActivity {
    private static Storage localDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        localDB = Storage.getInstance(this.getBaseContext());
        setView();
    }

    public void setView(){
        //Get the listview that will display the trips
        ListView listview = (ListView) findViewById(R.id.list_view);

        //Set the title
        String title = "TRIPS";
        TextView textTitle = (TextView) findViewById(R.id.list_title);
        textTitle.setText(title);

        /*Test data
        localDB.addTrip(new Trip("Trip 1", "2016-11-23", "12:00"));
        localDB.addTrip(new Trip("Trip 2", "2016-11-15", "1:00"));
        localDB.addTrip(new Trip("Trip 3", "2016-11-02", "2:00"));
        localDB.addTrip(new Trip("Trip 4", "2016-10-27", "3:00"));
        localDB.addTrip(new Trip("Trip 5", "2016-10-25", "4:00"));
        */
        ArrayList<Trip> testTrips = localDB.getTrips();

        //Create the adapter that is responsible for displaying the content on the list
        CardListAdapter adapter = new CardListAdapter(getBaseContext(), testTrips);
        listview.setAdapter(adapter);
    }

    public void addFabOnClick(View addButton){
        final Intent intent  = new Intent(ActivityMain.this, ActivityTrip.class);
        startActivity(intent);
    }

    public class CardListAdapter extends BaseAdapter {

        //The data to be displayed
        private ArrayList<Trip> trips;

        //The activity that contains the list
        private Context context;

        //Constructor
        public CardListAdapter(Context context, ArrayList<Trip> List) {
            this.trips = List;
            this.context = context;
        }

        @Override
        public int getCount() {
            return trips.size();
        }

        @Override
        public Object getItem(int position) {
            return trips.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        //This function gets triggered whenever the user scrolls
        //the list to display the items on the screen
        public View getView(int position, View convertView, ViewGroup parent) {

            //Get the template
            View v = convertView;
            if (v == null) {
                LayoutInflater vi = LayoutInflater.from(context);
                v = vi.inflate(R.layout.list_card, null);
            }

            //Get the item to be displayed
            final Trip trip = (Trip) getItem(position);

            //Set onClickListener for touching a trip card
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View popView) {
                    final Dialog popUp = new Dialog(ActivityMain.this);
                    popUp.setContentView(R.layout.view_trip_info);

                    //this will be where a query is sent to the db for details for this particular Trip
                    //details will be shown in this popup window

                    //Test data
                    ImageView imageView1 = (ImageView) popUp.findViewById(R.id.cylMis_status);
                    imageView1.setImageResource(R.drawable.ic_status_noerror);
                    ImageView imageView2 = (ImageView) popUp.findViewById(R.id.fuelStat_status);
                    imageView2.setImageResource(R.drawable.ic_status_error);
                    ImageView imageView3 = (ImageView) popUp.findViewById(R.id.rpmLimit_status);
                    imageView3.setImageResource(R.drawable.ic_status_error);
                    ImageView imageView4 = (ImageView) popUp.findViewById(R.id.seatBelt_status);
                    imageView4.setImageResource(R.drawable.ic_status_error);
                    ImageView imageView5 = (ImageView) popUp.findViewById(R.id.brakePedal_status);
                    imageView5.setImageResource(R.drawable.ic_status_error);
                    ImageView imageView6 = (ImageView) popUp.findViewById(R.id.abs_status);
                    imageView6.setImageResource(R.drawable.ic_status_error);
                    ImageView imageView7 = (ImageView) popUp.findViewById(R.id.powerSteerFail_status);
                    imageView7.setImageResource(R.drawable.ic_status_noerror);
                    ImageView imageView8 = (ImageView) popUp.findViewById(R.id.heaterFail_status);
                    imageView8.setImageResource(R.drawable.ic_status_noerror);

                    popUp.show();
                }
            });

            //Populate the card
            TextView textView1 = (TextView) v.findViewById(R.id.trip_name);
            textView1.setText(trip.getName());

            TextView textView2 = (TextView) v.findViewById(R.id.trip_date);
            textView2.setText(trip.getTimestampStart());

            TextView textView3 = (TextView) v.findViewById(R.id.trip_time);
            textView3.setText(trip.getTimestampEnd());

            return v;
        }
    }

}
