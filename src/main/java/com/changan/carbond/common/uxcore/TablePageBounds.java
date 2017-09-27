package com.changan.carbond.common.uxcore;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;

import java.io.Serializable;

/**
 * @author xiaobiao
 * @version 1
 * @date 2017/9/26
 */
public class TablePageBounds implements Serializable {
    private static final long serialVersionUID = -5329695235556035225L;
    private Integer pageSize;

    private Integer currentPage;

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public PageBounds getPageBounds() {
        PageBounds pageBounds = new PageBounds();
        if (null != pageSize) {
            pageBounds.setLimit(this.pageSize);
        }

        if (null != currentPage) {
            pageBounds.setPage(currentPage);
        }

        pageBounds.setContainsTotalCount(true);
        return pageBounds;
    }
}
