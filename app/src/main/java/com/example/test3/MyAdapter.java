package com.example.test3;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Karl
 */
public class MyAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<Text> mList;

    public MyAdapter(){ }

    /**
     * 构造MyAdapter适配器
     * @param mContext 上下文
     * @param mList 数据集
     */
    public MyAdapter(Context mContext, ArrayList<Text> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    /**
     * @return 列表项的个数
     */
    @Override
    public int getCount() {
        return 0;
    }

    /**
     *
     * @param position 位置
     * @return 列表项的数据
     */
    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    /**
     *
     * @param position 位置
     * @return 列表项的编号
     */
    @Override
    public long getItemId(int position) {
        return 0;
    }

    /**
     * 获取指定位置的列表项视图
     * @param position 置顶位置
     * @param convertView 转换视图
     * @param parent 父视图
     * @return 指定位置的列表项视图
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView == null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.text_item, parent, false);
            holder = new ViewHolder();
            holder.textView = convertView.findViewById(R.id.text_content);
            holder.imageView = convertView.findViewById(R.id.image_content);
            // 将视图持有者保存到转换视图中
            convertView.setTag(holder);
        }else{
            // 从转换视图中获取之前保存的视图持有者
            holder = (ViewHolder) convertView.getTag();
        }
        Text text = mList.get(position);
        holder.textView.setText(text.getContent());
        holder.imageView.setImageResource(text.getImageId());
        return convertView;
    }

    // 重用视图资源
    public final class ViewHolder{
        TextView textView;
        ImageView imageView;
    }
}
