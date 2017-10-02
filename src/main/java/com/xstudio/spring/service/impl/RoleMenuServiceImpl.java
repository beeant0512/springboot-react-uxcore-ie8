package com.xstudio.spring.service.impl;

import com.xstudio.common.BaseServiceImpl;
import com.xstudio.common.IBaseDao;
import com.xstudio.common.utils.IdWorker;
import com.xstudio.spring.mapper.RoleMenuMapper;
import com.xstudio.spring.model.RoleMenu;
import com.xstudio.spring.service.IRoleMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("RoleMenuService")
public class RoleMenuServiceImpl extends BaseServiceImpl<RoleMenu> implements IRoleMenuService {
    @Autowired
    private RoleMenuMapper roleMenuMapper;

    @Override
    public IBaseDao<RoleMenu> getRepositoryDao() {
        return this.roleMenuMapper;
    }

    @Override
    public void setDefaults(RoleMenu record) {
        if(record.getId() == null ) {
            record.setId(IdWorker.getId());
        }
    }

    @Override
    public String getKeyValue(RoleMenu record) {
        return Long.toString(record.getId());
    }

    @Override
    public void emptyKeyValue(RoleMenu record) {
        record.setId(null);
    }

    @Override
    public void setKeyValue(RoleMenu record, String keyValue) {
        record.setId(Long.valueOf(keyValue));
    }
}