package com.sarvarcorp.artifactguidedemo.fragments;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sarvarcorp.artifactguidedemo.App;
import com.sarvarcorp.artifactguidedemo.R;
import com.sarvarcorp.artifactguidedemo.base.BaseAppCompatActivity;
import com.sarvarcorp.artifactguidedemo.base.BaseFragment;
import com.sarvarcorp.artifactguidedemo.entities.GuideType;
import com.sarvarcorp.artifactguidedemo.listadapter.GuideTypesRecyclerViewAdapter;
import com.sarvarcorp.artifactguidedemo.models.GuideTypesViewModel;
import com.sarvarcorp.artifactguidedemo.workers.FragmentWorker;

import java.lang.reflect.InvocationTargetException;


public class GuideTypesFragment extends BaseFragment implements GuideTypesRecyclerViewAdapter.GuideTypesListListener {
    private GuideTypesViewModel viewModel;
    RecyclerView mGuideTypesListView;
    GuideTypesRecyclerViewAdapter mRecyclerViewAdepter;


    public GuideTypesFragment() {

    }

    @Override
    protected int getLayoutID() {
        return R.layout.fragment_guide_types;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = ViewModelProviders.of(this).get(GuideTypesViewModel.class);
        viewModel.init();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        initRecyclerView(view);
        return view;
    }

    public void initRecyclerView(View view) {
        mGuideTypesListView = view.findViewById(R.id.guideTypesRecyclerView);
        mRecyclerViewAdepter = new GuideTypesRecyclerViewAdapter(this,(BaseAppCompatActivity) this.getActivity());
        mGuideTypesListView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        //mItemsListView.setLayoutManager(new GridLayoutManager(this.getContext(),2));
        mGuideTypesListView.setAdapter(mRecyclerViewAdepter);
        mRecyclerViewAdepter.setValues(viewModel.getList());
    }

    @Override
    public void onClickListItem(View view, GuideType guideType) {
        if (guideType!=null && guideType.id>0) {

            try {
                GuidesFragment fragment = (GuidesFragment) App.getComponent().provideFragmentWorker().getFragment(GuidesFragment.class);
                fragment.setGuideType(guideType);
                App.getComponent().provideFragmentWorker().showFragment(GuidesFragment.class,true, fragment, FragmentWorker.AnimationType.openGuidesList, view);
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | java.lang.InstantiationException e) {
                e.printStackTrace();
            }
        }
    }
}
