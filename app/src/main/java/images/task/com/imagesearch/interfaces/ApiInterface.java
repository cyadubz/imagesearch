package images.task.com.imagesearch.interfaces;

import images.task.com.imagesearch.model.SearchSettings;

/**
 * Created by Вадим on 25.08.2014.
 */
public interface ApiInterface {
    public void search(String query, SearchSettings settings, NetworkListener networkListener);
}
