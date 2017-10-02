package com.xstudio.spring.service.impl;


import com.xstudio.common.BaseServiceImpl;
import com.xstudio.common.IBaseDao;
import com.xstudio.common.utils.IdWorker;
import com.xstudio.common.uxcore.SelectResponse;
import com.xstudio.spring.mapper.RoleMapper;
import com.xstudio.spring.model.Role;
import com.xstudio.spring.model.User;
import com.xstudio.spring.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("RoleService")
public class RoleServiceImpl extends BaseServiceImpl<Role> implements IRoleService {
    @Autowired
    private RoleMapper roleMapper;

    @Override
    public IBaseDao<Role> getRepositoryDao() {
        return this.roleMapper;
    }

    @Override
    public void setDefaults(Role record) {
        if (record.getRoleId() == null) {
            record.setRoleId(IdWorker.getId());
        }
    }

    @Override
    public String getKeyValue(Role record) {
        return Long.toString(record.getRoleId());
    }

    @Override
    public void emptyKeyValue(Role record) {
        record.setRoleId(null);
    }

    @Override
    public void setKeyValue(Role record, String keyValue) {
        record.setRoleId(Long.valueOf(keyValue));
    }

    @Override
    public List<User> getRoleMembersByRoleId(Long roleId) {
        return roleMapper.getRoleMembersByRoleId(roleId);
    }

    @Override
    public SelectResponse<Role> getUserRole4SelectByUserId(Long userId) {
        SelectResponse<Role> roleSelectResponse = new SelectResponse<>();
        roleSelectResponse.setSelected(roleMapper.getUserRoleSelectedByUserId(userId));
        roleSelectResponse.setUnselected(roleMapper.getUserRoleUnselectedByUserId(userId));
        return roleSelectResponse;
    }
}