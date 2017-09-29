package com.xstudio.spring.service.impl;

import com.xstudio.common.AppUserDetails;
import com.xstudio.common.BaseServiceImpl;
import com.xstudio.common.IBaseDao;
import com.xstudio.common.utils.IdWorker;
import com.xstudio.spring.mapper.UserMapper;
import com.xstudio.spring.model.User;
import com.xstudio.spring.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class UserServiceImpl extends BaseServiceImpl<User> implements IUserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public IBaseDao<User> getRepositoryDao() {
        return this.userMapper;
    }

    @Override
    public void setDefaults(User record) {
        if (record.getUserId() == null) {
            record.setUserId(IdWorker.getId());
        }
    }

    @Override
    public String getKeyValue(User record) {
        return Long.toString(record.getUserId());
    }

    @Override
    public void emptyKeyValue(User record) {
        record.setUserId(null);
    }

    @Override
    public AppUserDetails selectByUsername(String username) {
        return userMapper.selectByUsername(username);
    }

    public void updateLastLoginTime(Long userId) {
        User user = new User();
        user.setUserId(userId);
        user.setLastLoginAt(new Date());
        updateByPrimaryKeySelective(user);
    }
}