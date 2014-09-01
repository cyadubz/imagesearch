package images.task.com.imagesearch.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.android.volley.toolbox.NetworkImageView;

import java.util.ArrayList;

import images.task.com.imagesearch.R;
import images.task.com.imagesearch.api.SearchAPI;
import images.task.com.imagesearch.interfaces.NetworkListener;
import images.task.com.imagesearch.model.SearchItem;
import images.task.com.imagesearch.model.SearchSettings;


public class HomeActivity extends Activity {

    private EditText queryEditText;
    private EditText siteEditText;

    private RadioButton radioClipart;
    private RadioButton radioFace;
    private RadioButton radioLineart;
    private RadioButton radioNews;
    private RadioButton radioPhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initActivityViews();
    }

    private void initActivityViews() {
        Button searchButton = (Button)findViewById(R.id.buttonSearch_HomeActivity);
        queryEditText = (EditText)findViewById(R.id.editQuery_HomeActivity);
        siteEditText = (EditText)findViewById(R.id.editSite_HomeActivity);
        radioClipart = (RadioButton)findViewById(R.id.radioClipart_HomeActivity);
        radioFace = (RadioButton)findViewById(R.id.radioFace_HomeActivity);
        radioLineart = (RadioButton)findViewById(R.id.radioLineart_HomeActivity);
        radioNews = (RadioButton)findViewById(R.id.radioNews_HomeActivity);
        radioPhoto = (RadioButton)findViewById(R.id.radioPhoto_HomeActivity);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String query = queryEditText.getText().toString().trim();
                if (query.length() > 0) {
                    SearchAPI.getInstance().search(query, prepareSettings(), new NetworkListener<ArrayList<SearchItem>>() {
                        @Override
                        public void onSuccess(ArrayList<SearchItem> responseObject) {
                            if (responseObject.size() == 0) {
                                Toast.makeText(HomeActivity.this, getString(R.string.no_results), Toast.LENGTH_SHORT).show();
                                return;
                            }
                            Intent intent = new Intent(HomeActivity.this, ResultActivity.class);
                            intent.putParcelableArrayListExtra(ResultActivity.ITEMS_KEY, responseObject);
                            startActivity(intent);
                        }

                        @Override
                        public void onFailure(ArrayList<SearchItem> responseObject, String errorMessage) {
                        }
                    });
                }
            }
        });
    }

    private SearchSettings prepareSettings() {
        String imgType = "";
        String site;

        if (radioClipart.isChecked()) {
            imgType = SearchSettings.IMG_TYPE_CLIPART;
        }
        else if (radioFace.isChecked()) {
            imgType = SearchSettings.IMG_TYPE_FACE;
        }
        else if (radioLineart.isChecked()) {
            imgType = SearchSettings.IMG_TYPE_LINEART;
        }
        else if (radioNews.isChecked()) {
            imgType = SearchSettings.IMG_TYPE_NEWS;
        }
        else if (radioPhoto.isChecked()) {
            imgType = SearchSettings.IMG_TYPE_PHOTO;
        }

        site = siteEditText.getText().toString().trim();

        return new SearchSettings.Builder().setImageType(imgType).setSite(site).build();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
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
}
