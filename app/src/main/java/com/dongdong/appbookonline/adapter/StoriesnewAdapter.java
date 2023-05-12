package com.dongdong.appbookonline.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.dongdong.appbookonline.databinding.ItemNewstrBinding;
import com.dongdong.appbookonline.listener.newstrlistener;
import com.dongdong.appbookonline.model.Stories;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class StoriesnewAdapter extends RecyclerView.Adapter<StoriesnewAdapter.StrNewViewHolder>  {

    List<Stories> list;
    final newstrlistener newstrlistener;

    public StoriesnewAdapter(List<Stories> list, com.dongdong.appbookonline.listener.newstrlistener newstrlistener) {
        this.list = list;
        this.newstrlistener = newstrlistener;
    }

    @NonNull
    @NotNull
    @Override
    public StrNewViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
       ItemNewstrBinding newstrBinding= ItemNewstrBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return  new StrNewViewHolder(newstrBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull StrNewViewHolder holder, int position) {
        holder.SetData(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class  StrNewViewHolder extends RecyclerView.ViewHolder {

        ItemNewstrBinding binding;
        public StrNewViewHolder(ItemNewstrBinding itemNewstrBinding) {
            super(itemNewstrBinding.getRoot());
            binding=itemNewstrBinding;
        }
        void SetData(Stories stories){
            Glide.with(binding.getRoot()).load(stories.getStories_Img()).into(binding.imgstr);
            binding.namestr.setText(stories.getStories_Name());
            binding.viewstr.setText("Lượt xem: "+stories.getStories_View());
            binding.getRoot().setOnClickListener( view -> newstrlistener.StoriesClick(stories));
        }
    }
}
