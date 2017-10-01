package com.xstudio.mvc;


import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.xstudio.common.BaseModelObject;
import com.xstudio.common.IBaseService;
import com.xstudio.common.Msg;
import com.xstudio.common.uxcore.TablePageBounds;
import com.xstudio.common.uxcore.TableResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public abstract class BaseController<T extends BaseModelObject> {

    public abstract IBaseService getBaseService();

    public abstract String getViewFolder();

    @RequestMapping(value = {"", "index"})
    public String index() {
        return getViewFolder() + "/index";
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Msg<T> get(@PathVariable String id) {
        return getBaseService().selectByPrimaryKey(id);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public Msg<Boolean> delete(@PathVariable String id) {
        return getBaseService().deleteByPrimaryKey(id);
    }

    @RequestMapping(value = "delete", method = RequestMethod.POST)
    @ResponseBody
    public Msg<Integer> delete(String[] id) {
        return getBaseService().batchDeleteByPrimaryKey(id);
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    @ResponseBody
    public Msg<T> post(T record) {
        return getBaseService().insertSelective(record);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    @ResponseBody
    public Msg<T> put(T record, @PathVariable String id) {
        return getBaseService().updateByPrimaryKeySelective(record);
    }

    @RequestMapping(value = {"table"})
    @ResponseBody
    public TableResponse<T> table(T menu, TablePageBounds tablePageBounds) {
        Msg<PageList<T>> pageListMsg = getBaseService().fuzzySearchByPager(menu, tablePageBounds.getPageBounds());
        return new TableResponse<>(pageListMsg);
    }
}
