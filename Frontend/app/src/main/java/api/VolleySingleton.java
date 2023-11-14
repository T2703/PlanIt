package api;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * The {@code VolleySingleton} class represents a singleton instance of the Volley library's
 * {@link com.android.volley.RequestQueue}. It ensures a single instance of the request queue
 * is used throughout the application to handle network requests efficiently.
 * <p>
 * This class follows the Singleton design pattern, providing a single point of access to the
 * Volley request queue. It is responsible for initializing the request queue and adding requests
 * to it.
 * </p>
 * @author Joshua Gutierrez
 */
public class VolleySingleton {
    /**
     * The single instance of the {@code VolleySingleton} class.
     */
    private static VolleySingleton instance;
    /**
     * The application context.
     */
    private static Context context;
    /**
     * The Volley request queue.
     */
    private RequestQueue requestQueue;

    /**
     * Private constructor to prevent instantiation from outside the class.
     *
     * @param context The application context.
     */
    private VolleySingleton(Context context) {
        VolleySingleton.context = context;
        requestQueue = getRequestQueue();
    }

    /**
     * Retrieves the singleton instance of the {@code VolleySingleton} class.
     *
     * @param context The application context.
     * @return The singleton instance of {@code VolleySingleton}.
     */
    public static synchronized VolleySingleton getInstance(Context context) {
        if (instance == null) {
            instance = new VolleySingleton(context.getApplicationContext());
        }
        return instance;
    }

    /**
     * Retrieves the Volley request queue.
     *
     * @return The Volley request queue.
     */
    public RequestQueue getRequestQueue() {
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(context);
        }
        return requestQueue;
    }

    /**
     * Adds a request to the Volley request queue.
     *
     * @param request The request to be added to the queue.
     * @param <T>     The type of the response expected from the request.
     */
    public <T> void addToRequestQueue(Request<T> request) {
        getRequestQueue().add(request);
    }
}
