package com.example.techchat.ui.main;

import android.annotation.SuppressLint;
import android.content.Context;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.techchat.R;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.tab_text_1, R.string.tab_text_2};
    private final Context mContext;
    static Fragment one,two;
    @SuppressLint("WrongConstant")
    public SectionsPagerAdapter(Context context, FragmentManager fm) {
        super(fm);

        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        switch (position) {
            case 0:
                one=new FriendList();
                return one;
            case 1:
                return new SuggestionList();
        }
        return null;
    }
        @Nullable
        @Override
        public CharSequence getPageTitle ( int position){
            return mContext.getResources().getString(TAB_TITLES[position]);
        }

        @Override
        public int getCount () {
            // Show 2 total pages.
            return 2;
        }
    }
