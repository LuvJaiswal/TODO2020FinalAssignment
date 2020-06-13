package com.example.todo2020.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.todo2020.Fragments.AddEditTodoActivityFragment;
import com.example.todo2020.Fragments.HomeFragmentActivity;
import com.example.todo2020.MyDatabase.mytodo;
import com.example.todo2020.PagerAdapter;
import com.example.todo2020.R;
import com.example.todo2020.MyDatabase.RepositoryTodo;

import java.util.List;
import java.util.UUID;

public class ViewPagerActivity extends AppCompatActivity {
    private static final String My_PAGER_ID = "todo_id";


    private static final int DEFAULT_TASK_ID = -1;
    

    private static final String TAG = ViewPagerActivity.class.getSimpleName();

    private int id = DEFAULT_TASK_ID, position = 0;
    private PagerAdapter mEditPagerAdapter;
    private ViewPager viewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager);


        //final int todoId = getIntent().getIntExtra(EXTRA_TODO_ID, -1);

        Intent intent = getIntent();
        if (intent !=null && intent.hasExtra(My_PAGER_ID)){

            if ((id == DEFAULT_TASK_ID)){
                id = intent.getIntExtra(My_PAGER_ID,DEFAULT_TASK_ID);

            }
        }

        mEditPagerAdapter = new PagerAdapter(getSupportFragmentManager());

        viewPager = findViewById(R.id.Viewpager);

        RepositoryTodo repositoryTodo = new RepositoryTodo(getApplication());


        LiveData<List<mytodo>> mTodo = repositoryTodo.getAllNotes();


        mTodo.observe(this, new Observer<List<mytodo>>() {
            @Override
            public void onChanged(List<mytodo> mytodos) {
                for (int i = 0; i < mytodos.size(); i++) {
                    mEditPagerAdapter.addFragment(mytodos.get(i).getId());
                    if (mytodos.get(i).getId() == id) {
                        position = i;
                        Log.d(TAG,"Position" +position+ " has taskID " +id);
                        viewPager.setCurrentItem(position, false);
                    }
                }
            }
        });

        viewPager.setAdapter(mEditPagerAdapter);
    }
}
