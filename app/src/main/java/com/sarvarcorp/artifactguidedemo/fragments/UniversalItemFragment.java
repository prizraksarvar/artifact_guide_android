package com.sarvarcorp.artifactguidedemo.fragments;


import android.os.Bundle;
import android.transition.ChangeBounds;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.sarvarcorp.artifactguidedemo.App;
import com.sarvarcorp.artifactguidedemo.R;
import com.sarvarcorp.artifactguidedemo.base.BaseFragment;
import com.sarvarcorp.artifactguidedemo.design.animation.ButtonToFragmentTransition;
import com.sarvarcorp.artifactguidedemo.entities.UniversalItem;
import com.sarvarcorp.artifactguidedemo.models.UniversalItemViewModel;
import com.sarvarcorp.artifactguidedemo.models.UniversalItemsViewModel;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;


public class UniversalItemFragment extends BaseFragment implements Observer<UniversalItem> {
    private UniversalItem universalItem;
    private UniversalItemViewModel viewModel;
    private LiveData<UniversalItem> guideLive;

    private TextView titelTextView;
    private ImageView imageView;
    private TextView descriptionTextView;

    public UniversalItem getUniversalItem() {
        return universalItem;
    }

    public void setUniversalItem(UniversalItem universalItem) {
        this.universalItem = universalItem;
    }

    public UniversalItemFragment() {

    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = ViewModelProviders.of(this).get(UniversalItemViewModel.class);
        viewModel.init(universalItem.id);
        guideLive = viewModel.get();

        guideLive.observe(this,this);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        titelTextView = view.findViewById(R.id.universalItemTitleTextView);
        imageView = view.findViewById(R.id.universalItemImageView);
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
    public void onChanged(@Nullable UniversalItem universalItem) {
        if (universalItem!=null) {
            titelTextView.setText(universalItem.name);
            descriptionTextView.setText(universalItem.description);

            if (!universalItem.image.equals("")) {
                Glide.with(App.getComponent().provideStaticData().getMainActivity())
                        .load(universalItem.image)
                        .into(imageView);
                //setImage(universalItem.image, imageView);
            }

        }
    }

    @Override
    public void prepareEnterAnimation(FragmentTransaction fragmentTranaction, BaseFragment oldFragment, View view) {
        ImageView image = view.findViewById(R.id.guideListImageView);
        TextView title = view.findViewById(R.id.guideNameTextView);
        fragmentTranaction.addSharedElement(title,getTitleSharedName());
        if (image!=null)
            fragmentTranaction.addSharedElement(image, getImageSharedName());
        setSharedElementEnterTransition(new ButtonToFragmentTransition());
        setEnterTransition(new ChangeBounds());
        setExitTransition(new ChangeBounds());
        setSharedElementReturnTransition(new ButtonToFragmentTransition());
    }

    private String getTitleSharedName() {
        return "universalItemTitle"+ universalItem.id;
    }

    private String getImageSharedName() {
        return "universalItemImage"+ universalItem.id;
    }
}
