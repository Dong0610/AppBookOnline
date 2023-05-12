package com.dongdong.appbookonline.activity;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextPaint;
import android.util.Patterns;
import android.webkit.MimeTypeMap;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.dongdong.appbookonline.databinding.ActivitySignupBinding;
import com.dongdong.appbookonline.model.EmailCheck;
import com.dongdong.appbookonline.utility.Constants;
import com.dongdong.appbookonline.utility.PreferenceManager;
import com.dongdong.appbookonline.utility.UsingAll;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class SignUpActivity extends AppCompatActivity {

    private ActivitySignupBinding binding;
    private Context context;

    FirebaseFirestore database;
    private StorageReference reference = FirebaseStorage.getInstance().getReference();
    private Uri uri;
    List<EmailCheck> list = new ArrayList<>();

    private PreferenceManager preferenceManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignupBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        database = FirebaseFirestore.getInstance();
        SetViewItem();
        Setlistener();
        preferenceManager = new PreferenceManager(getApplicationContext());
        if(preferenceManager.getBollean(Constants.KEY_IS_SIGN_IN)){
            Intent it= new Intent(getApplicationContext(),HomeActivity.class);
            startActivity(it);
            finish();
        }
        list = new UsingAll().CheckEmail();
    }

    boolean CheckEmail(String email) {
        for (EmailCheck eml : list) {
            if (eml.getEmail().equals(email)) {
                return false;
            }
        }
        return true;
    }

    private void Setlistener() {
        binding.btnsignup.setOnClickListener(v -> {
            if (ValidsSignUpDetail()) {
             SignUp(uri);

            }
        });

        binding.signin.setOnClickListener(v->{
            startActivity(new Intent(getApplicationContext(),SignInActivity.class));
        });


    }

    private Boolean ValidsSignUpDetail() {
        String pass1 = binding.passw.getText().toString().trim();
        String pass2 = binding.repass.getText().toString().trim();

        if (binding.username.getText().toString().trim().isEmpty()) {
            ShowToast("Enter user name");
            return false;
        } else if (binding.email.getText().toString().trim().isEmpty()) {
            ShowToast("Enter email");
            return false;
        } else if (!CheckEmail(binding.email.getText().toString())) {
            ShowToast("Email đã được sử dụng");
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(binding.email.getText().toString()).matches()) {
            ShowToast("Yêu cầu một email");
            return false;
        } else if (binding.passw.getText().toString().trim().isEmpty()) {
            ShowToast("Hãy nhập mật khẩu");
            return false;
        } else if (!pass1.equals(pass2)) {
            ShowToast("Mật khẩu không khớp");
            return false;
        } else {
            return true;
        }
    }

    private void SignUp(Uri uri) {
        String id = binding.username.getText() + new UsingAll().generateRandomPassword(14);
        String time= new UsingAll().GetTime();
      String url="https://firebasestorage.googleapis.com/v0/b/docsachonline-dongdong.appspot.com/o/imgdefault.jpg?alt=media&token=3e4e5c76-bfcb-417d-b67f-9ed3d9306cee";
                        FirebaseFirestore database= FirebaseFirestore.getInstance();
                        HashMap<String,Object> user= new HashMap<>();
                        user.put(Constants.User_name,binding.username.getText().toString());
                        user.put(Constants.User_email,binding.email.getText().toString());
                        user.put(Constants.User_pass,binding.passw.getText().toString());
                        user.put(Constants.User_Img, url);
                        user.put(Constants.User_Admin,"0");
                        user.put(Constants.User_descible,"");
                        user.put(Constants.User_Time,time);

                        database.collection(Constants.User).document(id).set(user)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Intent it= new Intent(getApplicationContext(),HomeActivity.class);
                                    it.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(it);
                                    }
                                }).addOnFailureListener(e -> {
                            ShowToast(e.getMessage());
                        });
                        database.collection(Constants.User).add(user).addOnSuccessListener(
                                documentReference -> {
                                    preferenceManager.putBoollean(Constants.KEY_IS_SIGN_IN,true);
                                    preferenceManager.putString(Constants.User_ID,documentReference.getId());
                                    preferenceManager.putString(Constants.User_name,binding.username.getText().toString());
                                    preferenceManager.putString(Constants.User_Img,url);
                                    preferenceManager.putString(Constants.User_Admin,"0");
                                    preferenceManager.putString(Constants.User_email,binding.email.getText().toString());
                                    preferenceManager.putString(Constants.User_pass,binding.passw.getText().toString());
                                    preferenceManager.putString(Constants.User_Time,time);
                                    preferenceManager.putString(Constants.User_descible,"");
                                    Intent it= new Intent(getApplicationContext(),HomeActivity.class);
                                    it.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(it);
                                }
                        ).addOnFailureListener(
                                e -> {
                                    ShowToast(e.getMessage());
                                }
                        );
                    }

//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//                ShowToast("Không thể  upload");
//            }
//        });


    private String getFileExTension(Uri uri) {
        ContentResolver cr = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cr.getType(uri));
    }

    void ShowToast(String mess) {
        Toast.makeText(getApplicationContext(), mess, Toast.LENGTH_LONG).show();
    }

    private void SetViewItem() {

        TextPaint paint = binding.txtview.getPaint();
        float width = paint.measureText("Đăng kí tài khoản");

        Shader textShader = new LinearGradient(0, 0, width, binding.txtview.getTextSize(),
                new int[]{
                        Color.parseColor("#800080"),
                        Color.parseColor("#FF1493"),

                }, null, Shader.TileMode.CLAMP);
        binding.txtview.getPaint().setShader(textShader);
    }

}