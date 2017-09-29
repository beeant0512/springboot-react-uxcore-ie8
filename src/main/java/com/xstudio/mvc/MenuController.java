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

import java.util.List;

@Controller
@RequestMapping("menu")
public class MenuController extends BaseController<Menu> {

    @Autowired
    private IMenuService menuService;

    @Override
    public IBaseService getBaseService() {
        return menuService;
    }

    @Override
    public String getViewFolder() {
        return "menu";
    }

    @RequestMapping(value = {"tree"})
    @ResponseBody
    public Msg<List<MenuVo>> tree() {
        return menuService.getTree();
    }

    @RequestMapping(value = {"table"})
    @ResponseBody
    public TableResponse<Menu> table(Menu menu, TablePageBounds tablePageBounds) {
        Msg<PageList<Menu>> pageListMsg = menuService.fuzzySearchByPager(menu, tablePageBounds.getPageBounds());

        return new TableResponse<>(pageListMsg);
    }
}
