package com.xstudio.spring.mapper;

import com.xstudio.common.IBaseDao;
import com.xstudio.spring.model.LogUserLogin;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface LogUserLoginMapper extends IBaseDao<LogUserLogin> {
}