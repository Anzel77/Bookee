package com.example.test3;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.Toast;


/**
 * @author Karl
 */
public class Menu extends AppCompatActivity implements View.OnClickListener {

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
        switch (v.getId()) {
            case R.id.button_search_on_web:

                //直接跳转网页
//                Intent search = new Intent(Intent.ACTION_VIEW);
//                search.setData(Uri.parse("http://www.gutenberg.org"));
//                startActivity(search);

                //点按SEARCH显示菜单
                showPopupMenu(v);
                break;

            default:
                break;


        }
    }

    private void showPopupMenu(View view) {
        // View当前PopupMenu显示的相对View的位置
        PopupMenu popupMenu = new PopupMenu(this, view);
        // menu布局
        popupMenu.getMenuInflater().inflate(R.menu.search, popupMenu.getMenu());
        // menu的item点击事件
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                //Toast.makeText(getApplicationContext(), item.getTitle(), Toast.LENGTH_SHORT).show();

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
            }
        });

        popupMenu.show(); // 显示点按菜单

    }
}