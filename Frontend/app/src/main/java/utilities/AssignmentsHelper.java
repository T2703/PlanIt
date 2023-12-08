package utilities;

import android.content.Context;
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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import api.VolleySingleton;
import assignments.Course;
import homepage.Assignment;
import websockets.WebSocketManager;

public class AssignmentsHelper {

    public static final String URL_STRING_REQ = "http://coms-309-024.class.las.iastate.edu:8080/users/";
    public static List<Course> courses = new ArrayList<>();

    public static List<Assignment> assignments = new ArrayList<>();

    public static String getCourseById(String id) {
        for (int i = 0; i < courses.size(); i++) {
            if (Objects.equals(courses.get(i).getId(), id)) {
                return courses.get(i).getName();
            }
        }
        return null;
    }

    public static void getCoursesIds(Context context) {
        String token = WebSocketManager.getInstance().getAccessToken();
        String GET_COURSES_ID_URL = "https://canvas.iastate.edu/api/v1/courses?per_page=50&access_token=" + token;

        StringRequest stringRequest = new StringRequest(
                Request.Method.GET,
                GET_COURSES_ID_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray responseArray = new JSONArray(response);

                            for (int i = 0; i < responseArray.length(); i++) {
                                JSONObject object = responseArray.getJSONObject(i);

                                if (object.has("name")) {
                                    Course course = new Course(object.getString("id"),object.getString("name"));
                                    courses.add(course);
                                }
                            }
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("A server error has occurred while trying to get you token", error.toString());
                    }
                }
        );

        VolleySingleton.getInstance(context.getApplicationContext()).addToRequestQueue(stringRequest);
    }

    public static void populateAssignments(Context context) {
        for (int i = 0; i < courses.size(); i++) {
            getAssignments(courses.get(i).getId(), context);
        }
    }

    public static void getAssignments(String courseId, Context context) {
        String token = WebSocketManager.getInstance().getAccessToken();
        String GET_COURSES_ID_URL = "https://canvas.iastate.edu/api/v1/courses/" + courseId + "/assignments?per_page=50&access_token=" + token;

        StringRequest stringRequest = new StringRequest(
                Request.Method.GET,
                GET_COURSES_ID_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray responseArray = new JSONArray(response);

                            if (responseArray.length() != 0) {
                                for (int i = 0; i < responseArray.length(); i++) {
                                    JSONObject assignmentObj = responseArray.getJSONObject(i);

                                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");

                                    String name = AssignmentsHelper.getCourseById(courseId);
                                    String title = assignmentObj.getString("name");
                                    String dueDate = DateAndTimeHelper.formatDateForAssignments(assignmentObj.getString("due_at"));

                                    try {
                                        Date date = dateFormat.parse(assignmentObj.getString("due_at"));
                                        long millis = date.getTime();

                                        if (millis >= System.currentTimeMillis()) {
                                            assignments.add(new Assignment(name, title, "Due: " + dueDate));
                                        }
                                    } catch (ParseException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("A server error has occurred while trying to get you token", error.toString());
                    }
                }
        );

        VolleySingleton.getInstance(context.getApplicationContext()).addToRequestQueue(stringRequest);
    }
}
