package com.xstudio.common;

import com.github.miemiedev.mybatis.paginator.domain.Order;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.xstudio.common.enums.EnError;
import com.xstudio.common.utils.ContextUtil;
import com.xstudio.common.utils.ListUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 基础服务
 * <p>
 * Created by Beeant on 2016/12/24.
 */
public abstract class BaseServiceImpl<T extends BaseModelObject> implements IBaseService<T> {

    private static int batchPerSqlNumber = 100;
    protected final Logger logger = LoggerFactory.getLogger(getClass());

    public abstract String getKeyValue(T record);

    public abstract void emptyKeyValue(T record);

    @Override
    public Msg<String> uniqueValid(T record) {
        Msg<String> msg = new Msg<>();
        if (null == getKeyValue(record) || "".equals(getKeyValue(record))) {
            Long existNumber = getRepositoryDao().countByExample(record);
            if (existNumber > 0) {
                msg.setResult(EnError.CONFLICT);
                return msg;
            }

            return msg;
        }

        /* 主键存在 参数获取的对象主键不一致时 返回错误 */
        String id = getKeyValue(record);
        emptyKeyValue(record);
        List<T> dbRecord = getRepositoryDao().selectByExample(record, true);
        if (null != dbRecord && !dbRecord.isEmpty() && !id.equals(getKeyValue(dbRecord.get(0)))) {
            msg.setResult(EnError.CONFLICT);
            return msg;
        }

        return msg;
    }

    @Override
    public Long countByExample(T record) {
        return getRepositoryDao().countByExample(record);
    }

    //    @Transactional
    @Override
    public Msg<T> insert(T record) {
        Msg<T> msg = new Msg<>();
        setDefaults(record);
        setCreateInfo(record);
        int result = getRepositoryDao().insert(record);
        if (0 == result) {
            msg.setResult(EnError.INSERT_NONE);
            return msg;
        }

        msg.setData(record);
        return msg;
    }

    public void setCreateInfo(T record) {
        record.setCreateAt(new Date());
        if (StringUtils.isEmpty(record.getCreateBy())) {
            AppUserDetails user = ContextUtil.getCurrentUser();
            if (null != user) {
                record.setCreateBy(user.getUserId());
            } else {
                record.setCreateBy(0L);
            }
        }
    }

    public void setUpdateInfo(T record) {
        record.setUpdateAt(new Date());
        AppUserDetails user = ContextUtil.getCurrentUser();
        if (null != user) {
            record.setUpdateBy(user.getUserId());
        } else {
            record.setUpdateBy(0L);
        }
    }

    //    @Transactional
    @Override
    public Msg<T> insertSelective(T record) {
        Msg<T> msg = new Msg<>();
        setDefaults(record);
        setCreateInfo(record);
        int result = getRepositoryDao().insertSelective(record);
        if (0 == result) {
            msg.setResult(EnError.INSERT_NONE);
            return msg;
        }

        msg.setData(record);
        return msg;
    }

    @Override
    public Msg<List<T>> batchInsert(List<T> records) {
        Msg<List<T>> msg = new Msg<>();
        if (null == records || records.isEmpty()) {
            msg.setResult(EnError.INSERT_NONE);
            return msg;
        }
        for (T record : records) {
            setDefaults(record);
            setCreateInfo(record);
        }

        int insertCount = 0;
        List<List<T>> lists = ListUtils.splitList(records, batchPerSqlNumber);
        for (List<T> list : lists) {
            insertCount = insertCount + getRepositoryDao().batchInsert(list);
        }
        if (0 == insertCount) {
            msg.setResult(EnError.INSERT_NONE);
            return msg;
        }

        msg.setData(records);
        return msg;
    }

    @Override
    public Msg<List<T>> batchInsertSelective(List<T> records) {
        Msg<List<T>> msg = new Msg<>();
        if (null == records || records.isEmpty()) {
            msg.setResult(EnError.INSERT_NONE);
            return msg;
        }
        for (T record : records) {
            setDefaults(record);
            setCreateInfo(record);
        }

        int insertCount = 0;
        List<List<T>> lists = ListUtils.splitList(records, batchPerSqlNumber);
        for (List<T> list : lists) {
            insertCount = insertCount + getRepositoryDao().batchInsertSelective(list);
        }

        if (0 == insertCount) {
            msg.setResult(EnError.INSERT_NONE);
            return msg;
        }
        msg.setData(records);

        return msg;
    }


    @Override
    public Msg<Boolean> deleteByPrimaryKey(String keys) {
        Msg<Boolean> msg = new Msg<>();
        int result = getRepositoryDao().deleteByPrimaryKey(keys);
        if (0 == result) {
            msg.setResult(EnError.DELETE_NONE);
            return msg;
        }

        return msg;
    }

