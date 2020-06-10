package com.example.todo2020.MyDatabase;

import android.app.Application;
import android.content.Context;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Calendar;
import java.util.Date;

@Entity(tableName = "myTodoList")
public class mytodo {


    //fields assigned

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String title;
    private String description;
    private int priority;

    @ColumnInfo(name = "alarmy_at")
    private Calendar dateTime;

    @ColumnInfo(name = "updated_at")
    private Date updatedAt;


    @Ignore
    public mytodo(int id, String title, String description, int priority, Calendar dateTime, Date updatedAt) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.priority = priority;
        this.dateTime=dateTime;
        this.updatedAt = updatedAt;
    }

    /**
     * Calling constructors
     *
     * @param title
     * @param description
     * @param priority
     * @param updatedAt
     */

    public mytodo(String title, String description, int priority, Calendar dateTime, Date updatedAt) {
        this.title = title;
        this.description = description;
        this.priority = priority;
        this.dateTime=dateTime;
        this.updatedAt = updatedAt;
    }


    /**
     * only id is set because we have not added it in constructor
     *
     * @param id
     */

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getPriority() {
        return priority;
    }

    //for alarmy
    public Calendar getDateTime() {
        return dateTime;
    }

    //for alarmy
    public void setDateTime(Calendar dateTime) {
        this.dateTime = dateTime;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }


    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }


}
