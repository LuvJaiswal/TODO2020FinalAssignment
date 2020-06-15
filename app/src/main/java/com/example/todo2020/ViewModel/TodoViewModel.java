package com.example.todo2020.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.todo2020.MyDatabase.RepositoryTodo;
import com.example.todo2020.MyDatabase.Todo;

import java.util.List;


public class TodoViewModel extends AndroidViewModel {
    private RepositoryTodo repository;
    private LiveData<List<Todo>> allNotes;


    public TodoViewModel(@NonNull Application application) {
        super(application);
        repository = new RepositoryTodo(application);
        allNotes = repository.getAllNotes();
    }

    public void insert(Todo Todo) {
        repository.insert(Todo);
    }

    public void update(Todo Todo) {
        repository.update(Todo);
    }

    public void delete(Todo Todo) {
        repository.delete(Todo);
    }

    public void deleteAllNotes() {
        repository.deleteAllNotes();
    }

    public LiveData<List<Todo>> getAllNotes() {
        return allNotes;
    }


//    public LiveData<List<Todo>> searchQuery(String query) {
//        return allNotes;
//    }


}
