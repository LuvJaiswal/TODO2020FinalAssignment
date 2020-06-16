package com.example.todo2020.MyDatabase;

import android.app.Application;
import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class RepositoryTodo {
    private TodoDao todoDao;
    private LiveData<List<Todo>> allNotes;

    private static Context mContext;
    private static RepositoryTodo todoInstance;


    public RepositoryTodo(Application application) {
        TodoDatabase database = TodoDatabase.getInstance(application);
        todoDao = database.todoDao();   //here todoDao is the abstract method created in the TodoDatabase
        allNotes = todoDao.getAllNotes();

    }

    public void insert(Todo Todo) {
        new InsertNoteAsyncTask(todoDao).execute(Todo);

    }

    public void update(Todo Todo) {
        new UpdateNoteAsyncTask(todoDao).execute(Todo);

    }

    public void delete(Todo Todo) {
        new DeleteNoteAsyncTask(todoDao).execute(Todo);

    }

    public void deleteAllNotes() {
        new DeleteAllNoteAsyncTask(todoDao).execute();

    }

    public LiveData<List<Todo>> getAllNotes() {
        return allNotes;

    }

    public LiveData<Todo> getNote(int taskId) {
        return todoDao.loadTaskById(taskId);
    }


    //prevent the memory leak for insert
    private static class InsertNoteAsyncTask extends AsyncTask<Todo, Void, Void> {
        private TodoDao todoDao;

        private InsertNoteAsyncTask(TodoDao todoDao) {
            this.todoDao = todoDao;
        }

        @Override
        protected Void doInBackground(Todo... Todos) {
            todoDao.Insert(Todos[0]);
            return null;
        }
    }

    //prevent the memory leak for update
    private static class UpdateNoteAsyncTask extends AsyncTask<Todo, Void, Void> {
        private TodoDao todoDao;

        private UpdateNoteAsyncTask(TodoDao todoDao) {
            this.todoDao = todoDao;
        }

        @Override
        protected Void doInBackground(Todo... Todos) {
            todoDao.Update(Todos[0]);
            return null;
        }
    }

    //prevent the memory leak for delete Todo
    private static class DeleteNoteAsyncTask extends AsyncTask<Todo, Void, Void> {
        private TodoDao todoDao;

        private DeleteNoteAsyncTask(TodoDao todoDao) {
            this.todoDao = todoDao;
        }

        @Override
        protected Void doInBackground(Todo... Todos) {
            todoDao.Delete(Todos[0]);
            return null;
        }
    }

    //prevent the memory leak for delete all note
    private static class DeleteAllNoteAsyncTask extends AsyncTask<Void, Void, Void> {
        private TodoDao todoDao;

        private DeleteAllNoteAsyncTask(TodoDao todoDao) {
            this.todoDao = todoDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            todoDao.deleteAllNotes();
            return null;
        }
    }

    public static RepositoryTodo getInstance(Context context) {
        mContext = context.getApplicationContext();
        if (todoInstance == null) {
            todoInstance = new RepositoryTodo((Application) mContext);
        }
        return todoInstance;
    }

}
