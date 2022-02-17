package com.slyworks.rxjava_book.chap_02;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.paging.PagingDataAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.slyworks.rxjava_book.R;
import com.slyworks.rxjava_book.chap_02.models.NewsEntry;

public class NewsEntryPagedListAdapter extends PagingDataAdapter<NewsEntry, NewsEntryPagedListAdapter.ViewHolder> {
    //region Vars
    //endregion
    public NewsEntryPagedListAdapter(@NonNull DiffUtil.ItemCallback<NewsEntry> diffCallback) {
        super(diffCallback);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.li_rv_chap_02, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
       NewsEntry ne = getItem(position);
       holder.bind(ne);
    }


    class ViewHolder extends RecyclerView.ViewHolder{
        //region Vars
         private final TextView tvTitle = itemView.findViewById(R.id.tvTitle_rv_chap_02);
         private final TextView tvId = itemView.findViewById(R.id.tvId_rv_chap_02);
         private final TextView tvUpdated = itemView.findViewById(R.id.tvUpdated_rv_chap_02);
         private final TextView tvLink = itemView.findViewById(R.id.tvLink_rv_chap_02);
        //endregion
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        public void bind(NewsEntry ne){
            tvTitle.setText(ne.title  == null ? "no title" : ne.title);
            tvId.setText("ID:" + ne.id  == null ? "nil" : ne.id);
            tvUpdated.setText("Updated:" + ((ne.updated  == null) ? "nil" : ne.updated.toString()));
            tvLink.setText("Link:" + ne.source.getLink() );
        }
    }
}
