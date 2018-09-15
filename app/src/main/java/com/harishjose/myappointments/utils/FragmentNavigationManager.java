package com.harishjose.myappointments.utils;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;


/**
 * Created by harish.jose on 14-09-18.
 */
public class FragmentNavigationManager {
    /**
     * Loads the specified fragment by replacing the current fragment.
     * Ensures that the fragment with the same tag will be removed first
     *
     * @param fragment            the fragment to load
     * @param fragmentManager     the fragment manager
     * @param containerViewId     the fragment container to which the fragment to be added
     * @param fragmentTag         the fragment tag
     * @param isBackStackRequired sets whether the fragment transaction to be added to the back stack
     * @return true - if the process executed with out error
     */
    public static boolean replaceFragment(Context context, Fragment fragment, FragmentManager fragmentManager,
                                          int containerViewId, String fragmentTag, boolean isBackStackRequired) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out,android.R.animator.fade_in, android.R.animator.fade_out);
        fragmentTransaction.replace(containerViewId, fragment, fragmentTag);
        if (isBackStackRequired) {
            fragmentTransaction.addToBackStack(fragmentTag);
        }
        fragmentTransaction.commit();
        return true;
    }


    /**
     * Adds the specified fragment with out replacing the current fragment.
     * Ensures that the fragment with the same tag will be removed first
     *
     * @param fragment            the fragment to load
     * @param fragmentManager     the fragment manager
     * @param containerViewId     the fragment container to which the fragment to be added
     * @param fragmentTag         the fragment tag
     * @param isBackStackRequired sets whether the fragment transaction to be added to the back stack
     * @return true - if the process executed with out error
     */
    public static boolean addFragment(Context context, Fragment fragment, FragmentManager fragmentManager,
                                      int containerViewId, String fragmentTag, boolean isBackStackRequired) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out,android.R.animator.fade_in, android.R.animator.fade_out);
        if (isBackStackRequired) {
            fragmentTransaction.addToBackStack(fragmentTag);
        }
        fragmentTransaction.add(containerViewId, fragment, fragmentTag);
        fragmentTransaction.commit();
        return true;
    }

    /**
     * Function which returns fragment by tag
     * @param fragmentManager
     * @param tag
     * @return
     */
    public static Fragment findfragmentByTag(FragmentManager fragmentManager, String tag) {
        Fragment fragment = fragmentManager.findFragmentByTag(tag);
        if(fragment != null) {
            return fragment;
        }
        return null;
    }


}
