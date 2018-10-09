package com.vinicius.myhelloworld.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.vinicius.myhelloworld.fragment.AulaFragment;
import com.vinicius.myhelloworld.fragment.ChatFragment;
import com.vinicius.myhelloworld.fragment.ContatoFragment;


public class TabAdapter extends FragmentStatePagerAdapter{

    private String[] tituloAbas = {"AULAS", "CHATS", "AMIGOS"};

    public TabAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        Fragment fragment = null;

        switch (position){
            case 0:
                fragment = new AulaFragment();
                break;
            case 1:
                fragment = new ChatFragment();
                break;
            case 2:
                fragment = new ContatoFragment();
                break;
        }

        return fragment;
    }

    @Override
    public int getCount() {
        return tituloAbas.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tituloAbas[position];
    }
}
