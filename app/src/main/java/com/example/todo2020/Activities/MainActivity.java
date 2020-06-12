package com.example.todo2020.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.todo2020.Fragments.AddEditTodoActivityFragment;
import com.example.todo2020.Fragments.HomeFragmentActivity;
import com.example.todo2020.R;

import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    Fragment fragment;

    public static final String My_PAGER_ID = "todo_id";

    public static Intent newIntent(Context packageContext, int id) {
        Intent intent = new Intent(packageContext, MainActivity.class);
        intent.putExtra(My_PAGER_ID, id);
        return intent;
    }

    protected Fragment createFragment(){
       final int id = getIntent().getIntExtra(My_PAGER_ID,-1);
        return AddEditTodoActivityFragment.newInstance(id);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        fragment = new HomeFragmentActivity();
//        FragmentManager fm = getSupportFragmentManager();
//        FragmentTransaction fragmentTransaction = fm.beginTransaction();
//        fragmentTransaction.replace(R.id.frameLayout, fragment);
//        fragmentTransaction.commit();

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.frameLayout);



        if (fragment == null){

            Fragment myFragment = createFragment();

            fm.beginTransaction()
                    .add(R.id.frameLayout, myFragment)
                    .commit();
        }


    }


    @Override

    protected void onRestoreInstanceState(Bundle savedInstanceState) {

        super.onRestoreInstanceState(savedInstanceState);

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.commit();

//        Fragment fragment = fm.findFragmentById(R.id.frameLayout);
//
//        if (fragment == null){
//
//            Fragment myFragment = createFragment();
//
//            fm.beginTransaction()
//                    .add(R.id.frameLayout, myFragment)
//                    .commit();
//        }
    }

    @Override

    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

    }


}
