package com.dev.todos.Fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.List;

public class ChangeFragment {
    Context context;

    public ChangeFragment(Context context){
        this.context=context;

    }

    public void changeFragmentWithoutBackStack(FragmentManager fragmentManager, Bundle bundle, int id, Fragment fragment){
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.replace(id,fragment);
        fragmentTransaction.addToBackStack(null);
        fragment.setArguments(bundle);
        fragmentTransaction.commit();

    }

    public void changeFragmentWithBackStack(FragmentManager fragmentManager, Bundle bundle,int id, Fragment fragment){
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.replace(id,fragment);
        fragmentTransaction.addToBackStack("true");
        fragment.setArguments(bundle);
        fragmentTransaction.commit();
    }

    public void changeFragmentWithBackStack1(FragmentManager fragmentManager, Bundle bundle,int id, Fragment fragment){
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.replace(id,fragment);
        fragmentTransaction.addToBackStack(null);
        fragment.setArguments(bundle);
        fragmentTransaction.commit();
    }
}