    //    @Transactional
    @Override
    public Msg<Integer> deleteByExample(T record) {
        Msg<Integer> msg = new Msg<>();
        int result = getRepositoryDao().deleteByExample(record);
        if (0 == result) {
            msg.setResult(EnError.DELETE_NONE);
            return msg;
        }

        msg.setData(result);
        return msg;
    }

    //    @Transactional
    @Override
    public Msg<Integer> batchDeleteByPrimaryKey(String[] keys) {
        Msg<Integer> msg = new Msg<>();
        if (null == keys || 0 == keys.length) {
            msg.setResult(EnError.INSERT_NONE);
            return msg;
        }
        int result = getRepositoryDao().batchDeleteByPrimaryKey(keys);
        if (0 == result) {
            msg.setResult(EnError.DELETE_NONE);
            return msg;
        }

        msg.setData(result);
        return msg;
    }

    //    @Transactional
    @Override
    public Msg<T> updateByPrimaryKey(T record) {
        Msg<T> msg = new Msg<>();
        setDefaults(record);
        setUpdateInfo(record);
        int result = getRepositoryDao().updateByPrimaryKey(record);
        if (0 == result) {
            msg.setResult(EnError.UPDATE_NONE);
            return msg;
        }

        msg.setData(record);
        return msg;
    }

    //    @Transactional
    @Override
    public Msg<T> updateByPrimaryKeySelective(T record) {
        Msg<T> msg = new Msg<>();
        setDefaults(record);
        setUpdateInfo(record);
        int result = getRepositoryDao().updateByPrimaryKeySelective(record);
        if (0 == result) {
            msg.setResult(EnError.UPDATE_NONE);
            return msg;
        }

        // 重新获取数据
        T dbRecord = getRepositoryDao().selectByPrimaryKey(getKeyValue(record));
        msg.setData(dbRecord);
        return msg;
    }

    //    @Transactional
    @Override
    public Msg<T> updateByExample(T example, T record) {
        Msg<T> msg = new Msg<>();
        setDefaults(record);
        setUpdateInfo(record);
        int result = getRepositoryDao().updateByExample(example, record);
        if (0 == result) {
            msg.setResult(EnError.UPDATE_NONE);
            return msg;
        }

        // 重新获取数据
        T dbRecord = getRepositoryDao().selectByPrimaryKey(getKeyValue(record));
        msg.setData(dbRecord);
        return msg;
    }

    //    @Transactional
    @Override
    public Msg<T> updateByExampleSelective(T example, T record) {
        Msg<T> msg = new Msg<>();
        setDefaults(record);
        setUpdateInfo(record);
        int result = getRepositoryDao().updateByExampleSelective(example, record);
        if (0 == result) {
            msg.setResult(EnError.UPDATE_NONE);
            return msg;
        }

        // 重新获取数据
        T dbRecord = getRepositoryDao().selectByPrimaryKey(getKeyValue(record));
        msg.setData(dbRecord);
        return msg;
    }

    //    @Transactional
    @Override
    public Msg<List<T>> batchUpdateByPrimaryKey(List<T> records) {
        Msg<List<T>> msg = new Msg<>();
        if (null == records || records.isEmpty()) {
            msg.setResult(EnError.INSERT_NONE);
            return msg;
        }
        for (T record : records) {
            setDefaults(record);
            setUpdateInfo(record);
        }

        int count = 0;
        List<List<T>> lists = ListUtils.splitList(records, batchPerSqlNumber);
        for (List<T> list : lists) {
            count = count + getRepositoryDao().batchUpdateByPrimaryKey(list);
        }
        if (0 == count) {
            msg.setResult(EnError.UPDATE_NONE);
            return msg;
        }
        msg.setData(records);
        return msg;
    }

    //    @Transactional
    @Override
    public Msg<List<T>> batchUpdateByPrimaryKeySelective(List<T> records) {
        Msg<List<T>> msg = new Msg<>();
        if (null == records || records.isEmpty()) {
            msg.setResult(EnError.INSERT_NONE);
            return msg;
        }
        for (T record : records) {
            setDefaults(record);
            setUpdateInfo(record);
        }

        int count = 0;
        List<List<T>> lists = ListUtils.splitList(records, batchPerSqlNumber);
        for (List<T> list : lists) {
            count = count + getRepositoryDao().batchUpdateByPrimaryKeySelective(list);
        }

        if (0 == count) {
            msg.setResult(EnError.UPDATE_NONE);
            return msg;
        }

        msg.setData(records);
        return msg;
    }

