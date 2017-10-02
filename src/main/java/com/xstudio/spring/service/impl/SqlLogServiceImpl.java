package com.xstudio.spring.service.impl;

import com.xstudio.common.utils.IdWorker;
import com.xstudio.common.BaseServiceImpl;
import com.xstudio.common.IBaseDao;
import com.xstudio.spring.mapper.SqlLogMapper;
import com.xstudio.spring.model.SqlLog;
import com.xstudio.spring.service.ISqlLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("SqlLogService")
public class SqlLogServiceImpl extends BaseServiceImpl<SqlLog> implements ISqlLogService {
    @Autowired
    private SqlLogMapper sqlLogMapper;

    @Override
    public IBaseDao<SqlLog> getRepositoryDao() {
        return this.sqlLogMapper;
    }

    @Override
    public void setDefaults(SqlLog record) {
        if(record.getId() == null ) {
            record.setId(IdWorker.getId());
        }
    }

    @Override
    public String getKeyValue(SqlLog record) {
        return Long.toString(record.getId());
    }

    @Override
    public void emptyKeyValue(SqlLog record) {
        record.setId(null);
    }

    @Override
    public void setKeyValue(SqlLog record, String keyValue) {
        record.setId(Long.valueOf(keyValue));
    }
}