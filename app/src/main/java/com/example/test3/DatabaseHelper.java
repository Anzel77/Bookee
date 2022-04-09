package com.example.test3;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

/**
 * @author Karl
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String CREATE_CONTENT_TABLE = "create table content(" +
            "id_content integer primary key autoincrement," +
            "text_content text," +
            "picture_content integer," +
            "tag_content text" +
            ")";

    private Context mContext;

    public DatabaseHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        mContext = context;
    }

    /**
     * 创建数据库
     * @param db 数据库名称
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_CONTENT_TABLE);
        Toast.makeText(mContext, "create database complete", Toast.LENGTH_SHORT).show();
    }

    /**
     * 更新数据库，通常在增加表和删除表的时候进行
     * @param db 需要更新的数据库名称
     * @param oldVersion 旧的版本号
     * @param newVersion 新的版本号
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists content");
        onCreate(db);
    }
}
