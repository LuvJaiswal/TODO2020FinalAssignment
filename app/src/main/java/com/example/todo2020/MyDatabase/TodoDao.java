package com.example.todo2020.MyDatabase;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.todo2020.MyDatabase.mytodo;

import java.util.List;

@Dao
public interface TodoDao {

//Methods for operations implemented in the Dao

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void Insert(mytodo mytodo);

    @Update
    void Update(mytodo mytodo);

    @Delete
    void Delete(mytodo mytodo);

    //to delete all notes
    @Query("DELETE FROM myTodoList")
    void deleteAllNotes();


    @Query("SELECT * FROM myTodoList ORDER BY priority DESC")
    LiveData<List<mytodo>> getAllNotes();

    //will be notified immediately idf changes made
    @Query("Select * from myTodoList where id =:taskId")
    LiveData<mytodo> loadTaskById(int taskId);


    @Query("Select * from myTodoList where id =:taskId")
    mytodo noteTask(int taskId);

//    @Query("SELECT * FROM myTodoList WHERE title LIKE :searchquery")
//    public LiveData<List<mytodo>> searchFor(String searchquery);


}
