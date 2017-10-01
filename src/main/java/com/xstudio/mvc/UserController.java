package com.xstudio.mvc;


import com.xstudio.common.IBaseService;
import com.xstudio.common.Msg;
import com.xstudio.spring.model.Menu;
import com.xstudio.spring.model.User;
import com.xstudio.spring.service.IMenuService;
import com.xstudio.spring.service.IUserService;
import com.xstudio.spring.vo.MenuVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("user")
public class UserController extends BaseController<User> {

    @Autowired
    private IUserService userService;

    @Override
    public IBaseService getBaseService() {
        return userService;
    }

    @Override
    public String getViewFolder() {
        return "user";
    }
}
