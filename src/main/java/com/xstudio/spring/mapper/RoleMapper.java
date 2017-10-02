package com.xstudio.spring.mapper;

import com.xstudio.common.IBaseDao;
import com.xstudio.spring.model.Role;
import com.xstudio.spring.model.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RoleMapper extends IBaseDao<Role> {
    List<User> getRoleMembersByRoleId(Long roleId);

    List<Role> getUserRoleSelectedByUserId(Long userId);

    List<Role> getUserRoleUnselectedByUserId(Long userId);
}