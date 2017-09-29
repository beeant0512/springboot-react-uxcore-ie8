package com.xstudio.spring.mapper;

import com.xstudio.common.IBaseDao;
import com.xstudio.spring.model.SqlLog;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SqlLogMapper extends IBaseDao<SqlLog> {
}