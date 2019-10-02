package id.ac.umn.made_bbac_submission5.databases;

import android.net.Uri;

public class FavoritesHelper {

    private static final String TABLE_FAVORITES = "favorites";

    // SELECT COLUMN1, COLUMN2, COLUMN3, ...
    private String[] TABLE_COLUMN = {
            "data_id",
            "data_type",
            "data_title",
            "data_img",
            "data_overview",
            "data_score",
            "data_popularity",
            "user_name"
    };

    private static final String AUTHORITY = "id.ac.umn.made_basiliusbias_submission5";
    private static final String SCHEME = "content";

    public static final Uri CONTENT_URI = new Uri.Builder().scheme(SCHEME).authority(AUTHORITY).appendPath(TABLE_FAVORITES).build();

}
