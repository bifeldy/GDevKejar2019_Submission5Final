package id.ac.umn.made_basiliusbias_submission5.fragments;

import android.annotation.SuppressLint;
import android.arch.lifecycle.ViewModelProviders;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.*;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import id.ac.umn.made_basiliusbias_submission5.HidingScrollListener;
import id.ac.umn.made_basiliusbias_submission5.R;
import id.ac.umn.made_basiliusbias_submission5.Utility;
import id.ac.umn.made_basiliusbias_submission5.adapters.MovieGridAdapter;
import id.ac.umn.made_basiliusbias_submission5.adapters.TvListAdapter;
import id.ac.umn.made_basiliusbias_submission5.models.DiscoverMovieViewModel;
import id.ac.umn.made_basiliusbias_submission5.models.DiscoverTvViewModel;
import id.ac.umn.made_basiliusbias_submission5.pojos.Movie;
import id.ac.umn.made_basiliusbias_submission5.pojos.Tv;

import java.util.List;
import java.util.Objects;

public class MainFragment extends Fragment {

    // TODO :: Menyimpan Posisi Scroll RecyclerView Dari Fragment
    //         Ga Bisa Pake Parcelable Juga Ke `savedInstanceState`
    //         Help Donk Mas, Mba .. Biar Tidak mental Ke Atas Aja ..

    // Parameter Key
    private static final String FRAGMENT_NAME = "FRAGMENT_NAME";
    private static final String RECYCLER_TYPE = "RECYCLER_TYPE";

    // Parameter Data
    private String fragmentName;
    private String recyclerType;

    // Adapter
    private MovieGridAdapter movieGridAdapter;
    private TvListAdapter tvListAdapter;

    public MainFragment() {
        // Required empty public constructor
    }

    public static MainFragment newInstance(String fragmentName, String recyclerType) {

        MainFragment fragment = new MainFragment();

        Bundle args = new Bundle();
        args.putString(FRAGMENT_NAME, fragmentName);
        args.putString(RECYCLER_TYPE, recyclerType);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Get Parameters
        if (getArguments() != null) {
            fragmentName = getArguments().getString(FRAGMENT_NAME);
            recyclerType = getArguments().getString(RECYCLER_TYPE);
        }
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_recycler, container, false);

        // Loading Animation
        ImageView loading_image = v.findViewById(R.id.loading_image);
        TextView loading_text = v.findViewById(R.id.loading_text);

        // Show Loading Animation
        Glide.with(this)
                .load(getResources().getString(R.string.animated_loading_data))
                .transition(DrawableTransitionOptions.withCrossFade(125))
                .override(256, 256)
                .into(loading_image)
        ;
        loading_text.setText(R.string.loading_text);
        loading_text.setTextColor(Color.GREEN);

        // Determine Size Of Column To View As Grid
        int mNoOfColumns = Utility.calcNumOfCols(v.getContext(), 175);

        // Setting Up RecyclerView
        RecyclerView recyclerView = v.findViewById(R.id.recycler_fragment);
        recyclerView.setVisibility(View.GONE);

        // RecyclerType
        Utility.InitializeRecyclerGridList(v, recyclerView, mNoOfColumns, recyclerType);

        LinearLayout linearLayout = v.findViewById(R.id.linearLayout3);
        linearLayout.setVisibility(View.GONE);

        Objects.requireNonNull(getActivity()).findViewById(R.id.linearLayout2).setVisibility(View.VISIBLE);
        View finalV;
        EditText page_number = Objects.requireNonNull(getActivity()).findViewById(R.id.page_number);
        final int[] pageNumber = {Integer.parseInt(page_number.getText().toString())};
        Button next_page = getActivity().findViewById(R.id.next_page);
        Button previous_page = getActivity().findViewById(R.id.previous_page);
        Button first_page = getActivity().findViewById(R.id.first_page);
        Button last_page = getActivity().findViewById(R.id.last_page);

        // Check Fragment What Will Show
        switch (fragmentName) {

            // Coming Soon
            case "ComingSoon":

                // ReAssign View
                v = inflater.inflate(R.layout.fragment_coming_soon, container, false);
                getActivity().findViewById(R.id.linearLayout2).setVisibility(View.GONE);
                break;

            // Discover Movie
            case "DiscoverMovie":

                // Setting Up API
                DiscoverMovieViewModel discoverMovieViewModel = ViewModelProviders.of(Objects.requireNonNull(getActivity())).get(DiscoverMovieViewModel.class);
                discoverMovieViewModel.getDiscoverMovie().observe(this, movies -> {
                    if (movies != null) {

                        // Adding New Live Data To Adapter
                        movieGridAdapter.setMovies((List<Movie>) movies);
                    }
                });

                // Setting Up, Load, & Adding Data To Adapter
                movieGridAdapter = new MovieGridAdapter(v.getContext(), R.layout.item_grid);
                discoverMovieViewModel.loadDiscoverMovie(
                    v,
                    loading_image,
                    loading_text,
                    "popularity.desc",
                    2019,
                        pageNumber[0]
                );
                recyclerView.setAdapter(movieGridAdapter);

                // Navigation page
                finalV = v;

                // If the event is a key-down event on the "enter" button
                page_number.setOnKeyListener((v12, keyCode, event) -> {
                    if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                        if(page_number.getText().toString().equals("")) pageNumber[0] = 1;
                        else if(Integer.parseInt(page_number.getText().toString()) > discoverMovieViewModel.getDiscoverMovieAPI().getDiscoverMovie().getTotal_pages()) {
                            pageNumber[0] = discoverMovieViewModel.getDiscoverMovieAPI().getDiscoverMovie().getTotal_pages();
                        }
                        else pageNumber[0] = Math.max(Integer.parseInt(page_number.getText().toString()), 1);
                        loadMovieData(pageNumber, discoverMovieViewModel, page_number, finalV, loading_image, loading_text);
                        return true;
                    }
                    return false;
                });

