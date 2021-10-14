package edu.neu.khoury.madsea.saiqihe.todolistversion2;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;
@Dao
public interface TodoNoteDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public void insert(TodoNote note);
    @Query("SELECT * FROM todo_note_table")
    public LiveData<List<TodoNote>> selectAll();
    @Query("DELETE FROM todo_note_table")
    public void deleteAll();
    @Query("UPDATE todo_note_table set note_title = :title and note_detail = :detail where note_id = :id")
    void update(String title, String detail, int id);
}
