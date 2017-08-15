package com.changan.carbond.spring.service.impl;

import com.changan.carbond.common.AppUserDetails;
import com.changan.carbond.common.BaseServiceImpl;
import com.changan.carbond.common.IBaseDao;
import com.changan.carbond.common.utils.IdWorker;
import com.changan.carbond.spring.mapper.UserMapper;
import com.changan.carbond.spring.model.User;
import com.changan.carbond.spring.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
        if(record.getUserId() == null ) {
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
}