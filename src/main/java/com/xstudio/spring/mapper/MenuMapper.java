package com.xstudio.spring.mapper;

import com.xstudio.common.IBaseDao;
import com.xstudio.spring.model.Menu;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MenuMapper extends IBaseDao<Menu> {
}