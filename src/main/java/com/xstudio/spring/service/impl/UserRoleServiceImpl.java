package com.xstudio.spring.service.impl;

import com.xstudio.common.BaseServiceImpl;
import com.xstudio.common.IBaseDao;
import com.xstudio.common.utils.IdWorker;
import com.xstudio.spring.mapper.UserRoleMapper;
import com.xstudio.spring.model.UserRole;
import com.xstudio.spring.service.IUserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("UserRoleService")
public class UserRoleServiceImpl extends BaseServiceImpl<UserRole> implements IUserRoleService {
    @Autowired
    private UserRoleMapper userRoleMapper;

    @Override
    public IBaseDao<UserRole> getRepositoryDao() {
        return this.userRoleMapper;
    }

    @Override
    public void setDefaults(UserRole record) {
        if(record.getId() == null ) {
            record.setId(IdWorker.getId());
        }
    }

    @Override
    public String getKeyValue(UserRole record) {
        return Long.toString(record.getId());
    }

    @Override
    public void emptyKeyValue(UserRole record) {
        record.setId(null);
    }

    @Override
    public void setKeyValue(UserRole record, String keyValue) {
        record.setId(Long.valueOf(keyValue));
    }
}