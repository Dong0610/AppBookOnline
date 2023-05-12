package com.dongdong.appbookonline.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.os.Bundle;
import android.text.TextPaint;
import android.widget.Toast;

import com.dongdong.appbookonline.databinding.ActivitySigninBinding;
import com.dongdong.appbookonline.model.EmailCheck;
import com.dongdong.appbookonline.utility.Constants;
import com.dongdong.appbookonline.utility.PreferenceManager;
import com.dongdong.appbookonline.utility.UsingAll;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class SignInActivity extends AppCompatActivity {

    private ActivitySigninBinding binding;
    private FirebaseFirestore database= FirebaseFirestore.getInstance();
    private PreferenceManager preferenceManager;
    List<EmailCheck> liste;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding=ActivitySigninBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        liste= new UsingAll().CheckEmail();


        preferenceManager= new PreferenceManager(getApplicationContext());
        binding.btnsignin.setOnClickListener(view -> {
            String email= binding.email.getText().toString();
            String password= binding.passw.getText().toString();
            if(CheckVal(email,password)){
                SignIn(email,password);
            }
            else {
                ShowToast("Sai email hoặc mật khẩu");
            }
        });
        binding.signup.setOnClickListener(v->{
            startActivity(new Intent(getApplicationContext(),SignUpActivity.class));
        });
    }

    boolean CheckVal(String email,String pass){
        for (EmailCheck check:liste){
            if(check.getEmail().equals(email)&&check.getPass().equals(pass)){
                return true;
            }
        }
        return false;
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
    void ShowToast(String mess) {
        Toast.makeText(getApplicationContext(), mess, Toast.LENGTH_LONG).show();
    }

    private void SignIn(String email,String pass) {



        database.collection(Constants.User).whereEqualTo(Constants.User_email,email)
                .whereEqualTo(Constants.User_pass,pass)
                .get()
                .addOnCompleteListener(task -> {
                    if(task.isSuccessful()&&task.getResult()!=null&&task.getResult().getDocuments().size()>0){
                        DocumentSnapshot documentReference= task.getResult().getDocuments().get(0);
                        preferenceManager.putBoollean(Constants.KEY_IS_SIGN_IN,true);
                        preferenceManager.putString(Constants.User_ID,documentReference.getId());
                        preferenceManager.putString(Constants.User_name,documentReference.getString(Constants.User_name));
                        preferenceManager.putString(Constants.User_Img,documentReference.getString(Constants.User_Img));
                        preferenceManager.putString(Constants.User_email,documentReference.getString(Constants.User_email));
                        preferenceManager.putString(Constants.User_pass,documentReference.getString(Constants.User_pass));
                        preferenceManager.putString(Constants.User_Time,documentReference.getString(Constants.User_Time));
                        preferenceManager.putString(Constants.User_descible,documentReference.getString(Constants.User_descible));
                        Intent it= new Intent(getApplicationContext(),HomeActivity.class);
                        it.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(it);
                    }
                })
                .addOnFailureListener(e -> {
                    ShowToast("Sai mật khẩu");
                });

    }
}