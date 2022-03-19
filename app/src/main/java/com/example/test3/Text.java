package com.example.test3;

import java.util.ArrayList;

/**
 * 输入的内容项
 * @author Karl
 */
public class Text {
    private String content;
    private int imageId;
    private String tag;

    /**
     * @param content 输入的文字内容
     */

    public Text(String content) {
        this.content = content;
    }

    /**
     * @param imageId 输入的如片id
     */
    public Text(int imageId) {
        this.imageId = imageId;
    }


    public Text(String content, int imageId) {
        this.content = content;
        this.imageId = imageId;
    }

    public Text(String content, String tag) {
        this.content = content;
        this.tag = tag;
    }

    public Text(String content, int imageId, String tag) {
        this.content = content;
        this.imageId = imageId;
        this.tag = tag;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }


    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public int getImageId() {
        return imageId;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public static ArrayList<Text> getDefault() {
        ArrayList<Text> textList = new ArrayList<Text>();
        textList.add(new Text("英雄这种东西，当你想成为英雄的时候，你就已经失去了成为英雄的资格了。", "#所闻"));
        textList.add(new Text("两个人笑的是玩笑，一个人笑的是挖苦。", "#心灵砒霜"));
        textList.add(new Text("可爱是可以习得的。", "#心灵砒霜"));
        textList.add(new Text("人的心是散乱的", "#心灵砒霜"));
        return textList;
    }

}
