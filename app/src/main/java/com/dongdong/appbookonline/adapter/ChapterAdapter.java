package com.dongdong.appbookonline.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dongdong.appbookonline.databinding.ChapterItemBinding;
import com.dongdong.appbookonline.listener.ChapterListener;
import com.dongdong.appbookonline.model.Chapters;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ChapterAdapter extends  RecyclerView.Adapter<ChapterAdapter.ChapterViewHolder>{
    public ChapterAdapter(List<Chapters> list, ChapterListener listener) {
        this.list = list;
        this.listener = listener;
    }

    List<Chapters> list;
    final ChapterListener listener;

    @NonNull
    @NotNull
    @Override
    public ChapterViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        ChapterItemBinding itemBinding= ChapterItemBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new ChapterViewHolder(itemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ChapterViewHolder holder, int position) {
        holder.SetData(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ChapterViewHolder extends RecyclerView.ViewHolder{
        private ChapterItemBinding binding;

        public ChapterViewHolder(ChapterItemBinding itemView) {
            super(itemView.getRoot());
            binding=itemView;
        }

        void SetData(Chapters ctrs){
            binding.chapter.setText(ctrs.chapname);
            binding.getRoot().setOnClickListener(v->listener.ChapterClick(ctrs));
        }
    }
}
