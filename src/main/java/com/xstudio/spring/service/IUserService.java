package com.xstudio.spring.service;

import com.xstudio.common.AppUserDetails;
import com.xstudio.common.IBaseService;
import com.xstudio.spring.model.User;

public interface IUserService extends IBaseService<User> {
    AppUserDetails selectByUsername(String username);
}
