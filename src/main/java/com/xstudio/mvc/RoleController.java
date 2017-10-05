package com.xstudio.mvc;


import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.xstudio.common.IBaseService;
import com.xstudio.common.Msg;
import com.xstudio.common.uxcore.SelectResponse;
import com.xstudio.common.uxcore.TablePageBounds;
import com.xstudio.common.uxcore.TableResponse;
import com.xstudio.spring.model.Role;
import com.xstudio.spring.model.User;
import com.xstudio.spring.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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

    @RequestMapping(value = "members/{roleId}", method = RequestMethod.GET)
    TableResponse<User> roleMembers(@PathVariable(name = "roleId") Long roleId, TablePageBounds tablePageBounds) {
        Msg<PageList<User>> roleMembersByRoleId = roleService.getRoleMembersByRoleId(roleId, tablePageBounds.getPageBounds());
        return new TableResponse<>(roleMembersByRoleId);
    }

    @RequestMapping(value = "members/{roleId}", method = RequestMethod.POST)
    Msg<List<User>> roleMembers(Long[] id, @PathVariable(name = "roleId") Long roleId) {
        return  roleService.removeMembers(roleId, id);
    }

    @RequestMapping("userRole/{userId}")
    SelectResponse<Role> userRole(@PathVariable Long userId) {
        return roleService.getUserRole4SelectByUserId(userId);
    }
}
