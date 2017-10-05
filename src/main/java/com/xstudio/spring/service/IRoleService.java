package com.xstudio.spring.service;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.xstudio.common.IBaseService;
import com.xstudio.common.Msg;
import com.xstudio.common.uxcore.SelectResponse;
import com.xstudio.common.uxcore.TablePageBounds;
import com.xstudio.spring.model.Role;
import com.xstudio.spring.model.User;

import java.util.List;

public interface IRoleService extends IBaseService<Role> {
    Msg<PageList<User>> getRoleMembersByRoleId(Long roleId, PageBounds pageBounds);

    SelectResponse<Role> getUserRole4SelectByUserId(Long userId);

    Msg<List<User>> removeMembers(Long roleId, Long[] userId);
}