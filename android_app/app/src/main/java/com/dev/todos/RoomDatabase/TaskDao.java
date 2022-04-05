package com.dev.todos.RoomDatabase;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.RoomDatabase;
import androidx.room.Update;

import java.util.List;

@Dao
public interface TaskDao {

    @Query("SELECT * FROM RoomDatabaseClass")
    List<RoomDatabaseClass> getAll();

    @Insert
    void insert(RoomDatabaseClass roomDatabase);

  /* @Update
    void update(RoomDatabaseClass roomDatabase);*/

    @Update
    public int updateData(List<RoomDatabaseClass> roomDatabaseClasses);

}
