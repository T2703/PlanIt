package notifications;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import api.VolleySingleton;
import events.Event;
import websockets.WebSocketManager;

public class Notification {
    private String title;
    private String description;
    private boolean isRead;
    private boolean isSelected;

    public Notification(String title, String description, boolean isRead) {
        this.title = title;
        this.description = description;
        this.isRead = isRead;
        this.isSelected = false;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public boolean getIsRead() {
        return isRead;
    }

    public boolean getIsSelected() {
        return isSelected;
    }

    public void setIsSelected(boolean flag) {
        this.isSelected = flag;
    }
}
