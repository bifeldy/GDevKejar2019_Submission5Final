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

public class DiscoverTvViewModel extends ViewModel {

    private DiscoverSearchAPI discoverTvAPI;
    private MutableLiveData<List<?>> listDiscoverTv = new MutableLiveData<>();

    public void loadDiscoverTv(View v, ImageView loadingImage, TextView loadingText, String sort_by, int year, int page_number) {

        String urlApi = v.getContext().getResources().getString(R.string.tmdb_api) +
                        "discover/tv?api_key=" +
                        v.getContext().getResources().getString(R.string.tmdb_key) +
                        "&sort_by=" +
                        sort_by +
                        "&year=" +
                        year +
                        "&language=" +
                        Utility.getAppLanguage(v.getContext()) +
                        "&page=" +
                        page_number
        ;

        // API
        discoverTvAPI = new DiscoverSearchAPI();
        discoverTvAPI.setContext(v.getContext());

        // Fetching Data
        CacheReq cacheReq = Utility.discoverSearch(urlApi, discoverTvAPI, v, loadingImage, loadingText, listDiscoverTv, "Tvs");
        RequestQueue requestQueue = Volley.newRequestQueue(v.getContext());
        requestQueue.add(cacheReq);

    }

    public LiveData<List<?>> getDiscoverTv() {
        return listDiscoverTv;
    }

    public DiscoverSearchAPI getDiscoverTvAPI() {
        return discoverTvAPI;
    }
}
