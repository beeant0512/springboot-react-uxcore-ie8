package com.changan.carbond.spring.vo;

import com.changan.carbond.spring.model.Menu;

import java.util.List;

public class MenuVo extends Menu {
    private static final long serialVersionUID = -7412143773376214784L;

    private List<Menu> child;

    public List<Menu> getChild() {
        return child;
    }

    public void setChild(List<Menu> child) {
        this.child = child;
    }
}