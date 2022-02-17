package com.slyworks.rxjava_book.chap_02;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import com.slyworks.rxjava_book.chap_02.models.NewsEntry;

/**
 * Created by Joshua Sylvanus, 5:32 PM, 31-Dec-21.
 */
public class NewsEntryDiffUtil extends DiffUtil.ItemCallback<NewsEntry> {
    @Override
    public boolean areItemsTheSame(@NonNull NewsEntry oldItem, @NonNull NewsEntry newItem) {
        return oldItem.id.equals(newItem.id);
    }

    @Override
    public boolean areContentsTheSame(@NonNull NewsEntry oldItem, @NonNull NewsEntry newItem) {
        return oldItem.id == newItem.id;
    }
}
