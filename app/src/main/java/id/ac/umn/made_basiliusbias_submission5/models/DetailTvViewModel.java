package id.ac.umn.made_basiliusbias_submission5.models;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import id.ac.umn.made_basiliusbias_submission5.CacheReq;
import id.ac.umn.made_basiliusbias_submission5.R;
import id.ac.umn.made_basiliusbias_submission5.Utility;
import id.ac.umn.made_basiliusbias_submission5.apis.DetailAPI;
import id.ac.umn.made_basiliusbias_submission5.pojos.DetailTV;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

public class DetailTvViewModel extends ViewModel {

    private MutableLiveData<DetailTV> detailTv = new MutableLiveData<>();

    public void loadDetailTv(Context context, LinearLayout detailContent, ImageView loadingImage, TextView loadingText, int tvId) {

        String urlApi = context.getResources().getString(R.string.tmdb_api) +
                "tv/" +
                tvId +
                "?api_key=" +
                context.getResources().getString(R.string.tmdb_key) +
                "&language=" +
                Utility.getAppLanguage(context)
                ;

        // API
        DetailAPI detailTvAPI = new DetailAPI();
        detailTvAPI.setContext(context);

        // Fetching Data
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        CacheReq cacheReq = new CacheReq(0, urlApi,
                response -> {
                    try {

                        // Create Object Response
                        final String jsonString = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
                        JSONObject jsonObject = new JSONObject(jsonString);
                        detailTvAPI.createDetailTV(jsonObject);

                        // Hide Loading Animation
                        loadingImage.setVisibility(View.GONE);
                        loadingText.setVisibility(View.GONE);

                        // Add Data & Show
                        detailTv.postValue(detailTvAPI.getDetailTV());
                        detailContent.setVisibility(View.VISIBLE);
                    }
                    catch (UnsupportedEncodingException | JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> {

                    // Show Error Animation
                    Glide.with(context)
                            .load(R.drawable.maido3)
                            .override(256, 256)
                            .into(loadingImage)
                    ;

                    // Error Messages
                    loadingText.setText(error.toString());
                    loadingText.setTextColor(Color.RED);
                });
        requestQueue.add(cacheReq);
    }

    public LiveData<DetailTV> getDetailTv() {
        return detailTv;
    }
}
