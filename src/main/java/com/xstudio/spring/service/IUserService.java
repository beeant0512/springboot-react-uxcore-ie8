package com.xstudio.spring.service;

import com.xstudio.common.AppUserDetails;
import com.xstudio.common.IBaseService;
import com.xstudio.common.Msg;
import com.xstudio.spring.model.User;

public interface IUserService extends IBaseService<User> {
    AppUserDetails selectByUsername(String username);

    Msg<String> resetUserPasswordByUserId(Long userId, String password);
}
