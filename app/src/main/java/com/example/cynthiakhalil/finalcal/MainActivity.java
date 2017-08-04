package com.example.cynthiakhalil.finalcal;


import android.content.Intent;
import android.graphics.RectF;
import android.os.Bundle;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.app.AppCompatActivity;

import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.alamkanak.weekview.DateTimeInterpreter;
import com.alamkanak.weekview.MonthLoader;
import com.alamkanak.weekview.WeekView;
import com.alamkanak.weekview.WeekViewEvent;


import java.io.Serializable;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Locale;



import android.annotation.TargetApi;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.RectF;
import android.icu.text.DateFormat;
//import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.alamkanak.weekview.MonthLoader;
import com.alamkanak.weekview.WeekView;
import com.alamkanak.weekview.WeekViewEvent;
import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;
import com.sothree.slidinguppanel.ViewDragHelper;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import static android.R.attr.format;
import static android.R.attr.onClick;
import static android.graphics.Color.BLACK;
import static android.graphics.Color.WHITE;

public class MainActivity extends AppCompatActivity  {




    private DatabaseHelper db;
    private List<Event> events;

    private CompactCalendarView compactCalendarView;
   private ActionBar actionBar;
    private SlidingUpPanelLayout mLayout;
    private static final String TAG = MainActivity.class.getSimpleName();
    private ImageView previousDay;
    private ImageView doubleUp;
    private ListView lv;

    private TextView currentDate;
    private Calendar cal = Calendar.getInstance();
    //private RelativeLayout reLayout;
   // private int currentEventSize;
    //private int eventIndex;

    private Toolbar tb;
    private TextView tv_main;
    private boolean act = false;

    private Button newAct;
    customAdapter ca;
    //THIS ONLY HAS TO DO WITH THE DAILY





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        compactCalendarView = (CompactCalendarView) findViewById(R.id.mCalendar);
        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(false);
        actionBar.setTitle("finalCal");


        compactCalendarView.displayOtherMonthDays(true);


        tb = (Toolbar) findViewById(R.id.main_toolbar);

        //newAct = (Button) findViewById(R.id.newAct);

        db = new DatabaseHelper(getApplicationContext());
        tv_main = (TextView) findViewById(R.id.title_main);
        doubleUp = (ImageView) findViewById(R.id.imageView);
        mLayout = (SlidingUpPanelLayout) findViewById(R.id.sliding_layout);
        //reLayout = (RelativeLayout) findViewById(R.id.left_event_column);
       // eventIndex = reLayout.getChildCount();
        //currentDate = (TextView) findViewById(R.id.display_current_date);
        //currentDate.setText(displayDateInString(cal.getTime()));
        setTitleofTB(cal.getTime());
        //bt = (Button) findViewById(R.id.button);
        /*bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tryButton();
            }
        });*/



        loadEventsList();
        calendarDisplay();
        panelSlide();


        lv = (ListView) findViewById(R.id.lst);


