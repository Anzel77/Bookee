package com.example.test3;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

/**
 * @author Karl
 */
public class HomeActivity extends AppCompatActivity implements View.OnClickListener {

    DatabaseHelper dbHelper;
    private ArrayList<Text> textList = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //隐藏系统标题栏
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        //实例化按钮
        final ImageButton menuButton = findViewById(R.id.title_menu);
        final ImageButton searchButton = findViewById(R.id.title_search);
        final FloatingActionButton fab = findViewById(R.id.float_button_add);

        //按钮监听器
        menuButton.setOnClickListener(this);
        searchButton.setOnClickListener(this);
        fab.setOnClickListener(this);

        // 创建Room数据库实例
//        AppDatabase db = Room.databaseBuilder(this, AppDatabase.class, "bookee").build();
//        ContentDao contentDao = db.contentDao();
//        List<Content> contentList = contentDao.getAll();

        // 创建SQLite数据库
        dbHelper = new DatabaseHelper(this, "bookee.db", null, 3);
        dbHelper.getWritableDatabase();

        // 初始化textList的数据
        textList = Text.getDefault();
        initTextList();


        ListView listView = findViewById(R.id.list_view_text);
        TextAdapter adapter = new TextAdapter(HomeActivity.this, R.layout.text_item, textList);
//        TextAdapter adapter = new TextAdapter(HomeActivity.this, R.layout.text_item, contentList);

        // 构建 listView 与 textList 的关联
        listView.setAdapter(adapter);

        // 点击list中的项
        listView.setOnItemClickListener((parent, view, position, id) -> {
            Text text = textList.get(position);
            // 点击响应的内容
            Toast.makeText(HomeActivity.this, text.getContent(), Toast.LENGTH_SHORT).show();
        });


        // 长按list中的项
        listView.setOnItemLongClickListener((parent, view, position, id) -> {
            Text text = textList.get(position);
//            Toast.makeText(HomeActivity.this, "This item has no option", Toast.LENGTH_SHORT).show();
            showPopupMenu(view, text);
            return true;
        });

    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_search:

                SQLiteDatabase db = dbHelper.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put("text_content", "Compromise is not a dirty word.");
                values.put("tag_content", "#心灵砒霜");
                db.insert("content", null, values);
                values.clear();

                break;

            case R.id.title_menu:
                Intent menu = new Intent(HomeActivity.this, MenuActivity.class);
                startActivity(menu);

                break;

            case R.id.float_button_add:
                Intent editButton = new Intent(HomeActivity.this, CustomInputActivity.class);
                startActivityForResult(editButton, 1);

                break;

            default:
                break;
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();

    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            String inputText = data.getStringExtra("text");
            int index = inputText.indexOf("\n#");
            if (index != -1) {
                int length = inputText.length();
                String text = inputText.substring(0, index);
                String tag = inputText.substring(index + 1, length);
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put("text_content", text);
                values.put("tag_content", tag);
                db.insert("content", null, values);
                values.clear();
            } else {
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put("text_content", inputText);
                db.insert("content", null, values);
                values.clear();
            }


//            Text text = new Text(inputText);
//            textList.add(text);
//            ListView listView = (ListView) findViewById(R.id.list_view_text);
//            TextAdapter adapter = new TextAdapter(HomeActivity.this, R.layout.text_item, textList);
//            listView.setAdapter(adapter);
        }
    }

    /**
     * 显示弹窗选项
     */
    private void showPopupMenu(View view, Text text) {
        // View当前PopupMenu显示的相对View的位置
        PopupMenu popupMenu = new PopupMenu(this, view);
        // menu布局
        popupMenu.getMenuInflater().inflate(R.menu.multi_option, popupMenu.getMenu());
        // menu的item点击事件
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                //Toast.makeText(getApplicationContext(), item.getTitle(), Toast.LENGTH_SHORT).show();

                switch (item.getItemId()) {
                    case R.id.option_modify:


                        break;
                    case R.id.option_delete:
                        String id = Integer.toString(text.getTextId());
                        SQLiteDatabase db = dbHelper.getWritableDatabase();
                        db.delete("content", "id_content = ?", new String[]{id});
                        Toast.makeText(getApplicationContext(), "Delete complete", Toast.LENGTH_SHORT).show();

                        break;
                    case R.id.option_copy:
                        //获取剪贴板管理器：
                        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                        // 创建普通字符型ClipData
                        ClipData clip = ClipData.newPlainText("BooKee copy", text.getContent());
                        // 将ClipData内容放到系统剪贴板里。
                        clipboard.setPrimaryClip(clip);

                        Toast.makeText(getApplicationContext(), "Copy complete", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        break;

                }

                return false;
            }
        });

        popupMenu.show(); // 显示点按菜单

    }

    void initTextList() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.query("content", null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") int textId = cursor.getInt(cursor.getColumnIndex("id_content"));
                @SuppressLint("Range") String text = cursor.getString(cursor.getColumnIndex("text_content"));
                @SuppressLint("Range") String tag = cursor.getString(cursor.getColumnIndex("tag_content"));
                Text text1 = new Text(textId, text, tag);
                textList.add(text1);
            } while (cursor.moveToNext());
        }
        cursor.close();
    }

    void addTextList() {

    }


}