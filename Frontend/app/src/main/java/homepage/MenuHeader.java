package homepage;

import android.content.Context;
import android.util.Log;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import api.VolleySingleton;
import websockets.WebSocketManager;

public class MenuHeader {
    private static final String URL_STRING_REQ = "http://coms-309-024.class.las.iastate.edu:8080/username/";
    private final Context context;

    public MenuHeader(Context context) {
        this.context = context;
    }
    public String getUsername() {
        return WebSocketManager.getInstance().getUsername();
    }

    public void getUserEmailAddress(String username, TextView email) {
        StringRequest stringRequest = new StringRequest(
                Request.Method.GET,
                URL_STRING_REQ + username,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject responseJSON = new JSONObject(response);
                            email.setText(responseJSON.getString("email"));
                        } catch (JSONException e) {
                            e.printStackTrace();
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
        VolleySingleton.getInstance(context.getApplicationContext()).addToRequestQueue(stringRequest);;
    }
}
