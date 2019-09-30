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
import id.ac.umn.made_basiliusbias_submission5.pojos.Tv;

import java.text.DecimalFormat;
import java.util.List;

public class TvListAdapter extends RecyclerView.Adapter<TvListAdapter.TvListViewHolder> {

    private List<Tv> tvs;
    private Context recyclerContext;
    private int rowLayout;

    public TvListAdapter(Context recyclerContext, int rowLayout) {
        this.recyclerContext = recyclerContext;
        this.rowLayout = rowLayout;
    }

    public void setTvs(List<Tv> tvs) {
        this.tvs = tvs;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TvListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        // Inflate the layout for recycler content
        LayoutInflater inflater = LayoutInflater.from(this.recyclerContext);
        View view = inflater.inflate(rowLayout, viewGroup, false);

        return new TvListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TvListViewHolder tvShowViewHolder, int i) {

        // Get Movie
        final Tv tv = tvs.get(i);

        // Load Image
        Glide.with(recyclerContext)
            .load(tv.getPoster_path())
            .transition(DrawableTransitionOptions.withCrossFade(250)) // Transition Effect Load Image
            .apply(new RequestOptions().override(175, 247))
            .into(tvShowViewHolder.tv_show_image);

        // Set Title
        tvShowViewHolder.tv_show_title.setText(tv.getName());
        if(!(tv.getOverview().replaceAll("\\p{Blank}","")).equals("")) {
            tvShowViewHolder.tv_show_overview.setText(tv.getOverview());
        }

        // Set Score & Popularity
        String pattern = "###,###.###";
        DecimalFormat decimalFormat = new DecimalFormat(pattern);
        tvShowViewHolder.tv_show_score.setText(decimalFormat.format(tv.getVote_average()));
        tvShowViewHolder.tv_show_popularity.setText(decimalFormat.format(tv.getPopularity()));

        // Click Listener
        tvShowViewHolder.itemView.setOnClickListener(v -> {

            // Open Detail Activity And Passing Data
            Intent intent = new Intent(recyclerContext, DetailActivity.class);
            intent.putExtra("activity_title", recyclerContext.getResources().getString(R.string.detail) + " -- " + recyclerContext.getResources().getString(R.string.tv) + " #");
            intent.putExtra("data_id", tv.getId());
            intent.putExtra("data_type", "TV");
            intent.putExtra("tmdb_url", "https://www.themoviedb.org/tv/" + tv.getId());
            recyclerContext.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() { return (tvs != null) ? tvs.size() : 0; }

    // Inner Class
    static class TvListViewHolder extends RecyclerView.ViewHolder {

        // UI Object
        private ImageView tv_show_image;
        private TextView tv_show_title, tv_show_overview, tv_show_score, tv_show_popularity;

        private TvListViewHolder(@NonNull View itemView) {
            super(itemView);

            // Find UI Object
            tv_show_image = itemView.findViewById(R.id.tv_show_image);
            tv_show_title = itemView.findViewById(R.id.tv_show_title);
            tv_show_overview = itemView.findViewById(R.id.tv_show_overview);
            tv_show_score = itemView.findViewById(R.id.tv_show_score);
            tv_show_popularity = itemView.findViewById(R.id.tv_show_popularity);

            // Click Listener Will Be Override By onBindViewHolder
            itemView.setOnClickListener(v -> {
                /* int position = getAdapterPosition(); */
            });
        }
    }
}
