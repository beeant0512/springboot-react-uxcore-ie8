package com.changan.carbond.spring.mapper;

import com.changan.carbond.common.AppUserDetails;
import com.changan.carbond.common.IBaseDao;
import com.changan.carbond.spring.model.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends IBaseDao<User> {
    AppUserDetails selectByUsername(String username);
}