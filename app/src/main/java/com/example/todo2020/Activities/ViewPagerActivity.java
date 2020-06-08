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

import com.example.todo2020.Fragments.AddEditTodoActivityFragment;
import com.example.todo2020.MyDatabase.mytodo;
import com.example.todo2020.R;
import com.example.todo2020.MyDatabase.RepositoryTodo;

import java.util.List;
import java.util.UUID;

public class ViewPagerActivity extends AppCompatActivity {
    private static final String EXTRA_TODO_ID = "todo_id";

     private ViewPager viewPager;

     private LiveData<List<mytodo>> mTodo;



    public static Intent newIntent(Context packageContext, UUID todoId) {
        Intent intent = new Intent(packageContext, ViewPagerActivity.class);
        intent.putExtra(EXTRA_TODO_ID, todoId);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager);

        viewPager = findViewById(R.id.Viewpager);
        final int todoId = getIntent().getIntExtra(EXTRA_TODO_ID, -1);

        mTodo = RepositoryTodo.getInstance(this).getAllNotes();

        mTodo.observe(this, new Observer<List<mytodo>>() {
            @Override
            public void onChanged(List<mytodo> mytodos) {
                for (int i = 0; i < mytodos.size(); i++) {
                    if (mytodos.get(i).getId() == todoId ) {
                        viewPager.setCurrentItem(i);
                        break;
                    }
                }
            }
        });

        FragmentManager fragmentManager = getSupportFragmentManager();

        viewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager) {
            @Override
            public int getCount() {
                return mTodo.getValue().size();
               // return 0;
            }

            @Override
            public Fragment getItem(int i) {
               return AddEditTodoActivityFragment.newInstance(mTodo.getValue().get(i).getId());
//                int todoid = mTodo.getValue().get(i).getId();
//                return AddEditTodoActivityFragment.newInstance(todoid);





//                mytodo todo = mTodo.get(i);
//                Log.d("TODOPAGER", "" + todo.getId());
//                return AddEditTodoActivityFragment.newInstance(todo.getId());

            }

        });



    }
}
