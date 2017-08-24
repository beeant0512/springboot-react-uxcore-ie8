package com.changan.carbond.mvc;


import com.changan.carbond.common.BaseModelObject;
import com.changan.carbond.common.IBaseService;
import com.changan.carbond.common.Msg;
import com.changan.carbond.common.enums.EnError;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public abstract class BaseController<T extends BaseModelObject> {

    public abstract IBaseService getBaseService();

    public abstract String getViewFolder();

    @RequestMapping(value = {"","index"})
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

    @RequestMapping(value = "", method = RequestMethod.POST)
    @ResponseBody
    public Msg<T> post(T record) {
        return getBaseService().insertSelective(record);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    @ResponseBody
    public Msg<T> put(T record, @PathVariable String id) {
        Msg<T> msg = new Msg<>();
        msg.setResult(EnError.NO_MATCH);
        return msg;
    }
}
