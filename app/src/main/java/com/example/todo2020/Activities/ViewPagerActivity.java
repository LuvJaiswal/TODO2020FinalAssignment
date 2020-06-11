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

import com.example.todo2020.EditPagerAdapter;
import com.example.todo2020.Fragments.AddEditTodoActivityFragment;
import com.example.todo2020.Fragments.HomeFragmentActivity;
import com.example.todo2020.MyDatabase.mytodo;
import com.example.todo2020.R;
import com.example.todo2020.MyDatabase.RepositoryTodo;

import java.util.List;
import java.util.UUID;

public class ViewPagerActivity extends AppCompatActivity {
    private static final String EXTRA_TODO_ID = "todo_id";


    private static final int DEFAULT_TASK_ID = -1;

    //  private LiveData<List<mytodo>> mTodo;

    private static final String TAG = ViewPagerActivity.class.getSimpleName();

    private int mTaskId = DEFAULT_TASK_ID, position = 0;
    private EditPagerAdapter mEditPagerAdapter;
    private ViewPager viewPager;

//    public static Intent newIntent(Context packageContext, int todoId) {
//        Intent intent = new Intent(packageContext, ViewPagerActivity.class);
//        intent.putExtra(EXTRA_TODO_ID, todoId);
//        return intent;
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager);


        //final int todoId = getIntent().getIntExtra(EXTRA_TODO_ID, -1);
           Intent intent = getIntent();
           if (intent !=null && intent.hasExtra(EXTRA_TODO_ID)){
               if ((mTaskId == DEFAULT_TASK_ID)){
                   mTaskId = intent.getIntExtra(EXTRA_TODO_ID,DEFAULT_TASK_ID);
               }
           }

        mEditPagerAdapter = new EditPagerAdapter(getSupportFragmentManager());
        viewPager = findViewById(R.id.Viewpager);
        RepositoryTodo repositoryTodo = new RepositoryTodo(getApplication());
        LiveData<List<mytodo>> mTodo = repositoryTodo.getAllNotes();
        mTodo.observe(this, new Observer<List<mytodo>>() {
            @Override
            public void onChanged(List<mytodo> mytodos) {
                for (int i = 0; i < mytodos.size(); i++) {
                    mEditPagerAdapter.addFragment(mytodos.get(i).getId());
                    if (mytodos.get(i).getId() == mTaskId) {
                        position = i;
                        Log.d(TAG,"Position" +position+ " has taskID " +mTaskId);
                        viewPager.setCurrentItem(position, false);
                    }
                }
            }
        });

        viewPager.setAdapter(mEditPagerAdapter);
    }
}