            ca = new customAdapter(this, events);
        lv.setAdapter(ca);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(this, "onItemClick", Toast.LENGTH_SHORT).show();
            }
        });





        //previousDay = (ImageView)findViewById(R.id.previous_day);
        //nextDay = (ImageView)findViewById(R.id.next_day);



        /*previousDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                previousCalendarDate();
            }
        });
        nextDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextCalendarDate();
            }
        });
        displayDailyEvents(cal.getTime());*/


    }





    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main1, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();

        switch (id){
            /*case R.id.action_today:
                mWeekView.goToToday();
                return true;*/
            case R.id.week_view_btn:

                Intent intent = new Intent(this, Main2Activity.class);
                //intent.putExtra("MyClass", (Serializable) events);
                startActivity(intent);
                return true;
            /*case R.id.action_three_day_view:
                if (mWeekViewType != TYPE_THREE_DAY_VIEW) {
                    item.setChecked(!item.isChecked());
                    mWeekViewType = TYPE_THREE_DAY_VIEW;
                    mWeekView.setNumberOfVisibleDays(3);

                    // Lets change some dimensions to best fit the view.
                    mWeekView.setColumnGap((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8, getResources().getDisplayMetrics()));
                    mWeekView.setTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 12, getResources().getDisplayMetrics()));
                    mWeekView.setEventTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 12, getResources().getDisplayMetrics()));
                }
                return true;*/
            /*case R.id.action_week_view:
                if (mWeekViewType != TYPE_WEEK_VIEW) {
                    item.setChecked(!item.isChecked());
                    mWeekViewType = TYPE_WEEK_VIEW;
                    mWeekView.setNumberOfVisibleDays(7);

                    // Lets change some dimensions to best fit the view.
                    mWeekView.setColumnGap((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2, getResources().getDisplayMetrics()));
                    mWeekView.setTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 10, getResources().getDisplayMetrics()));
                    mWeekView.setEventTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 10, getResources().getDisplayMetrics()));
                }
                return true;*/
        }

        return super.onOptionsItemSelected(item);
    }

        /*@Override
        public boolean onTouchEvent(MotionEvent ev) {
            // Here we actually handle the touch event (e.g. if the action is ACTION_MOVE,
            // scroll this container).
            // This method will only be called if the touch event was intercepted in
            // onInterceptTouchEvent
                return true;
        }*/












    private void setTitleofTB(Date date) {

        SimpleDateFormat month_date = new SimpleDateFormat("MMMM, YYYY");
        String month_name = month_date.format(date.getTime());
        tv_main.setText(month_name);

    }



    /*private void previousCalendarDate(){


        while(reLayout.getChildCount() >= 26) {
            reLayout.removeViewAt(eventIndex - 1);
        }


        cal.add(Calendar.DAY_OF_MONTH, -1);

        currentDate.setText(displayDateInString(cal.getTime()));


        displayDailyEvents(cal.getTime());
    }

    private void nextCalendarDate(){

        while(reLayout.getChildCount() >= 26) {
            reLayout.removeViewAt(eventIndex - 1);
        }

        cal.add(Calendar.DAY_OF_MONTH, 1);
        //cal.set(Calendar.DAY_OF_MONTH, 1);
        currentEventSize = compactCalendarView.getEvents(cal.getTime()).size();
        currentDate.setText(displayDateInString(cal.getTime()));
        //reLayout.removeAllViews();
        displayDailyEvents(cal.getTime());
    }*/

    private String displayDateInString(Date mDate){
        SimpleDateFormat formatter = new SimpleDateFormat("d MMMM, yyyy", Locale.ENGLISH);
        return formatter.format(mDate);
    }
    /*private void displayDailyEvents(Date calendarDate){

        List<Event> currentEvents= compactCalendarView.getEvents(calendarDate);
        Calendar calendar = Calendar.getInstance();
        for(Event eObject : currentEvents){

            calendar.setTimeInMillis(eObject.getTimeInMillis());
            Date eventDate = calendar.getTime();

            String eventMessage = eObject.getData().toString();
            int eventBlockHeight = 40;
            Log.d(TAG, "Height " + eventBlockHeight);
            displayEventSection(eventDate, eventBlockHeight, eventMessage);
        }
    }

    private void displayEventSection(Date eventDate, int height, String message){

        SimpleDateFormat timeFormatter = new SimpleDateFormat("HH:mm", Locale.ENGLISH);
        String displayValue = timeFormatter.format(eventDate);
        String[]hourMinutes = displayValue.split(":");
        int hours = Integer.parseInt(hourMinutes[0]);
        int minutes = Integer.parseInt(hourMinutes[1]);
        Log.d(TAG, "Hour value " + hours);
        Log.d(TAG, "Minutes value " + minutes);
        int topViewMargin = (hours * 50)+10;
        Log.d(TAG, "Margin top " + topViewMargin);
        createEventView(topViewMargin, height, message);
    }

    private void createEventView(int topMargin, int height, String message){
        TextView mEventView = new TextView(MainActivity.this);
        RelativeLayout.LayoutParams lParam = new RelativeLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lParam.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        lParam.topMargin = topMargin;
        lParam.leftMargin = 24;
        mEventView.setLayoutParams(lParam);
        mEventView.setPadding(24, 0, 24, 0);
        mEventView.setHeight(height * 2);
        mEventView.setGravity(0x11);
        mEventView.setTextColor(Color.parseColor("#ffffff"));
        mEventView.setText(message);
        mEventView.setBackgroundColor(Color.parseColor("#3F51B5"));
        reLayout.addView(mEventView, eventIndex - 1);
    }*/

    private void calendarDisplay() {


        compactCalendarView.setFirstDayOfWeek(Calendar.MONDAY);
        compactCalendarView.setUseThreeLetterAbbreviation(true);
        compactCalendarView.setBackgroundColor(WHITE);
        compactCalendarView.setListener(new CompactCalendarView.CompactCalendarViewListener() {


            @Override
            public void onDayClick(Date dateClicked) {
                cal.setTime(dateClicked);
                Context context = getApplicationContext();

            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
                    setTitleofTB(firstDayOfNewMonth);
            }

            @Override
            public void onLongDayClick(final Date dateClicked) {

                LinearLayout layout = new LinearLayout(MainActivity.this);
                layout.setOrientation(LinearLayout.VERTICAL);

                AlertDialog.Builder mBuilder = new AlertDialog.Builder(MainActivity.this);
                View mView = getLayoutInflater().inflate(R.layout.dialog_spinner, null);
                View dView = getLayoutInflater().inflate(R.layout.time_picker, null);
                //mView.setPadding(100,0,0,0);


                View sView = getLayoutInflater().inflate(R.layout.time_picker, null);



                mView.setPadding(100,50,0,0);

                TextView text1 = new TextView(MainActivity.this);
                TextView text2 = new TextView(MainActivity.this);
                TextView tv = new TextView(MainActivity.this);
                TextView text3 = new TextView(MainActivity.this);

                text1.setText("Customer:");
                text1.setTextColor(Color.parseColor("#0000FF"));
                text1.setPadding(100, 50, 0, 0);
                text2.setText("Time:");
                text2.setTextColor(Color.parseColor("#0000FF"));
                text2.setPadding(100, 100, 0, 0);
                text3.setText("Duration:");
                text3.setTextColor(Color.parseColor("#0000FF"));
                text3.setPadding(100, 50, 0, 0);
                tv.setText("Set Event:");
                tv.setTextColor(Color.parseColor("#0000FF"));
                tv.setPadding(50, 50, 0, 0);
                tv.setTextSize(20);


                final TimePicker mPicker = (TimePicker) sView.findViewById(R.id.simpleTimePicker);
                final Spinner mSpinner = (Spinner) mView.findViewById(R.id.spinner);
                final TimePicker dPicker = (TimePicker) dView.findViewById(R.id.simpleTimePicker);

                mPicker.setIs24HourView(true);
                dPicker.setIs24HourView(true);
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.customerList));
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                mSpinner.setAdapter(adapter);

                layout.addView(tv);
                layout.addView(text1);
                layout.addView(mView);
                layout.addView(text2);
                layout.addView(sView);
                layout.addView(text3);
                layout.addView(dView);
                mBuilder.setView(layout);
                mBuilder.setPositiveButton("Set", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String name = mSpinner.getSelectedItem().toString();
                        String dt = dateClicked.toString();
                        String hour = ""+mPicker.getCurrentHour();
                        String minutes = ""+mPicker.getCurrentMinute();
                        int durationhour = dPicker.getCurrentHour();
                        int durationmin = dPicker.getCurrentMinute();
                        try {



                            Long result = toEpochConvert(dt, hour, minutes);
                            WeekViewEvent event = new WeekViewEvent();

                            Event e = new Event();
                            e.setDurationHour(durationhour);
                            e.setDurationMin(durationmin);
                            e.setData(name);
                            e.setTimeInMillis(result);
                            if(name.toString().equals("Samir"))
                            {
                                e.setColor(getResources().getColor(R.color.event_color_01));
                            }
                            else if(name.toString().equals("Jean"))
                            {
                                e.setColor(getResources().getColor(R.color.event_color_02));
                            }
                            else if(name.toString().equals("Frank"))
                            {
                                e.setColor(getResources().getColor(R.color.event_color_03));
                            }
                            else if(name.toString().equals("Fadi"))
                            {
                                e.setColor(getResources().getColor(R.color.event_color_04));
                            }
                            else if(name.toString().equals("Carol"))
                            {
                                e.setColor(getResources().getColor(R.color.colorAccent));
                            }
                            else
                            {
                                e.setColor(getResources().getColor(R.color.colorDivider));
                            }
                            Toast.makeText(MainActivity.this, ""+e.getID(), Toast.LENGTH_SHORT).show();

                            db.createEvent(e);
                            compactCalendarView.addEvent(e, true);
                            events.add(e);
                            ca.notifyDataSetChanged();
                            //events.add(e);
                            //onMonthChange(Calendar.YEAR, Calendar.MONTH);
                            Log.e("inside try:", e.toString());
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }


                        Log.e("dialogue:", ""+dateClicked.toString());
                        //Toast.makeText(MainActivity.this, ""+dc.getTime(), Toast.LENGTH_LONG).show();

                    }
                });

                mBuilder.setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                AlertDialog dialog = mBuilder.create();
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.show();


            }
        });
    }


    private void panelSlide() {


        mLayout.addPanelSlideListener(new SlidingUpPanelLayout.PanelSlideListener() {
            Context m = getApplicationContext();
            @Override
            public void onPanelSlide(View panel, float slideOffset) {
                if(!act) {

                    Log.i(TAG, "onPanelSlide, offset " + slideOffset);

                }
            }

            @Override
            public void onPanelStateChanged(View panel, SlidingUpPanelLayout.PanelState previousState, SlidingUpPanelLayout.PanelState newState) {
                Log.i(TAG, "onPanelStateChanged " + newState);
            }
        });
        mLayout.setFadeOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
            }
        });
    }


    private void loadEventsList()
    {
        events = db.getAllEvents();
        compactCalendarView.addEvents(events);

        Log.e("createElist!!", "listcreated");
    }


    public long toEpochConvert(String date, String hour, String minute) throws ParseException {

        String str = date;

        StringBuilder myDate = new StringBuilder(str);
        if(hour.length()==1)
        {
            hour = "0"+hour;
        }
        if(minute.length()==1)
        {
            minute = "0"+minute;
        }
        myDate.setCharAt(11, hour.charAt(0));
        myDate.setCharAt(12, hour.charAt(1));
        myDate.setCharAt(14, minute.charAt(0));
        myDate.setCharAt(15, minute.charAt(1));


        SimpleDateFormat df = new SimpleDateFormat("E MMM dd HH:mm:ss Z yyyy");

        Date dt = df.parse(myDate.toString());
        //long epoch = Long.parseLong((""+dt.getTime()+"L"));
        //System.out.println(epoch); //1055545912454
        Log.e("the date123123123123:", ""+dt.getTime());
        return dt.getTime();

    }


    @Override
    public void onBackPressed() {
        if (mLayout != null &&
                (mLayout.getPanelState() == SlidingUpPanelLayout.PanelState.EXPANDED || mLayout.getPanelState() == SlidingUpPanelLayout.PanelState.ANCHORED)) {
            mLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
        } else {
            super.onBackPressed();
        }
    }
}
