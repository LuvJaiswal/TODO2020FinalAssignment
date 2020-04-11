package com.example.todo2020;

import android.app.Application;
import android.os.AsyncTask;
import android.provider.ContactsContract;

import androidx.lifecycle.LiveData;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Update;

import java.util.List;

public class RepositoryNote {
    private TodoDao todoDao;
    private LiveData<List<Note>> allNotes;

    public RepositoryNote(Application application){
        NoteDatabase database = NoteDatabase.getInstance(application);
        todoDao = database.todoDao();
        allNotes = todoDao.getAllNotes();

    }

    public void insert(Note note){
        new InsertNoteAsyncTask(todoDao).execute(note);

    }

    public void update(Note note){
        new UpdateNoteAsyncTask(todoDao).execute(note);

    }

    public void delete(Note note){
        new DeleteNoteAsyncTask(todoDao).execute(note);

    }

    public void deleteAllNotes(){
        new DeleteAllNoteAsyncTask(todoDao).execute();

    }

    public LiveData<List<Note>> getAllNotes(){
        return allNotes;

    }

    private static class InsertNoteAsyncTask extends AsyncTask<Note, Void,Void>{
        private TodoDao todoDao;

        private InsertNoteAsyncTask(TodoDao todoDao){
            this.todoDao =todoDao;
        }
        @Override
        protected Void doInBackground(Note... notes) {
            todoDao.Insert(notes[0]);
            return null;
        }
    }


    private static class UpdateNoteAsyncTask extends AsyncTask<Note, Void,Void>{
        private TodoDao todoDao;

        private UpdateNoteAsyncTask(TodoDao todoDao){
            this.todoDao =todoDao;
        }
        @Override
        protected Void doInBackground(Note... notes) {
            todoDao.Update(notes[0]);
            return null;
        }
    }

    private static class DeleteNoteAsyncTask extends AsyncTask<Note, Void,Void>{
        private TodoDao todoDao;

        private DeleteNoteAsyncTask(TodoDao todoDao){
            this.todoDao =todoDao;
        }
        @Override
        protected Void doInBackground(Note... notes) {
            todoDao.Delete(notes[0]);
            return null;
        }
    }

    private static class DeleteAllNoteAsyncTask extends AsyncTask<Void, Void,Void>{
        private TodoDao todoDao;

        private DeleteAllNoteAsyncTask(TodoDao todoDao){
            this.todoDao =todoDao;
        }
        @Override
        protected Void doInBackground(Void... voids) {
            todoDao.deleteAllNotes();
            return null;
        }
    }

}
