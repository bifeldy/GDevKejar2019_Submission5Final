package id.ac.umn.made_basiliusbias_submission5.models;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import id.ac.umn.made_basiliusbias_submission5.DbHelper;
import id.ac.umn.made_basiliusbias_submission5.R;
import id.ac.umn.made_basiliusbias_submission5.pojos.Movie;
import id.ac.umn.made_basiliusbias_submission5.pojos.Tv;

import java.util.List;

public class FavoriteViewModel extends ViewModel {

    private DbHelper dbHelper;

    private MutableLiveData<List<Movie>> listFavoriteMovie = new MutableLiveData<>();
    private MutableLiveData<List<Tv>> listFavoriteTv = new MutableLiveData<>();

    public void loadFavoriteMovie(View v, ImageView loadingImage, TextView loadingText, String data_type, String user_name) {
        dbHelper = new DbHelper(v.getContext());

        // Hide Loading Animation
        loadingImage.setVisibility(View.GONE);
        loadingText.setVisibility(View.GONE);

        // noinspection unchecked
        listFavoriteMovie.postValue((List<Movie>) dbHelper.getFavorites(data_type, user_name));
        v.findViewById(R.id.recycler_fragment).setVisibility(View.VISIBLE);
    }

    public LiveData<List<Movie>> getFavoriteMovie() {
        return listFavoriteMovie;
    }

    public void loadFavoriteTv(View v, ImageView loadingImage, TextView loadingText, String data_type, String user_name) {
        dbHelper = new DbHelper(v.getContext());

        // Hide Loading Animation
        loadingImage.setVisibility(View.GONE);
        loadingText.setVisibility(View.GONE);

        // noinspection unchecked
        listFavoriteTv.postValue((List<Tv>) dbHelper.getFavorites(data_type, user_name));
        v.findViewById(R.id.recycler_fragment).setVisibility(View.VISIBLE);
    }

    public LiveData<List<Tv>> getFavoriteTv() {
        return listFavoriteTv;
    }
}
