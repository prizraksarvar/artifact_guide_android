package com.sarvarcorp.artifact.fragments;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.transition.ChangeBounds;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sarvarcorp.artifact.App;
import com.sarvarcorp.artifact.R;
import com.sarvarcorp.artifact.base.BaseAppCompatActivity;
import com.sarvarcorp.artifact.base.BaseFragment;
import com.sarvarcorp.artifact.design.animation.ButtonToFragmentTransition;
import com.sarvarcorp.artifact.entities.Guide;
import com.sarvarcorp.artifact.entities.GuideType;
import com.sarvarcorp.artifact.listadapter.GuidesRecyclerViewAdapter;
import com.sarvarcorp.artifact.models.GuidesViewModel;
import com.sarvarcorp.artifact.workers.FragmentWorker;

import java.lang.reflect.InvocationTargetException;

/**
 * A simple {@link Fragment} subclass.
 */
public class GuidesFragment extends BaseFragment implements GuidesRecyclerViewAdapter.GuidesListListener {
    private GuideType guideType;
    private GuidesViewModel viewModel;
    private RecyclerView mGuidesListView;
    private GuidesRecyclerViewAdapter mRecyclerViewAdepter;
    private TextView title;
    private ConstraintLayout layout;

    public GuideType getGuideType() {
        return guideType;
    }

    public void setGuideType(GuideType guideType) {
        this.guideType = guideType;
    }

    public GuidesFragment() {

    }

    @Override
    protected int getLayoutID() {
        return R.layout.fragment_guides;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = ViewModelProviders.of(this).get(GuidesViewModel.class);
        viewModel.init(guideType.id);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        initRecyclerView(view);
        layout = view.findViewById(R.id.guideConstrainLayout);
        title = view.findViewById(R.id.guideTypeTitleTextView);
        title.setText(guideType.name);

        ViewCompat.setTransitionName(title,getTitleSharedName());
        ViewCompat.setTransitionName(layout, getLayoutSharedName());
        return view;
    }

    public void initRecyclerView(View view) {
        mGuidesListView = view.findViewById(R.id.guideRecyclerView);
        mRecyclerViewAdepter = new GuidesRecyclerViewAdapter(this,(BaseAppCompatActivity) this.getActivity());
        mGuidesListView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        //mGuidesListView.setLayoutManager(new GridLayoutManager(this.getContext(),2));
        mGuidesListView.setAdapter(mRecyclerViewAdepter);
        mRecyclerViewAdepter.setValues(viewModel.getList());
    }

    @Override
    public void onClickListItem(View view, Guide guide) {
        if (guide!=null && guide.id>0) {

            try {
                GuideFragment fragment = (GuideFragment) App.getComponent().provideFragmentWorker().getFragment(GuideFragment.class);
                fragment.setGuide(guide);
                App.getComponent().provideFragmentWorker().showFragment(GuideFragment.class,true, fragment, FragmentWorker.AnimationType.openGuide, view);
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | java.lang.InstantiationException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void prepareAnimation(FragmentTransaction fragmentTranaction, BaseFragment oldFragment, View view) {

    }

    @Override
    public void prepareEnterAnimation(FragmentTransaction fragmentTranaction, BaseFragment oldFragment, View view) {
        ConstraintLayout layout = view.findViewById(R.id.guideTypeButton);
        TextView title = view.findViewById(R.id.guideTypeNameTextView);
        fragmentTranaction.addSharedElement(title,getTitleSharedName());
        fragmentTranaction.addSharedElement(layout,getLayoutSharedName());
        setSharedElementEnterTransition(new ButtonToFragmentTransition());
        setEnterTransition(new ChangeBounds());
        setExitTransition(new ChangeBounds());
        setSharedElementReturnTransition(new ButtonToFragmentTransition());
    }

    private String getTitleSharedName() {
        return "quideTypeName"+guideType.id;
    }

    private String getLayoutSharedName() {
        return "quideTypeLayout"+guideType.id;
    }
}
