package com.changan.carbond.mvc;


import com.changan.carbond.common.IBaseService;
import com.changan.carbond.common.Msg;
import com.changan.carbond.common.uxcore.TablePageBounds;
import com.changan.carbond.common.uxcore.TableResponse;
import com.changan.carbond.spring.model.Menu;
import com.changan.carbond.spring.service.IMenuService;
import com.changan.carbond.spring.vo.MenuVo;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
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
