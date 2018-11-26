package com.sarvarcorp.artifactguidedemo.workers;


import android.os.Build;
import android.view.View;

import com.sarvarcorp.artifactguidedemo.R;
import com.sarvarcorp.artifactguidedemo.base.Base;
import com.sarvarcorp.artifactguidedemo.base.BaseFragment;

import java.lang.reflect.InvocationTargetException;

import javax.inject.Inject;
import javax.inject.Singleton;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

@Singleton
public class FragmentWorker extends Base implements FragmentManager.OnBackStackChangedListener {
    private FragmentManager fragmentManager;
    private Class<?> currentFragment;

    public enum AnimationType {
        openGuide,
        closeGuide,
        openGuidesList,
        closeGuideList,
        openFragment
    }

    @Inject
    public FragmentWorker(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
        fragmentManager.addOnBackStackChangedListener(this);
    }

    public void showFragment(Class<?> clazz, boolean addToBackStack, AnimationType animationType) {
        try {
            BaseFragment fragment = getFragment(clazz);
            showFragment(clazz,addToBackStack,fragment, animationType);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException e) {
            e.printStackTrace();
            return;
        }
    }

    public void showFragment(Class<?> clazz, boolean addToBackStack, BaseFragment fragment, AnimationType animationType) {
        showFragment(clazz,addToBackStack, fragment, animationType, fragment.getView());
    }

    public void showFragment(Class<?> clazz, boolean addToBackStack, BaseFragment fragment, AnimationType animationType, View view) {
        FragmentTransaction fragmentTranaction = fragmentManager.beginTransaction();

        // fragmentTranaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        /*if (addToBackStack)
            fragmentTranaction.setCustomAnimations(R.animator.anim_in, R.animator.anim_none);
        else
            fragmentTranaction.setCustomAnimations( R.animator.anim_none, R.animator.anim_out);*/

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && currentFragment!=null) {
            BaseFragment oldFragment = getCurrentFragment();
            fragment.prepareAnimation(fragmentTranaction, oldFragment, view);
            fragment.prepareEnterAnimation(fragmentTranaction, oldFragment, view);
            fragment.prepareExitAnimation(fragmentTranaction, oldFragment, view);
            fragment.prepareReturnAnimation(fragmentTranaction, oldFragment, view);
            fragment.prepareReenterAnimation(fragmentTranaction, oldFragment, view);
            /*fragment.setSharedElementEnterTransition(new DetailsTransition());
            fragment.setEnterTransition(new Fade());
            fragment.setExitTransition(new Fade());
            fragment.setSharedElementReturnTransition(new DetailsTransition());*/
        }

        if (currentFragment==null) {
            fragmentTranaction.add(R.id.mainContainer, fragment);
        } else {
            /*if (currentFragment == clazz) {
                return;
            }*/
            fragmentTranaction.replace(R.id.mainContainer, fragment);
            if (addToBackStack) {
                fragmentTranaction.addToBackStack(clazz.getName());

                /*if (backStack.contains(currentFragment)) {
                    backStack.remove(currentFragment);
                }
                backStack.push(currentFragment);*/
            }
        }
        currentFragment = clazz;
        fragmentTranaction.commit();
        /*Toolbar toolbar = (Toolbar) mainActivity.findViewById(R.id.toolbar);
        toolbar.setTitle(getFragmentName(currentFragment));*/
    }

    public BaseFragment getCurrentFragment() {
        int i = fragmentManager.getFragments().size();
        while (i>0) {
            if (fragmentManager.getFragments().get(i - 1) instanceof BaseFragment) {
                return (BaseFragment) fragmentManager.getFragments().get(i - 1);
            }
            i--;
        }
        return null;
    }

    public BaseFragment getFragment(Class<?> clazz) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        return (BaseFragment) clazz.getDeclaredConstructor().newInstance();
    }

    @Override
    public void onBackStackChanged() {
        if (fragmentManager.getFragments().size()>0) {
            Fragment fragment = getCurrentFragment();
            currentFragment = fragment.getClass();
        } else {
            currentFragment = null;
        }
    }

    public void onActivityDestroy() {
        currentFragment = null;
    }
}
