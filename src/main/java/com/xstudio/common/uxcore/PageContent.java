package com.xstudio.common.uxcore;

import java.io.Serializable;
import java.util.List;

/**
 * @author xiaobiao
 * @version 1
 * @date 2017/9/26
 */
public class PageContent<T> implements Serializable {

    private static final long serialVersionUID = -4234979458908464943L;
    private List<T> data;

    private Integer currentPage;

    private Integer totalCount;

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }
}
