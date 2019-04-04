package com.cavalerie.vlc123.gogogo.Adapters.Tabpager;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.cavalerie.vlc123.gogogo.Fragment.Dashboard_fragment.AccueilFragment;
import com.cavalerie.vlc123.gogogo.Fragment.Dashboard_fragment.MessagerieFragment;
import com.cavalerie.vlc123.gogogo.Fragment.Dashboard_fragment.ReservationFragment;
import com.cavalerie.vlc123.gogogo.Fragment.Dashboard_fragment.VoyageFragment;

public class tabPagerDashboardAdapter extends FragmentStatePagerAdapter {



    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return "";
    }

    public tabPagerDashboardAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                AccueilFragment accueilFragment = new AccueilFragment();
                return accueilFragment;
            case 1:
                VoyageFragment voyageFragment = new VoyageFragment();
                return voyageFragment;
            case 2:
                ReservationFragment reservationFragment = new ReservationFragment();
                return reservationFragment;
            case 3:
                MessagerieFragment messagerieFragment = new MessagerieFragment();
                return messagerieFragment;
        }

        return null;
    }

    @Override
    public int getCount() {
        return 4;
    }
}
