package edu.neu.khoury.madsea.saiqihe.todolistversion2.roomDatabaseComponents;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface TodoNoteDao {
    @Insert
    public void insert(TodoNote note);
    @Query("SELECT * FROM todo_note_table")
    public LiveData<List<TodoNote>> selectAll();
    @Query("DELETE FROM todo_note_table")
    public void deleteAll();
    @Update
    void update(TodoNote note);
    @Query("UPDATE todo_note_table set note_title = :title and note_detail = :detail where note_id = :id")
    void update2(String title, String detail, int id);
    @Delete
    public void delete(TodoNote note);
}
