package com.dongdong.appbookonline.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;
import android.widget.Toast;

import com.dongdong.appbookonline.databinding.ActivityReadstoriesBinding;
import com.dongdong.appbookonline.model.Chapters;
import com.dongdong.appbookonline.utility.Constants;
import com.dongdong.appbookonline.utility.UsingAll;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class ReadStories extends AppCompatActivity {

    private ActivityReadstoriesBinding binding;
    int val = 0;
    int maxchap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityReadstoriesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        String chap = (String) getIntent().getSerializableExtra("Chapter");
        maxchap = (int) getIntent().getSerializableExtra("MaxChap");
        GetMaxChapter();

        LoadChap(chap);
        binding.icsetting.setOnClickListener(v -> {
                    startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                    finish();
                }
        );

        binding.layoutitem.setVisibility(View.GONE);
        boolean isview = true;

        binding.txtstrcontent.setOnLongClickListener(view -> {
            if (isview) {
                binding.layoutitem.setVisibility(View.VISIBLE);
            }

            return false;
        });

        binding.idval.setOnClickListener(v -> {
            binding.layoutitem.setVisibility(View.GONE);
        });
        binding.seektextside.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

                if (i == 2) {
                    binding.txtstrcontent.setTextSize(14);
                }
                if (i == 3) {
                    binding.txtstrcontent.setTextSize(16);
                }
                if (i == 4) {
                    binding.txtstrcontent.setTextSize(19);
                }
                if (i == 5) {
                    binding.txtstrcontent.setTextSize(21);
                }
                if (i == 6) {
                    binding.txtstrcontent.setTextSize(24);
                }

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }

    private void GetMaxChapter() {

    }


    String LoadChapPrNt(String chapid, int val, String strid) {
        String id = strid;
        int chapnum = Integer.parseInt(chapid.substring(20));
        if (val == 1) {
            if (chapnum == maxchap) {
                chapnum =maxchap;
            } else {
                chapnum = chapnum + 1;
            }


        } else if (val == 0) {
            chapnum = chapnum - 1;
        }


        if (chapnum == 0) {
            return id += "0001";
        } else {

            if (Integer.toString(chapnum).length() == 1) {
                id += "000" + chapnum;
            } else if (Integer.toString(chapnum).length() == 2) {
                id += "00" + chapnum;
            } else if (Integer.toString(chapnum).length() == 3) {
                id += "0" + chapnum;
            } else if (Integer.toString(chapnum).length() == 4) {
                id += Integer.toString(chapnum);
            }
            return id;

        }
    }

    private void LoadChap(String chaps) {
        Chapters chap = new Chapters();
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        database.collection(Constants.ChapterStr).document(chaps)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful() && task.getResult() != null) {

                        chap.strid = task.getResult().get(Constants.ChapterStr_IDStr).toString();
                        chap.chapid = task.getResult().getId().toString();
                        chap.chapname = task.getResult().get(Constants.ChapterStr_Name).toString();
                        chap.content = task.getResult().get(Constants.ChapterStr_Content).toString();
                        chap.time = task.getResult().get(Constants.ChapterStr_Time).toString();

                        binding.txtchapname.setText(chap.chapname);
                        binding.txtstrcontent.setText(chap.content);
                    }
                })
                .addOnFailureListener(e -> {

                    try {
                        new UsingAll().ShowDialog(e.getMessage(), getApplicationContext());
                    } catch (Exception exception) {
                        exception.printStackTrace();
                    }
                });

        binding.prechap.setOnClickListener(v -> {
            String chapid = LoadChapPrNt(chap.chapid, 0, chap.strid);
            Intent it = new Intent(getApplicationContext(), ReadStories.class);
            it.putExtra("Chapter", chapid);
            it.putExtra("MaxChap", maxchap);
            startActivity(it);
            finish();
        });
        binding.nextchap.setOnClickListener(v -> {
            String chapid = LoadChapPrNt(chap.chapid, 1, chap.strid);
          Intent it = new Intent(getApplicationContext(), ReadStories.class);
            it.putExtra("Chapter", chapid);
            it.putExtra("MaxChap", maxchap);
            startActivity(it);
            finish();
        });

        binding.icbackpress.setOnClickListener(v -> {
            onBackPressed();
            finish();
        });
    }
}