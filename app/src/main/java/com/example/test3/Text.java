package com.example.test3;

import java.util.ArrayList;

/**
 * 输入的内容项
 * @author Karl
 */
public class Text {
    private int textId;
    private String content;
    private int imageId;
    private String tag;

    public Text(int textId, String content, int imageId, String tag) {
        this.textId = textId;
        this.content = content;
        this.imageId = imageId;
        this.tag = tag;
    }

    public Text(int textId, String content) {
        this.textId = textId;
        this.content = content;
    }

    public Text(int textId, String content, String tag) {
        this.textId = textId;
        this.content = content;
        this.tag = tag;
    }

    public Text(int textId, int imageId) {
        this.textId = textId;
        this.imageId = imageId;
    }

    public Text(int textId, int imageId, String tag) {
        this.textId = textId;
        this.imageId = imageId;
        this.tag = tag;
    }

    public Text(String content, String tag) {
        this.content = content;
        this.tag = tag;
    }

    public int getTextId() {
        return textId;
    }

    public String getContent() {
        return content;
    }

    public int getImageId() {
        return imageId;
    }

    public String getTag() {
        return tag;
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
