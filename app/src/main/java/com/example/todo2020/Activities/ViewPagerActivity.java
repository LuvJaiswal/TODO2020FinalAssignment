package com.example.todo2020.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.lifecycle.Observer;

import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.todo2020.Fragments.AddEditTodoActivityFragment;
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

    private int id = DEFAULT_TASK_ID, position = 1;
    private PagerAdapter mEditPagerAdapter;
    private ViewPager viewPager;
    private List<Todo> todos;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager);


        final int todoId = getIntent().getIntExtra("ID", -1);
        Log.d(TAG,"print id" +todoId);


        mEditPagerAdapter = new PagerAdapter(getSupportFragmentManager());

        viewPager = findViewById(R.id.Viewpager);


        FragmentManager fragmentManager = getSupportFragmentManager();
        final FragmentStatePagerAdapter fragmentStatePagerAdapter = new FragmentStatePagerAdapter(fragmentManager) {
            @Override
            public Fragment getItem(int position) {
                Todo todo = todos.get(position);
                Log.d(TAG, "" + todo.getId());
                return AddEditTodoActivityFragment.newInstance(todo.getId());
            }

            @Override
            public int getCount() {
                if (todos == null)
                    return 0;
                return todos.size();
            }
        };

        viewPager.setAdapter(fragmentStatePagerAdapter);


        RepositoryTodo.getInstance(this).getAllNotes().observe(this, new Observer<List<Todo>>() {
            @Override
            public void onChanged(List<Todo> mTodo) {
                Log.d(TAG,"on changed:" +mTodo.size());
                todos = mTodo;
                fragmentStatePagerAdapter.notifyDataSetChanged();
                for (int i = 0; i < todos.size(); i++) {
//                    mEditPagerAdapter.addFragment(mTodo.get(i).getId());

                    if (todos.get(i).getId() == todoId) {
                        position = i;
                        Log.d(TAG, "Position" + position + " has taskID " + todoId);
                        viewPager.setCurrentItem(position);
                        break;
                    }
                }
            }
        });

//        viewPager.setAdapter(mEditPagerAdapter);


    }

}

