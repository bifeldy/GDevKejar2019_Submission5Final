package id.ac.umn.made_basiliusbias_submission5.activities;

import android.annotation.SuppressLint;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import id.ac.umn.made_basiliusbias_submission5.DbHelper;
import id.ac.umn.made_basiliusbias_submission5.LangApp;
import id.ac.umn.made_basiliusbias_submission5.R;
import id.ac.umn.made_basiliusbias_submission5.Utility;
import id.ac.umn.made_basiliusbias_submission5.models.DetailMovieViewModel;
import id.ac.umn.made_basiliusbias_submission5.models.DetailTvViewModel;

import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

@SuppressWarnings("Duplicates")
public class DetailActivity extends LangApp {

    // Shared Preferences
    private static final String PREFERENCES_FILENAME = "USER_INFORMATION";
    private static final int PREFERENCES_MODE = Context.MODE_PRIVATE;
    private static final String KEY_USERNAME = "USERNAME";

    // Data Passed
    private String activity_title;
    private int data_id;
    private String data_type;
    private String tmdb_url;
    private boolean isFavorited;

    // ImageURL
    private String imgUrl;

    // Detail Content UI
    private LinearLayout air_date;

    // Header UI
    private ImageView detail_image;
    private TextView detail_title;
    private TextView detail_score;
    private TextView detail_vote_count;
    private TextView detail_type;
    private TextView detail_status;
    private TextView detail_number_of_episodes;
    private TextView detail_first_air_date;
    private TextView detail_last_air_date;
    private TextView detail_premiered_broadcast;

    // Content UI
    private TextView detail_overview;
    private TextView detail_original_name;
    private TextView detail_popularity;
    private TextView detail_genres;

