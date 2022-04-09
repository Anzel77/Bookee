package com.example.test3;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;


/**
 * @author Karl
 */
public class MenuActivity extends AppCompatActivity implements View.OnClickListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        //隐藏系统标题栏
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        final Button searchOnWebButton = findViewById(R.id.button_search_on_web);
        searchOnWebButton.setOnClickListener(this);



    }


    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.button_search_on_web) {
            showPopupMenu(v);
        }

    }

    /**
     * 显示菜单
     *
     * @param view 用户操作的视图
     */
    @SuppressLint("NonConstantResourceId")
    private void showPopupMenu(View view) {
        PopupMenu popupMenu = new PopupMenu(this, view);
        // 调用menu布局以生成一个Popupmenu的布局
        popupMenu.getMenuInflater().inflate(R.menu.search, popupMenu.getMenu());
        // menu中的item点击事件
        popupMenu.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case R.id.search_Gutenberg:
                    Intent searchGutenberg = new Intent(Intent.ACTION_VIEW);
                    searchGutenberg.setData(Uri.parse("http://www.gutenberg.org"));
                    startActivity(searchGutenberg);
                    break;
                case R.id.search_Z_library:
                    Intent searchZlibrary = new Intent(Intent.ACTION_VIEW);
                    searchZlibrary.setData(Uri.parse("https://zh.ng1lib.org/"));
                    startActivity(searchZlibrary);
                    break;
                case R.id.search_shuge:
                    Intent searchShuge = new Intent(Intent.ACTION_VIEW);
                    searchShuge.setData(Uri.parse("https://new.shuge.org/"));
                    startActivity(searchShuge);
                    break;
                default:
                    break;
            }
            return false;
        });
        popupMenu.show(); // 显示点按菜单
    }
}