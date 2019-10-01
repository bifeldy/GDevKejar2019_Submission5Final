package id.ac.umn.made_basiliusbias_submission5.fragments;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
import id.ac.umn.made_basiliusbias_submission5.R;
import id.ac.umn.made_basiliusbias_submission5.Utility;
import id.ac.umn.made_basiliusbias_submission5.adapters.MovieGridAdapter;
import id.ac.umn.made_basiliusbias_submission5.adapters.TvListAdapter;
import id.ac.umn.made_basiliusbias_submission5.models.SearchMovieViewModel;
import id.ac.umn.made_basiliusbias_submission5.models.SearchTvViewModel;
import id.ac.umn.made_basiliusbias_submission5.pojos.Movie;
import id.ac.umn.made_basiliusbias_submission5.pojos.Tv;

import java.util.List;
import java.util.Objects;

public class SearchFragment extends Fragment {

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

    public SearchFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static SearchFragment newInstance(String fragmentName, String recyclerType) {

        SearchFragment fragment = new SearchFragment();

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

        // Setting Up RecyclerView
        RecyclerView recyclerView = v.findViewById(R.id.recycler_fragment);
        recyclerView.setVisibility(View.GONE);

        // Determine Size Of Column To View As Grid
        int mNoOfColumns = Utility.calcNumOfCols(v.getContext(), 175);

        // Loading Animation
        ImageView loading_image = v.findViewById(R.id.loading_image);
        TextView loading_text = v.findViewById(R.id.loading_text);

        // RecyclerType
        Utility.InitializeRecyclerGridList(v, recyclerView, mNoOfColumns, recyclerType);

        // Show Loading Animation
        loading_text.setText(R.string.no_data);
        loading_text.setTextColor(Color.BLUE);
        Glide.with(this)
                .load(R.drawable.maido3)
                .transition(DrawableTransitionOptions.withCrossFade(125))
                .override(256, 256)
                .into(loading_image)
        ;

        EditText searchQuery = v.findViewById(R.id.search_text);
        Button searchButton = v.findViewById(R.id.search_button);

        // Check Fragment What Will Show
        switch (fragmentName) {

            // Coming Soon
            case "NoData":

                // ReAssign View
                v = inflater.inflate(R.layout.fragment_no_data, container, false);
                break;

            // Search Movies
            case "SearchMovies":

                // Setting Up Data
                SearchMovieViewModel searchMovieViewModel = ViewModelProviders.of(Objects.requireNonNull(getActivity())).get(SearchMovieViewModel.class);
                searchMovieViewModel.getSearchMovie().observe(this, movies -> {
                    if (movies != null) {

                        // Adding New Live Data To Adapter
                        // noinspection unchecked
                        movieGridAdapter.setMovies((List<Movie>) movies);
                    }
                });

                // Setting Up, Load, & Adding Data To Adapter
                movieGridAdapter = new MovieGridAdapter(v.getContext(), R.layout.item_grid);
                recyclerView.setAdapter(movieGridAdapter);

                View finalV = v;
                searchButton.setOnClickListener(v12 -> {
                    if(!searchQuery.getText().toString().equals("")) {
                        searchMovieViewModel.loadSearchMovie(
                                finalV,
                                loading_image,
                                loading_text,
                                searchQuery.getText().toString(),
                                1
                        );
                    }
                });
                break;

            // Search TVs
            case "SearchTV":

                // Setting Up API
                SearchTvViewModel searchTvViewModel = ViewModelProviders.of(Objects.requireNonNull(getActivity())).get(SearchTvViewModel.class);
                searchTvViewModel.getSearchTv().observe(this, tvs -> {
                    if (tvs != null) {

                        // Adding New Live Data To Adapter
                        // noinspection unchecked
                        tvListAdapter.setTvs((List<Tv>) tvs);
                    }
                });

                // Setting Up, Load, & Adding Data To Adapter
                tvListAdapter = new TvListAdapter(v.getContext(), R.layout.item_list);
                recyclerView.setAdapter(tvListAdapter);

                View finalV1 = v;
                searchButton.setOnClickListener(v12 -> {
                    if(!searchQuery.getText().toString().equals("")) {
                        searchTvViewModel.loadSearchTv(
                                finalV1,
                                loading_image,
                                loading_text,
                                searchQuery.getText().toString(),
                                1
                        );
                    }
                });
                break;
        }

        return v;
    }

    // TODO: Rename method, update argument and hook method into UI event
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

        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
