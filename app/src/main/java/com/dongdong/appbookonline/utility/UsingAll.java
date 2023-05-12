package com.dongdong.appbookonline.utility;

import android.content.Context;
import android.content.DialogInterface;

import androidx.appcompat.app.AlertDialog;

import com.dongdong.appbookonline.model.EmailCheck;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class UsingAll {
    public  void ShowDialog(String mess, Context context) throws Exception
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Thông báo!");
        builder.setMessage(mess);

        builder.setNegativeButton("Thoát", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                dialog.dismiss();
            }
        });
        builder.show();
    }

    FirebaseFirestore database= FirebaseFirestore.getInstance();
    public  String generateRandomPassword(int len)
    {

        final String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < len; i++)
        {
            int randomIndex = random.nextInt(chars.length());
            sb.append(chars.charAt(randomIndex));
        }

        return sb.toString();
    }
    public String GetTime()
    {
        Date today = new Date();
        Calendar cal = Calendar.getInstance(); cal.setTime(today);


        String day =""+ cal.get(Calendar.DAY_OF_MONTH);
        String month =""+ cal.get(Calendar.MONTH);
        String year =""+ cal.get(Calendar.YEAR);
        String second=""+ cal.get(Calendar.SECOND);
        String minute=""+ cal.get(Calendar.MINUTE);
        String hour=""+ cal.get(Calendar.HOUR);

        if (month.length() == 1)
        {
            month = "0" + month;
        }
        if (day.length() == 1)
        {
            day = "0" + day;
        }
        if (second.length() == 1)
        {
            second = "0" + second;
        }
        if (minute.length() == 1)
        {
            minute = "0" + minute;
        }
        if (hour.length() == 1)
        {
            hour = "0" + hour;
        }
        return hour+":"+minute+":"+second+" "+day +"/"+ month+"/" + year;
    }
    public List<EmailCheck> CheckEmail(){
        List<EmailCheck> lists = new ArrayList<>();
        database.collection(Constants.User)
                .get()
                .addOnCompleteListener(task -> {

                    for (QueryDocumentSnapshot document : task.getResult()) {
                        EmailCheck eml = new EmailCheck();
                        eml.setEmail(document.getString(Constants.User_email));
                        eml.setPass(document.getString(Constants.User_pass));
                        eml.setId(document.getString(document.getId()));

                        lists.add(eml);
                    }

                });

        return lists;
    }

}
