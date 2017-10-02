package com.xstudio.spring.service.impl;

import com.xstudio.common.BaseServiceImpl;
import com.xstudio.common.IBaseDao;
import com.xstudio.common.utils.IdWorker;
import com.xstudio.spring.mapper.LogUserLoginMapper;
import com.xstudio.spring.model.LogUserLogin;
import com.xstudio.spring.service.ILogUserLoginService;
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
    public void setKeyValue(LogUserLogin record, String keyValue) {
        record.setLogLoginId(Long.valueOf(keyValue));
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