package id.ac.umn.made_basiliusbias_submission5.apis;

import android.content.Context;
import id.ac.umn.made_basiliusbias_submission5.R;
import id.ac.umn.made_basiliusbias_submission5.pojos.*;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DetailAPI {

    private Context context;
    private DetailMovie detailMovie;
    private DetailTV detailTV;

    public DetailAPI() {
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public DetailMovie getDetailMovie() {
        return detailMovie;
    }

    public DetailTV getDetailTV() {
        return detailTV;
    }

    /* *** Movie *** */

    public void createDetailMovie(JSONObject response) {

        detailMovie = new DetailMovie();

        try {

            // Get Data
            if(!response.isNull("adult")) detailMovie.setAdult(response.getBoolean("adult"));
            if(!response.isNull("backdrop_path")) detailMovie.setBackdrop_path(context.getResources().getString(R.string.tmdb_img) + response.getString("backdrop_path"));

            if (!response.isNull("belongs_to_collection")) {
                JSONObject belongs_to_collection = response.getJSONObject("belongs_to_collection");
                BelongsToCollection belongsToCollection = new BelongsToCollection();
                if (!belongs_to_collection.isNull("id")) belongsToCollection.setId(belongs_to_collection.getInt("id"));
                if (!belongs_to_collection.isNull("name")) belongsToCollection.setName(belongs_to_collection.getString("name"));
                if (!belongs_to_collection.isNull("poster_path")) belongsToCollection.setPoster_path(belongs_to_collection.getString("poster_path"));
                if (!belongs_to_collection.isNull("backdrop_path")) belongsToCollection.setBackdrop_path(belongs_to_collection.getString("backdrop_path"));
                detailMovie.setBelongs_to_collection(belongsToCollection);
            }

            if(!response.isNull("budget")) detailMovie.setBudget(response.getInt("budget"));

            if (!response.isNull("genres")) {
                List<Genres> genres = new ArrayList<>();
                for (int i = 0; i < response.getJSONArray("genres").length(); i++) {
                    JSONObject response_genres = response.getJSONArray("genres").getJSONObject(i);
                    Genres genre = new Genres();
                    if (!response_genres.isNull("name")) genre.setName(response_genres.getString("name"));
                    if (!response_genres.isNull("id")) genre.setId(response_genres.getInt("id"));
                    genres.add(genre);
                }
                detailMovie.setGenres(genres);
            }

            if(!response.isNull("homepage")) detailMovie.setHomepage(response.getString("homepage"));
            if(!response.isNull("id")) detailMovie.setId(response.getInt("id"));
            if(!response.isNull("imdb_id")) detailMovie.setImdb_id(response.getString("imdb_id"));
            if(!response.isNull("original_title")) detailMovie.setOriginal_title(response.getString("original_title"));
            if(!response.isNull("original_language")) detailMovie.setOriginal_language(response.getString("original_language"));
            if(!response.isNull("popularity")) detailMovie.setPopularity(response.getDouble("popularity"));
            if(!response.isNull("overview")) detailMovie.setOverview(response.getString("overview"));
            if(!response.isNull("poster_path")) detailMovie.setPoster_path(context.getResources().getString(R.string.tmdb_img) + response.getString("poster_path"));

            if (!response.isNull("production_countries")) {
                List<ProductionCountries> productionCountries = new ArrayList<>();
                for (int i = 0; i < response.getJSONArray("production_countries").length(); i++) {
                    JSONObject response_productionCountry = response.getJSONArray("production_countries").getJSONObject(i);
                    ProductionCountries productionCountry = new ProductionCountries();
                    if (!response_productionCountry.isNull("name")) productionCountry.setName(response_productionCountry.getString("name"));
                    if (!response_productionCountry.isNull("iso_3166_1")) productionCountry.setIso_3166_1(response_productionCountry.getString("iso_3166_1"));
                    productionCountries.add(productionCountry);
                }
                detailMovie.setProduction_countries(productionCountries);
            }

            if (!response.isNull("production_companies")) {
                List<ProductionCompanies> productionCompanies = new ArrayList<>();
                for (int i = 0; i < response.getJSONArray("production_companies").length(); i++) {
                    ProductionCompanies productionCompany = new ProductionCompanies();
                    JSONObject response_productionCompany = response.getJSONArray("production_companies").getJSONObject(i);
                    if (!response_productionCompany.isNull("origin_country")) productionCompany.setOrigin_country(response_productionCompany.getString("origin_country"));
                    if (!response_productionCompany.isNull("name")) productionCompany.setName(response_productionCompany.getString("name"));
                    if (!response_productionCompany.isNull("logo_path")) productionCompany.setLogo_path(response_productionCompany.getString("logo_path"));
                    if (!response_productionCompany.isNull("id")) productionCompany.setId(response_productionCompany.getInt("id"));
                    productionCompanies.add(productionCompany);
                }
                detailMovie.setProduction_companies(productionCompanies);
            }

            if(!response.isNull("release_date")) detailMovie.setRelease_date(response.getString("release_date"));
            if(!response.isNull("revenue")) detailMovie.setRevenue(response.getInt("revenue"));
            if(!response.isNull("runtime")) detailMovie.setRuntime(response.getInt("runtime"));

            if (!response.isNull("spoken_languages")) {
                List<SpokenLanguages> spokenLanguages = new ArrayList<>();
                for (int i = 0; i < response.getJSONArray("spoken_languages").length(); i++) {
                    JSONObject response_spokenLanguages = response.getJSONArray("spoken_languages").getJSONObject(i);
                    SpokenLanguages spokenLanguage = new SpokenLanguages();
                    if (!response_spokenLanguages.isNull("name")) spokenLanguage.setName(response_spokenLanguages.getString("name"));
                    if (!response_spokenLanguages.isNull("iso_639_1")) spokenLanguage.setIso_639_1(response_spokenLanguages.getString("iso_639_1"));
                    spokenLanguages.add(spokenLanguage);
                }
                detailMovie.setSpoken_languages(spokenLanguages);
            }

            if(!response.isNull("status")) detailMovie.setStatus(response.getString("status"));
            if(!response.isNull("tagline")) detailMovie.setTagline(response.getString("tagline"));
            if(!response.isNull("title")) detailMovie.setTitle(response.getString("title"));
            if(!response.isNull("video")) detailMovie.setVideo(response.getBoolean("video"));
            if(!response.isNull("vote_average")) detailMovie.setVote_average(response.getDouble("vote_average"));
            if(!response.isNull("vote_count")) detailMovie.setVote_count(response.getInt("vote_count"));
        }
        catch (JSONException e) {

            // Print Error Log
            e.printStackTrace();
        }

    }

    /* *** TV *** */

    public void createDetailTV(JSONObject response) {

        detailTV = new DetailTV();

        try {

            // Get Data
            if(!response.isNull("backdrop_path")) detailTV.setBackdrop_path(context.getResources().getString(R.string.tmdb_img) + response.getString("backdrop_path"));

            if (!response.isNull("created_by")) {
                List<CreatedBy> createdBys = new ArrayList<>();
                for (int i = 0; i < response.getJSONArray("created_by").length(); i++) {
                    JSONObject response_createdby = response.getJSONArray("created_by").getJSONObject(i);
                    CreatedBy createdBy = new CreatedBy();
                    if (!response_createdby.isNull("id")) createdBy.setId(response_createdby.getInt("id"));
                    if (!response_createdby.isNull("name")) createdBy.setName(response_createdby.getString("name"));
                    if (!response_createdby.isNull("credit_id")) createdBy.setCredit_id(response_createdby.getString("credit_id"));
                    if (!response_createdby.isNull("gender")) createdBy.setGender(response_createdby.getInt("gender"));
                    if (!response_createdby.isNull("profile_path")) createdBy.setProfile_path(response_createdby.getString("profile_path"));
                    createdBys.add(createdBy);
                }
                detailTV.setCreated_by(createdBys);
            }

            if(!response.isNull("episode_run_time")) {
                String[] episodeRunTimes = new String[response.optJSONArray("episode_run_time").length()];
                for (int j = 0; j < response.optJSONArray("episode_run_time").length(); j++) {
                    episodeRunTimes[j] = response.optJSONArray("episode_run_time").optString(j);
                }
                detailTV.setLanguages(episodeRunTimes);
            }

            if(!response.isNull("first_air_date")) detailTV.setFirst_air_date(response.getString("first_air_date"));

            if (!response.isNull("genres")) {
                List<Genres> genres = new ArrayList<>();
                for (int i = 0; i < response.getJSONArray("genres").length(); i++) {
                    JSONObject response_genres = response.getJSONArray("genres").getJSONObject(i);
                    Genres genre = new Genres();
                    if (!response_genres.isNull("id")) genre.setId(response_genres.getInt("id"));
                    if (!response_genres.isNull("name")) genre.setName(response_genres.getString("name"));
                    genres.add(genre);
                }
                detailTV.setGenres(genres);
            }

            if(!response.isNull("homepage")) detailTV.setHomepage(response.getString("homepage"));
            if(!response.isNull("id")) detailTV.setId(response.getInt("id"));
            if(!response.isNull("in_production")) detailTV.setIn_production(response.getBoolean("in_production"));

            if(!response.isNull("languages")) {
                String[] lang = new String[response.optJSONArray("languages").length()];
                for (int j = 0; j < response.optJSONArray("languages").length(); j++) {
                    lang[j] = response.optJSONArray("languages").optString(j);
                }
                detailTV.setLanguages(lang);
            }

            if(!response.isNull("last_air_date")) detailTV.setLast_air_date(response.getString("last_air_date"));

            if(!response.isNull("last_episode_to_air")) {
                JSONObject response_lastEpisodeToAir = response.getJSONObject("last_episode_to_air");
                EpisodeToAir lastEpisodeToAir = new EpisodeToAir();
                if(!response_lastEpisodeToAir.isNull("air_date")) lastEpisodeToAir.setAir_date(response_lastEpisodeToAir.getString("air_date"));
                if(!response_lastEpisodeToAir.isNull("episode_number")) lastEpisodeToAir.setEpisode_number(response_lastEpisodeToAir.getInt("episode_number"));
                if(!response_lastEpisodeToAir.isNull("id")) lastEpisodeToAir.setId(response_lastEpisodeToAir.getInt("id"));
                if(!response_lastEpisodeToAir.isNull("name")) lastEpisodeToAir.setName(response_lastEpisodeToAir.getString("name"));
                if(!response_lastEpisodeToAir.isNull("overview")) lastEpisodeToAir.setOverview(response_lastEpisodeToAir.getString("overview"));
                if(!response_lastEpisodeToAir.isNull("production_code")) lastEpisodeToAir.setProduction_code(response_lastEpisodeToAir.getString("production_code"));
                if(!response_lastEpisodeToAir.isNull("season_number")) lastEpisodeToAir.setSeason_number(response_lastEpisodeToAir.getInt("season_number"));
                if(!response_lastEpisodeToAir.isNull("show_id")) lastEpisodeToAir.setShow_id(response_lastEpisodeToAir.getInt("show_id"));
                if(!response_lastEpisodeToAir.isNull("still_path")) lastEpisodeToAir.setStill_path(response_lastEpisodeToAir.getString("still_path"));
                if(!response_lastEpisodeToAir.isNull("vote_average")) lastEpisodeToAir.setVote_average(response_lastEpisodeToAir.getDouble("vote_average"));
                if(!response_lastEpisodeToAir.isNull("vote_count")) lastEpisodeToAir.setVote_count(response_lastEpisodeToAir.getInt("vote_count"));
                detailTV.setLast_episode_to_air(lastEpisodeToAir);
            }

            if(!response.isNull("name")) detailTV.setName(response.getString("name"));

            // Always Null -- We Can't Predict The Future Episode ..
            if(!response.isNull("next_episode_to_air")) detailTV.setNext_episode_to_air(null);

            if(!response.isNull("networks")) {
                List<Networks> networks = new ArrayList<>();
                for (int i = 0; i < response.getJSONArray("genres").length(); i++) {
                    JSONObject response_network = response.getJSONArray("genres").getJSONObject(i);
                    Networks network = new Networks();
                    if (!response_network.isNull("id")) network.setId(response_network.getInt("id"));
                    if (!response_network.isNull("name")) network.setName(response_network.getString("name"));
                    if (!response_network.isNull("logo_path")) network.setName(response_network.getString("logo_path"));
                    if (!response_network.isNull("origin_country")) network.setName(response_network.getString("origin_country"));
                    networks.add(network);
                }
                detailTV.setNetworks(networks);
            }

            if(!response.isNull("number_of_episodes")) detailTV.setNumber_of_episodes(response.getInt("number_of_episodes"));
            if(!response.isNull("number_of_seasons")) detailTV.setNumber_of_seasons(response.getInt("number_of_seasons"));

            if(!response.isNull("origin_country")) {
                String[] origin_country = new String[response.optJSONArray("origin_country").length()];
                for (int j = 0; j < response.optJSONArray("origin_country").length(); j++) {
                    origin_country[j] = response.optJSONArray("origin_country").optString(j);
                }
                detailTV.setOrigin_country(origin_country);
            }

            if(!response.isNull("original_name")) detailTV.setOriginal_name(response.getString("original_name"));
            if(!response.isNull("original_language")) detailTV.setOriginal_language(response.getString("original_language"));
            if(!response.isNull("overview")) detailTV.setOverview(response.getString("overview"));
            if(!response.isNull("popularity")) detailTV.setPopularity(response.getDouble("popularity"));
            if(!response.isNull("poster_path")) detailTV.setPoster_path(context.getResources().getString(R.string.tmdb_img) + response.getString("poster_path"));

            if (!response.isNull("production_companies")) {
                List<ProductionCompanies> productionCompanies = new ArrayList<>();
                for (int i = 0; i < response.getJSONArray("production_companies").length(); i++) {
                    JSONObject response_productionCompany = response.getJSONArray("production_companies").getJSONObject(i);
                    ProductionCompanies productionCompany = new ProductionCompanies();
                    if (!response_productionCompany.isNull("id")) productionCompany.setId(response_productionCompany.getInt("id"));
                    if (!response_productionCompany.isNull("name")) productionCompany.setName(response_productionCompany.getString("name"));
                    if (!response_productionCompany.isNull("logo_path")) productionCompany.setLogo_path(response_productionCompany.getString("logo_path"));
                    if (!response_productionCompany.isNull("origin_country")) productionCompany.setOrigin_country(response_productionCompany.getString("origin_country"));
                    productionCompanies.add(productionCompany);
                }
                detailTV.setProduction_companies(productionCompanies);
            }

            if (!response.isNull("seasons")) {
                List<Seasons> seasons = new ArrayList<>();
                for (int i = 0; i < response.getJSONArray("seasons").length(); i++) {
                    JSONObject response_season = response.getJSONArray("seasons").getJSONObject(i);
                    Seasons season = new Seasons();
                    if (!response_season.isNull("id")) season.setId(response_season.getInt("id"));
                    if (!response_season.isNull("episode_count")) season.setEpisode_count(response_season.getInt("episode_count"));
                    if (!response_season.isNull("name")) season.setName(response_season.getString("name"));
                    if (!response_season.isNull("air_date")) season.setAir_date(response_season.getString("air_date"));
                    if (!response_season.isNull("overview")) season.setOverview(response_season.getString("overview"));
                    if (!response_season.isNull("poster_path")) season.setPoster_path(response_season.getString("poster_path"));
                    if (!response_season.isNull("season_number")) season.setSeason_number(response_season.getInt("season_number"));
                    seasons.add(season);
                }
                detailTV.setSeasons(seasons);
            }

            if(!response.isNull("status")) detailTV.setStatus(response.getString("status"));
            if(!response.isNull("type")) detailTV.setType(response.getString("type"));
            if(!response.isNull("vote_average")) detailTV.setVote_average(response.getDouble("vote_average"));
            if(!response.isNull("vote_count")) detailTV.setVote_count(response.getInt("vote_count"));
        }
        catch (JSONException e) {

            // Print Error Log
            e.printStackTrace();
        }
    }
}
