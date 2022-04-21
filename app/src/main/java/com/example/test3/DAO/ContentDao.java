package com.example.test3.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.test3.Entity.Content;

import java.util.List;

/**
 * @author Karl
 */
@Dao
public interface ContentDao {

    /**
     * 插入 content 表的某一行或某几行
     *
     * @param content Content 对象，表的行
     */
    @Insert
    void insert(Content... content);

    /**
     * 删除 content 表中的某一行或某几行
     *
     * @param content Content 对象，表的行
     */
    @Delete
    void delete(Content... content);

    /**
     * 更新content表中的某一行或某几行
     *
     * @param content Content 对象，表的行
     */
    @Update
    void update(Content... content);

    /**
     * 获取所有 content 表中的数据
     *
     * @return content 中的所有数据
     */
    @Query("select * from content")
    List<Content> getAll();

    /**
     * 加载所有text内容
     */
    @Query("select text from content")
    List<String> loadAllText();


    /**
     * 加载某一id对应的text内容
     *
     * @param id 查询特定内容的id
     * @return
     */
    @Query("select text from content where content_id = :id")
    String loadTextWhenIdIs(int id);

    /**
     * 查找包含特定关键字的text内容
     *
     * @param search 查找的内容
     * @return
     */
    @Query("select text from content where text like :search")
    List<String> findTextWhenTextLikes(String search);

    /**
     *
     * @param id
     * @return
     */
    @Query("select image_id from content where content_id = :id")
    int loadImageWhenIdIs(int id);


}
