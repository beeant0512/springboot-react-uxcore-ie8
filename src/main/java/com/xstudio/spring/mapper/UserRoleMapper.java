package com.xstudio.spring.mapper;

import com.xstudio.common.IBaseDao;
import com.xstudio.spring.model.UserRole;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserRoleMapper extends IBaseDao<UserRole> {
}