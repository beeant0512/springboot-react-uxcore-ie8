package com.xstudio.mvc;


import com.xstudio.common.IBaseService;
import com.xstudio.spring.model.User;
import com.xstudio.spring.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("user")
public class UserController extends BaseController<User> {

    @Autowired
    private IUserService userService;

    @Override
    public IBaseService getBaseService() {
        return userService;
    }
}
