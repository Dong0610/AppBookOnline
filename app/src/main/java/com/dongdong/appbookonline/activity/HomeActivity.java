package com.dongdong.appbookonline.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;

import com.bumptech.glide.Glide;
import com.dongdong.appbookonline.adapter.StoriesnewAdapter;
import com.dongdong.appbookonline.databinding.ActivityHomeBinding;
import com.dongdong.appbookonline.listener.newstrlistener;
import com.dongdong.appbookonline.model.Stories;
import com.dongdong.appbookonline.model.User;
import com.dongdong.appbookonline.utility.Constants;
import com.dongdong.appbookonline.utility.PreferenceManager;
import com.dongdong.appbookonline.utility.UsingAll;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class HomeActivity extends AppCompatActivity implements newstrlistener {

    private User recevieuser;
    ActivityHomeBinding binding;
    private PreferenceManager preferenceManager;
    FirebaseFirestore database;
    StoriesnewAdapter storiesnewAdapter;
    List<Stories> listnews= new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        preferenceManager= new PreferenceManager(getApplicationContext());
        database= FirebaseFirestore.getInstance();
        LoadUserDetail();

        binding.imguser.setOnClickListener(view -> {
            preferenceManager.clear();
            startActivity(new Intent(getApplicationContext(),SignUpActivity.class));
        });

         LoadListNewStr();



    }

    private List<Stories> LoadListNewStr() {
        List<Stories> strsort= new ArrayList<>();
        database.collection(Constants.Stories).get()
                .addOnCompleteListener(task -> {
                    if(task.isSuccessful()&&task.getResult().size()>0){
                        for (DocumentSnapshot snapshot:task.getResult()){
                            Stories stories= new Stories();
                            stories.setStories_Id(snapshot.getId());
                            stories.setStories_Name(snapshot.get(Constants.Stories_Name).toString());
                            stories.setStories_CtgrID(snapshot.get(Constants.Stories_CtgrID).toString());
                            stories.setStories_Time(snapshot.get(Constants.Stories_Time).toString());
                            stories.setStories_Img(snapshot.get(Constants.Stories_Img).toString());
                            stories.setStories_Des((snapshot.get(Constants.Stories_Des).toString()));
                            stories.setStories_View(snapshot.get(Constants.Stories_View).toString());
                            stories.setStories_IdAuth(snapshot.get(Constants.Stories_IdAuth).toString());
                            stories.setStories_Status(snapshot.get(Constants.Stories_Status).toString());

                            listnews.add(stories);
                            strsort.add(stories);
                        }

                        storiesnewAdapter= new StoriesnewAdapter(listnews,this::StoriesClick);
                        Collections.sort(listnews,(o1,o2)->o1.getStories_Time().compareTo(o2.getStories_Time()));
                        binding.newstories.setAdapter(storiesnewAdapter);
                        Collections.sort(strsort,(o1,o2)->o1.getStories_View().compareTo(o2.getStories_View()));

                        StoriesnewAdapter adapter= new StoriesnewAdapter(strsort,this::StoriesClick);
                        binding.sortview.setAdapter(adapter);

                        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false);
                        binding.stralllist.setLayoutManager(gridLayoutManager);
                        binding.stralllist.setAdapter(adapter);
                    }
                })
                .addOnFailureListener(e -> {
                    try {
                        new UsingAll().ShowDialog(e.getMessage(),HomeActivity.this);
                    } catch (Exception exception) {
                        exception.printStackTrace();
                    }
                });

        return  listnews;
    }

    void UpdateViewStr(String id , int view){
        DocumentReference reference= database.collection(Constants.Stories)
                .document( id);
        reference.update(Constants.Stories_View,view+1);
    }


    private void LoadUserDetail() {
        String url= preferenceManager.getString(Constants.User_Img);
        Glide.with(binding.getRoot()).load(url).into(binding.imguser);
    }
    @Override
    public void StoriesClick(Stories stories) {
        Intent intent = new Intent(getApplicationContext(), DetailStrActivity.class);

        UpdateViewStr(stories.getStories_Id(),Integer.parseInt(stories.getStories_View()));
      intent.putExtra(Constants.Stories,  stories);
        startActivity(intent);

    }
}