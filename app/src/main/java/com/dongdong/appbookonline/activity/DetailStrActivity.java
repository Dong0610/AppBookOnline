package com.dongdong.appbookonline.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.bumptech.glide.Glide;
import com.dongdong.appbookonline.databinding.ActivityDetailstrBinding;
import com.dongdong.appbookonline.databinding.ChapterItemBinding;
import com.dongdong.appbookonline.model.Stories;
import com.dongdong.appbookonline.utility.Constants;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;


public class DetailStrActivity extends AppCompatActivity {

    private ActivityDetailstrBinding binding;
    private Stories stories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailstrBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        stories = (Stories) getIntent().getSerializableExtra(Constants.Stories);
        LoadStr(stories);
        binding.backbtn.setOnClickListener(v->startActivity(new Intent(getApplicationContext(),HomeActivity.class)));
    }

    private void LoadStr(Stories stories) {
        Glide.with(binding.getRoot()).load(stories.getStories_Img()).into(binding.imguser);
        binding.strname.setText(stories.getStories_Name());
        binding.strmota.setText("Mô tả: "+stories.getStories_Des());
        binding.strview.setText("Số lượt xem: "+stories.getStories_View());
        binding.idcategory.setText("Thể loại: "+stories.getStories_CtgrID());
        binding.emailuser.setText("Mã tác giả: "+stories.getStories_IdAuth());

        binding.btreadstr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it= new Intent(getApplicationContext(), ChapterActivity.class);
                it.putExtra(Constants.ChapterStr,stories);
                startActivity(it);
            }
        });



    }
}