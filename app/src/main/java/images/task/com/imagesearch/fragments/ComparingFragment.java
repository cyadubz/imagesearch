package images.task.com.imagesearch.fragments;



import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;

import images.task.com.imagesearch.R;
import images.task.com.imagesearch.TheApplication;
import images.task.com.imagesearch.activities.ResultActivity;
import images.task.com.imagesearch.api.SearchAPI;
import images.task.com.imagesearch.interfaces.ContentListener;
import images.task.com.imagesearch.model.SearchItem;

/**
 * A simple {@link Fragment} subclass.
 *
 */
public class ComparingFragment extends Fragment {

    private SearchItem item;
    private static final int REQUEST_TAKE_PHOTO = 1;
    private ImageView imagePhoto;
    private ContentListener contentListener;
    private TextView textComparing;

    public ComparingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_comparing, container, false);
        initFragmentViews(view);
        return view;
    }

    private void initFragmentViews(View view) {
        NetworkImageView image = (NetworkImageView)view.findViewById(R.id.imageItem_ComparingFragment);
        imagePhoto = (ImageView)view.findViewById(R.id.imageComparing_ComparingFragment);
        TextView textTitle = (TextView)view.findViewById(R.id.textTitle_ComparingFragment);
        TextView textSnippet = (TextView)view.findViewById(R.id.textSnippet_ComparingFragment);
        textComparing = (TextView)view.findViewById(R.id.textComparing);

        if (getArguments() != null) {
            item = getArguments().getParcelable(ResultActivity.ITEM_KEY);
            Bitmap bitmap = getArguments().getParcelable(ResultActivity.BITPAM_KEY);
            imagePhoto.setImageBitmap(bitmap);
            if (bitmap != null) {
                makePhotoVisible();
            }
        }

        image.setImageUrl(item.getLink(), SearchAPI.getInstance().getImageLoader());
        textTitle.setText(item.getTitle());
        textSnippet.setText(item.getSnippet());

        Button buttonAddPhoto = (Button)view.findViewById(R.id.buttonAddPhoto_ComparingFragment);
        if (!getActivity().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
            buttonAddPhoto.setEnabled(false);
        }
        buttonAddPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchTakePictureIntent();
            }
        });
    }

    private void dispatchTakePictureIntent() {
        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        if (cameraIntent.resolveActivity(TheApplication.getAppContext().getPackageManager()) != null) {
            startActivityForResult(cameraIntent, REQUEST_TAKE_PHOTO);
        }
    }

    private void makePhotoVisible() {
        textComparing.setVisibility(View.GONE);
        imagePhoto.setVisibility(View.VISIBLE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == getActivity().RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            imagePhoto.setImageBitmap(photo);
            contentListener.saveBitmap(photo);
            makePhotoVisible();
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        contentListener = (ContentListener)getActivity();
    }
}
