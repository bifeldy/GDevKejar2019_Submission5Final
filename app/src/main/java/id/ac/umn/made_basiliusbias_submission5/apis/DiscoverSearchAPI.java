package id.ac.umn.made_basiliusbias_submission5.apis;

import android.content.Context;
import id.ac.umn.made_basiliusbias_submission5.R;
import id.ac.umn.made_basiliusbias_submission5.pojos.DiscoverMovie;
import id.ac.umn.made_basiliusbias_submission5.pojos.DiscoverTv;
import id.ac.umn.made_basiliusbias_submission5.pojos.Movie;
import id.ac.umn.made_basiliusbias_submission5.pojos.Tv;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DiscoverSearchAPI {

    private Context context;
    private DiscoverMovie discoverMovie;
    private DiscoverTv discoverTV;

    public DiscoverSearchAPI() {
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public DiscoverMovie getDiscoverMovie() {
        return discoverMovie;
    }

    public DiscoverTv getDiscoverTV() {
        return discoverTV;
    }

    /* *** Movie *** */

    private List<Movie> generateMovieList(JSONObject response) {

        List<Movie> movies = new ArrayList<>();

        try {

            for (int i = 0; i < response.getJSONArray("results").length(); i++) {

                JSONObject results = response.getJSONArray("results").getJSONObject(i);
                Movie movie = new Movie();

                if(!results.isNull("vote_count")) movie.setVote_count(results.getInt("vote_count"));
                if(!results.isNull("id")) movie.setId(results.getInt("id"));
                if(!results.isNull("video")) movie.setVideo(results.getBoolean("video"));
                if(!results.isNull("vote_average")) movie.setVote_average(results.getDouble("vote_average"));
                if(!results.isNull("title")) movie.setTitle(results.getString("title"));
                if(!results.isNull("popularity")) movie.setPopularity(results.getDouble("popularity"));
                if(!results.isNull("poster_path")) movie.setPoster_path(context.getResources().getString(R.string.tmdb_img) + results.getString("poster_path"));
                if(!results.isNull("original_language")) movie.setOriginal_language(results.getString("original_language"));
                if(!results.isNull("original_title")) movie.setOriginal_title(results.getString("original_title"));

                if(!results.isNull("genre_ids")) {
                    int[] genres = new int[results.optJSONArray("genre_ids").length()];
                    for (int j = 0; j < results.optJSONArray("genre_ids").length(); j++) {
                        genres[j] = results.optJSONArray("genre_ids").optInt(j);
                    }
                    movie.setGenre_ids(genres);
                }

                if(!results.isNull("backdrop_path")) movie.setBackdrop_path(context.getResources().getString(R.string.tmdb_img) + results.getString("backdrop_path"));
                if(!results.isNull("adult")) movie.setAdult(results.getBoolean("adult"));
                if(!results.isNull("overview")) movie.setOverview(results.getString("overview"));
                if(!results.isNull("release_date")) movie.setRelease_date(results.getString("release_date"));

                // Add To List
                movies.add(movie);
            }
        }
        catch (JSONException e) {

            // Print Error Log
            e.printStackTrace();
        }

        return movies;
    }

    public void createDiscoverMovie(JSONObject response) {

        discoverMovie = new DiscoverMovie();

        try {

            // Get Data Info
            if(!response.isNull("page")) discoverMovie.setPage(response.getInt("page"));
            if(!response.isNull("total_results")) discoverMovie.setTotal_results(response.getInt("total_results"));
            if(!response.isNull("total_pages")) discoverMovie.setTotal_pages(response.getInt("total_pages"));

            // Get List
            List<Movie> movies = generateMovieList(response);
            discoverMovie.setResults(movies);
        }
        catch (JSONException e) {

            // Print Error Log
            e.printStackTrace();
        }
    }

    /* *** TV *** */

    private List<Tv> generateTVList(JSONObject response) {

        List<Tv> tvs = new ArrayList<>();

        try {

            for (int i = 0; i < response.getJSONArray("results").length(); i++) {

                JSONObject results = response.getJSONArray("results").getJSONObject(i);
                Tv tv = new Tv();

                if(!results.isNull("vote_count")) tv.setVote_count(results.getInt("vote_count"));
                if(!results.isNull("id")) tv.setId(results.getInt("id"));
                if(!results.isNull("first_air_date")) tv.setFirst_air_date(results.getString("first_air_date"));
                if(!results.isNull("vote_average")) tv.setVote_average(results.getDouble("vote_average"));
                if(!results.isNull("name")) tv.setName(results.getString("name"));
                if(!results.isNull("popularity")) tv.setPopularity(results.getDouble("popularity"));
                if(!results.isNull("poster_path")) tv.setPoster_path(context.getResources().getString(R.string.tmdb_img) + results.getString("poster_path"));
                if(!results.isNull("original_name")) tv.setOriginal_name(results.getString("original_name"));
                if(!results.isNull("original_language")) tv.setOriginal_language(results.getString("original_language"));

                if(!results.isNull("genre_ids")) {
                    JSONArray genre_ids = results.optJSONArray("genre_ids");
                    int[] genres = new int[genre_ids.length()];
                    for (int j = 0; j < genre_ids.length(); j++) {
                        genres[j] = genre_ids.optInt(j);
                    }
                    tv.setGenre_ids(genres);
                }

                if(!results.isNull("overview")) tv.setOverview(results.getString("overview"));
                if(!results.isNull("backdrop_path")) tv.setBackdrop_path(context.getResources().getString(R.string.tmdb_img) + results.getString("backdrop_path"));

                if(!results.isNull("origin_country")) {
                    JSONArray origin_country = results.optJSONArray("origin_country");
                    String[] countries = new String[origin_country.length()];
                    for (int j = 0; j < origin_country.length(); j++) {
                        countries[j] = origin_country.optString(j);
                    }
                    tv.setOrigin_country(countries);
                }

                // Add To List
                tvs.add(tv);
            }
        }
        catch (JSONException e) {

            // Print Error Log
            e.printStackTrace();
        }

        return tvs;
    }

    public void createDiscoverTV(JSONObject response) {

        discoverTV = new DiscoverTv();

        try {

            // Get Data Info
            if(!response.isNull("page")) discoverTV.setPage(response.getInt("page"));
            if(!response.isNull("total_results")) discoverTV.setTotal_results(response.getInt("total_results"));
            if(!response.isNull("total_pages")) discoverTV.setTotal_pages(response.getInt("total_pages"));

            // Get List
            List<Tv> tvs = generateTVList(response);
            discoverTV.setResults(tvs);
        }
        catch (JSONException e) {

            // Print Error Log
            e.printStackTrace();
        }
    }
}
