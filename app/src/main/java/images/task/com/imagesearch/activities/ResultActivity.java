package images.task.com.imagesearch.activities;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

import images.task.com.imagesearch.R;
import images.task.com.imagesearch.fragments.ComparingFragment;
import images.task.com.imagesearch.fragments.SearchResultsFragment;
import images.task.com.imagesearch.interfaces.ContentListener;
import images.task.com.imagesearch.model.SearchItem;

public class ResultActivity extends Activity implements ContentListener {

    public final static String ITEMS_KEY = "items";
    public final static String ITEM_KEY = "item";
    public final static String BITPAM_KEY = "bitmap";

    private Bitmap bitmap;
    private ArrayList<SearchItem> items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        if (getIntent().getExtras() != null) {
            items = getIntent().getParcelableArrayListExtra(ITEMS_KEY);
        }

        initActivityViews();
    }

    private void initActivityViews() {
        showComparingFragment(0);
        Button buttonMore = (Button)findViewById(R.id.buttonMore_ResultsActivity);
        buttonMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSearchResultsFragment();
            }
        });
    }

    public void showSearchResultsFragment() {
        Fragment fragment = new SearchResultsFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(ITEMS_KEY, items);
        fragment.setArguments(bundle);
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.frameContent_ResultActivity, fragment);
        transaction.commit();
    }

    public void showComparingFragment(int position) {
        Fragment fragment = new ComparingFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(ITEM_KEY, items.get(position));
        if (bitmap != null) {
            bundle.putParcelable(BITPAM_KEY, bitmap);
        }
        fragment.setArguments(bundle);
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.frameContent_ResultActivity, fragment);
        transaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.result, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void saveBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    @Override
    public void updateItems(ArrayList<SearchItem> items) {
        this.items = items;
    }
}
