package com.xstudio.spring.service;

import com.xstudio.common.IBaseService;
import com.xstudio.common.Msg;
import com.xstudio.spring.model.Menu;
import com.xstudio.spring.vo.MenuVo;

import java.util.List;

public interface IMenuService extends IBaseService<Menu> {

    Msg<List<MenuVo>> getTree();
}