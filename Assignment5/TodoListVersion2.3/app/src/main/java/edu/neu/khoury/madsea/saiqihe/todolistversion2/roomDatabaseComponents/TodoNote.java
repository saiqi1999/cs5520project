package edu.neu.khoury.madsea.saiqihe.todolistversion2.roomDatabaseComponents;

import android.os.Parcel;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Objects;

@Entity(tableName = "todo_note_table")
public class TodoNote {

    @ColumnInfo(name = "note_id")
    @PrimaryKey(autoGenerate = true)
    private Integer noteId;

    @NonNull
    @ColumnInfo(name = "note_title")
    private String title;

    @ColumnInfo(name = "note_detail")
    private String detail;

    @ColumnInfo(name = "note_checked")
    private String checked;

    @ColumnInfo(name = "note_alarmTime")
    private String alarmTime;

    public String getChecked() {
        return checked;
    }

    public void setChecked(String checked) {
        this.checked = checked;
    }

    public String getAlarmTime() {
        return alarmTime;
    }

    public void setAlarmTime(String alarmTime) {
        this.alarmTime = alarmTime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public Integer getNoteId() {
        return noteId;
    }

    public void setNoteId(int noteId) {
        this.noteId = noteId;
    }

    public TodoNote(@NonNull String title, String detail) {
        this.title = title;
        this.detail = detail;
    }
    @Ignore
    public TodoNote(Integer noteId, @NonNull String title, String detail) {
        this.noteId = noteId;
        this.title = title;
        this.detail = detail;
    }
    public TodoNote(Parcel in){
        noteId = in.readInt();
        title = in.readString();
        detail = in.readString();
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TodoNote todoNote = (TodoNote) o;
        return Objects.equals(title, todoNote.title) && Objects.equals(detail, todoNote.detail);
    }
    @Override
    public int hashCode() {
        return Objects.hash(title, detail);
    }

}
