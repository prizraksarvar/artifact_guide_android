package com.sarvarcorp.artifactguidedemo;

import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;

import com.sarvarcorp.artifactguidedemo.base.BaseAppCompatActivity;
import com.sarvarcorp.artifactguidedemo.fragments.GuideTypesFragment;
import com.sarvarcorp.artifactguidedemo.workers.FragmentWorker;

public class MainActivity extends BaseAppCompatActivity {
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        App.getComponent().provideStaticData().setFragmentManager(getSupportFragmentManager());
        App.getComponent().provideStaticData().setMainActivity(this);

        toolbar = this.findViewById(R.id.mainToolbar);
        setSupportActionBar(toolbar);

        showInitFragment();
    }

    protected void showInitFragment() {
        toolbar.setVisibility(View.GONE);
        App.getComponent().provideFragmentWorker().showFragment(GuideTypesFragment.class, false, FragmentWorker.AnimationType.openFragment);
    }
}
