package images.task.com.imagesearch.model;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONObject;

import images.task.com.imagesearch.api.SearchAPI;

/**
 * Created by Вадим on 27.08.2014.
 */
public class SearchItem implements Parcelable {

    private String displayLink;
    private String title;
    private String link;
    private String snippet;

    public SearchItem() {

    }

    public SearchItem(JSONObject jsonObject) {
        this.displayLink = jsonObject.optString("displayLink");
        this.title = jsonObject.optString("title");
        this.link = jsonObject.optString("link");
        this.snippet = jsonObject.optString("snippet");
    }

    public String getDisplayLink() {
        return displayLink;
    }

    public void setDisplayLink(String displayLink) {
        this.displayLink = displayLink;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getSnippet() {
        return snippet;
    }

    public void setSnippet(String snippet) {
        this.snippet = snippet;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(displayLink);
        dest.writeString(title);
        dest.writeString(link);
        dest.writeString(snippet);
    }

    public SearchItem(Parcel in) {
        this.displayLink = in.readString();
        this.title = in.readString();
        this.link = in.readString();
        this.snippet = in.readString();
    }

    public static Creator<SearchItem> CREATOR = new Creator<SearchItem>() {
        @Override
        public SearchItem createFromParcel(Parcel source) {
            return new SearchItem(source);
        }

        @Override
        public SearchItem[] newArray(int size) {
            return new SearchItem[size];
        }
    };
}
