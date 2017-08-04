package com.example.cynthiakhalil.finalcal;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.sundeepk.compactcalendarview.domain.Event;

import java.util.Date;
import java.util.List;

/**
 * Created by Cynthia.Khalil on 8/1/2017.
 */

public class customAdapter extends BaseAdapter {

    List<Event> eList;
    Context mContext;
    DatabaseHelper db;


   public customAdapter() {
    }

    public customAdapter(Context mContext, List<Event> eList) {


        this.eList = eList;
        this.mContext = mContext;
        db = new DatabaseHelper(mContext);
        //Log.v("list", eList.toString());
    }


    @Override
    public int getCount() {
        return eList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View row = inflater.inflate(R.layout.row, parent, false);
        TextView name, date;
        ImageView i1;
        i1=(ImageView)row.findViewById(R.id.imageView);
        i1.setBackgroundColor(eList.get(position).getColor());

        name = (TextView) row.findViewById(R.id.name);
        date = (TextView) row.findViewById(R.id.date);

        name.setText(eList.get(position).getData().toString());
        date.setText(getFromEpoch(eList.get(position).getTimeInMillis()));


        return (row);
    }


    public String getFromEpoch(long time)
    {
        Date date = new Date(time);

        String month = ""+date.getMonth();
        String hour = ""+date.getHours();
        String day = ""+date.getDay();
        String minute = ""+date.getMinutes();
        if(hour.length() == 1)
        {
            hour = "0"+hour;
        }
        if(minute.length() == 1)
        {

            minute = "0"+minute;
        }

        Log.e("DATE YO:", "month:"+month+" day:"+day+" hour:"+ hour);
        return ""+month+"/"+day+" @ "+hour+":"+minute;
    }


    @Override
    public CharSequence[] getAutofillOptions() {
        return new CharSequence[0];
    }
}
