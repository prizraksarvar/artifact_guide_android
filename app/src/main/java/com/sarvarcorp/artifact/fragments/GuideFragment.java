package com.sarvarcorp.artifact.fragments;


import android.os.Bundle;
import android.transition.ChangeBounds;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.sarvarcorp.artifact.App;
import com.sarvarcorp.artifact.R;
import com.sarvarcorp.artifact.base.BaseFragment;
import com.sarvarcorp.artifact.design.animation.ButtonToFragmentTransition;
import com.sarvarcorp.artifact.entities.Guide;
import com.sarvarcorp.artifact.models.GuideViewModel;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;


public class GuideFragment extends BaseFragment implements Observer<Guide> {
    private Guide guide;
    private GuideViewModel viewModel;
    private LiveData<Guide> guideLive;

    private TextView titelTextView;
    private ImageView imageView;
    private TextView descriptionTextView;

    public Guide getGuide() {
        return guide;
    }

    public void setGuide(Guide guide) {
        this.guide = guide;
    }

    public GuideFragment() {

    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = ViewModelProviders.of(this).get(GuideViewModel.class);
        viewModel.init(guide.id);
        guideLive = viewModel.get();

        guideLive.observe(this,this);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        titelTextView = view.findViewById(R.id.guideTitleTextView);
        imageView = view.findViewById(R.id.guideShowImageView);
        descriptionTextView = view.findViewById(R.id.guideDescriptionTextView);

        ViewCompat.setTransitionName(titelTextView,getTitleSharedName());
        ViewCompat.setTransitionName(imageView, getImageSharedName());
        return view;
    }

    @Override
    protected int getLayoutID() {
        return R.layout.fragment_guide;
    }

    @Override
    public void onChanged(@Nullable Guide guide) {
        if (guide!=null) {
            titelTextView.setText(guide.name);
            descriptionTextView.setText(guide.description);

            if (!guide.image.equals("")) {
                Glide.with(App.getComponent().provideStaticData().getMainActivity())
                        .load(guide.image)
                        .into(imageView);
                //setImage(guide.image, imageView);
            }

        }
    }

    @Override
    public void prepareEnterAnimation(FragmentTransaction fragmentTranaction, BaseFragment oldFragment, View view) {
        ImageView image = view.findViewById(R.id.guideListImageView);
        TextView title = view.findViewById(R.id.guideNameTextView);
        fragmentTranaction.addSharedElement(title,getTitleSharedName());
        fragmentTranaction.addSharedElement(image, getImageSharedName());
        setSharedElementEnterTransition(new ButtonToFragmentTransition());
        setEnterTransition(new ChangeBounds());
        setExitTransition(new ChangeBounds());
        setSharedElementReturnTransition(new ButtonToFragmentTransition());
    }

    private String getTitleSharedName() {
        return "quideName"+guide.id;
    }

    private String getImageSharedName() {
        return "quideImage"+guide.id;
    }
}
