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
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collections;

/**
 * @author Karl
 */
public class HomeActivity extends AppCompatActivity implements View.OnClickListener {

    /**
     * 数据库名称
     */
    private static final String DB_NAME = "bookee.db";
    /**
     * 数据库版本号
     */
    private static final int DB_VERSION = 3;
    /**
     * AddButton传输给InputActivity的请求码
     */
    private static final int ADD_REQUEST_C = 0;
    /**
     * OperationDialog中Modify传给InputActivity的请求码
     */
    private static final int MODIFY_REQUEST_C = 1;
    /**
     * TagButton传输给TagActivity的请求码
     */
    private static final int TAG_FOR_SEARCH_REQUEST_C = 2;
    /**
     * InputActivity返回给Home的结果码
     */
    private static final int INPUT_RESULT_C = 0;
    /**
     * TagActivity返回给Home用于搜索特定tag内容的结果码
     */
    private static final int TAG_RESULT_C = 1;
    /**
     * TagActivity返回给Home用于加载所有数据库内容的结果码
     */
    private static final int TAG_ALL_RESULT_C = 2;

    DatabaseHelper dbHelper;
    /**
     * 存放数据库数据的容器
     */
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
        final ImageButton tagButton = findViewById(R.id.title_tag);
        final ImageButton searchButton = findViewById(R.id.title_search);
        final FloatingActionButton addFloatButton = findViewById(R.id.float_button_add);

        //按钮监听器
        tagButton.setOnClickListener(this);
        searchButton.setOnClickListener(this);
        addFloatButton.setOnClickListener(this);

        // 创建SQLite数据库
        dbHelper = new DatabaseHelper(this, DB_NAME, null, DB_VERSION);
        dbHelper.getWritableDatabase();

        // 初始化textList的数据
        // Model
        textList = new ArrayList<>();
//        textList = Text.getDefault();
        initTextList();
        // 逆序 textList
        Collections.reverse(textList);

        // View
        ListView listView = findViewById(R.id.list_view_text);
        // 如果textList中没有数据，传入一个View
        View listviewEmpty = findViewById(R.id.text_list_view_empty);
        listView.setEmptyView(listviewEmpty);

        // 构建 listView 与 textList 的关联
        // Controller
        TextAdapter adapter = new TextAdapter(HomeActivity.this, R.layout.text_item, textList);
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
//            showPopupMenu(view, text);
            showTextOperateDialog(text);
            return true;
        });

    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_search:
                showSearchOptionDialog();
                break;
            case R.id.title_tag:
                Intent tag = new Intent(HomeActivity.this, TagActivity.class);
                startActivityForResult(tag, TAG_FOR_SEARCH_REQUEST_C);
                break;
            case R.id.float_button_add:
                Intent editButton = new Intent(HomeActivity.this, CustomInputActivity.class);
                startActivityForResult(editButton, ADD_REQUEST_C);
                break;
            default:
                break;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }


