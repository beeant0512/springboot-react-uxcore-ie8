package com.xstudio.spring.mapper;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.xstudio.common.IBaseDao;
import com.xstudio.common.uxcore.TablePageBounds;
import com.xstudio.spring.model.Role;
import com.xstudio.spring.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface RoleMapper extends IBaseDao<Role> {
    PageList<User> getRoleMembersByRoleId(@Param("roleId") Long roleId, @Param("pageBounds") PageBounds pageBounds);

    List<Role> getUserRoleSelectedByUserId(Long userId);

    List<Role> getUserRoleUnselectedByUserId(Long userId);

    Integer removeMembers(@Param("roleId") Long roleId, @Param("userId") Long[] userId);
}