package com.example.todo2020.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.todo2020.MyDatabase.Todo;
import com.example.todo2020.PagerAdapter;
import com.example.todo2020.R;
import com.example.todo2020.MyDatabase.RepositoryTodo;

import java.util.ArrayList;
import java.util.List;

import static android.os.Build.ID;

public class ViewPagerActivity extends AppCompatActivity {
    private static final String My_PAGER_ID = "todo_id";


    private static final int DEFAULT_TASK_ID = -1;
    

    private static final String TAG = ViewPagerActivity.class.getSimpleName();

    private int id = DEFAULT_TASK_ID, position = 0;
    private PagerAdapter mEditPagerAdapter;
    private ViewPager viewPager;
    private List<Todo> mTodo = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager);


        //final int todoId = getIntent().getIntExtra(EXTRA_TODO_ID, -1);

        Intent intent = getIntent();
        if (intent !=null && intent.hasExtra(ID)){

            if ((id == DEFAULT_TASK_ID)){
                id = intent.getIntExtra(ID,DEFAULT_TASK_ID);

            }
        }

        mEditPagerAdapter = new PagerAdapter(getSupportFragmentManager());

        viewPager = findViewById(R.id.Viewpager);




        RepositoryTodo.getInstance(this).getAllNotes().observe(this, new Observer<List<Todo>>() {
            @Override
            public void onChanged(List<Todo> mTodo) {
                for (int i = 0; i < mTodo.size(); i++) {
                    mEditPagerAdapter.addFragment(mTodo.get(i).getId());
                    if (mTodo.get(i).getId() == id) {
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
