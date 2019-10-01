package id.ac.umn.made_basiliusbias_submission5.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import id.ac.umn.made_basiliusbias_submission5.R;
import id.ac.umn.made_basiliusbias_submission5.activities.DetailActivity;
import id.ac.umn.made_basiliusbias_submission5.pojos.Movie;

import java.text.DecimalFormat;
import java.util.List;

public class MovieGridAdapter extends RecyclerView.Adapter<MovieGridAdapter.MovieGridViewHolder> {

    private List<Movie> movies;
    private Context recyclerContext;
    private int rowLayout;

    public MovieGridAdapter(Context recyclerContext, int rowLayout) {
        this.recyclerContext = recyclerContext;
        this.rowLayout = rowLayout;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MovieGridViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        // Inflate the layout for recycler content
        LayoutInflater inflater = LayoutInflater.from(this.recyclerContext);
        View view = inflater.inflate(rowLayout, viewGroup, false);

        return new MovieGridViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieGridViewHolder movieViewHolder, int i) {

        // Get Movie
        final Movie movie = movies.get(i);

        // Load Image
        Glide.with(recyclerContext)
            .load(movie.getPoster_path())
            .transition(DrawableTransitionOptions.withCrossFade(250)) // Transition Effect Load Image
            .apply(new RequestOptions().override(175, 247))
            .into(movieViewHolder.movies_image);

        // Set Title
        movieViewHolder.movies_title.setText(movie.getTitle());

        // Set Score & Popularity
        String pattern = "###,###.###";
        DecimalFormat decimalFormat = new DecimalFormat(pattern);
        movieViewHolder.movies_vote_average.setText(decimalFormat.format(movie.getVote_average()));
        movieViewHolder.movies_popularity.setText(decimalFormat.format(movie.getPopularity()));

        // Click Listener
        movieViewHolder.itemView.setOnClickListener(v -> {

            // Open Detail Activity And Passing Data
            Intent intent = new Intent(recyclerContext, DetailActivity.class);
            intent.putExtra("activity_title", recyclerContext.getResources().getString(R.string.detail) + " -- " + recyclerContext.getResources().getString(R.string.movie) + " #");
            intent.putExtra("data_id", movie.getId());
            intent.putExtra("data_type", "movie");
            intent.putExtra("tmdb_url", "https://www.themoviedb.org/movie/" + movie.getId());
            recyclerContext.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() { return (movies != null) ? movies.size() : 0; }

    // Inner Class
    static class MovieGridViewHolder extends RecyclerView.ViewHolder {

        // UI Object
        private ImageView movies_image;
        private TextView movies_title, movies_vote_average, movies_popularity;

        private MovieGridViewHolder(@NonNull View itemView) {
            super(itemView);

            // Find UI Object
            movies_image = itemView.findViewById(R.id.movies_image);
            movies_title = itemView.findViewById(R.id.movies_title);
            movies_vote_average = itemView.findViewById(R.id.movies_vote_average);
            movies_popularity = itemView.findViewById(R.id.movies_popularity);

            // Click Listener Will Be Override By onBindViewHolder
            itemView.setOnClickListener(v -> {
                /* int position = getAdapterPosition(); */
            });
        }
    }
}
