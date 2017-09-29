package com.xstudio.spring.service.impl;

import com.alibaba.fastjson.JSON;
import com.xstudio.common.BaseServiceImpl;
import com.xstudio.common.IBaseDao;
import com.xstudio.common.Msg;
import com.xstudio.common.enums.EnError;
import com.xstudio.common.utils.IdWorker;
import com.xstudio.spring.mapper.MenuMapper;
import com.xstudio.spring.model.Menu;
import com.xstudio.spring.service.IMenuService;
import com.xstudio.spring.vo.MenuVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        if(record.getMenuId() == null){
            record.setMenuId(IdWorker.getId());
        }
    }

    @Override
    public String getKeyValue(Menu record) {
        return null;
    }

    @Override
    public void emptyKeyValue(Menu record) {

    }


    @Override
    public Msg<List<MenuVo>> getTree() {
        Msg<List<MenuVo>> msg = new Msg<>();
        List<Menu> list = menuMapper.selectByExample(new Menu(), true);
        if (list.isEmpty()) {
            msg.setResult(EnError.NO_MATCH);
            return msg;
        }

        Map<Long, MenuVo> menuMap = new HashMap<>();
        MenuVo menuVo;
        for (Menu menu : list) {
            menuVo = JSON.parseObject(JSON.toJSONString(menu), MenuVo.class);
            menuVo.setChild(new ArrayList<>());
            menuMap.put(menu.getMenuId(), menuVo);
        }


        List<MenuVo> topMenuList = new ArrayList<>();

        for (Map.Entry<Long, MenuVo> menu : menuMap.entrySet()) {
            if (menu.getValue().getParentMenuId() != 0) {
                menuMap.get(menu.getValue().getParentMenuId()).getChild().add(menu.getValue());
            } else {
                topMenuList.add(menu.getValue());
            }
        }

        msg.setData(topMenuList);
        return msg;
    }
}