package com.xstudio.spring.service;

import com.xstudio.common.IBaseService;
import com.xstudio.common.uxcore.SelectResponse;
import com.xstudio.spring.model.Role;
import com.xstudio.spring.model.User;

import java.util.List;

public interface IRoleService extends IBaseService<Role> {
    List<User> getRoleMembersByRoleId(Long roleId);

    SelectResponse<Role> getUserRole4SelectByUserId(Long userId);
}