package com.changan.carbond.spring.service.impl;

import com.changan.carbond.common.BaseServiceImpl;
import com.changan.carbond.common.IBaseDao;
import com.changan.carbond.common.Msg;
import com.changan.carbond.common.enums.EnError;
import com.changan.carbond.spring.mapper.MenuMapper;
import com.changan.carbond.spring.model.Menu;
import com.changan.carbond.spring.service.IMenuService;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
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

    @Override
    public Msg<PageList<Menu>> selectByExampleByPager(Menu record, PageBounds pageBounds) {
        Msg<PageList<Menu>> msg = new Msg<>();
        PageList<Menu> result = menuMapper.selectByExampleByPagerExtend(record, true, pageBounds);
        if (result.isEmpty()) {
            msg.setResult(EnError.NO_MATCH);
            return msg;
        }

        msg.setData(result);
        return msg;
    }
}