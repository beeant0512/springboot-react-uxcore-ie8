package com.xstudio.spring.vo;

import com.xstudio.spring.model.Role;
import com.xstudio.spring.model.User;

import java.util.List;

/**
 * @author xiaobiao
 * @version 1
 * @date 2017/10/2
 */
public class UserContext extends User {

    private static final long serialVersionUID = 9074219829519725972L;

    /**
     * 角色列表
     */
    private List<Role> roles;

    private List<MenuVo> menus;
    /**
     * 权限集
     */
    private List<String> perms;

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public List<String> getPerms() {
        return perms;
    }

    public void setPerms(List<String> perms) {
        this.perms = perms;
    }

    public List<MenuVo> getMenus() {
        return menus;
    }

    public void setMenus(List<MenuVo> menus) {
        this.menus = menus;
    }
}
