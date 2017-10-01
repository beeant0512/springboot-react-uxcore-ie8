package com.xstudio.mvc;


import com.xstudio.common.IBaseService;
import com.xstudio.common.Msg;
import com.xstudio.common.uxcore.TablePageBounds;
import com.xstudio.common.uxcore.TableResponse;
import com.xstudio.spring.model.Menu;
import com.xstudio.spring.service.IMenuService;
import com.xstudio.spring.vo.MenuVo;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("menu")
public class MenuController extends BaseController<Menu> {

    @Autowired
    private IMenuService menuService;

    @Override
    public IBaseService getBaseService() {
        return menuService;
    }

    @RequestMapping(value = {"tree"})
    @ResponseBody
    public Msg<List<MenuVo>> tree() {
        return menuService.getTree();
    }

    @RequestMapping("treeTable")
    @ResponseBody
    public TableResponse<MenuVo> treeTable(Menu menu, TablePageBounds tablePageBounds){
        Msg<PageList<MenuVo>> pageListMsg = menuService.fuzzySearchVoByPager(menu, tablePageBounds.getPageBounds());
        pageListMsg.getData().get(0).setData(new ArrayList<>());
        return new TableResponse<>(pageListMsg);
    }
}
