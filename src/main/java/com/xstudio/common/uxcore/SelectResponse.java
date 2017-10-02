package com.xstudio.common.uxcore;

import java.io.Serializable;
import java.util.List;

/**
 * @author xiaobiao
 * @version 1
 * @date 2017/10/2
 */
public class SelectResponse<T> implements Serializable {

    private static final long serialVersionUID = 2709983170159557021L;
    private List<T> selected;

    private List<T> unselected;

    public List<T> getSelected() {
        return selected;
    }

    public void setSelected(List<T> selected) {
        this.selected = selected;
    }

    public List<T> getUnselected() {
        return unselected;
    }

    public void setUnselected(List<T> unselected) {
        this.unselected = unselected;
    }
}
