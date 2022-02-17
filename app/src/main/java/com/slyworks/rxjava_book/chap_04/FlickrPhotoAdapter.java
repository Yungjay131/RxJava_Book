package com.slyworks.rxjava_book.chap_04;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.slyworks.rxjava_book.R;
import com.slyworks.rxjava_book.chap_04.models.FlickrPhoto;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Joshua Sylvanus, 5:15 PM, 10-Feb-22.
 */
public class FlickrPhotoAdapter extends RecyclerView.Adapter<FlickrPhotoAdapter.ViewHolder> {
    //region Vars
    private List<FlickrPhoto> mList = new ArrayList<>();
    //endregion

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.li_rv_chap_04);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
       final FlickrPhoto entity = mList.get(position);
       holder.bind(entity);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public void setData(List<FlickrPhoto> list){
        mList = list;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        //region Vars
        private CardView rootLayout;
        private TextView tvPersonName;
        private TextView tvPersonAge;
        private ImageView ivPersonPhoto;
        //endregion

        ViewHolder(View itemView){
            super(itemView);
            initViews();
        }

        private void initViews(){
            rootLayout = itemView.findViewById(R.id.rootLayout);
            tvPersonName = itemView.findViewById(R.id.tvPersonName_frag_chap_04);
            tvPersonAge = itemView.findViewById(R.id.tvPersonAge_frag_chap_04);
            ivPersonPhoto = itemView.findViewById(R.id.ivPersonPhoto_frag_chap_04);
        }

        public void bind(FlickrPhoto entity){
            tvPersonName.setText(entity.getTitle());
            tvPersonAge.setText(entity.getUsername());

            Glide.with(ivPersonPhoto.getContext())
                    .load(entity.getThumbnailUrl())
                    .into(ivPersonPhoto);
        }

    }
}
