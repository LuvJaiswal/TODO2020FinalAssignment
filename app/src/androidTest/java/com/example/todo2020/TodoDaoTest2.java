/*
package com.example.todo2020;

import android.content.Context;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.Observer;
import androidx.room.Room;
import androidx.test.InstrumentationRegistry;
import androidx.test.core.app.ApplicationProvider;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;

public class TodoDaoTest2 {
    @Rule
    public TestRule rule = new InstantTaskExecutorRule();


    private TodoDao todoDao;
    private NoteDatabase db;

    @Mock
    private Observer<List<Note>> observer;

//    @Before
//    public void setUp() throws Exception {
////        MockitoAnnotations.initMocks(this);
//
//        Context context = InstrumentationRegistry.getTargetContext();
////        Context context = ApplicationProvider.getApplicationContext();
//        db = Room.inMemoryDatabaseBuilder(context, NoteDatabase.class)
//                .allowMainThreadQueries().build();
//        todoDao = db.todoDao();
//    }

    @Before
    public void setUp() throws Exception {
        Context context = ApplicationProvider.getApplicationContext();
        db = Room.inMemoryDatabaseBuilder(context, NoteDatabase.class).build();
        todoDao = db.todoDao();
    }


    @After
    public void closeDb() throws IOException {
        db.close();
    }

    @Test
    public void insert() throws Exception {
        // given
        Note note = new Note("abcd", "hello am here", 1);
        todoDao.getAllNotes().observeForever(observer);
        // when
        todoDao.Insert(note);
        // then
        verify(observer).onChanged(Collections.singletonList(note));
    }

    @Test
    public void update() throws Exception{
        // given
        Note note = new Note("abcd", "hello am here", 1);
        todoDao.getAllNotes().observeForever(observer);
        // when
        todoDao.Update(note);
        // then
        verify(observer).onChanged(Collections.singletonList(note));
    }

    @Test
    public void delete() throws Exception{
        // given
        Note note = new Note("abcd", "hello am here", 1);
        todoDao.getAllNotes().observeForever(observer);
        // when
        todoDao.Delete(note);
        // then
        verify(observer).onChanged(Collections.singletonList(note));
    }

    @Test
    public void deleteAllNotes()throws Exception {
        // given
        Note note = new Note("abcd", "hello am here", 1);
        todoDao.getAllNotes().observeForever(observer);
        // when
        todoDao.deleteAllNotes();
        // then
        verify(observer).onChanged(Collections.singletonList(note));
    }

    @Test
    public void getAllNotes() throws Exception{
        // given
        Note note = new Note("abcd", "hello am here", 1);
        todoDao.getAllNotes().observeForever(observer);
        // when
        todoDao.getAllNotes();
        // then
        verify(observer).onChanged(Collections.singletonList(note));
    }
}

 */