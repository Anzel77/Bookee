package com.example.test3.Activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.test3.R;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Karl
 */
public class CustomInputActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int INPUT_RESULT_CODE = 0;
    private static final int CAMERA_REQUEST_CODE = 3;
    private static final int CROP_REQUEST_CODE = 4;
    private static final int GALLERY_REQUEST_CODE = 5;
    public static File tempFile;

    private EditText editText;
    private ImageView imageViewInput;
    private Uri imageUri;

    /**
     * EditText获取焦点并显示软键盘
     */
    public static void showSoftInputFromWindow(@NonNull Activity activity, @NonNull EditText editText) {
        editText.setFocusable(true);
        editText.setFocusableInTouchMode(true);
        editText.requestFocus();
        //显示软键盘
        activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
    }

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

        // 实例化返回按钮
        final ImageButton backToHome = (ImageButton) findViewById(R.id.back_to_home);
        backToHome.setOnClickListener(this);

        // 实例化发送按钮
        final ImageButton sendButton = (ImageButton) findViewById(R.id.send_button);
        sendButton.setOnClickListener(this);

        // 实例化摄像头按钮
        final ImageButton cameraButton = (ImageButton) findViewById(R.id.camera_button);
        cameraButton.setOnClickListener(this);

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

        // 获取Home传递过来的需要修改的数据
        Intent intent = getIntent();
        String textModify = intent.getStringExtra("text_modify");
        if (!TextUtils.isEmpty(textModify)) {
            editText.setText(textModify);
            editText.setSelection(textModify.length());
        }

        // 显示虚拟键盘
        showSoftInputFromWindow(this, editText);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

//        if (resultCode == RESULT_OK) {
//            if(requestCode == CAMERA_REQUEST_CODE){
//                Intent intent = new Intent("com.android.camera.action.CROP");
//                intent.setDataAndType(imageUri, "image/*");
//                intent.putExtra("scale", true);
//                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
//                startActivityForResult(intent, CROP_REQUEST_CODE);
//            } else if (requestCode == CROP_REQUEST_CODE) {
//                try {
//                    Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
//                    imageViewInput.setImageBitmap(bitmap);
//                } catch (FileNotFoundException e) {
//                    e.printStackTrace();
//                }
//            } else if (requestCode == GALLERY_REQUEST_CODE) {
//                try {
//                    if(data != null) {
//                        imageUri = data.getData();
//                    }
//                    Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
//                    imageViewInput.setImageBitmap(bitmap);
//                } catch (FileNotFoundException e) {
//                    e.printStackTrace();
//                }
//            }
//        }


    }

    @SuppressLint({"NonConstantResourceId", "QueryPermissionsNeeded"})
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.send_button:
                String inputText = editText.getText().toString().trim();
                if (!TextUtils.isEmpty(inputText)) {
                    Intent intent = new Intent();
                    Bundle bundle = new Bundle();
                    bundle.putString("text_input", inputText);
                    intent.putExtras(bundle);
                    setResult(INPUT_RESULT_CODE, intent);
                    finish();
                }
                editText.setText(null);
                break;
            case R.id.back_to_home:
                finish();
                break;
            case R.id.camera_button:
//                openCamera(this);
//                openGallery();

                // 清空输入栏内容
                editText.setText(null);
                break;
            default:
                break;
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
     * @param inputText EditText中已输入的文字信息
     */
    public void bufferContent(String inputText) {
        FileOutputStream out;
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
     * 打开相机
     * @param activity 上下文
     */
    public void openCamera(Activity activity) {
        //獲取系統版本
        int currentapiVersion = android.os.Build.VERSION.SDK_INT;
        // 激活相机
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // 判断存储卡是否可以用，可用进行存储
        if (hasSdcard()) {
            @SuppressLint("SimpleDateFormat") SimpleDateFormat timeStampFormat = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
            String filename = timeStampFormat.format(new Date());
            tempFile = new File(Environment.getExternalStorageDirectory(), filename + ".jpg");
            if (currentapiVersion < 24) {
                // 从文件中创建uri
                imageUri = Uri.fromFile(tempFile);
            } else {
                //兼容android7.0 使用共享文件的形式
                ContentValues contentValues = new ContentValues(1);
                contentValues.put(MediaStore.Images.Media.DATA, tempFile.getAbsolutePath());
                //检查是否有存储权限，以免崩溃
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    //申请WRITE_EXTERNAL_STORAGE权限
                    Toast.makeText(this,"请开启存储权限",Toast.LENGTH_SHORT).show();
                    return;
                }
                imageUri = activity.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
            }
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        }
        // 开启一个带有返回值的Activity，请求码为CAMERA_REQUEST_CODE
        activity.startActivityForResult(intent, CAMERA_REQUEST_CODE);
    }

    /**
     * 判断sdcard是否被挂载
     */
    public static boolean hasSdcard() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    /**
     * 打开相册
     */
    public void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, GALLERY_REQUEST_CODE);
    }

}


