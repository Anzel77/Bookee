package com.example.test3.Entity;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

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
