package com.example.test3;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.time.LocalDate;
import java.util.Date;

/**
 * @author Karl
 */
@Entity(tableName = "content", primaryKeys = {"content_id", "createTime"})
public class Content {
    @NonNull
    @ColumnInfo
    public int content_id;

    @ColumnInfo
    public String text;

    @ColumnInfo
    public int image_id;

    @ColumnInfo
    public String tag;


    @ColumnInfo
    public LocalDate createTime;


    Content(){}

    public Content(String text, LocalDate createTime) {
        this.text = text;
        this.createTime = createTime;
    }

    public Content(int content_id, String text){
        this.content_id = content_id;
        this.text = text;
    }

    public Content(int content_id, String text, LocalDate createTime) {
        this.content_id = content_id;
        this.text = text;
        this.createTime = createTime;
    }
}