                next_page.setOnClickListener(v1 -> {
                    pageNumber[0]++;
                    loadMovieData(pageNumber, discoverMovieViewModel, page_number, finalV, loading_image, loading_text);
                });

                previous_page.setOnClickListener(v1 -> {
                    if(pageNumber[0] > 1) pageNumber[0]--;
                    loadMovieData(pageNumber, discoverMovieViewModel, page_number, finalV, loading_image, loading_text);
                });

                first_page.setOnClickListener(v1 -> {
                    pageNumber[0] = 1;
                    loadMovieData(pageNumber, discoverMovieViewModel, page_number, finalV, loading_image, loading_text);
                });

                last_page.setOnClickListener(v1 -> {
                    pageNumber[0] = discoverMovieViewModel.getDiscoverMovieAPI().getDiscoverMovie().getTotal_pages();
                    loadMovieData(pageNumber, discoverMovieViewModel, page_number, finalV, loading_image, loading_text);
                });

                break;

            // Discover TV
            case "DiscoverTV":

                // Setting Up API
                DiscoverTvViewModel discoverTvViewModel = ViewModelProviders.of(Objects.requireNonNull(getActivity())).get(DiscoverTvViewModel.class);
                discoverTvViewModel.getDiscoverTv().observe(this, tvs -> {
                    if (tvs != null) {

                        // Adding New Live Data To Adapter
                        tvListAdapter.setTvs((List<Tv>) tvs);
                    }
                });

                // Setting Up, Load, & Adding Data To Adapter
                tvListAdapter = new TvListAdapter(v.getContext(), R.layout.item_list);
                discoverTvViewModel.loadDiscoverTv(
                    v,
                    loading_image,
                    loading_text,
                    "popularity.desc",
                    2019,
                        pageNumber[0]
                );
                recyclerView.setAdapter(tvListAdapter);

                // Navigation page
                finalV = v;

                // If the event is a key-down event on the "enter" button
                page_number.setOnKeyListener((v12, keyCode, event) -> {
                    if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                        if(page_number.getText().toString().equals("")) pageNumber[0] = 1;
                        else if(Integer.parseInt(page_number.getText().toString()) > discoverTvViewModel.getDiscoverTvAPI().getDiscoverTV().getTotal_pages()) {
                            pageNumber[0] = discoverTvViewModel.getDiscoverTvAPI().getDiscoverTV().getTotal_pages();
                        }
                        else pageNumber[0] = Math.max(Integer.parseInt(page_number.getText().toString()), 1);
                        loadTvData(pageNumber, discoverTvViewModel, page_number, finalV, loading_image, loading_text);
                        return true;
                    }
                    return false;
                });

                next_page.setOnClickListener(v1 -> {
                    pageNumber[0]++;
                    loadTvData(pageNumber, discoverTvViewModel, page_number, finalV, loading_image, loading_text);
                });

                previous_page.setOnClickListener(v1 -> {
                    if(pageNumber[0] > 1) pageNumber[0]--;
                    loadTvData(pageNumber, discoverTvViewModel, page_number, finalV, loading_image, loading_text);
                });

                first_page.setOnClickListener(v1 -> {
                    pageNumber[0] = 1;
                    loadTvData(pageNumber, discoverTvViewModel, page_number, finalV, loading_image, loading_text);
                });

                last_page.setOnClickListener(v1 -> {
                    pageNumber[0] = discoverTvViewModel.getDiscoverTvAPI().getDiscoverTV().getTotal_pages();
                    loadTvData(pageNumber, discoverTvViewModel, page_number, finalV, loading_image, loading_text);
                });
                break;

            // TODO :: Tempat Buat Fragment `MainActivity` Lainnya Di Sini
            //         `MoviesPopular`, `MoviesTopRated`, `MoviesUpcoming`, `MoviesNowPlaying`
            //         `TvShowsPopular`, `TvShowsTopRated`, `TvShowsOnTv`, `TvShowsAiringToday`

        }

        LinearLayout botNav = Objects.requireNonNull(getActivity()).findViewById(R.id.linearLayout2);

        recyclerView.setOnScrollListener(new HidingScrollListener() {
            @Override
            public void onHide() {
                botNav.animate().translationY(botNav.getHeight()).setInterpolator(new AccelerateInterpolator(2)).start();
            }
            @Override
            public void onShow() {
                botNav.animate().translationY(0).setInterpolator(new DecelerateInterpolator(2)).start();
            }
        });

        return v;
    }

    @SuppressLint("SetTextI18n")
    private void loadMovieData(int[] pageNumber, DiscoverMovieViewModel discoverMovieViewModel, EditText page_number, View finalV, ImageView loading_image, TextView loading_text) {
        page_number.setText(Integer.toString(pageNumber[0]));
        discoverMovieViewModel.loadDiscoverMovie(
                finalV,
                loading_image,
                loading_text,
                "popularity.desc",
                2019,
                pageNumber[0]
        );
    }

    @SuppressLint("SetTextI18n")
    private void loadTvData(int[] pageNumber, DiscoverTvViewModel discoverTvViewModel, EditText page_number, View finalV, ImageView loading_image, TextView loading_text) {
        page_number.setText(Integer.toString(pageNumber[0]));
        discoverTvViewModel.loadDiscoverTv(
                finalV,
                loading_image,
                loading_text,
                "popularity.desc",
                2019,
                pageNumber[0]
        );
    }
}
