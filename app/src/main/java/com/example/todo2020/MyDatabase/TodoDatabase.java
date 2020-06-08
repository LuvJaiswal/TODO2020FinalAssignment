package com.example.todo2020.MyDatabase;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.Date;

@Database(entities = {mytodo.class}, version = 1, exportSchema = false)
@TypeConverters(DateConverter.class)

public abstract class TodoDatabase extends RoomDatabase {

    //need to turn this class into singleton
    private static TodoDatabase instance;

    public abstract TodoDao todoDao();


    /***
     * single database instance
     * only one thread at a time can access this method
     * @param context
     * @return
     */
    public static synchronized TodoDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    TodoDatabase.class, "note_database")
                    .fallbackToDestructiveMigration()    //to tell room how to migrate in new Schema
                    .addCallback(roomCallback)   //attached room call back from room database
                    .build();
        }
        return instance;

    }

    //database created first time
    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };


    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {

        private TodoDao todoDao;

        private PopulateDbAsyncTask(TodoDatabase db) {
            todoDao = db.todoDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            todoDao.Insert(new mytodo("title 1", "Description 1", 1, new Date()));
            todoDao.Insert(new mytodo("title 2", "Description 2", 2, new Date()));
            todoDao.Insert(new mytodo("title 3", "Description 3", 3, new Date()));
            return null;
        }
    }

}