    // @Transactional(readOnly = true)
    @Override
    public Msg<T> selectByPrimaryKey(String keys) {
        Msg<T> msg = new Msg<>();
        T result = getRepositoryDao().selectByPrimaryKey(keys);
        if (null == result) {
            msg.setResult(EnError.NO_MATCH);
            return msg;
        }

        msg.setData(result);
        return msg;
    }

    // @Transactional(readOnly = true)
    @Override
    public Msg<T> selectOneByExample(T record, boolean distinct) {
        Msg<T> msg = new Msg<>();
        List<T> result;
        try {
            result = getRepositoryDao().selectByExampleWithBLOBs(record, true);
        } catch (Exception e) {
            result = getRepositoryDao().selectByExample(record, true);
        }
        if (null == result || result.isEmpty()) {
            msg.setResult(EnError.NO_MATCH);
            return msg;
        }

        if (result.size() > 1) {
            msg.setResult(EnError.NO_MATCH);
            msg.setMsg("获取到的数据大于1条");
            return msg;
        }

        msg.setData(result.get(0));
        return msg;
    }

    @Override
    public Msg<T> selectOneByExampleWithBLOBs(T record, boolean distinct) {
        Msg<T> msg = new Msg<>();
        List<T> result = getRepositoryDao().selectByExampleWithBLOBs(record, true);

        if (null == result || result.isEmpty()) {
            msg.setResult(EnError.NO_MATCH);
            return msg;
        }

        if (result.size() > 1) {
            msg.setResult(EnError.NO_MATCH);
            msg.setMsg("获取到的数据大于1条");
            return msg;
        }

        msg.setData(result.get(0));
        return msg;
    }

    // @Transactional(readOnly = true)
    @Override
    public Msg<T> selectOneByExample(T record) {
        return selectOneByExample(record, true);
    }

    // @Transactional(readOnly = true)
    @Override
    public Msg<List<T>> selectAllByExample(T record) {
        List<Order> orders = new ArrayList<>();
        orders.add(new Order("create_at", Order.Direction.DESC, ""));
        return selectAllByExample(record, orders);
    }

    @Override
    public Msg<List<T>> selectAllByExample(T record, List<Order> orders) {
        Msg<List<T>> msg = new Msg<>();
        Msg<PageList<T>> pageListMsg = new Msg<>();
        List<T> list = new ArrayList<>();
        int limit = 5000;
        PageBounds pageBounds = new PageBounds(1, limit);
        pageBounds.setOrders(orders);
        boolean doLoop = true;
        while (pageListMsg.getSuccess() && doLoop) {
            pageListMsg = selectByExampleByPager(record, pageBounds);
            if (pageListMsg.getSuccess()) {
                doLoop = pageListMsg.getData().size() >= limit;
                list.addAll(pageListMsg.getData());
            }

            pageBounds.setPage(pageBounds.getPage() + 1);
        }

        if (list.isEmpty()) {
            msg.setCode(1);
            msg.setResult(EnError.NO_MATCH);
            return msg;
        }

        msg.setData(list);
        return msg;
    }

    // @Transactional(readOnly = true)
    @Override
    public Msg<PageList<T>> selectByExampleByPager(T record, PageBounds pageBounds) {
        Msg<PageList<T>> msg = new Msg<>();
        PageList<T> result = getRepositoryDao().selectByExampleByPager(record, pageBounds);
        if (result.isEmpty()) {
            msg.setResult(EnError.NO_MATCH);
            return msg;
        }

        msg.setData(result);
        return msg;
    }

    // @Transactional(readOnly = true)
    @Override
    public Msg<T> fuzzySearch(T record) {
        Msg<T> msg = new Msg<>();
        T result = getRepositoryDao().fuzzySearch(record);
        if (null == result) {
            msg.setResult(EnError.NO_MATCH);
            return msg;
        }

        msg.setData(result);
        return msg;
    }

    // @Transactional(readOnly = true)
    @Override
    public Msg<PageList<T>> fuzzySearchByPager(T record, PageBounds pageBounds) {
        Msg<PageList<T>> msg = new Msg<>();
        if (null == pageBounds.getOrders() && pageBounds.getOrders().isEmpty()) {
            List<Order> orders = new ArrayList<>();
            orders.add(new Order("createAt", Order.Direction.DESC, ""));
            pageBounds.setOrders(orders);
        }
        PageList<T> result = getRepositoryDao().fuzzySearchByPager(record, pageBounds);
        if (result.isEmpty()) {
            msg.setResult(EnError.NO_MATCH);
            return msg;
        }

        msg.setData(result);
        return msg;
    }
}
