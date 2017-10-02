package com.xstudio.spring.mapper;

import com.xstudio.common.IBaseDao;
import com.xstudio.spring.model.Menu;
import com.xstudio.spring.model.Role;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MenuMapper extends IBaseDao<Menu> {
    List<Menu> getPermissionMenusByRoles(List<Role> roles);

    List<String> getMenuFullpathByRoles(List<Role> roles);
}