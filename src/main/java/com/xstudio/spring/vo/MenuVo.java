package com.xstudio.spring.vo;

import com.xstudio.spring.model.Menu;

import java.util.List;

public class MenuVo extends Menu {
    private static final long serialVersionUID = -7412143773376214784L;

    private List<Menu> data;

    public List<Menu> getData() {
        return data;
    }

    public void setData(List<Menu> data) {
        this.data = data;
    }
}
