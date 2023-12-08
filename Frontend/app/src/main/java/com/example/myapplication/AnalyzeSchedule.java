package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import api.VolleySingleton;
import calendar.CalendarDailyPage;
import calendar.CalendarMonthlyPage;
import calendar.CalendarWeeklyPage;
import events.Event;
import events.EventsListViewer;
import groups.GroupInfo;
import groups.MemberViewer;
import profile.UserManager;


public class AnalyzeSchedule extends AppCompatActivity {

    /**
     * Back button.
     */
    private ImageButton back_button;
    private TextView textView;

    private String result ="";

    /**
     * The button that brings up the popup menu displaying the views of the calendars.
     */
    private ImageButton menu_button;


    private static final String URL= "http://coms-309-024.class.las.iastate.edu:8080/scheduleAnalysis/";  //userId

    UserManager userManager = UserManager.getInstance();

    private void getRequest(){
        StringRequest stringRequest = new StringRequest(
                Request.Method.GET,
                URL + userManager.getUserID(),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("result", response);

                        try {
                            textView = findViewById(R.id.response);
                            textView.setText(response);
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle any errors that occur during the request
                        Log.e("A server error has occurred", error.toString());
                    }
                }
        );

        // Adding request to request queue
        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //textView = findViewById(R.id.textView);
        setContentView(R.layout.activity_analyze_schedule);
        //back_button = findViewById(R.id.back_button_two2);
        menu_button = findViewById(R.id.menu_calendar_button);
        getRequest();
        //textView.setText(result);

        /*back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                overridePendingTransition(R.anim.empty_anim, R.anim.empty_anim);
            }
        });*/

        menu_button.setOnClickListener(new View.OnClickListener() {
            /**
             * Called when the specified view is clicked. Creates and displays a {@link PopupMenu}
             * anchored to the clicked view, inflates the options menu for the calendar, and shows the popup menu.
             *
             * @param view The view that was clicked.
             *             It can be used to identify which view triggered the click event.
             */
            @Override
            public void onClick(View view) {
                PopupMenu popup_menu = new PopupMenu(AnalyzeSchedule.this, view);
                popup_menu.getMenuInflater().inflate(R.menu.options_menu_calendar, popup_menu.getMenu());
                popup_menu.show();

                popup_menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    /**
                     * Called when a menu item in the options menu is clicked.
                     * Performs actions based on the selected menu item, such as starting a new activity.
                     *
                     * @param menuItem The menu item that was clicked.
                     *                 It can be used to identify which menu item triggered the click event.
                     * @return true if the menu item click has been handled, false otherwise.
                     */
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        if (menuItem.getItemId() == R.id.monthly_view) {
                            Intent intent = new Intent(AnalyzeSchedule.this, CalendarMonthlyPage.class);
                            ActivityOptions options = ActivityOptions.makeCustomAnimation(AnalyzeSchedule.this, R.anim.empty_anim, R.anim.empty_anim);
                            startActivity(intent, options.toBundle());
                        }

                        else if (menuItem.getItemId() == R.id.daily_view) {
                            Intent intent = new Intent(AnalyzeSchedule.this, CalendarDailyPage.class);
                            ActivityOptions options = ActivityOptions.makeCustomAnimation(AnalyzeSchedule.this, R.anim.empty_anim, R.anim.empty_anim);
                            startActivity(intent, options.toBundle());
                        }

                        else if (menuItem.getItemId() == R.id.all_events) {
                            Intent intent = new Intent(AnalyzeSchedule.this, EventsListViewer.class);
                            ActivityOptions options = ActivityOptions.makeCustomAnimation(AnalyzeSchedule.this, R.anim.empty_anim, R.anim.empty_anim);
                            startActivity(intent, options.toBundle());
                        }

                        else if (menuItem.getItemId() == R.id.weekly_view) {
                            Intent intent = new Intent(AnalyzeSchedule.this, CalendarWeeklyPage.class);
                            ActivityOptions options = ActivityOptions.makeCustomAnimation(AnalyzeSchedule.this, R.anim.empty_anim, R.anim.empty_anim);
                            startActivity(intent, options.toBundle());
                        }

                        return true;
                    }
                });

                popup_menu.show();
            }
        });
    }






    
}