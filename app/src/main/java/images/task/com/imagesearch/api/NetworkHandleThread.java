package images.task.com.imagesearch.api;

import android.os.Handler;
import android.os.HandlerThread;

/**
 * Created by Вадим on 25.08.2014.
 */
public class NetworkHandleThread extends HandlerThread {
    private Handler mHandler;

    public NetworkHandleThread(String name) {
        super(name);
        start();
        mHandler = new Handler(getLooper());
    }


    public synchronized  Handler getHandler() {
        return mHandler;
    }
}
