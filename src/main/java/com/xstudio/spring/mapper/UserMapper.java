package com.xstudio.spring.mapper;

import com.xstudio.common.AppUserDetails;
import com.xstudio.common.IBaseDao;
import com.xstudio.spring.model.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends IBaseDao<User> {
    AppUserDetails selectByUsername(String username);
}