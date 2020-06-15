package com.example.todo2020;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.todo2020.Fragments.AddEditTodoActivityFragment;

import java.util.ArrayList;
import java.util.List;

public class PagerAdapter extends FragmentPagerAdapter {

    private List<Integer> mTodo = new ArrayList<>();

    public PagerAdapter(@NonNull FragmentManager fm){
        super(fm);
    }

    public void addFragment(int title){
        mTodo.add(title);
        notifyDataSetChanged();
    }

    @Override
    public Fragment getItem(int position) {
        return AddEditTodoActivityFragment.newInstance(mTodo.get(position));
    }

    @Override
    public int getCount() {
        return mTodo.size();
    }
}

