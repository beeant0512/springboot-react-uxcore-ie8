package com.xstudio.mvc;


import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.xstudio.common.BaseModelObject;
import com.xstudio.common.IBaseService;
import com.xstudio.common.Msg;
import com.xstudio.common.uxcore.TablePageBounds;
import com.xstudio.common.uxcore.TableResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
public abstract class BaseController<T extends BaseModelObject> {

    public abstract IBaseService getBaseService();

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Msg<T> get(@PathVariable(name = "id") String id, HttpServletRequest request, HttpServletResponse response) {
        return getBaseService().selectByPrimaryKey(id);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public Msg<Boolean> delete(@PathVariable(name = "id") String id, HttpServletRequest request, HttpServletResponse response) {
        return getBaseService().deleteByPrimaryKey(id);
    }

    @RequestMapping(value = "delete", method = RequestMethod.POST)
    @ResponseBody
    public Msg<Integer> delete(String[] id, HttpServletRequest request, HttpServletResponse response) {
        return getBaseService().batchDeleteByPrimaryKey(id);
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    @ResponseBody
    public Msg<T> post(T record, HttpServletRequest request, HttpServletResponse response) {
        return getBaseService().insertSelective(record);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    @ResponseBody
    public Msg<T> put(T record, @PathVariable(name = "id") String id, HttpServletRequest request, HttpServletResponse response) {
        getBaseService().setKeyValue(record, id);
        return getBaseService().updateByPrimaryKeySelective(record);
    }

    @RequestMapping(value = {"table"})
    @ResponseBody
    public TableResponse<T> table(T menu, TablePageBounds tablePageBounds, HttpServletRequest request, HttpServletResponse response) {
        Msg<PageList<T>> pageListMsg = getBaseService().fuzzySearchByPager(menu, tablePageBounds.getPageBounds());
        return new TableResponse<>(pageListMsg);
    }
}
