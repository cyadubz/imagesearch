package images.task.com.imagesearch.fragments;



import android.app.Activity;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import images.task.com.imagesearch.R;
import images.task.com.imagesearch.activities.ResultActivity;
import images.task.com.imagesearch.interfaces.ContentListener;
import images.task.com.imagesearch.model.SearchItem;
import images.task.com.imagesearch.widget.SearchResultsAdapter;

/**
 * A simple {@link Fragment} subclass.
 *
 */
public class SearchResultsFragment extends Fragment {

    private ListView listView;
    private SearchResultsAdapter adapter;
    private ArrayList<SearchItem> items;
    private ContentListener contentListener;

    public SearchResultsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search_results, container, false);
        initFragmentViews(view);
        return view;
    }

    private void initFragmentViews(View view) {
        if (getArguments() != null) {
            items = getArguments().getParcelableArrayList(ResultActivity.ITEMS_KEY);
        }

        listView = (ListView)view.findViewById(R.id.listView_SearchResultsFragment);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                contentListener.showComparingFragment(position);
            }
        });

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        adapter = new SearchResultsAdapter(getActivity(), items);
        listView.setAdapter(adapter);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        contentListener = (ContentListener)getActivity();
    }
}
