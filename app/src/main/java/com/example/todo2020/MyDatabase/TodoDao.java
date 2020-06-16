package com.example.todo2020.MyDatabase;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface TodoDao {

//Methods for operations implemented in the Dao

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void Insert(Todo Todo);

    @Update
    void Update(Todo Todo);

    @Delete
    void Delete(Todo Todo);

    //to delete all notes
    @Query("DELETE FROM myTodoList")
    void deleteAllNotes();


    @Query("SELECT * FROM myTodoList ORDER BY priority DESC")
    LiveData<List<Todo>> getAllNotes();

    //will be notified immediately idf changes made
    @Query("Select * from myTodoList where id =:taskId")
    LiveData<Todo> loadTaskById(int taskId);


    @Query("Select * from myTodoList where id =:taskId")
    Todo noteTask(int taskId);

//    @Query("SELECT * FROM myTodoList WHERE title LIKE :searchquery")
//    public LiveData<List<Todo>> searchFor(String searchquery);


}
