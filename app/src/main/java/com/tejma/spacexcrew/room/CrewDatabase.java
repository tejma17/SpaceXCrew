package com.tejma.spacexcrew.room;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.tejma.spacexcrew.pojo.Crew;

import org.jetbrains.annotations.NotNull;


@Database(entities = {Crew.class}, version = 2)
public abstract class CrewDatabase extends RoomDatabase {
    private static CrewDatabase instance;

    public abstract CrewDao crewDao();

    public static synchronized CrewDatabase getInstance(Context context) {
        if(instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    CrewDatabase.class, "crew_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallBack)
                    .build();
        }

        return instance;
    }

    private static RoomDatabase.Callback roomCallBack = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull @NotNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
            Log.d("TAG", "REACHD");
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void>{
        private CrewDao crewDao;
        private PopulateDbAsyncTask(CrewDatabase db){
            crewDao = db.crewDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            crewDao.deleteAll();
            return null;
        }
    }
}
