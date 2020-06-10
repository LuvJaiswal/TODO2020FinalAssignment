package com.example.todo2020.Fragments;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProviders;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.todo2020.ViewModel.TodoViewModel;
import com.example.todo2020.MyDatabase.mytodo;
import com.example.todo2020.R;
import com.example.todo2020.MyDatabase.RepositoryTodo;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static android.app.Activity.RESULT_OK;

public class AddEditTodoActivityFragment extends Fragment {

    private static final String TAG = AddEditTodoActivityFragment.class.getSimpleName();


    private LiveData<mytodo> mTodo;


    //task id Extra implemented in the intent
    public static final String EXTRA_ID =
            "com.example.todo2020.EXTRA_ID";

    //task title Extra implemented in the intent
    public static final String EXTRA_TITLE =
            "com.example.todo2020.EXTRA_TITLE";

    //task description Extra implemented in the intent
    public static final String EXTRA_DESCRIPTION =
            "com.example.todo2020.EXTRA_DESCRIPTION";

    //task priority Extra implemented in the intent
    public static final String EXTRA_PRIORITY =
            "com.example.todo2020.EXTRA_PRIORITY";

    public static final String EXTRA_DATE =
            "com.example.todo2020.EXTRA_DATE";

    //for alarmy
    public static final String EXTRA_DATE_TIME =
            "com.example.todo2020.EXTRA_DATE_TIME";

    public static final String INSTANCE_TASK_ID = "instanceTaskId";

    private static final int REQUEST_CODE_SPEECH_INPUT = 1000;

    private static final int REQUEST_CODE_SPEECH_INPUT2 = 9000;

    // Constant for default task id when not in update mode
    private static final int DEFAULT_TASK_ID = -1;

    private int mTaskId = DEFAULT_TASK_ID;

    private TodoViewModel todoViewModel;

    //Fields as Views in the fragment
    private EditText todoTitle, todoDescription;
    private NumberPicker numberPicker;
    private ImageButton mVoicebutton;
    private ImageButton mVoicebuttondes;
    private TextView mTodoDateTimeTextView;

   //for alarm date and timme
    private Calendar mTodoDateTime;



    public static final String My_ID = "todo_id";


    //for view pager  am confused
    public static AddEditTodoActivityFragment newInstance(int id) {
        Bundle args = new Bundle();
        args.putSerializable(My_ID, id);
        AddEditTodoActivityFragment fragmentFirst = new AddEditTodoActivityFragment();
        fragmentFirst.setArguments(args);
        return fragmentFirst;
    }


    public AddEditTodoActivityFragment() {

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.activity_add_todo, null);
        setHasOptionsMenu(true);
        return root;
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        int todoId = getArguments().getInt(My_ID);
        mTodo = RepositoryTodo.getInstance(getActivity()).getNote(todoId);

        if(savedInstanceState != null && savedInstanceState.containsKey(INSTANCE_TASK_ID)) {
            mTaskId = savedInstanceState.getInt(INSTANCE_TASK_ID, DEFAULT_TASK_ID);
        }


        todoViewModel = ViewModelProviders.of(this).get(TodoViewModel.class);

        System.out.println("TAG = " + TAG);
        Log.d(TAG, "Check the log here");

        todoTitle = (EditText) view.findViewById(R.id.todoTitle);
        mVoicebutton = (ImageButton) view.findViewById(R.id.mic);
        todoDescription = (EditText) view.findViewById(R.id.todoDescription);
        mVoicebuttondes = (ImageButton) view.findViewById(R.id.mic2);

        //for alarmy
        mTodoDateTimeTextView = (TextView)view.findViewById(R.id.todo_date_time);

        numberPicker = (NumberPicker) view.findViewById(R.id.np_picker);
        numberPicker.setMinValue(1);
        numberPicker.setMaxValue(5);


        mVoicebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                speak();
            }
        });

        mVoicebuttondes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                speakdescription();
            }
        });


        ((AppCompatActivity)getActivity()).getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);


        Bundle bundle = this.getArguments();

        int REQUEST_CODE = bundle.getInt("REQUEST_CODE");

        if (REQUEST_CODE == 2) {
            getActivity().setTitle("Edit Todo");
            todoTitle.setText(bundle.getString(EXTRA_TITLE, ""));
            todoDescription.setText(bundle.getString(EXTRA_DESCRIPTION, ""));
            numberPicker.setValue(bundle.getInt(EXTRA_PRIORITY, 1));

            //for alarmy
            mTodoDateTimeTextView.setText(bundle.getString(EXTRA_DATE_TIME,""));



        } else {

            getActivity().setTitle("Add Todo");
        }


    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.add_todo_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    /**
     * This is the method for saving the todo items
     * also proper validation for the fields is done
     */

    private void saveTodo() {
        String title = todoTitle.getText().toString();
        String description = todoDescription.getText().toString();
        int priority = numberPicker.getValue();

        String calender = mTodoDateTimeTextView.getText().toString();


//        //for alarmy
//        mTodoDateTimeTextView.setText(mTodoDateTime.getTimeInMillis() == 0 ? "" : DateFormat.is24HourFormat(getActivity()) ? new SimpleDateFormat("MMMM dd, yyyy  h:mm").format(mTodoDateTime.getTime()) : new SimpleDateFormat("MMMM dd, yyyy  h:mm a").format(mTodoDateTime.getTime()));


        Date date = new Date();
        Calendar calendar = Calendar.getInstance();

        if (title.trim().isEmpty()) {
            todoTitle.setError("title required,please enter and save");
            todoTitle.requestFocus();
            return;
        }
        if (description.trim().isEmpty()) {
            todoDescription.setError("Description required,please enter and save");
            todoDescription.requestFocus();
            return;
        }

        Bundle bundle = this.getArguments();

        int REQUEST_CODE = bundle.getInt("REQUEST_CODE", 1);

        if (REQUEST_CODE == 2) {
            int id = bundle.getInt(AddEditTodoActivityFragment.EXTRA_ID, -1);

            if (id == -1) {
                Toast.makeText(getActivity(), "Todo cannot be updated ", Toast.LENGTH_SHORT).show();
                return;

            }

            mytodo mytodo = new mytodo(title, description, priority, calendar, date);
            mytodo.setId(id);
            todoViewModel.update(mytodo);

            Toast.makeText(getActivity(), "Todo Updated", Toast.LENGTH_SHORT).show();

            HomeFragmentActivity homeFragmentActivity = new HomeFragmentActivity();

            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frameLayout, homeFragmentActivity);
            fragmentTransaction.addToBackStack("Home");
            fragmentTransaction.commit();

        } else {

            mytodo mytodo = new mytodo(title, description, priority, calendar, date);
            todoViewModel.insert(mytodo);
            Toast.makeText(getActivity(), "mytodo saved", Toast.LENGTH_SHORT).show();

            HomeFragmentActivity homeFragmentActivity = new HomeFragmentActivity();

            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frameLayout, homeFragmentActivity);
            fragmentTransaction.addToBackStack("Home");
            fragmentTransaction.commit();
        }

    }

    /**
     * Menu item "save" is added in the fragment activity
     *
     * @param item
     * @return
     */

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_todo:
                saveTodo();
                return true;

            case R.id.add_time_date_todo:
                final Calendar currentDateTime = Calendar.getInstance();
                mTodoDateTime = Calendar.getInstance();
                TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
                        mTodoDateTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        mTodoDateTime.set(Calendar.MINUTE, minute);
                        int year, month, dayOfMonth;
                        if (mTodoDateTime.get(Calendar.HOUR_OF_DAY) * 60 + mTodoDateTime.get(Calendar.MINUTE) < currentDateTime.get(Calendar.HOUR_OF_DAY) * 60 + currentDateTime.get(Calendar.MINUTE)) {
                            currentDateTime.add(Calendar.DATE, 1);
                            year = currentDateTime.get(Calendar.YEAR);
                            month = currentDateTime.get(Calendar.MONTH);
                            dayOfMonth = currentDateTime.get(Calendar.DAY_OF_MONTH);
                            currentDateTime.add(Calendar.DATE, -1);
                        } else {
                            year = currentDateTime.get(Calendar.YEAR);
                            month = currentDateTime.get(Calendar.MONTH);
                            dayOfMonth = currentDateTime.get(Calendar.DAY_OF_MONTH);
                        }
                        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                                mTodoDateTime.set(Calendar.YEAR, year);
                                mTodoDateTime.set(Calendar.MONTH, month);
                                mTodoDateTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                              //  mTodoDateTimeTextView.setText(DateFormat.is24HourFormat(getActivity()) ? new SimpleDateFormat("MMMM dd, yyyy  h:mm").format(mTodoDateTime.getTime()) : new SimpleDateFormat("MMMM dd, yyyy  h:mm a").format(mTodoDateTime.getTime()));
                            }
                        }, year, month, dayOfMonth);
                        Calendar minDateTime = Calendar.getInstance();
                        minDateTime.set(year, month, dayOfMonth);
                        datePickerDialog.getDatePicker().setMinDate(minDateTime.getTimeInMillis());
                        datePickerDialog.show();
                    }
                }, currentDateTime.get(Calendar.HOUR_OF_DAY), currentDateTime.get(Calendar.MINUTE), DateFormat.is24HourFormat(getActivity()));
                timePickerDialog.show();
                return true;

            default:


        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Speech for title
     */

    public void speak() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "HI speak something");

        try {
            startActivityForResult(intent, REQUEST_CODE_SPEECH_INPUT);
        } catch (Exception e) {
            Toast.makeText(getActivity(), "" + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Speech for description
     */

    public void speakdescription() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "HI speak something");

        try {
            startActivityForResult(intent, REQUEST_CODE_SPEECH_INPUT2);
        } catch (Exception e) {
            Toast.makeText(getActivity(), "" + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * retrives speech to text and is replaced to their respective fields
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_CODE_SPEECH_INPUT:
                if (resultCode == RESULT_OK && data != null) {
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    todoTitle.setText(result.get(0));
                }
                break;

            case REQUEST_CODE_SPEECH_INPUT2:
                if (resultCode == RESULT_OK && data != null) {
                    ArrayList<String> results = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    todoDescription.setText(results.get(0));
                }
                break;
        }


    }


}