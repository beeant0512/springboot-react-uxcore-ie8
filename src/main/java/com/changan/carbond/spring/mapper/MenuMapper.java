package com.changan.carbond.spring.mapper;

import com.changan.carbond.common.IBaseDao;
import com.changan.carbond.spring.model.Menu;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MenuMapper extends IBaseDao<Menu> {
    PageList<Menu> selectByExampleByPagerExtend(Menu record, boolean all, PageBounds pageBounds);
}