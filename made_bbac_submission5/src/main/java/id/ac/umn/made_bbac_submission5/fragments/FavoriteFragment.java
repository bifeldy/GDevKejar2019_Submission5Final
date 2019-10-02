package id.ac.umn.made_bbac_submission5.fragments;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import id.ac.umn.made_bbac_submission5.R;
import id.ac.umn.made_bbac_submission5.Utility;
import id.ac.umn.made_bbac_submission5.adapters.MovieGridAdapter;
import id.ac.umn.made_bbac_submission5.adapters.TvListAdapter;
import id.ac.umn.made_bbac_submission5.models.FavoriteViewModel;

import java.util.Objects;

public class FavoriteFragment extends Fragment {

    // Parameter Key
    private static final String FRAGMENT_NAME = "FRAGMENT_NAME";
    private static final String RECYCLER_TYPE = "RECYCLER_TYPE";

    // Parameter Data
    private String fragmentName;
    private String recyclerType;

    // Adapter
    private MovieGridAdapter movieGridAdapter;
    private TvListAdapter tvListAdapter;

    // Listener
    private OnFragmentInteractionListener mListener;

    public FavoriteFragment() {
        // Required empty public constructor
    }

    public static FavoriteFragment newInstance(String fragmentName, String recyclerType) {

        FavoriteFragment fragment = new FavoriteFragment();

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_recycler, container, false);

        // Loading Animation
        TextView loading_text = v.findViewById(R.id.loading_text);
        ImageView loading_image = v.findViewById(R.id.loading_image);

        // Show Loading Animation
        loading_text.setTextColor(Color.GREEN);
        loading_text.setText(R.string.loading_text);
        Glide.with(this)
                .load(getResources().getString(R.string.animated_loading_data))
                .transition(DrawableTransitionOptions.withCrossFade(125))
                .override(256, 256)
                .into(loading_image)
        ;

        // Determine Size Of Column To View As Grid
        int mNoOfColumns = Utility.calcNumOfCols(v.getContext(), 175);

        // Setting Up RecyclerView
        RecyclerView recyclerView = v.findViewById(R.id.recycler_fragment);
        recyclerView.setVisibility(View.GONE);

        // RecyclerType
        if (recyclerType != null && !recyclerType.equals("") && recyclerType.equals("LinearLayout")) {
            recyclerView.setLayoutManager(new LinearLayoutManager(v.getContext()));
        }
        else if (recyclerType != null && !recyclerType.equals("") && recyclerType.equals("GridLayout")) {
            recyclerView.setLayoutManager(new GridLayoutManager(v.getContext(), mNoOfColumns));
        }

        EditText searchQuery = v.findViewById(R.id.search_text);
        Button searchButton = v.findViewById(R.id.search_button);

        // Check Fragment What Will Show
        switch (fragmentName) {

            // Favorite Movies
            case "FavoriteMovies":

                // Setting Up Data
                FavoriteViewModel favoriteMovieViewModel = ViewModelProviders.of(Objects.requireNonNull(getActivity())).get(FavoriteViewModel.class);
                favoriteMovieViewModel.getFavoriteMovie().observe(this, movies -> {
                    if (movies != null) {

                        // Adding New Live Data To Adapter
                        movieGridAdapter.setMovies(movies);
                    }
                });

                // Setting Up, Load, & Adding Data To Adapter
                movieGridAdapter = new MovieGridAdapter(v.getContext(), R.layout.item_grid);
                recyclerView.setAdapter(movieGridAdapter);

                searchButton.setOnClickListener(v12 -> {
                    if(!searchQuery.getText().toString().equals("")) {
                        favoriteMovieViewModel.loadFavorite(
                                v,
                                loading_image,
                                loading_text,
                                "movie",
                                searchQuery.getText().toString()
                        );
                    }
                });
                break;

            // Favorite TVs
            case "FavoriteTV":

                // Setting Up API
                FavoriteViewModel favoriteTvViewModel = ViewModelProviders.of(Objects.requireNonNull(getActivity())).get(FavoriteViewModel.class);
                favoriteTvViewModel.getFavoriteTv().observe(this, tvs -> {
                    if (tvs != null) {

                        // Adding New Live Data To Adapter
                        tvListAdapter.setTvs(tvs);
                    }
                });

                // Setting Up, Load, & Adding Data To Adapter
                tvListAdapter = new TvListAdapter(v.getContext(), R.layout.item_list);
                recyclerView.setAdapter(tvListAdapter);

                searchButton.setOnClickListener(v12 -> {
                    if(!searchQuery.getText().toString().equals("")) {
                        favoriteTvViewModel.loadFavorite(
                                v,
                                loading_image,
                                loading_text,
                                "tv",
                                searchQuery.getText().toString()
                        );
                    }
                });
                break;
        }

        return v;
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        }
        else {
            throw new RuntimeException(context.toString() + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
