package com.example.todo2020.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.lifecycle.Observer;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.todo2020.Fragments.AddEditTodoActivityFragment;
import com.example.todo2020.MyDatabase.Todo;
import com.example.todo2020.R;
import com.example.todo2020.MyDatabase.RepositoryTodo;
import com.example.todo2020.ViewModel.TodoViewModel;

import java.util.List;

public class ViewPagerActivity extends AppCompatActivity {
    private static final String My_PAGER_ID = "todo_id";

    private ViewPager viewPager;

    private List<Todo> mTodo;


    private static final String TAG = ViewPagerActivity.class.getSimpleName();


    public static Intent newIntent(Context packageContext, int id) {
        Intent intent = new Intent(packageContext, ViewPagerActivity.class);
        intent.putExtra(My_PAGER_ID, id);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager);

        viewPager = (ViewPager) findViewById(R.id.Viewpager);
        final int id = getIntent().getIntExtra(My_PAGER_ID, -1);


        Log.d(TAG, "Values:" + "\n\n" + mTodo);

        FragmentManager fragmentManager = getSupportFragmentManager();

        RepositoryTodo.getInstance(this).getAllNotes().observe(this, new Observer<List<Todo>>() {
            @Override
            public void onChanged(List<Todo> mtodo) {
                for (int i = 0; i < mtodo.size(); i++) {
                   mTodo = mtodo;
                    if (mtodo.get(i).getId() == id) {
                        viewPager.setCurrentItem(i);
                        break;
                    }
                }
            }
        });


        viewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager) {
            @Override
            public int getCount() {
                return mTodo.size();
            }

            @Override
            public Fragment getItem(int i) {
                return AddEditTodoActivityFragment.newInstance(mTodo.get(i).getId());
            }

        });



    }
}
