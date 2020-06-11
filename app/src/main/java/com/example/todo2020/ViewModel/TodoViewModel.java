package com.example.todo2020.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.todo2020.MyDatabase.RepositoryTodo;
import com.example.todo2020.MyDatabase.mytodo;

import java.util.List;


public class TodoViewModel extends AndroidViewModel {
    private RepositoryTodo repository;
    private LiveData<List<mytodo>> allNotes;


    public TodoViewModel(@NonNull Application application) {
        super(application);
        repository = new RepositoryTodo(application);
        allNotes = repository.getAllNotes();
    }

    public void insert(mytodo mytodo) {
        repository.insert(mytodo);
    }

    public void update(mytodo mytodo) {
        repository.update(mytodo);
    }

    public void delete(mytodo mytodo) {
        repository.delete(mytodo);
    }

    public void deleteAllNotes() {
        repository.deleteAllNotes();
    }

    public LiveData<List<mytodo>> getAllNotes() {
        return allNotes;
    }


//    public LiveData<List<mytodo>> searchQuery(String query) {
//        return allNotes;
//    }


}
