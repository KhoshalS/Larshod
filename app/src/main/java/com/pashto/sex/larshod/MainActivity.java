package com.pashto.sex.larshod;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.TypefaceSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.VideoView;

import com.google.android.play.core.review.ReviewInfo;
import com.google.android.play.core.review.ReviewManager;
import com.google.android.play.core.review.ReviewManagerFactory;
import com.google.android.play.core.tasks.OnCompleteListener;
import com.google.android.play.core.tasks.OnFailureListener;
import com.google.android.play.core.tasks.Task;

public class MainActivity extends AppCompatActivity {
    private PopupWindow mpopup;
    Intent popIntent;
    private boolean showTutorial;

    private SharedPreferences larShodSP;
    private SharedPreferences.Editor spEditor;
    private ReviewInfo reviewInfo;
    private ReviewManager reviewManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        larShodSP = getSharedPreferences("sp1", Context.MODE_PRIVATE);
        showTutorial = larShodSP.getBoolean("showtoturial", true);
        if (showTutorial){
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    showAbout();
                }
            }, 1000);

            spEditor = getSharedPreferences("sp1", MODE_PRIVATE).edit();
            spEditor.putBoolean("showtoturial",false);
            spEditor.apply();
        }



        //getSupportActionBar().hide();
        getWindow().setStatusBarColor(ContextCompat.getColor(this,R.color.color1));
        getWindow().setNavigationBarColor(ContextCompat.getColor(this,R.color.color3));


        AskRating();

    }

    public void onClick(View view){
        Intent i = new Intent(MainActivity.this, SelectionActivity.class);
        Bundle mBundle = new Bundle();
        spEditor = getSharedPreferences("sp1", MODE_PRIVATE).edit();
        switch (view.getId()){
            case R.id.womenlayout:
                mBundle.putInt("key", 0);
                spEditor.putInt("cat", 0);
                break;

            case R.id.couplelayout:
                mBundle.putInt("key", 1);
                spEditor.putInt("cat", 1);
                break;

            case R.id.menlayout:
                mBundle.putInt("key", 2);
                spEditor.putInt("cat", 2);
                break;

            case R.id.moreAPPText :
                Intent intentx = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/developer?id=Khoshal+Saidy&hl=en_US&gl=US"));
                startActivity(intentx);
                return;

            case R.id.telegramText :
                Intent intentxx = new Intent(Intent.ACTION_VIEW, Uri.parse("https://t.me/KhoshalApps"));
                startActivity(intentxx);
                return;

            case R.id.about_us :
                showAbout();
                return;

        }
        spEditor.apply();
        i.putExtra("key",mBundle);
        startActivity(i);
    }


    public void showAbout(){

        final View popUpView;
        popUpView = getLayoutInflater().inflate(R.layout.pop_up_tutorial,
                null); // inflating popup layout

        mpopup = new PopupWindow(popUpView, WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT, false); // Creation of popup
        mpopup.setAnimationStyle(android.R.style.Animation_Dialog);
        mpopup.showAtLocation(popUpView, Gravity.CENTER, 0, 0); // Displaying popup
        mpopup.setBackgroundDrawable(new ColorDrawable(Color.BLACK));
        mpopup.setOutsideTouchable(true);
        LinearLayout popUpContainer= popUpView.findViewById(R.id.popUpContainer_back);
        popUpContainer.setBackgroundResource(R.drawable.pop_up_container_background);



        ImageView telegram = popUpView.findViewById(R.id.about_telegram);
        ImageView facebook = popUpView.findViewById(R.id.about_facebook);
        ImageView youtube = popUpView.findViewById(R.id.about_youtube);
        ImageView playstore = popUpView.findViewById(R.id.about_playstore);
        ImageView twitter = popUpView.findViewById(R.id.about_twitter);

        telegram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://t.me/khoshalapps"));
                startActivity(popIntent);
            }
        });

        twitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/eshaq_azizi1"));
                startActivity(popIntent);
            }
        });

        facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://fb.com/khoshalapps"));
                startActivity(popIntent);
            }
        });

        youtube.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/channel/UC5ejr-4EqzjSfm_Nzfo25Nw"));
                startActivity(popIntent);
            }
        });

        playstore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/developer?id=Khoshal+Saidy&hl=en_US&gl=US"));
            startActivity(popIntent);
            }
        });


        AppCompatButton no = popUpView.findViewById(R.id.no_btn);
        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mpopup.dismiss();

            }
        });


    }


    public void AskRating(){
        SharedPreferences settings = getSharedPreferences("ratingSP", 0);

        if (settings.getBoolean("my_first_time", true)) {
            //the app is being launched for first time, do something
            Log.d("Comments", "First time");

            // first time task

            // record the fact that the app has been started at least once
            settings.edit().putBoolean("my_first_time", false).apply();
        }else {
            reviewManager = ReviewManagerFactory.create(MainActivity.this);
            Task<ReviewInfo> request = reviewManager.requestReviewFlow();
            request.addOnCompleteListener(new OnCompleteListener<ReviewInfo>() {
                @Override
                public void onComplete(@NonNull Task<ReviewInfo> task) {

                    if (task.isSuccessful()) {

                        reviewInfo = task.getResult();
                        Task<Void> flow = reviewManager.launchReviewFlow(MainActivity.this, reviewInfo).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(Exception e) {
                                //Toast.makeText(MainActivity.this, "Rating Failed", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                //   Toast.makeText(MainActivity.this, "Review Completed, Thank You!", Toast.LENGTH_SHORT).show();
                            }
                        });

                    }
                }
            });

        }
    }


}