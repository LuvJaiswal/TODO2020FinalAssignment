package com.example.todo2020;

import android.content.Context;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.Observer;
import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.mockito.Mock;

import java.io.IOException;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.validateMockitoUsage;
import static org.mockito.Mockito.verify;

public class NoteDatabaseTest {
    private NoteDatabase db;
    private TodoDao todoDao;

    @Before
    public void createDB(){
        Context context = ApplicationProvider.getApplicationContext();
        db = Room.inMemoryDatabaseBuilder(context, NoteDatabase.class)
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
        Note note = new Note(1,"hello", "hello am here", 1, new Date());
        todoDao.Insert(note);
        Note byNoteId = todoDao.noteTask(1);
        assertEquals(byNoteId.getId(),1);
    }


}