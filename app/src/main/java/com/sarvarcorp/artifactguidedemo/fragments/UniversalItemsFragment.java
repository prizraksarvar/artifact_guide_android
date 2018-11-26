package com.sarvarcorp.artifactguidedemo.fragments;


import android.os.Bundle;
import android.transition.ChangeBounds;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class UniversalItemsFragment extends BaseFragment implements UniversalItemRecyclerViewAdapter.UniversalItemListListener {
    private UniversalItemsViewModel viewModel;
    private RecyclerView mItemsListView;
    private UniversalItemRecyclerViewAdapter mRecyclerViewAdepter;
    private UniversalItem parentItem;

    private TextView title;
    private ConstraintLayout layout;


    public UniversalItemsFragment() {

    }

    @Override
    protected int getLayoutID() {
        return R.layout.fragment_universal_items;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = ViewModelProviders.of(this).get(UniversalItemsViewModel.class);
        viewModel.init(parentItem != null ? parentItem.id : 0);
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
        initRecyclerView(view);

        layout = view.findViewById(R.id.universalItemsFragmentLayout);
        title = view.findViewById(R.id.universalItemTitleTextView);
        String titleString = "";
        if (parentItem!=null) {
            titleString = parentItem.name;
        }
        title.setText(titleString);

        ViewCompat.setTransitionName(title,getTitleSharedName());
        ViewCompat.setTransitionName(layout, getLayoutSharedName());
        return view;
    }

    public void initRecyclerView(View view) {
        mItemsListView = view.findViewById(R.id.universalItemsRecyclerView);
        mRecyclerViewAdepter = new UniversalItemRecyclerViewAdapter(this,(BaseAppCompatActivity) this.getActivity());
        mItemsListView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        //mItemsListView.setLayoutManager(new GridLayoutManager(this.getContext(),2));
        mItemsListView.setAdapter(mRecyclerViewAdepter);
        mRecyclerViewAdepter.setValues(viewModel.getList());
    }

    @Override
    public void onClickListItem(View view, UniversalItem universalItem) {
        if (universalItem!=null && universalItem.id>0) {
            try {
                if (!universalItem.isDetail) {
                    UniversalItemsFragment fragment = (UniversalItemsFragment) App.getComponent().provideFragmentWorker().getFragment(UniversalItemsFragment.class);
                    fragment.setParentItem(universalItem);
                    App.getComponent().provideFragmentWorker().showFragment(GuidesFragment.class,true, fragment, FragmentWorker.AnimationType.openGuidesList, view);
                } else {
                    UniversalItemsFragment fragment = (UniversalItemsFragment) App.getComponent().provideFragmentWorker().getFragment(UniversalItemsFragment.class);
                    fragment.setParentItem(universalItem);
                    App.getComponent().provideFragmentWorker().showFragment(GuidesFragment.class,true, fragment, FragmentWorker.AnimationType.openGuidesList, view);
                }
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | java.lang.InstantiationException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void prepareEnterAnimation(FragmentTransaction fragmentTranaction, BaseFragment oldFragment, View view) {
        ConstraintLayout layout = view.findViewById(R.id.universalItemButton);
        TextView title = view.findViewById(R.id.universalItemTitleTextView);
        ImageView imageView = view.findViewById(R.id.universalItemImageView);
        fragmentTranaction.addSharedElement(title,getTitleSharedName());
        fragmentTranaction.addSharedElement(layout,getLayoutSharedName());
        if (imageView!=null) {
            fragmentTranaction.addSharedElement(imageView,getImageSharedName());
        }
        setSharedElementEnterTransition(new ButtonToFragmentTransition());
        setEnterTransition(new ChangeBounds());
        setExitTransition(new ChangeBounds());
        setSharedElementReturnTransition(new ButtonToFragmentTransition());
    }

    private String getTitleSharedName() {
        return "universalItemTitle"+parentItem.id;
    }

    private String getLayoutSharedName() {
        return "universalItemLayout"+parentItem.id;
    }

    private String getImageSharedName() {
        return "universalItemImage"+parentItem.id;
    }
}
