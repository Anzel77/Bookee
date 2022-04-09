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
@Entity(tableName = "content")
public class Content {
    @NonNull
    @PrimaryKey
    public int content_id;

    @ColumnInfo
    public String text;

    @ColumnInfo
    public int image_id;

    @ColumnInfo
    public String tag;

    Content(){}

    public Content(int content_id, String text){
        this.content_id = content_id;
        this.text = text;
    }

}
