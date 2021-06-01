package com.tejma.spacexcrew.room;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.tejma.spacexcrew.pojo.Crew;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class CrewViewModel extends AndroidViewModel {

    private CrewRepository repository;
    private LiveData<List<Crew>> allCrew;

    public CrewViewModel(@NonNull @NotNull Application application) {
        super(application);
        repository = new CrewRepository(application);
        allCrew = repository.getAllCrew();
    }

    public void insert(List<Crew> crew){
        repository.insert(crew);
    }

    public Crew getCrewMember(String id){
        return repository.getCrewMember(id);
    }

    public void deleteAll(){
        repository.deleteAll();
    }

    public LiveData<List<Crew>> getAllCrew(){
        return allCrew;
    }
}
