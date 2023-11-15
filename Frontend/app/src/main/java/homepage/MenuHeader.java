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

/**
 * @author Joshua Gutierrez
 * The MenuHeader class is responsible for managing the header information displayed in the navigation menu.
 * It retrieves and displays the username and email address associated with the logged-in user.
 */
public class MenuHeader {
    private static final String URL_STRING_REQ = "http://coms-309-024.class.las.iastate.edu:8080/username/";
    private final Context context;

    /**
     * Constructs a new MenuHeader instance with the specified context.
     *
     * @param context The context in which the MenuHeader is created.
     */
    public MenuHeader(Context context) {
        this.context = context;
    }

    /**
     * Retrieves the username of the logged-in user using the WebSocketManager.
     *
     * @return The username of the logged-in user.
     */
    public String getUsername() {
        return WebSocketManager.getInstance().getUsername();
    }

    /**
     * Retrieves the email address of the user with the given username and updates the provided TextView.
     * Makes a network request to fetch the user's email address from the server.
     *
     * @param username The username of the user for whom to retrieve the email address.
     * @param email    The TextView where the email address will be displayed.
     */
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

        VolleySingleton.getInstance(context.getApplicationContext()).addToRequestQueue(stringRequest);;
    }
}
