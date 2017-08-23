package com.changan.carbond.spring.service.impl;

import com.changan.carbond.common.BaseServiceImpl;
import com.changan.carbond.common.IBaseDao;
import com.changan.carbond.common.utils.IdWorker;
import com.changan.carbond.spring.mapper.LogUserLoginMapper;
import com.changan.carbond.spring.model.LogUserLogin;
import com.changan.carbond.spring.service.ILogUserLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("LogUserLoginService")
public class LogUserLoginServiceImpl extends BaseServiceImpl<LogUserLogin> implements ILogUserLoginService {
    @Autowired
    private LogUserLoginMapper logUserLoginMapper;

    @Override
    public IBaseDao<LogUserLogin> getRepositoryDao() {
        return this.logUserLoginMapper;
    }

    @Override
    public void setDefaults(LogUserLogin record) {
        if(record.getLogLoginId() == null ) {
            record.setLogLoginId(IdWorker.getId());
        }
    }

    @Override
    public String getKeyValue(LogUserLogin record) {
        return Long.toString(record.getLogLoginId());
    }

    @Override
    public void emptyKeyValue(LogUserLogin record) {
        record.setLogLoginId(null);
    }
}