    @SuppressLint({"SetTextI18n", "SimpleDateFormat"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // Get Passed Data From Main Fragment
        Bundle bundle = getIntent().getExtras();
        if(bundle != null) {
            activity_title = bundle.getString("activity_title");
            data_id = bundle.getInt("data_id");
            data_type = bundle.getString("data_type");
            tmdb_url = bundle.getString("tmdb_url");
        }

        // Change Activity Page UI Toolbar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(activity_title + data_id);
        }

        // Show Loading Animation
        ImageView loading_image = findViewById(R.id.loading_image);
        loading_image.setVisibility(View.VISIBLE);
        Glide.with(this)
            .load(getResources().getString(R.string.animated_loading_data))
            .override(256, 256)
            .into(loading_image)
        ;
        TextView loading_text = findViewById(R.id.loading_text);
        loading_text.setVisibility(View.VISIBLE);
        loading_text.setText(R.string.loading_text);
        loading_text.setTextColor(Color.GREEN);

        // Find Detail Content
        LinearLayout detail_content = findViewById(R.id.detail_content);
        detail_content.setVisibility(View.GONE);

        // Find Air Date
        air_date = findViewById(R.id.air_date);

        // Find Header UI Object
        detail_image = findViewById(R.id.detail_image);
        detail_title = findViewById(R.id.detail_title);
        detail_score = findViewById(R.id.detail_score);
        detail_vote_count = findViewById(R.id.detail_vote_count);
        detail_type = findViewById(R.id.detail_type);
        detail_status = findViewById(R.id.detail_status);
        detail_number_of_episodes = findViewById(R.id.detail_number_of_episodes);
        detail_first_air_date = findViewById(R.id.detail_first_air_date);
        detail_last_air_date = findViewById(R.id.detail_last_air_date);
        detail_premiered_broadcast = findViewById(R.id.detail_premiered_broadcast);

        // Find Content UI Object
        detail_overview = findViewById(R.id.detail_overview);
        detail_original_name = findViewById(R.id.detail_original_name);
        detail_popularity = findViewById(R.id.detail_popularity);
        detail_genres = findViewById(R.id.detail_genres);

        // Load Data
        if(data_type.equalsIgnoreCase("Movie")) {

            // Setting Up API
            DetailMovieViewModel detailMovieViewModel = ViewModelProviders.of(this).get(DetailMovieViewModel.class);

            // Showing Data
            detailMovieViewModel.getDetailMovie().observe(this, movie -> {
                if (movie != null) {

                    imgUrl = movie.getPoster_path();
                    Glide.with(this)
                            .load(movie.getPoster_path())
                            .transition(DrawableTransitionOptions.withCrossFade(125))
                            .into(detail_image)
                    ;

                    // Hard-Coded
                    detail_number_of_episodes.setText("1 " + getResources().getString(R.string.episodes));
                    air_date.setVisibility(View.GONE);
                    detail_type.setText(getResources().getString(R.string.movie));

                    // Number
                    detail_score.setText(Double.toString(movie.getVote_average()));
                    detail_vote_count.setText(movie.getVote_count() + " " + getResources().getString(R.string.votes));
                    detail_popularity.setText(Double.toString(movie.getPopularity()));

                    // String
                    if(!movie.getTitle().equalsIgnoreCase("")) detail_title.setText(movie.getTitle());
                    if(!movie.getStatus().equalsIgnoreCase("")) detail_status.setText(movie.getStatus());
                    if(!movie.getOverview().equalsIgnoreCase("")) detail_overview.setText(movie.getOverview());
                    if(!movie.getOriginal_title().equalsIgnoreCase("")) detail_original_name.setText(movie.getOriginal_title());

                    Locale locale = new Locale(
                        Utility.getSystemLocale().split("-")[0], // Language
                        Utility.getSystemLocale().split("-")[1]  // Country
                    );
                    DateFormatSymbols dateFormatSymbols = new DateFormatSymbols(locale);
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    try {
                        Date dateObj = simpleDateFormat.parse(movie.getRelease_date());
                        simpleDateFormat = new SimpleDateFormat("EEEE, dd MMMM yyyy @ HH:mm a", dateFormatSymbols);
                        String date = simpleDateFormat.format(dateObj);
                        detail_premiered_broadcast.setText(date);
                    }
                    catch (ParseException e) {
                        e.printStackTrace();
                    }

                    StringBuilder genres = new StringBuilder();
                    for(int i=0; i<movie.getGenres().size(); i++) {
                        if(i>0 && i<movie.getGenres().size()) genres.append(", ");
                        genres.append(movie.getGenres().get(i).getName());
                    }
                    if(!genres.toString().equalsIgnoreCase("")) detail_genres.setText(genres.toString());
                }
            });

            // Setting Up, Load, & Adding Data
            detailMovieViewModel.loadDetailMovie(
                    this,
                    detail_content,
                    Objects.requireNonNull(loading_image),
                    Objects.requireNonNull(loading_text),
                    data_id
            );
        }
        else if(data_type.equalsIgnoreCase("TV")){

            // Setting Up API
            DetailTvViewModel detailTvViewModel = ViewModelProviders.of(this).get(DetailTvViewModel.class);

            // Showing Data
            detailTvViewModel.getDetailTv().observe(this, tv -> {
                if (tv != null) {

                    imgUrl = tv.getPoster_path();
                    Glide.with(this)
                            .load(tv.getPoster_path())
                            .transition(DrawableTransitionOptions.withCrossFade(125))
                            .into(detail_image)
                    ;

                    // Hard-Coded
                    detail_premiered_broadcast.setVisibility(View.GONE);
                    detail_type.setText(getResources().getString(R.string.tv));

                    // Number
                    detail_score.setText(Double.toString(tv.getVote_average()));
                    detail_vote_count.setText(tv.getVote_count() + " " + getResources().getString(R.string.votes));
                    detail_popularity.setText(Double.toString(tv.getPopularity()));
                    detail_number_of_episodes.setText(tv.getNumber_of_episodes() + " " + getResources().getString(R.string.episodes));

                    // String
                    if(!tv.getName().equalsIgnoreCase("")) detail_title.setText(tv.getName());
                    if(!tv.getStatus().equalsIgnoreCase("")) detail_status.setText(tv.getStatus());
                    if(!tv.getOverview().equalsIgnoreCase("")) detail_overview.setText(tv.getOverview());
                    if(!tv.getOriginal_name().equalsIgnoreCase("")) detail_original_name.setText(tv.getOriginal_name());
                    detail_first_air_date.setText(tv.getFirst_air_date());
                    detail_last_air_date.setText(tv.getLast_air_date());

                    StringBuilder genres = new StringBuilder();
                    for(int i=0; i<tv.getGenres().size(); i++) {
                        if(i>0 && i<tv.getGenres().size()) genres.append(", ");
                        genres.append(tv.getGenres().get(i).getName());
                    }
                    detail_genres.setText(genres.toString());
                }
            });

            // Setting Up, Load, & Adding Data
            detailTvViewModel.loadDetailTv(
                    this,
                    detail_content,
                    Objects.requireNonNull(loading_image),
                    Objects.requireNonNull(loading_text),
                    data_id
            );
        }

        // Get Data Shared Preferences For Login
        SharedPreferences userInfo = getSharedPreferences(PREFERENCES_FILENAME, PREFERENCES_MODE);

        // Find FloatingActionButton
        FloatingActionButton floating_liked_button = findViewById(R.id.floating_liked_button);
        DbHelper mDBHelper = new DbHelper(this);
        isFavorited = mDBHelper.isFavorited(data_id, data_type, userInfo.getString(KEY_USERNAME, ""));

        if(isFavorited) {
            floating_liked_button.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_favorite));
        }
        else {
            floating_liked_button.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_favorite_border));
        }

        floating_liked_button.setOnClickListener(v -> {
            if(!isFavorited) {
                isFavorited = true;
                floating_liked_button.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_favorite));
                mDBHelper.addFavorite(
                        data_id,
                        data_type,
                        detail_title.getText().toString(),
                        imgUrl,
                        detail_overview.getText().toString(),
                        Double.parseDouble(detail_score.getText().toString()),
                        Double.parseDouble(detail_popularity.getText().toString()),
                        userInfo.getString(KEY_USERNAME, ""));
                Snackbar.make(v, getResources().getString(R.string.add_favorites) + " :: " + data_id, Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }
            else {
                isFavorited = false;
                floating_liked_button.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_favorite_border));
                mDBHelper.removeFavorite(data_id, data_type, userInfo.getString(KEY_USERNAME, ""));
                Snackbar.make(v, getResources().getString(R.string.remove_favorites) + " :: " + data_id, Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_detail_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // For Open Intent
        Intent intent;

        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        if (item.getItemId() == R.id.action_detail_view_on_tmdb) {

            intent = new Intent(Intent.ACTION_VIEW, Uri.parse(tmdb_url));
            startActivity(intent);
        }

        // Back To Parent Activity
        return super.onOptionsItemSelected(item);
    }
}
