package utilities;

import android.content.Context;
import android.util.Log;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import api.VolleySingleton;

public class NotificationsHelper {
    public static final String URL_STRING_REQ = "http://coms-309-024.class.las.iastate.edu:8080/notifications";

    public static void setNumberOfUnreadNotifications(TextView number) {
        StringRequest stringRequest = new StringRequest(
                Request.Method.GET,
                URL_STRING_REQ,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray list = new JSONArray(response);
                            int numOfNotificationsUnread = 0;

                            for (int i = 0; i < list.length(); i++) {
                                numOfNotificationsUnread++;
                            }

                            number.setText(String.valueOf(numOfNotificationsUnread));
                            Log.d("total", String.valueOf(numOfNotificationsUnread));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle any errors that occur during the request
                        Log.e("A server error has occurred, please try again later.", error.toString());
                    }
                }
        );
        // Adding request to request queue
        VolleySingleton.getInstance(number.getContext()).addToRequestQueue(stringRequest);
        ;
    }
}
