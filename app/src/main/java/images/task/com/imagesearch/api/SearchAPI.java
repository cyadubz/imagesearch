package images.task.com.imagesearch.api;

import android.app.ActivityManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import images.task.com.imagesearch.TheApplication;
import images.task.com.imagesearch.interfaces.ApiInterface;
import images.task.com.imagesearch.interfaces.NetworkListener;
import images.task.com.imagesearch.model.SearchItem;
import images.task.com.imagesearch.model.SearchSettings;

/**
 * Created by Вадим on 25.08.2014.
 */
public class SearchAPI extends NetworkHandleThread implements ApiInterface {

    private static final String CUSTOM_SEARCH_KEY = "017744976890893520337:gkr-56jfbuk";
    //private static final String CUSTOM_SEARCH_KEY = "017744976890893520337:kk4smpl9swi";
    //private static final String CUSTOM_SEARCH_KEY = "001535641358891162607:tgmzrj4u26e";
    private static final String API_KEY = "AIzaSyD42Ck7ASBzTp05gb54ZVmPZ3bG2noZegA";
    private static final String GOOGLE_API_URL = "https://www.googleapis.com/customsearch/v1?";
    private static final String SEARCH_TYPE_IMAGES = "&searchType=image";


    private static SearchAPI instance;
    private RequestQueue requestQueue;
    private ImageLoader imageLoader;

    private SearchAPI() {
        super("API_THREAD");

        requestQueue = Volley.newRequestQueue(TheApplication.getAppContext());
        int memClass = ((ActivityManager) TheApplication.getAppContext().getSystemService(Context.ACTIVITY_SERVICE)).getMemoryClass();
        // Use 1/8th of the available memory for this memory cache.
        int cacheSize = 1024 * 1024 * memClass / 8;
        imageLoader = new ImageLoader(requestQueue, new BitmapLruCache(
                cacheSize));
    }

    public static SearchAPI getInstance() {
        if (instance == null) {
            instance = new SearchAPI();
        }
        return instance;
    }

    private String getInitialParameters() {
        String params;
        StringBuilder builder = new StringBuilder();
        builder.append("key=").append(API_KEY).append("&cx=").append(CUSTOM_SEARCH_KEY);
        params = builder.toString();
        return params;
    }

    public static boolean hasConnection() {
        ConnectivityManager cm = (ConnectivityManager) TheApplication.getAppContext().getSystemService(
                Context.CONNECTIVITY_SERVICE);

        NetworkInfo wifiNetwork = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (wifiNetwork != null && wifiNetwork.isConnected()) {
            return true;
        }

        NetworkInfo mobileNetwork = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if (mobileNetwork != null && mobileNetwork.isConnected()) {
            return true;
        }

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnected();
    }

    @Override
    public void search(String query, SearchSettings settings, NetworkListener networkListener) {
        addRequest(new SearchRunnable(query, settings, networkListener), networkListener);
    }

    private void addRequest(Runnable runnable,  NetworkListener networkListener) {
        if (hasConnection()) {
            getHandler().post(runnable);
        } else {
            networkListener.onFailure(null, "No Internet Connection");
        }
    }

    public ImageLoader getImageLoader() {
        if (imageLoader != null) {
            return imageLoader;
        } else {
            throw new IllegalStateException("ImageLoader not initialized");
        }
    }

    private class SearchRunnable implements Runnable {

        private String query;
        private NetworkListener networkListener;
        private SearchSettings settings;

        public SearchRunnable(String query, SearchSettings settings, NetworkListener networkListener) {
            this.query = query;
            this.settings = settings;
            this.networkListener = networkListener;
        }

        @Override
        public void run() {
            try {
                addSearchRequest();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private String getSettingsParameters() {
            String params;
            StringBuilder builder = new StringBuilder();
            if (settings.getImgType() != "") {
                builder.append("&imgType=").append(settings.getImgType());
            }
            if (settings.getSiteSearch() != "") {
                builder.append("&siteSearch=").append(settings.getSiteSearch());
            }

            params = builder.toString();
            return params;
        }

        private Request<?> addSearchRequest() throws IOException {
            String url = String.format("%s%s%s%s%s%s", GOOGLE_API_URL, getInitialParameters(), SEARCH_TYPE_IMAGES, "&q=", query, getSettingsParameters());

            Response.Listener<JSONObject> listener = new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    JSONArray jsonArray = new JSONArray();
                    try {
                        jsonArray = response.getJSONArray("items");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    ArrayList<SearchItem> items = new ArrayList<SearchItem>();

                    for (int i = 0; i < jsonArray.length(); i++) {
                        SearchItem item = new SearchItem(jsonArray.optJSONObject(i));
                        items.add(item);
                    }

                    networkListener.onSuccess(items);
                }
            };

            JsonObjectRequest jsonObjRequest = new JsonObjectRequest(url, null, listener, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    networkListener.onFailure(null, error.getMessage());
                }
            });

            //requestQueue.add(jsonObjRequest);
            return requestQueue.add(jsonObjRequest);
        }
    }
}
