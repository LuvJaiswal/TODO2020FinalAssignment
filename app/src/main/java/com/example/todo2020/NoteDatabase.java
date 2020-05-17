package com.example.todo2020;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.Date;

@Database(entities = {Note.class}, version = 1, exportSchema = false)
@TypeConverters(DateConverter.class)

public abstract class NoteDatabase extends RoomDatabase {

    //need to turn this class into singleton
    private static NoteDatabase instance;

    public abstract TodoDao todoDao();

    public static synchronized NoteDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    NoteDatabase.class, "note_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return instance;

    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };


    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {

        private TodoDao todoDao;

        private PopulateDbAsyncTask(NoteDatabase db) {
            todoDao = db.todoDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
          todoDao.Insert(new Note("title 1", "Description 1",1, new Date()));
//            todoDao.Insert(new Note("Title 2", "Description 2", 2));
//            todoDao.Insert(new Note("Title 3", "Description 3", 3));

            return null;
        }
    }

}
