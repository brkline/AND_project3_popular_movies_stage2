package com.example.and_project3_popular_movies_stage2.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.and_project3_popular_movies_stage2.R;
import com.example.and_project3_popular_movies_stage2.models.Video;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieVideosAdapter extends RecyclerView.Adapter<MovieVideosAdapter.MovieVideoViewHolder> {

    private List<Video> movieVideos;
    private Context context;
//    public final static String YOUTUBE_BASE_URL = "http://www.youtube.com/watch?v=";
    public final static String YOUTUBE_BASE_URL = "https://www.youtube.com/watch?v=";
    public final static String YOUTUBE_BASE_IMAGE_URL = "https://img.youtube.com/vi/";
    public final static String YOUTUBE_IMAGE_EXTENSION = "/0.jpg";

    public MovieVideosAdapter(Context context, List<Video> videos) {
        this.context = context;
        this.movieVideos = videos;
    }

    @NonNull
    @Override
    public MovieVideoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new MovieVideoViewHolder(LayoutInflater.from(context).inflate(R.layout.movie_video_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MovieVideoViewHolder holder, int position) {

//        final Trailer trailer = movieTrailers.get(position);
        holder.bindTrailer(movieVideos.get(position));
    }

    class MovieVideoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        @BindView(R.id.video_thumbnail_iv)
        ImageView videoThumbnail;
        @BindView(R.id.video_title)
        TextView videoTitle;
        @BindView(R.id.video_play_button)
        ImageButton videoPlayButton;

        MovieVideoViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            videoPlayButton.setOnClickListener(this);
        }

        void bindTrailer(Video video) {
            videoTitle.setText(video.getVideoTitle());
            if (video.getVideoSite().equalsIgnoreCase("youtube")) {
                Uri uri = Uri.parse(YOUTUBE_BASE_IMAGE_URL + video.getVideoKey() + YOUTUBE_IMAGE_EXTENSION);
                Picasso.get()
                        .load(uri)
                        .placeholder(R.drawable.ic_launcher_background)
                        .error(R.drawable.ic_outline_not_interested_120)
                        .into(videoThumbnail);
            }

        }

        @Override
        public void onClick(View view) {
            Video videoVideo = movieVideos.get(getAdapterPosition());
            if (null != videoVideo) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(YOUTUBE_BASE_URL + videoVideo.getVideoKey()));
                if (null != intent.resolveActivity(context.getPackageManager())) {
                    context.startActivity(intent);
                } else {
                    Toast.makeText(context, "Error playing movie trailer", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    @Override
    public int getItemCount() {
        if (null == movieVideos) {
            return 0;
        } else {
            return movieVideos.size();
        }
    }
}
