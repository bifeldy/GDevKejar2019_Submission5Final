package id.ac.umn.made_basiliusbias_submission5.models;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import id.ac.umn.made_basiliusbias_submission5.CacheReq;
import id.ac.umn.made_basiliusbias_submission5.R;
import id.ac.umn.made_basiliusbias_submission5.Utility;
import id.ac.umn.made_basiliusbias_submission5.apis.DiscoverSearchAPI;

import java.util.List;

public class SearchMovieViewModel extends ViewModel {

    private MutableLiveData<List<?>> listSearchMovie = new MutableLiveData<>();

    public void loadSearchMovie(View v, ImageView loadingImage, TextView loadingText, String query, int page_number) {

        String urlApi = v.getContext().getResources().getString(R.string.tmdb_api) +
                        "search/movie?api_key=" +
                        v.getContext().getResources().getString(R.string.tmdb_key) +
                        "&query=" +
                        query +
                        "&include_adult=true" +
                        "&language=" +
                        Utility.getAppLanguage(v.getContext()) +
                        "&page=" +
                        page_number
        ;

        // API
        DiscoverSearchAPI searchMovieAPI = new DiscoverSearchAPI();
        searchMovieAPI.setContext(v.getContext());

        // Fetching Data
        CacheReq cacheReq = Utility.discoverSearch(urlApi, searchMovieAPI, v, loadingImage, loadingText, listSearchMovie, "Movies");
        RequestQueue requestQueue = Volley.newRequestQueue(v.getContext());
        requestQueue.add(cacheReq);
    }

    public LiveData<List<?>> getSearchMovie() {
        return listSearchMovie;
    }
}
