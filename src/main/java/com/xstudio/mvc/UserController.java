package com.xstudio.mvc;


import com.xstudio.common.IBaseService;
import com.xstudio.common.Msg;
import com.xstudio.spring.model.User;
import com.xstudio.spring.model.UserRole;
import com.xstudio.spring.service.IUserRoleService;
import com.xstudio.spring.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("user")
public class UserController extends BaseController<User> {

    @Autowired
    private IUserService userService;

    @Autowired
    private IUserRoleService userRoleService;

    @Override
    public IBaseService getBaseService() {
        return userService;
    }

    @Override
    public Msg<User> post(User record, HttpServletRequest request, HttpServletResponse response) {
        Msg<User> insertResult = super.post(record, request, response);
        if (insertResult.getSuccess()) {
            updateUserRoles(request, insertResult);
        }
        return insertResult;
    }

    @Override
    public Msg<User> put(User record, @PathVariable(name = "id")  String id, HttpServletRequest request, HttpServletResponse response) {
        Msg<User> updateResult = super.put(record, id, request, response);
        if (updateResult.getSuccess()) {
            updateUserRoles(request, updateResult);
        }
        return updateResult;
    }

    @RequestMapping("resetpwd/{userId}")
    public Msg<String> resetpwd(String password, @PathVariable(name = "userId") Long userId){

       return userService.resetUserPasswordByUserId(userId, password);
    }

    private void updateUserRoles(HttpServletRequest request, Msg<User> updateResult) {
        UserRole userRole = new UserRole();
        userRole.setUserId(updateResult.getData().getUserId());
        userRoleService.deleteByExample(userRole);
        String[] roles = request.getParameterValues("roles");
        List<UserRole> userRoles = new ArrayList<>();
        for (String roleId : roles) {
            userRole = new UserRole();
            userRole.setRoleId(Long.valueOf(roleId));
            userRole.setUserId(updateResult.getData().getUserId());
            userRoles.add(userRole);
        }

        userRoleService.batchInsertSelective(userRoles);
    }
}
