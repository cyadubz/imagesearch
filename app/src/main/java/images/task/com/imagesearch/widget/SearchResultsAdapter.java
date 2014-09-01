package images.task.com.imagesearch.widget;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;

import java.util.List;

import images.task.com.imagesearch.R;
import images.task.com.imagesearch.api.SearchAPI;
import images.task.com.imagesearch.model.SearchItem;

/**
 * Created by Вадим on 30.08.2014.
 */
public class SearchResultsAdapter extends AbstractAdapter<SearchItem, SearchResultsAdapter.SearchResultsHolder> {

    public SearchResultsAdapter(Context context, List<SearchItem> items) {
        super(context, R.layout.search_result_item, items);
    }

    @Override
    protected void bindView(SearchResultsHolder viewHolder, SearchItem item) {

        viewHolder.imageView.setImageUrl(item.getLink(), SearchAPI.getInstance().getImageLoader());
        viewHolder.textTitle.setText(item.getTitle());
        viewHolder.textSnippet.setText(item.getSnippet());
    }

    @Override
    protected SearchResultsHolder createHolder(View view) {
        return new SearchResultsHolder(view);
    }

    public static class SearchResultsHolder extends AbstractAdapter.ViewHolder {

        private NetworkImageView imageView;
        private TextView textTitle;
        private TextView textSnippet;

        public SearchResultsHolder(final View view) {
            super(view);
            imageView = get(R.id.image_SearchResultsItem);
            textTitle = get(R.id.textTitle_SearchResultsItem);
            textSnippet = get(R.id.textSnippet_SearchResultsItem);
        }

    }

}
