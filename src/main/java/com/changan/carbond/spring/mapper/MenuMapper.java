package com.changan.carbond.spring.mapper;

import com.changan.carbond.common.IBaseDao;
import com.changan.carbond.spring.model.Menu;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MenuMapper extends IBaseDao<Menu> {
}