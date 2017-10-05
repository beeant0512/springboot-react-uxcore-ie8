package com.xstudio.spring.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.xstudio.common.BaseServiceImpl;
import com.xstudio.common.IBaseDao;
import com.xstudio.common.Msg;
import com.xstudio.common.enums.EnError;
import com.xstudio.common.utils.IdWorker;
import com.xstudio.spring.mapper.MenuMapper;
import com.xstudio.spring.model.Menu;
import com.xstudio.spring.model.Role;
import com.xstudio.spring.service.IMenuService;
import com.xstudio.spring.vo.MenuVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

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
        if (record.getMenuId() == null) {
            record.setMenuId(IdWorker.getId());
        }

        if (StringUtils.isEmpty(record.getParentMenuId())) {
            record.setParentMenuId(0L);
        }

        if (null == record.getLevel()) {
            record.setLevel(1);
        }

        record.setFullPath(Long.toString(record.getMenuId()).concat("-"));
        // 存在父节点，获取父节点信息，并补充当前节点的信息
        if (0L != record.getParentMenuId()) {
            Menu parent = menuMapper.selectByPrimaryKey(Long.toString(record.getParentMenuId()));
            record.setLevel(parent.getLevel() + 1);
            record.setFullPath(parent.getFullPath().concat(record.getFullPath()));
        }
    }

    @Override
    public void setUpdateInfo(Menu record) {
        record.setFullPath(Long.toString(record.getMenuId()).concat("-"));
        // 存在父节点，获取父节点信息，并补充当前节点的信息
        if (0L != record.getParentMenuId()) {
            Menu parent = menuMapper.selectByPrimaryKey(Long.toString(record.getParentMenuId()));
            record.setLevel(parent.getLevel() + 1);
            record.setFullPath(parent.getFullPath().concat(record.getFullPath()));
        }
        super.setUpdateInfo(record);
    }

    @Override
    public String getKeyValue(Menu record) {
        return null;
    }

    @Override
    public void emptyKeyValue(Menu record) {
        record.setMenuId(null);
    }

    @Override
    public void setKeyValue(Menu record, String keyValue) {
        record.setMenuId(Long.valueOf(keyValue));
    }

    @Override
    public Msg<List<MenuVo>> getTree() {
        Msg<List<MenuVo>> msg = new Msg<>();
        Menu example = new Menu();
        example.setType("link");
        List<Menu> list = menuMapper.selectByExample(example, true);
        if (list.isEmpty()) {
            msg.setResult(EnError.NO_MATCH);
            return msg;
        }

        Map<Long, MenuVo> menuMap = new HashMap<>();
        MenuVo menuVo;
        for (Menu menu : list) {
            menuVo = JSON.parseObject(JSON.toJSONString(menu), MenuVo.class);
            menuVo.setData(new ArrayList<>());
            menuMap.put(menu.getMenuId(), menuVo);
        }

        List<MenuVo> topMenuList = new ArrayList<>();

        for (Map.Entry<Long, MenuVo> menu : menuMap.entrySet()) {
            if (menu.getValue().getParentMenuId() != 0) {
                menuMap.get(menu.getValue().getParentMenuId()).getData().add(menu.getValue());
            } else {
                topMenuList.add(menu.getValue());
            }
        }

        msg.setData(topMenuList);
        return msg;
    }

    @Override
    public Msg<PageList<MenuVo>> fuzzySearchVoByPager(Menu menu, PageBounds pageBounds) {
        Msg<PageList<Menu>> pageListMsg = super.fuzzySearchByPager(menu, pageBounds);
        Msg<PageList<MenuVo>> listMsg = JSON.parseObject(JSON.toJSONString(pageListMsg), new TypeReference<Msg<PageList<MenuVo>>>(){});
        return listMsg;
    }

    @Override
    public List<MenuVo> getPermissionMenusByRoles(List<Role> roles) {
        List<MenuVo> list = new ArrayList<>();
        // todo permission menus
//        List<String> fullpaths = menuMapper.getMenuFullpathByRoles(roles);
//        List<Menu> menus = menuMapper.getPermissionMenusByRoles(roles);

        return list;
    }
}