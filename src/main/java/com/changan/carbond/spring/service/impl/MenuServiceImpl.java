package com.changan.carbond.spring.service.impl;

import com.changan.carbond.common.BaseServiceImpl;
import com.changan.carbond.common.IBaseDao;
import com.changan.carbond.spring.mapper.MenuMapper;
import com.changan.carbond.spring.model.Menu;
import com.changan.carbond.spring.service.IMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("MenuService")
public class MenuServiceImpl extends BaseServiceImpl<Menu> implements IMenuService {
    @Autowired
    private MenuMapper menuMapper;

    @Override
    public IBaseDao<Menu> getRepositoryDao() {
        return this.menuMapper;
    }

    @Override
    public void setDefaults(Menu record) {

    }

    @Override
    public String getKeyValue(Menu record) {
        return null;
    }

    @Override
    public void emptyKeyValue(Menu record) {

    }
}