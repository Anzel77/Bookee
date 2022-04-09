package com.example.test3;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

/**
 * @author Karl
 */
public class CustomInputActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText editText;
    private DatabaseHelper dbHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);

        //隐藏系统标题栏
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        // 实例化EditText
        editText = (EditText) findViewById(R.id.input_text);

        // send 图片按钮
        final ImageButton sendButton = (ImageButton) findViewById(R.id.send_button);
        sendButton.setOnClickListener(this);


        // 加载缓存文件中的文字信息
        String inputText = load();
        // 判断从缓存文件中加载的文字信息是否为空值，非空值时进行显示操作
        if (!TextUtils.isEmpty(inputText)) {
            // 将从缓存文件中读取到的文字信息传入EditText中
            editText.setText(inputText);
            // 讲光标移动到文本的末尾
            editText.setSelection(inputText.length());
//            Toast.makeText(this, "Restoring succeeded", Toast.LENGTH_SHORT).show();
        }

        showSoftInputFromWindow(this,editText);
    }


    /**
     * 点按发送按钮
     * @param v 按钮
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.send_button){
            String inputText = editText.getText().toString().trim();
            if (!TextUtils.isEmpty(inputText)){
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putString("text",inputText);
                intent.putExtras(bundle);
                setResult(Activity.RESULT_OK, intent);
                finish();
            }
            editText.setText(null);

        }else if (v.getId() == R.id.back_to_home){

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 将输入框的文字写进缓存文件
        String inputText = editText.getText().toString();
        bufferContent(inputText);
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    /**
     * 缓存输入的文字信息信息
     * @param inputText EditText输入的文字信息
     */
    public void bufferContent(String inputText) {
        FileOutputStream out = null;
        BufferedWriter bufferedWriter = null;
        try {
            out = openFileOutput("text_data_buffered", Context.MODE_PRIVATE);
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(out));
            bufferedWriter.write(inputText);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bufferedWriter != null) {
                    bufferedWriter.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 获取缓存问文件中的信息
     * @return 缓存文件中的文字信息
     */
    public String load() {
        FileInputStream in = null;
        BufferedReader bufferedReader = null;
        StringBuilder stringBuilder = new StringBuilder();
        try {
            in = openFileInput("text_data_buffered");
            bufferedReader = new BufferedReader(new InputStreamReader(in));
            String line = "";
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return stringBuilder.toString();
    }

    /**
     * EditText获取焦点并显示软键盘
     */
    public static void showSoftInputFromWindow(Activity activity, EditText editText) {
        editText.setFocusable(true);
        editText.setFocusableInTouchMode(true);
        editText.requestFocus();
        //显示软键盘
        activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
    }

}


