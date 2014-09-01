package images.task.com.imagesearch;

import android.app.Application;
import android.content.Context;

import images.task.com.imagesearch.api.SearchAPI;

/**
 * Created by Вадим on 22.08.2014.
 */
public class TheApplication extends Application {

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        SearchAPI.getInstance();
    }

    public static Context getAppContext() {
        return context;
    }
}
