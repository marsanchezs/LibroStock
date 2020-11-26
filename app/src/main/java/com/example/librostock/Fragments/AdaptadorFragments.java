package com.example.librostock.Fragments;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class AdaptadorFragments extends FragmentPagerAdapter {

    int numTabs;

    public AdaptadorFragments(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
        this.numTabs = behavior;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch(position){
            case 0:
                return new frListaLibros();
            case 1:
                return new frAgregarLibro();
            case 2:
                return new frAgregarAutor();
            default:
                return null;

        }
    }

    @Override
    public int getCount() {
        return numTabs;
    }
}
