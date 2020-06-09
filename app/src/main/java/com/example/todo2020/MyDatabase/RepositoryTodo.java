package com.example.todo2020.MyDatabase;

import android.app.Application;
import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class RepositoryTodo {
    private TodoDao todoDao;
    private LiveData<List<mytodo>> allNotes;

    private static Context mContext;
    private static RepositoryTodo todoInstance;


    public RepositoryTodo(Application application) {
        TodoDatabase database = TodoDatabase.getInstance(application);
        todoDao = database.todoDao();   //here todoDao is the abstract method created in the TodoDatabase
        allNotes = todoDao.getAllNotes();

    }

    public void insert(mytodo mytodo) {
        new InsertNoteAsyncTask(todoDao).execute(mytodo);

    }

    public void update(mytodo mytodo) {
        new UpdateNoteAsyncTask(todoDao).execute(mytodo);

    }

    public void delete(mytodo mytodo) {
        new DeleteNoteAsyncTask(todoDao).execute(mytodo);

    }

    public void deleteAllNotes() {
        new DeleteAllNoteAsyncTask(todoDao).execute();

    }

    public LiveData<List<mytodo>> getAllNotes() {
        return allNotes;

    }

    public LiveData<mytodo> getNote(int taskId) {
        return todoDao.loadTaskById(taskId);
    }



    //prevent the memory leak for insert
    private static class InsertNoteAsyncTask extends AsyncTask<mytodo, Void, Void> {
        private TodoDao todoDao;

        private InsertNoteAsyncTask(TodoDao todoDao) {
            this.todoDao = todoDao;
        }

        @Override
        protected Void doInBackground(mytodo... mytodos) {
            todoDao.Insert(mytodos[0]);
            return null;
        }
    }

    //prevent the memory leak for update
    private static class UpdateNoteAsyncTask extends AsyncTask<mytodo, Void, Void> {
        private TodoDao todoDao;

        private UpdateNoteAsyncTask(TodoDao todoDao) {
            this.todoDao = todoDao;
        }

        @Override
        protected Void doInBackground(mytodo... mytodos) {
            todoDao.Update(mytodos[0]);
            return null;
        }
    }

    //prevent the memory leak for delete mytodo
    private static class DeleteNoteAsyncTask extends AsyncTask<mytodo, Void, Void> {
        private TodoDao todoDao;

        private DeleteNoteAsyncTask(TodoDao todoDao) {
            this.todoDao = todoDao;
        }

        @Override
        protected Void doInBackground(mytodo... mytodos) {
            todoDao.Delete(mytodos[0]);
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

    public static RepositoryTodo getInstance(Context context){
        mContext = context.getApplicationContext();
        if(todoInstance == null){
            todoInstance = new RepositoryTodo((Application) mContext);
        }
        return todoInstance;
    }

}
