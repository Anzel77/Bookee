package com.example.test3;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

/**
 * @author Karl
 */
public class TagActivity extends AppCompatActivity{

    DatabaseHelper dbHelper;
    private ArrayList<String> tagList = null;
    private static final String DB_NAME = "bookee.db";
    private static final int DB_VERSION = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        //隐藏系统标题栏
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        dbHelper = new DatabaseHelper(this, DB_NAME, null, DB_VERSION);
        dbHelper.getWritableDatabase();

        // Model
        tagList = new ArrayList<>();
        initTagList();

        // View
        ListView listView = (ListView) findViewById(R.id.list_view_tag);
        // 如果textList中没有数据，传入一个View
        View listviewEmpty = findViewById(R.id.tag_list_view_empty);
        listView.setEmptyView(listviewEmpty);

        // Controller
        TagAdapter adapter = new TagAdapter(TagActivity.this, R.layout.tag, tagList);
        listView.setAdapter(adapter);

        // 点击list中的项
        listView.setOnItemClickListener((parent, view, position, id) -> {
            String tag = tagList.get(position);
            if (!TextUtils.isEmpty(tag)){
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putString("tag_for_search", tag);
                intent.putExtras(bundle);
                setResult(Activity.RESULT_OK, intent);
                finish();
            }


            // 点击响应的内容
//            Toast.makeText(TagActivity.this, tag, Toast.LENGTH_SHORT).show();
        });

    }

    void initTagList() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT DISTINCT tag_content FROM content WHERE tag_content IS NOT NULL", null);
        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") String tag = cursor.getString(cursor.getColumnIndex("tag_content"));
                tagList.add(tag);
            } while (cursor.moveToNext());
        }
        cursor.close();
    }


}