package com.example.todo2020.Fragments;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.todo2020.ViewModel.TodoViewModel;
import com.example.todo2020.MyDatabase.mytodo;
import com.example.todo2020.R;
import com.example.todo2020.MyDatabase.TodoAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static android.app.Activity.RESULT_OK;

public class HomeFragmentActivity extends Fragment {

    private static final String TAG = HomeFragmentActivity.class.getSimpleName();

    public static final int ADD_TODO_REQUEST = 1;

    public static final int EDIT_TODO_REQUEST = 2;  //for edit

    private TodoViewModel todoViewModel;

    public ArrayList<mytodo> mytodos;




    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.activity_home, null);
        setHasOptionsMenu(true);
        return root;
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FloatingActionButton floatingActionButton = view.findViewById(R.id.floatingActionButton);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddEditTodoActivityFragment addEditTodoActivityFragment = new AddEditTodoActivityFragment();


                Bundle bundle = new Bundle();
                bundle.putInt("REQUEST_CODE", ADD_TODO_REQUEST);

                addEditTodoActivityFragment.setArguments(bundle);

                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frameLayout, addEditTodoActivityFragment);
                fragmentTransaction.addToBackStack("Home");
                fragmentTransaction.commit();


            }
        });


        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);


        final TodoAdapter adapter = new TodoAdapter();

        recyclerView.setAdapter(adapter);

        todoViewModel = ViewModelProviders.of(this).get(TodoViewModel.class);
        todoViewModel.getAllNotes().observe(this, new Observer<List<mytodo>>() {
            @Override
            public void onChanged(List<mytodo> mytodos) {
                //updates Recyclerview
                Log.d(TAG, "retrieving data from database");
                adapter.notifyDataSetChanged();
                adapter.setTodo(mytodos);

            }
        });


        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                final mytodo tempMytodo = adapter.getNoteAt(viewHolder.getAdapterPosition());

                todoViewModel.delete(tempMytodo);

                View contextView = getView().findViewById(R.id.recyclerView);



                /*
                Snackbar with undo functionality added for delete todo
                 */

                Snackbar.make(contextView, "Todo deleted", Snackbar.LENGTH_LONG).setAction("Undo", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        todoViewModel.insert(tempMytodo);
                    }
                }).show();

            }
        }).attachToRecyclerView(recyclerView);


        recyclerView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Toast.makeText(getActivity(), "hi", Toast.LENGTH_SHORT).show();
                return false;
            }
            // Start ActionMode after long-click.
        });

        adapter.setOnItemClickListener(new TodoAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(mytodo mytodo) {
                AddEditTodoActivityFragment addEditTodoActivityFragment = new AddEditTodoActivityFragment();

                Bundle bundle = new Bundle();
                bundle.putInt(AddEditTodoActivityFragment.EXTRA_ID, mytodo.getId());
                bundle.putString(AddEditTodoActivityFragment.EXTRA_TITLE, mytodo.getTitle());
                bundle.putString(AddEditTodoActivityFragment.EXTRA_DESCRIPTION, mytodo.getDescription());
                bundle.putInt(AddEditTodoActivityFragment.EXTRA_PRIORITY, mytodo.getPriority());
                bundle.putInt("REQUEST_CODE", EDIT_TODO_REQUEST);

                addEditTodoActivityFragment.setArguments(bundle);

                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frameLayout, addEditTodoActivityFragment);
                fragmentTransaction.addToBackStack("Todo List");
                fragmentTransaction.commit();


            }


        });


    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);

        final MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

           TodoAdapter adapter;

            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                newText = newText.toLowerCase();
                ArrayList<mytodo> newList = new ArrayList<>();
                for(mytodo mytodo: newList){
                    String titlename = mytodo.getTitle().toLowerCase();
                    if (titlename.contains(newText)){
                        mytodos.add(mytodo);
                        Log.d(TAG,"watch out");
                    }
                }
                adapter.setFilter(newList);
                return true;
            }
        });

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_TODO_REQUEST && resultCode == RESULT_OK) {
            String title = data.getStringExtra(AddEditTodoActivityFragment.EXTRA_TITLE);
            String description = data.getStringExtra(AddEditTodoActivityFragment.EXTRA_DESCRIPTION);
            int priority = data.getIntExtra(AddEditTodoActivityFragment.EXTRA_PRIORITY, 1);
            Date date = new Date();

            mytodo mytodo = new mytodo(title, description, priority, date);
            todoViewModel.insert(mytodo);
            Toast.makeText(getActivity(), "Todo saved", Toast.LENGTH_SHORT).show();
        } else if (requestCode == EDIT_TODO_REQUEST && resultCode == RESULT_OK) {
            int id = data.getIntExtra(AddEditTodoActivityFragment.EXTRA_ID, -1);
            if (id == -1) {
                Toast.makeText(getActivity(), "Todo cannot be updated ", Toast.LENGTH_SHORT).show();
                return;

            }
            String title = data.getStringExtra(AddEditTodoActivityFragment.EXTRA_TITLE);
            String description = data.getStringExtra(AddEditTodoActivityFragment.EXTRA_DESCRIPTION);
            int priority = data.getIntExtra(AddEditTodoActivityFragment.EXTRA_PRIORITY, 1);
            Date date = new Date();


            mytodo mytodo = new mytodo(title, description, priority, date);
            mytodo.setId(id);
            todoViewModel.update(mytodo);

            Toast.makeText(getActivity(), "Todo Updated", Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(getActivity(), "Todo not saved", Toast.LENGTH_SHORT).show();
        }


    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete_All_notes:

                /*
                Delete all Notes Alert Dialogue box
                 */
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Are you sure?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        todoViewModel.deleteAllNotes();
                        Toast.makeText(getActivity(), "All Todo notes deleted", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

                AlertDialog ad = builder.create();
                ad.show();
                return true;


                /*
                     sendfeedback implicit intent
                 */

            case R.id.sendfeedback:
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setData(Uri.parse("email"));
                String[] s = {"jaiswallove20@gmail.com"};
                i.putExtra(Intent.EXTRA_EMAIL, s);
                i.putExtra(Intent.EXTRA_SUBJECT, "LoveTODO : 'Write your Subject'");
                i.putExtra(Intent.EXTRA_TEXT, "This is an email body for your feedback");
                i.setType("message/rfc822");
                Intent chooser = Intent.createChooser(i, "Launch Email for Todo Feedback");
                startActivity(chooser);
                return true;

            case R.id.Logoutmenu:
                getActivity().finish();
                return true;

//            case R.id.OpenMyCalandar:
//                long startMillis = System.currentTimeMillis();
//                Uri.Builder builder1 = CalendarContract.CONTENT_URI.buildUpon();
//                builder1.appendPath("time");
//                ContentUris.appendId(builder1, startMillis);
//                Intent intent = new Intent(Intent.ACTION_VIEW).setData(builder1.build());
//                startActivity(intent);
//                return true;


            default:
                return super.onOptionsItemSelected(item);

        }

    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().setTitle("MINE TODO-LIST");
    }


    /*
       Activity lifecycle implemented
             */


    @Override
    public void onStart() {
        super.onStart();
        System.out.println("TAG = " + TAG);
        Log.d(TAG, "Started");

        TodoViewModel todoViewModel =
                ViewModelProviders.of(getActivity()).get(TodoViewModel.class);
    }

    @Override
    public void onPause() {
        super.onPause();
        System.out.println("TAG = " + TAG);
        Log.d(TAG, "Paused");
    }


    @Override
    public void onStop() {
        super.onStop();
        System.out.println("TAG = " + TAG);
        Log.d(TAG, "Stopped");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        System.out.println("TAG = " + TAG);
        Log.d(TAG, "Destroyed");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        System.out.println("TAG = " + TAG);
        Log.d(TAG, "Detached");
    }


}
