package com.dongdong.appbookonline.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.dongdong.appbookonline.adapter.ChapterAdapter;
import com.dongdong.appbookonline.databinding.ActivityChapterBinding;
import com.dongdong.appbookonline.listener.ChapterListener;
import com.dongdong.appbookonline.model.Chapters;
import com.dongdong.appbookonline.model.Stories;
import com.dongdong.appbookonline.utility.Constants;
import com.dongdong.appbookonline.utility.UsingAll;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ChapterActivity extends AppCompatActivity implements ChapterListener {
    private ActivityChapterBinding binding;
    Intent it;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChapterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        it= new Intent(getApplicationContext(),ReadStories.class);
        Stories strid=(Stories) getIntent().getSerializableExtra(Constants.ChapterStr);
        LoadChapter(strid.getStories_Id());


    }

    private void LoadChapter(String strid) {
        FirebaseFirestore database= FirebaseFirestore.getInstance();
        List<Chapters> chapters= new ArrayList<>();

        database.collection(Constants.ChapterStr).get()
                .addOnCompleteListener(task -> {
                    if(task.isSuccessful()&&task.getResult().size()>0){
                        for (DocumentSnapshot document:task.getResult()){

                                Chapters chap= new Chapters();
                                chap.chapid= document.getId();
                                chap.strid=document.get(Constants.ChapterStr_IDStr).toString();
                                chap.chapname=document.get(Constants.ChapterStr_Name).toString();
                                chap.content= document.get(Constants.ChapterStr_Content).toString();
                                chap.time= document.get(Constants.ChapterStr_Time).toString();
                                if(chap.strid.equals(strid)){
                                    chapters.add(chap);
                                }


                        }
                        it.putExtra("MaxChap",chapters.size());
                        ChapterAdapter adapter=new ChapterAdapter(chapters,this);
                        binding.listchapter.setAdapter(adapter);
                    }
                })
                .addOnFailureListener(e -> {
                    try {
                        new UsingAll().ShowDialog(e.getMessage(),ChapterActivity.this);
                    } catch (Exception exception) {
                        exception.printStackTrace();
                    }
                });
    }

    @Override
    public void ChapterClick(Chapters chap) {
        it.putExtra("Chapter",chap.chapid);
        startActivity(it);
        finish();
    }
}