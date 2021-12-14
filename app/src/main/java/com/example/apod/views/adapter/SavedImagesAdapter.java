package com.example.apod.views.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.apod.R;
import com.example.apod.model.APODImageData;
import java.util.List;

public class SavedImagesAdapter extends RecyclerView.Adapter<SavedImagesAdapter.ViewHolder> {

    Context context;
    List<APODImageData> bookmarkedIMagesList;

    public SavedImagesAdapter(List<APODImageData> bookmarkedIMagesList, Context context) {
        this.context = context;
        this.bookmarkedIMagesList = bookmarkedIMagesList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View listItem = layoutInflater.inflate(R.layout.item, viewGroup, false);
        return new ViewHolder(listItem);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        viewHolder.render(viewHolder,position);
    }

    @Override
    public int getItemCount() {
        return bookmarkedIMagesList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        int position;
        ViewHolder holder;
        ImageView image;
        TextView heading;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            initViews(itemView);
        }

        private void initViews(@NonNull View itemView) {
           image = itemView.findViewById(R.id.imageIV_);
           heading = itemView.findViewById(R.id.title);
        }

        public void render(ViewHolder viewHolder, int position) {
            this.position = position;
            this.holder = viewHolder;

            try {
                Glide.with(context)
                        .load(bookmarkedIMagesList.get(position).getUrl())
                        .into(image);

                heading.setText(bookmarkedIMagesList.get(position).getTitle());
            }catch (Exception e){

            }


        }
    }
}
