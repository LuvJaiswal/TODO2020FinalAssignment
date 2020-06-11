package com.example.todo2020;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.todo2020.Fragments.AddEditTodoActivityFragment;

import java.util.ArrayList;
import java.util.List;

public class EditPagerAdapter extends FragmentPagerAdapter {
    private final List<Integer> mFragmentId = new ArrayList<>();



    public EditPagerAdapter(@NonNull FragmentManager fm){
        super(fm);
    }

    public void addFragment(int title){
        mFragmentId.add(title);
        notifyDataSetChanged();
    }

    @Override
    public Fragment getItem(int position) {
        return AddEditTodoActivityFragment.newInstance(mFragmentId.get(position));
    }

    @Override
    public int getCount() {
        return mFragmentId.size();
    }
}
