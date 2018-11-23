package com.sarvarcorp.artifactguidedemo.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public abstract class BaseFragment extends Fragment {

    protected abstract int getLayoutID();

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(getLayoutID(), container, false);
    }

    public void prepareAnimation(FragmentTransaction fragmentTranaction, BaseFragment oldFragment, View view) {

    }

    public void prepareEnterAnimation(FragmentTransaction fragmentTranaction, BaseFragment oldFragment, View view) {

    }

    public void prepareExitAnimation(FragmentTransaction fragmentTranaction, BaseFragment oldFragment, View view) {

    }

    public void prepareReturnAnimation(FragmentTransaction fragmentTranaction, BaseFragment oldFragment, View view) {

    }

    public void prepareReenterAnimation(FragmentTransaction fragmentTranaction, BaseFragment oldFragment, View view) {

    }
}
