package com.changan.carbond.spring.service;

import com.changan.carbond.common.AppUserDetails;
import com.changan.carbond.common.IBaseService;
import com.changan.carbond.spring.model.User;

public interface IUserService extends IBaseService<User> {
    AppUserDetails selectByUsername(String username);
}
