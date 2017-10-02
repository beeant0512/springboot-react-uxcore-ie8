package com.xstudio.mvc;


import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.xstudio.common.IBaseService;
import com.xstudio.common.Msg;
import com.xstudio.common.uxcore.SelectResponse;
import com.xstudio.common.uxcore.TablePageBounds;
import com.xstudio.common.uxcore.TableResponse;
import com.xstudio.spring.model.Menu;
import com.xstudio.spring.model.Role;
import com.xstudio.spring.model.User;
import com.xstudio.spring.service.IMenuService;
import com.xstudio.spring.service.IRoleService;
import com.xstudio.spring.vo.MenuVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("role")
public class RoleController extends BaseController<Role> {

    @Autowired
    private IRoleService roleService;

    @Override
    public IBaseService getBaseService() {
        return roleService;
    }

    @RequestMapping("members/{roleId}")
    List<User> roleMembers(@PathVariable(name = "roleId") Long roleId){
        return roleService.getRoleMembersByRoleId(roleId);
    }

    @RequestMapping("userRole/{userId}")
    SelectResponse<Role> userRole(@PathVariable Long userId){
        return roleService.getUserRole4SelectByUserId(userId);
    }
}
