package com.sarvarcorp.artifact;

import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.sarvarcorp.artifact.base.BaseAppCompatActivity;
import com.sarvarcorp.artifact.fragments.UniversalItemsFragment;
import com.sarvarcorp.artifact.workers.FragmentWorker;

import java.util.concurrent.Executor;

public class MainActivity extends BaseAppCompatActivity {
    private String activityState = "";
    private Toolbar toolbar;
    private AdView mAdView;

    private InterstitialAd mInterstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        App.getComponent().provideStaticData().setFragmentManager(getSupportFragmentManager());
        App.getComponent().provideStaticData().setMainActivity(this);

        toolbar = this.findViewById(R.id.mainToolbar);
        setSupportActionBar(toolbar);
        showInitFragment();
        initAds();
    }

    protected void initAds() {
        initBunnerAds();
        initInterstitealAds();
    }

    protected void initBunnerAds() {
        MobileAds.initialize(this, "YOUR_ADMOB_APP_ID");
        AdView adView = new AdView(this);
        adView.setAdSize(AdSize.BANNER);
        adView.setAdUnitId("ca-app-pub-7280670502444208/9134435052");
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }

    protected void initInterstitealAds() {
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-7280670502444208/7026188600");
        loadInterstitealAds();

        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                loadInterstitealAds();
            }
        });
    }

    protected void loadInterstitealAds() {
        Executor executor = App.getComponent().provideExecutor();
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
    }

    protected void showInitFragment() {
        toolbar.setVisibility(View.GONE);
        App.getComponent().provideFragmentWorker().showFragment(UniversalItemsFragment.class, false, FragmentWorker.AnimationType.openFragment);
    }

    public boolean showInterstitealAds() {
        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
            return true;
        }
        return false;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onDestroy() {
        App.getComponent().provideFragmentWorker().onActivityDestroy();

        super.onDestroy();
        System.exit(0);
    }
}
