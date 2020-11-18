package com.example.taskmasker;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface TaskDao {

    @Query("SELECT * FROM Task")
    public List<Task> getAllTasks();

    //Übung 5
    @Query("SELECT * from Task WHERE id like :ID")
    public Task getTask(int ID);

    @Insert
    public void addTask(Task task);

    @Update
    public void updateTask(Task task);

    @Delete
    public void deleteTask(Task task);

}
