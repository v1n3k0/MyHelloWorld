package com.vinicius.myhelloworld.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.vinicius.myhelloworld.fragment.AprendeFragment;
import com.vinicius.myhelloworld.fragment.ContatoFragment;
import com.vinicius.myhelloworld.fragment.PerfilFragment;


public class TabAdapter extends FragmentStatePagerAdapter{

    private String[] tituloAbas = {"APRENDER", "CONTATOS", "PERFIL"};

    public TabAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        Fragment fragment = null;

        switch (position){
            case 0:
                fragment = new AprendeFragment();
                break;
            case 1:
                fragment = new ContatoFragment();
                break;
            case 2:
                fragment = new PerfilFragment();
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
