package id.ac.umn.made_basiliusbias_submission5.models;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Binder;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import id.ac.umn.made_basiliusbias_submission5.R;
import id.ac.umn.made_basiliusbias_submission5.pojos.Movie;
import id.ac.umn.made_basiliusbias_submission5.pojos.Tv;

import java.util.ArrayList;
import java.util.List;

import static id.ac.umn.made_basiliusbias_submission5.databases.FavoritesHelper.CONTENT_URI;

public class FavoriteViewModel extends ViewModel {

    // Shared Preferences
    private static final String PREFERENCES_FILENAME = "USER_INFORMATION";
    private static final int PREFERENCES_MODE = Context.MODE_PRIVATE;
    private static final String KEY_USERNAME = "USERNAME";

    private MutableLiveData<List<Movie>> listFavoriteMovie = new MutableLiveData<>();
    private MutableLiveData<List<Tv>> listFavoriteTv = new MutableLiveData<>();

    public void loadFavorite(View v, ImageView loadingImage, TextView loadingText, String data_type) {

        // Hide Loading Animation
        loadingImage.setVisibility(View.GONE);
        loadingText.setVisibility(View.GONE);

        final long identityToken = Binder.clearCallingIdentity();

        // Get Data Shared Preferences For Login
        SharedPreferences userInfo = v.getContext().getSharedPreferences(PREFERENCES_FILENAME, PREFERENCES_MODE);

        Uri uri = Uri.parse(CONTENT_URI + "/" + userInfo.getString(KEY_USERNAME, "") + "/" + data_type);

        List<Movie> movies = new ArrayList<>();
        List<Tv> tvs = new ArrayList<>();

        Cursor cursor = v.getContext().getContentResolver().query(uri, null, null, null, null, null);
        while(cursor != null && cursor.moveToNext()) {
            if(data_type.equalsIgnoreCase("movie")) {
                movies.add(
                    new Movie(
                        cursor.getInt(0),
                        cursor.getString(3),
                        cursor.getString(2),
                        cursor.getInt(6),
                        cursor.getInt(5)
                    )
                );
            }
            else if(data_type.equalsIgnoreCase("tv")) {
                tvs.add(
                    new Tv(
                        cursor.getString(3),
                        cursor.getInt(0),
                        cursor.getDouble(5),
                        cursor.getDouble(6),
                        cursor.getString(2),
                        cursor.getString(4)
                    )
                );
            }
        }
        if(cursor != null) cursor.close();

        Binder.restoreCallingIdentity(identityToken);

        if(data_type.equalsIgnoreCase("movie")) listFavoriteMovie.postValue(movies);
        else if(data_type.equalsIgnoreCase("tv")) listFavoriteTv.postValue(tvs);

        v.findViewById(R.id.recycler_fragment).setVisibility(View.VISIBLE);
    }

    public LiveData<List<Movie>> getFavoriteMovie() {
        return listFavoriteMovie;
    }

    public LiveData<List<Tv>> getFavoriteTv() {
        return listFavoriteTv;
    }
}
