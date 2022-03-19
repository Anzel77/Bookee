package com.example.test3;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import java.util.List;

/**
 * @author Karl
 */
public class Home extends AppCompatActivity implements View.OnClickListener , ContentDao{

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

        // 初始化textList的数据
        textList = Text.getDefault();

        ListView listView = (ListView) findViewById(R.id.list_view_text);
        TextAdapter adapter = new TextAdapter(Home.this, R.layout.text_item, textList);

        // 构建 listView 与 textList 的关联
        listView.setAdapter(adapter);


        // 点击list中的项
        listView.setOnItemClickListener((parent, view, position, id) -> {
            Text text = textList.get(position);
             // 点击响应的内容
//            Toast.makeText(Home.this, text.getContent(), Toast.LENGTH_SHORT).show();
        });


        // 长按list中的项
        listView.setOnItemLongClickListener((parent, view, position, id) -> {
            Text text = textList.get(position);
//            Toast.makeText(Home.this, "This item has no option", Toast.LENGTH_SHORT).show();
            showPopupMenu(view);
            return true;
        });

    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_search:

                break;

            case R.id.title_menu:
                Intent menu = new Intent(Home.this, Menu.class);
                startActivity(menu);
                break;

            case R.id.float_button_add:
                Intent editButton = new Intent(Home.this, CustomInput.class);
                startActivity(editButton);
                break;

            default:
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            String inputText = data.getStringExtra("text");
            Text text = new Text(inputText);
            textList.add(text);
            ListView listView = (ListView) findViewById(R.id.list_view_text);
            TextAdapter adapter = new TextAdapter(Home.this, R.layout.text_item, textList);
            listView.setAdapter(adapter);
        }
    }

    /**
     *显示弹窗选项
     */
    private void showPopupMenu(View view) {
        // View当前PopupMenu显示的相对View的位置
        PopupMenu popupMenu = new PopupMenu(this, view);
        // menu布局
        popupMenu.getMenuInflater().inflate(R.menu.multi_option, popupMenu.getMenu());
        // menu的item点击事件
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                //Toast.makeText(getApplicationContext(), item.getTitle(), Toast.LENGTH_SHORT).show();

                switch (item.getItemId()) {
                    case R.id.option_modify:

                        break;
                    case R.id.option_delete:

                        break;
                    case R.id.option_copy:

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

    /**
     * 插入 content 表的某一行或某几行
     *
     * @param content Content 对象，表的行
     */
    @Override
    public void insert(Content... content) {

    }

    /**
     * 删除 content 表中的某一行或某几行
     *
     * @param content Content 对象，表的行
     */
    @Override
    public void delete(Content... content) {

    }

    /**
     * 更新content表中的某一行或某几行
     *
     * @param content Content 对象，表的行
     */
    @Override
    public void update(Content... content) {

    }

    /**
     * 获取所有 content 表中的数据
     *
     * @return content 中的所有数据
     */
    @Override
    public List<Content> getAll() {
        return null;
    }

    /**
     * 加载所有text内容
     * @return
     */
    @Override
    public List<String> loadAllText() {
        return null;
    }

    /**
     * 加载某一id对应的text内容
     *
     * @param id 查询特定内容的id
     * @return
     */
    @Override
    public String loadTextWhenIdIs(int id) {
        return null;
    }

    /**
     * 查找包含特定关键字的text内容
     *
     * @param search 查找的内容
     * @return
     */
    @Override
    public List<String> findTextWhenTextLikes(String search) {
        return null;
    }

    @Override
    public int loadImageWhenIdIs(int id) {
        return 0;
    }
}