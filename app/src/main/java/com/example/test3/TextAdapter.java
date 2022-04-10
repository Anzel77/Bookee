package com.example.test3;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Karl
 */
public class TextAdapter extends ArrayAdapter<Text>{

    private final int resourceId;

    public TextAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Text> objects) {
        super(context, resource, objects);
        resourceId = resource;
    }

    /**
     *
     * @param position 指定位置
     * @param convertView 转换视图
     * @param parent
     * @return
     */
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        Text text = getItem(position);
        ViewHolder viewHolder;

        /**
         * 如果convertView中没有布局信息，则构建布局信息，并传入 ViewHolder 中。
         * 如果存在布局信息，更新ViewHolder中的布局信息
         */
        if (convertView == null) {
            // 生成 LayoutInflater 对象
            convertView = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);

            viewHolder = new ViewHolder();

            viewHolder.textView = convertView.findViewById(R.id.text_content);
            viewHolder.imageView = convertView.findViewById(R.id.image_content);
            viewHolder.tagView = convertView.findViewById(R.id.tag_content);

            //将viewHolder存储到convertView中
            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.textView.setText(text.getContent());
        viewHolder.imageView.setImageResource(text.getImageId());
        viewHolder.tagView.setText(text.getTag());

        return convertView;
    }

    /**
     * 定义静态内部类 ViewHolder ，用于复用布局
     */
    static class ViewHolder{
        TextView textView;
        ImageView imageView;
        TextView tagView;
    }
}
