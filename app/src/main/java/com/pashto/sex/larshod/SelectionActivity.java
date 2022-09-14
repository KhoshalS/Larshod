package com.pashto.sex.larshod;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.TypefaceSpan;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AdSettings;
import com.facebook.ads.AdSize;
import com.facebook.ads.AdView;
import com.facebook.ads.InterstitialAd;
import com.facebook.ads.InterstitialAdListener;
import com.pashto.sex.larshod.Models.AllDataList;
import com.pashto.sex.larshod.Models.ItemModel;

import java.util.ArrayList;

public class SelectionActivity extends AppCompatActivity {
    private int cat;
    private Bundle mBundle1,mBundle2;
    CardView actionbar;
    TextView appbarTitle,title,topic;
    ImageView imageView;
    RelativeLayout topicLayout,adLayout;
    boolean adISLoading=false;
    int adCount=2;
    private InterstitialAd interstitial;
    ScrollView scrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selection);
        scrollView=findViewById(R.id.theScrollView);
        actionbar=findViewById(R.id.selectionAPPBAR);
        appbarTitle=findViewById(R.id.appbarTitle);
        title=findViewById(R.id.view_title);
        topic=findViewById(R.id.view_textview);
        imageView=findViewById(R.id.view_img);
        topicLayout=findViewById(R.id.topicLayout);
        adLayout=findViewById(R.id.adContainerLayout);
        Bundle bundle= getIntent().getBundleExtra("key");
        cat=bundle.getInt("key");
        topicLayout.setVisibility(View.GONE);

        DatabaseAccess databaseAccess=DatabaseAccess.getInstance(getApplicationContext());
        databaseAccess.open();
        databaseAccess.generateData();

        ArrayList<ItemModel> items = AllDataList.getInstance().getList().get(cat).getList();

        getWindow().setStatusBarColor(Color.parseColor(AllDataList.colors.get(cat)));
        actionbar.setCardBackgroundColor(Color.parseColor(AllDataList.colors.get(cat)));
        adLayout.setBackgroundColor(Color.parseColor(AllDataList.colors.get(cat)));
        appbarTitle.setText(AllDataList.catTitles.get(cat));

        ArrayList<Integer> imgArray = new ArrayList<>();
        ArrayList<String> titleArray = new ArrayList<>();
        for(int i=0; i<items.size(); i++) {
            imgArray.add(getResources().getIdentifier(items.get(i).getImage(), "drawable", getPackageName()));
            titleArray.add(items.get(i).getTitle());
        }



        ListView listView=(ListView)findViewById(R.id.selection_list_view);

        // For populating list data
        CustomCountryList customCountryList = new CustomCountryList(this, titleArray,imgArray,AllDataList.colors.get(cat));
        listView.setAdapter(customCountryList);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                topicLayout.setVisibility(View.VISIBLE);
                topic.setText(items.get(position).getTopic());
                title.setText(items.get(position).getTitle());
                imageView.setBackgroundResource(getResources().getIdentifier(items.get(position).getImage(), "drawable", getPackageName()));
                loadInterstial();
                scrollView.smoothScrollTo(0,0);

            }});



        LoadAd();
        loadInterstial();
    }

    public void goBack(View view){
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        if (interstitial!=null && interstitial.isAdLoaded() && adCount>3){
            adCount=0;
            interstitial.show();
            adISLoading=false;
        }

        if (topicLayout.getVisibility()==View.VISIBLE){
            topicLayout.setVisibility(View.GONE);
        }else{
            super.onBackPressed();
        }
    }

    public void LoadAd(){
        if (BuildConfig.DEBUG) { AdSettings.setTestMode(true); }
        AdView adView = new AdView(this, getResources().getString(R.string.facebookAdBanner), AdSize.BANNER_HEIGHT_90);
        LinearLayout adContainer = (LinearLayout) findViewById(R.id.adContainer);
        adContainer.addView(adView);
        adView.loadAd();

    }

    public void loadInterstial(){
        adCount++;

        if (adISLoading){
            Toast.makeText(SelectionActivity.this, "denyed "+adCount, Toast.LENGTH_SHORT).show();
            return; }

        adISLoading=true;
        interstitial = new InterstitialAd(this, getString(R.string.facebookAdInterstial1));
        InterstitialAdListener interstitialAdListener = new InterstitialAdListener() {
            @Override
            public void onInterstitialDisplayed(Ad ad) {
                adISLoading=false;
            }

            @Override
            public void onInterstitialDismissed(Ad ad) {
                adISLoading=false;

            }

            @Override
            public void onError(Ad ad, AdError adError) {
                // Toast.makeText(JokeViewActivity.this, "erre", Toast.LENGTH_SHORT).show();
                adISLoading=false;

            }

            @Override
            public void onAdLoaded(Ad ad) {
                adISLoading=true;
                // Toast.makeText(JokeSelectActivity.this, "Loaded", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdClicked(Ad ad) {
                adISLoading=false;
            }

            @Override
            public void onLoggingImpression(Ad ad) {
            }
        };
        interstitial.loadAd(interstitial.buildLoadAdConfig()
                .withAdListener(interstitialAdListener)
                // .withCacheFlags()
                .build());
    }
}
