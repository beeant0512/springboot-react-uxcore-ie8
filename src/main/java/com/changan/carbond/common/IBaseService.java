package com.changan.carbond.common;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;

import java.util.List;

/**
 * Created by Beeant on 2016/12/24.
 */
public interface IBaseService<T extends BaseModelObject> {

    Long countByExample(T record);

    Msg<T> insert(T record);

    Msg<T> insertSelective(T record);

    Msg<List<T>> batchInsert(List<T> record);

    Msg<List<T>> batchInsertSelective(List<T> record);

    Msg<Boolean> deleteByPrimaryKey(String keys);

    Msg<Integer> deleteByExample(T record);

    Msg<Integer> batchDeleteByPrimaryKey(String[] keys);

    Msg<T> updateByPrimaryKey(T record);

    Msg<T> updateByPrimaryKeySelective(T record);

    Msg<T> updateByExample(T example, T record);

    Msg<T> updateByExampleSelective(T example, T record);

    Msg<List<T>> batchUpdateByPrimaryKey(List<T> record);

    Msg<List<T>> batchUpdateByPrimaryKeySelective(List<T> record);

    Msg<T> selectByPrimaryKey(String keys);

    Msg<T> selectOneByExample(T record, boolean distinct);

    Msg<T> selectOneByExample(T record);

    Msg<List<T>> selectAllByExample(T record);

    Msg<PageList<T>> selectByExampleByPager(T record, PageBounds pageBounds);

    Msg<T> fuzzySearch(T record);

    Msg<PageList<T>> fuzzySearchByPager(T record, PageBounds pageBounds);

    IBaseDao<T> getRepositoryDao();

    void setDefaults(T record);

    /**
     *
     * @param record 待验证对象字段
     * @return 验证结果 msg.code == 0 时表示该字段可用
     */
    Msg<String> uniqueValid(T record);
}
