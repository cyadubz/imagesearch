package images.task.com.imagesearch.interfaces;

/**
 * Created by Вадим on 22.08.2014.
 */
public interface NetworkListener<T> {
    public void onSuccess(T responseObject);
    public void onFailure(T responseObject, String errorMessage);
}
