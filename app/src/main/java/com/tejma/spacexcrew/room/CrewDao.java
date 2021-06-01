package com.tejma.spacexcrew.room;



import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.tejma.spacexcrew.pojo.Crew;

import java.util.List;

@Dao
public interface CrewDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<Crew> crew);

    @Query("DELETE FROM crew_table")
    void deleteAll();

    @Query("SELECT * FROM crew_table WHERE id = :memId")
    Crew getCrewMember(String memId);

    @Query("SELECT * FROM crew_table ORDER BY name ASC")
    LiveData<List<Crew>> getAllCrew();
}
