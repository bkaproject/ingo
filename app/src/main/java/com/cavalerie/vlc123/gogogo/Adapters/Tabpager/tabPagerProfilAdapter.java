package com.cavalerie.vlc123.gogogo.Adapters.Tabpager;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.cavalerie.vlc123.gogogo.Fragment.Profil_fragment.detailFragment;
import com.cavalerie.vlc123.gogogo.Fragment.Profil_fragment.paymentFragment;

public class tabPagerProfilAdapter extends FragmentStatePagerAdapter {

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
         switch (position) {
             case 0:
                 return "Details";

             case 1:
                 return "Mode de payement";
         }

         return null;
    }

    public tabPagerProfilAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                detailFragment DetailFragment = new detailFragment();
                return DetailFragment;

            case 1:
                paymentFragment PaymentFragment = new paymentFragment();
                return PaymentFragment;
        }

        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }
}
