package com.xstudio.spring.service.impl;


import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.xstudio.common.BaseServiceImpl;
import com.xstudio.common.IBaseDao;
import com.xstudio.common.Msg;
import com.xstudio.common.enums.EnError;
import com.xstudio.common.utils.IdWorker;
import com.xstudio.common.uxcore.SelectResponse;
import com.xstudio.common.uxcore.TablePageBounds;
import com.xstudio.spring.mapper.RoleMapper;
import com.xstudio.spring.mapper.UserRoleMapper;
import com.xstudio.spring.model.Role;
import com.xstudio.spring.model.User;
import com.xstudio.spring.model.UserRole;
import com.xstudio.spring.service.IRoleService;
import com.xstudio.spring.service.IUserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("RoleService")
public class RoleServiceImpl extends BaseServiceImpl<Role> implements IRoleService {
    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private UserRoleMapper userRoleMapper;

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
    public Msg<PageList<User>> getRoleMembersByRoleId(Long roleId, PageBounds pageBounds) {
        Msg<PageList<User>> msg = new Msg<>();
        PageList<User> list = roleMapper.getRoleMembersByRoleId(roleId, pageBounds);
        if(list.isEmpty()){
            msg.setResult(EnError.NO_MATCH);
            return msg;
        }

        msg.setData(list);
        return msg;
    }

    @Override
    public SelectResponse<Role> getUserRole4SelectByUserId(Long userId) {
        SelectResponse<Role> roleSelectResponse = new SelectResponse<>();
        roleSelectResponse.setSelected(roleMapper.getUserRoleSelectedByUserId(userId));
        roleSelectResponse.setUnselected(roleMapper.getUserRoleUnselectedByUserId(userId));
        return roleSelectResponse;
    }

    @Override
    public Msg<List<User>> removeMembers(Long roleId, Long[] userId) {
        Msg<List<User>> msg = new Msg<>();

        Integer i = roleMapper.removeMembers(roleId, userId);

        return msg;
    }

    @Override
    public Msg<Boolean> deleteByPrimaryKey(String keys) {
        UserRole userRole = new UserRole();
        userRole.setRoleId(Long.valueOf(keys));
        userRoleMapper.deleteByExample(userRole);
        return super.deleteByPrimaryKey(keys);
    }

    @Override
    public Msg<Integer> batchDeleteByPrimaryKey(String[] keys) {
        UserRole userRole = new UserRole();
        for (String key : keys) {
            userRole.setRoleId(Long.valueOf(key));
            userRoleMapper.deleteByExample(userRole);
        }

        return super.batchDeleteByPrimaryKey(keys);
    }
}