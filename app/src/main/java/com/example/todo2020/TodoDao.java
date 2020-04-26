package com.example.todo2020;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface TodoDao {

    @Insert
    void Insert(Note note);

    @Update
    void Update(Note note);

    @Delete
    void Delete(Note note);

    @Query("DELETE FROM myTodoList")
    void deleteAllNotes();


    @Query("SELECT * FROM myTodoList ORDER BY priority DESC")
    LiveData<List<Note>>getAllNotes();

    //will be notified immediately idf changes made
}
