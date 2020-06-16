package com.example.todo2020;

import android.content.Context;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;

import com.example.todo2020.MyDatabase.Todo;
import com.example.todo2020.MyDatabase.TodoDao;
import com.example.todo2020.MyDatabase.TodoDatabase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.Date;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;

public class todoDatabaseTest {
    private TodoDatabase db;
    private TodoDao todoDao;

    @Before
    public void createDB(){
        Context context = ApplicationProvider.getApplicationContext();
        db = Room.inMemoryDatabaseBuilder(context, TodoDatabase.class)
                .allowMainThreadQueries()
                .build();
        todoDao = db.todoDao();
    }


    @After
    public void closeDb() throws IOException {
        db.close();
    }

    @Test
    public void WriteUserAndReadInList() throws Exception {
        Todo Todo = new Todo(1,"hello", "hello am here", 1, new Date());
        todoDao.Insert(Todo);
        Todo byTodoId = todoDao.noteTask(1);
        assertEquals(byTodoId.getId(),1);
    }


}