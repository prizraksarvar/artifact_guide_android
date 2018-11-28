package com.sarvarcorp.artifactguidedemo.fragments;


import android.graphics.Color;
import android.os.Bundle;
import android.transition.ChangeBounds;
import android.transition.Fade;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.sarvarcorp.artifactguidedemo.App;
import com.sarvarcorp.artifactguidedemo.R;
import com.sarvarcorp.artifactguidedemo.base.BaseAppCompatActivity;
import com.sarvarcorp.artifactguidedemo.base.BaseFragment;
import com.sarvarcorp.artifactguidedemo.design.animation.ButtonToFragmentTransition;
import com.sarvarcorp.artifactguidedemo.entities.UniversalItem;
import com.sarvarcorp.artifactguidedemo.listadapter.UniversalItemRecyclerViewAdapter;
import com.sarvarcorp.artifactguidedemo.models.UniversalItemsViewModel;
import com.sarvarcorp.artifactguidedemo.workers.FragmentWorker;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class UniversalItemDescriptionFragment extends BaseFragment implements Observer<List<UniversalItem>> {
    private UniversalItemsViewModel viewModel;
    private UniversalItem parentItem;
    private LiveData<List<UniversalItem>> universalItemLive;
    private LinearLayout layout;


    public UniversalItemDescriptionFragment() {

    }

    @Override
    protected int getLayoutID() {
        return R.layout.fragment_description;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = ViewModelProviders.of(this).get(UniversalItemsViewModel.class);
        viewModel.init(parentItem != null ? parentItem.id : 0);
        universalItemLive = viewModel.getList();
        universalItemLive.observe(this,this);
    }

    public UniversalItem getParentItem() {
        return parentItem;
    }

    public void setParentItem(UniversalItem parentItem) {
        this.parentItem = parentItem;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);

        layout = view.findViewById(R.id.descriptionContainerLayout);

        return view;
    }

    @Override
    public void onChanged(List<UniversalItem> universalItems) {
        layout.removeAllViews();
        for (UniversalItem item: universalItems) {
            if (item.isDetail) {
                View view = getLayoutInflater().inflate(R.layout.description_item_title, layout);
                TextView title = view.findViewById(R.id.descriptionTitleTextView);
                title.setText(item.name);
            }
            if (!item.description.equals("")) {
                View view = getLayoutInflater().inflate(R.layout.description_item_text, layout);
                TextView title = view.findViewById(R.id.descriptionTextView);
                title.setText(item.description);
            }
            if (!item.image.equals("")) {
                View view = getLayoutInflater().inflate(R.layout.description_item_image, layout);
                ImageView imageView = view.findViewById(R.id.descriptionImageView);
                Glide.with(App.getComponent().provideStaticData().getMainActivity())
                        .load(item.image)
                        .into(imageView);
            }
        }
    }
}
