package images.task.com.imagesearch.model;

/**
 * Created by Вадим on 25.08.2014.
 */
public class SearchSettings {

    public final static String IMG_TYPE_CLIPART = "clipart";
    public final static String IMG_TYPE_FACE = "face";
    public final static String IMG_TYPE_LINEART = "lineart";
    public final static String IMG_TYPE_NEWS = "news";
    public final static String IMG_TYPE_PHOTO = "photo";


    // Here may be more settings but it is for now..

    private String siteSearch;
    private String imgType;


    public String getSiteSearch() {
        return siteSearch;
    }

    public void setSiteSearch(String siteSearch) {
        this.siteSearch = siteSearch;
    }

    public String getImgType() {
        return imgType;
    }

    public void setImgType(String imgType) {
        this.imgType = imgType;
    }

    private SearchSettings(Builder builder) {
        this.siteSearch = builder.siteSearch;
        this.imgType = builder.imgType;
    }

    public static class Builder {
        private String siteSearch;
        private String imgType;

        public Builder() {

        }

        public Builder setSite(String siteSearch) {
            this.siteSearch = siteSearch;
            return this;
        }

        public Builder setImageType(String imageType) {
            this.imgType = imageType;
            return this;
        }

        public SearchSettings build() {
            return new SearchSettings(this);
        }

    }
}
