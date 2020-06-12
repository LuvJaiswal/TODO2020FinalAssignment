package com.example.todo2020.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

import com.example.todo2020.Fragments.HomeFragmentActivity;
import com.example.todo2020.R;

public class ListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.frameLayout);

        if (fragment == null) {
            HomeFragmentActivity todoListFragment = new HomeFragmentActivity();
            fm.beginTransaction()
                    .add(R.id.frameLayout, todoListFragment)
                    .commit();
        }
    }
}