//    /**
//     * 显示弹窗选项
//     */
//    private void showPopupMenu(View view, Text text) {
//        // View当前PopupMenu显示的相对View的位置
//        PopupMenu popupMenu = new PopupMenu(this, view);
//        // menu布局
//        popupMenu.getMenuInflater().inflate(R.menu.multi_option, popupMenu.getMenu());
//        // menu的item点击事件
//        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
//            @SuppressLint("NonConstantResourceId")
//            @Override
//            public boolean onMenuItemClick(MenuItem item) {
//                //Toast.makeText(getApplicationContext(), item.getTitle(), Toast.LENGTH_SHORT).show();
//                switch (item.getItemId()) {
//                    case R.id.option_modify:
//
//                        break;
//                    case R.id.option_delete:
//                        Integer id = text.getTextId();
//                        dataDelete(id);
//                        // 移除textList中的某一项
//                        textList.remove(text);
//                        textListToAdapter();
//                        // 提示删除成功
//                        Toast.makeText(getApplicationContext(), "Delete complete", Toast.LENGTH_SHORT).show();
//                        break;
//                    case R.id.option_copy:
//                        //获取剪贴板管理器：
//                        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
//                        // 创建普通字符型ClipData
//                        ClipData clip = ClipData.newPlainText("BooKee copy", text.getContent());
//                        // 将ClipData内容放到系统剪贴板里。
//                        clipboard.setPrimaryClip(clip);
//                        Toast.makeText(getApplicationContext(), "Copy complete", Toast.LENGTH_SHORT).show();
//                        break;
//                    default:
//                        break;
//                }
//                return false;
//            }
//        });
//        popupMenu.show(); // 显示点按菜单
//    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_REQUEST_C || requestCode == MODIFY_REQUEST_C) {
            if (resultCode == INPUT_RESULT_C && data != null) {
                String inputText = data.getStringExtra("text_input");
                // 检测tag所在位置
                int index = inputText.indexOf("\n#");
                // 判断是否存在tag，存在index值为所在位置，不存在index值为-1
                if (index != -1) {
                    int length = inputText.length();
                    // 获取text内容
                    String text = inputText.substring(0, index);
                    // 获取tag内容
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
                lastDataToAdapter();
            }
        } else if (requestCode == TAG_FOR_SEARCH_REQUEST_C) {
            if (resultCode == TAG_RESULT_C && data != null) {
                String tagForSearch = data.getStringExtra("tag_for_search");
                if (!TextUtils.isEmpty(tagForSearch.trim())) {
                    SQLiteDatabase db = dbHelper.getWritableDatabase();
                    Cursor cursor = db.rawQuery("SELECT * FROM content WHERE tag_content IN (?)", new String[]{tagForSearch});
                    textList.clear();
                    if (cursor.moveToFirst()) {
                        do {
                            @SuppressLint("Range") int textId = cursor.getInt(cursor.getColumnIndex("id_content"));
                            @SuppressLint("Range") String text = cursor.getString(cursor.getColumnIndex("text_content"));
                            @SuppressLint("Range") String tag = cursor.getString(cursor.getColumnIndex("tag_content"));
                            Text textInDatabase = new Text(textId, text, tag);
                            textList.add(textInDatabase);
                        } while (cursor.moveToNext());
                    }
                    cursor.close();
                    Collections.reverse(textList);
                    textListToAdapter();
                }
            } else if (resultCode == TAG_ALL_RESULT_C){
                textList.clear();
                initTextList();
                Collections.reverse(textList);
                textListToAdapter();
            }
        }
    }

    /**
     * 初始化textList
     * 将数据库中所有的数据传入textList
     */
    void initTextList() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM content", null);
        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") int textId = cursor.getInt(cursor.getColumnIndex("id_content"));
                @SuppressLint("Range") String text = cursor.getString(cursor.getColumnIndex("text_content"));
                @SuppressLint("Range") String tag = cursor.getString(cursor.getColumnIndex("tag_content"));
                Text textInDatabase = new Text(textId, text, tag);
                textList.add(textInDatabase);
            } while (cursor.moveToNext());
        }
        cursor.close();
    }

    /**
     * 连接 listview 和 adapter
     */
    void textListToAdapter() {
        ListView listView = findViewById(R.id.list_view_text);
        TextAdapter adapter = new TextAdapter(HomeActivity.this, R.layout.text_item, textList);
        listView.setAdapter(adapter);
    }

    /**
     * 将数据库中最后添加的数据传到 textList 中
     */
    void lastDataToAdapter() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery("SELECT * FROM content", null);
        // 获取最后一组数据
        cursor.moveToLast();
        @SuppressLint("Range") int textId = cursor.getInt(cursor.getColumnIndex("id_content"));
        @SuppressLint("Range") String text = cursor.getString(cursor.getColumnIndex("text_content"));
        @SuppressLint("Range") String tag = cursor.getString(cursor.getColumnIndex("tag_content"));
        Text textAdd = new Text(textId, text, tag);
        Collections.reverse(textList);
        textList.add(textAdd);
        Collections.reverse(textList);
        textListToAdapter();
    }

    /**
     * 删除数据库中的某一条数据
     * @param id 需要删除的数据的 id
     */
    void dataDelete(Integer id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.execSQL("DELETE FROM content WHERE id_content = ?", new String[]{id.toString()});
    }


    void dataUpdate(Text text) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.execSQL("UPDATE content SET text_content = ?, tag_content = ? WHERE id_content = ?",
                new String[]{text.getContent(), text.getTag(), Integer.toString(text.getTextId())});
    }

    void showSearchOptionDialog() {
        DialogFragment searchOnWebDialog = new SearchOptionDialogFragment();
        searchOnWebDialog.show(getSupportFragmentManager(), "search_option");
    }

    void showTextOperateDialog(Text text) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose a operation")
                .setItems(R.array.text_operate_option, (dialog, which) -> {
                    // 获取文本项的 id
                    Integer id = text.getTextId();
                    switch (which) {
                        case 0:
                            dataDelete(id);
                            textList.remove(text);
                            textListToAdapter();
                            String data = text.getContent() + '\n' + text.getTag();
                            Intent intent = new Intent(this, CustomInputActivity.class);
                            intent.putExtra("text_modify", data);
                            startActivityForResult(intent, MODIFY_REQUEST_C);
                            break;
                        case 1:
                            dataDelete(id);
                            // 移除textList中的某一项
                            textList.remove(text);
                            textListToAdapter();
                            // 提示删除成功
                            Toast.makeText(getApplicationContext(), "Delete complete", Toast.LENGTH_SHORT).show();
                            break;
                        case 2:
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
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

}