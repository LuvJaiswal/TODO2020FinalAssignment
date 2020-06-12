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
import com.example.todo2020.R;
import com.example.todo2020.MyDatabase.RepositoryTodo;

import java.util.List;
import java.util.UUID;

public class ViewPagerActivity extends AppCompatActivity {
    private static final String My_PAGER_ID = "todo_id";

    private ViewPager viewPager;

    private LiveData<List<mytodo>> mTodo;


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

        viewPager = findViewById(R.id.Viewpager);
        final int id = getIntent().getIntExtra(My_PAGER_ID, -1);

        mTodo = RepositoryTodo.getInstance(this).getAllNotes();
        Log.d(TAG, "Values:" + "\n\n" + mTodo);

        FragmentManager fragmentManager = getSupportFragmentManager();

        mTodo.observe(this, new Observer<List<mytodo>>() {
            @Override
            public void onChanged(List<mytodo> mtodo) {
                for (int i = 0; i < mtodo.size(); i++) {
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
                return mTodo.getValue().size();
            }

            @Override
            public Fragment getItem(int i) {
                return AddEditTodoActivityFragment.newInstance(mTodo.getValue().get(i).getId());
            }

        });


//        for (int i = 0; i < mTodo.getValue().size(); i++) {
//            if (mTodo.getValue().get(i).getId() == (id)) {
//                viewPager.setCurrentItem(i);
//                break;
//            }
//        }

    }
}
