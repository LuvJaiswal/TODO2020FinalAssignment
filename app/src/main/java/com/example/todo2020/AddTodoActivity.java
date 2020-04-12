package com.example.todo2020;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;

public class AddTodoActivity extends AppCompatActivity {

    public static final String EXTRA_TITLE=
            "com.example.todo2020.EXTRA_TITLE";

    public static final String EXTRA_DESCRIPTION=
            "com.example.todo2020.EXTRA_DESCRIPTION";

    public static final String EXTRA_PRIORITY=
            "com.example.todo2020.EXTRA_PRIORITY";

    private EditText todoTitle, todoDescription;
    private NumberPicker numberPicker;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_todo);

        todoTitle = (EditText) findViewById(R.id.todoTitle);
        todoDescription = (EditText) findViewById(R.id.todoDescription);

        numberPicker = (NumberPicker) findViewById(R.id.np_Picker);
        numberPicker.setMinValue(1);
        numberPicker.setMaxValue(15);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);
        setTitle("Add Todo");

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_todo_menu, menu);
        return true;
    }

    private void saveTodo() {
        String title = todoTitle.getText().toString();
        String description = todoDescription.getText().toString();
        int priority = numberPicker.getValue();

        if(title.trim().isEmpty() || description.trim().isEmpty()){
            Toast.makeText(this, "Please insert a title and description", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent data = new Intent();
        data.putExtra(EXTRA_TITLE,title);
        data.putExtra(EXTRA_DESCRIPTION,description);
        data.putExtra(EXTRA_PRIORITY,priority);

        setResult(RESULT_OK,data);
        finish();

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_todo:
                saveTodo();
                return true;
            default:


        }
        return super.onOptionsItemSelected(item);
    }


}
