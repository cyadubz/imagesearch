package images.task.com.imagesearch.interfaces;

import android.graphics.Bitmap;

import java.util.ArrayList;

import images.task.com.imagesearch.model.SearchItem;

/**
 * Created by Вадим on 30.08.2014.
 */
public interface ContentListener {
    public void saveBitmap(Bitmap bitmap);

    public void updateItems(ArrayList<SearchItem> items);

    public void showSearchResultsFragment();

    public void showComparingFragment(int position);
}
