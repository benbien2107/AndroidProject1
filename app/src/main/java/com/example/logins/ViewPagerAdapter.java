package com.example.logins;

import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import org.jetbrains.annotations.Nullable;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    public ViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch(position){
            case 0:
                return new UpcomingFragment();
            case 1:
                return new PastFragment();
            case 2:
                return new CancelledFragment();

        }
        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle (int postition){
        String title = "";
        switch (postition) {
            case 0:
                title = "UPCOMING";
                break;
            case 1:
                title = "PAST";
                break;
            case 2:
                title = "CANCELLED";
                break;
        }
        return title;
    }
    @Override
    public void restoreState(final Parcelable state, final ClassLoader loader) {
        try {
            super.restoreState(state, loader);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
