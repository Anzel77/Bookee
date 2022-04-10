package com.example.test3;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class TagAdapter extends ArrayAdapter<String> {
    private final int resourceId;

    public TagAdapter(@NonNull Context context, int resource, @NonNull ArrayList<String> objects) {
        super(context, resource, objects);
        resourceId = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String tag = getItem(position);
        TagAdapter.ViewHolder viewHolder;

        if (convertView == null) {
            // 生成 LayoutInflater 对象
            convertView = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
            viewHolder = new TagAdapter.ViewHolder();
            viewHolder.tagView = convertView.findViewById(R.id.tag_menu);
            //将viewHolder存储到convertView中
            convertView.setTag(viewHolder);

        } else {
            viewHolder = (TagAdapter.ViewHolder) convertView.getTag();
        }
        viewHolder.tagView.setText(tag);


        return convertView;
    }

    static class ViewHolder{
        TextView tagView;
    }
}
