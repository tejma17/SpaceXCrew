package com.tejma.spacexcrew.room;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.tejma.spacexcrew.pojo.Crew;

import java.util.List;

public class CrewRepository {
    private CrewDao crewDao;
    private LiveData<List<Crew>> allCrew;

    public CrewRepository(Application application){
        CrewDatabase crewdatabase = CrewDatabase.getInstance(application);
        crewDao = crewdatabase.crewDao();
        allCrew = crewDao.getAllCrew();
    }

    public void insert(List<Crew> crews){
        new InsertCrewAsyncTask(crewDao).execute(crews);
    }

    public void deleteAll(){
        new DeleteAllAsyncTask(crewDao).execute();
    }

    public Crew getCrewMember(String id){
        return crewDao.getCrewMember(id);
    }

    public LiveData<List<Crew>> getAllCrew() {
        return allCrew;
    }

    public static class InsertCrewAsyncTask extends AsyncTask<List<Crew>, Void, Void> {
        private CrewDao crewDao;

        private InsertCrewAsyncTask(CrewDao crewDao){
            this.crewDao = crewDao;
        }

        @Override
        protected Void doInBackground(List<Crew>... crews) {
            crewDao.insert(crews[0]);
            return null;
        }
    }

    public static class DeleteAllAsyncTask extends AsyncTask<Void, Void, Void> {
        private CrewDao crewDao;
        private DeleteAllAsyncTask(CrewDao crewDao){
            this.crewDao = crewDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            crewDao.deleteAll();
            return null;
        }
    }


}


