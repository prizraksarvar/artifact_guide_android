package com.sarvarcorp.artifactguidedemo.design.animation;

import android.graphics.drawable.TransitionDrawable;
import android.os.Build;
import android.transition.AutoTransition;
import android.transition.Slide;
import android.transition.ChangeBounds;
import android.transition.ChangeImageTransform;
import android.transition.ChangeTransform;
import android.transition.Transition;
import android.transition.TransitionSet;
import android.transition.TransitionValues;

import android.view.View;

public class ButtonToFragmentTransition extends TransitionSet {
    public ButtonToFragmentTransition() {
        setOrdering(ORDERING_TOGETHER);
        addTransition(new ChangeImageTransform());
        addTransition(new ChangeBounds());

        addTransition(new ChangeColor());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            addTransition(new ChangeTransform());
        }
    }
}