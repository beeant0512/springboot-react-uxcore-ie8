package com.changan.carbond.spring.service;

import com.changan.carbond.common.IBaseService;
import com.changan.carbond.common.Msg;
import com.changan.carbond.spring.model.Menu;
import com.changan.carbond.spring.vo.MenuVo;

import java.util.List;

public interface IMenuService extends IBaseService<Menu> {

    Msg<List<MenuVo>> getTree();
}