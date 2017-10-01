package com.xstudio.spring.service;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.xstudio.common.IBaseService;
import com.xstudio.common.Msg;
import com.xstudio.spring.model.Menu;
import com.xstudio.spring.vo.MenuVo;

import java.util.List;

public interface IMenuService extends IBaseService<Menu> {

    Msg<List<MenuVo>> getTree();

    Msg<PageList<MenuVo>> fuzzySearchVoByPager(Menu menu, PageBounds pageBounds);
}