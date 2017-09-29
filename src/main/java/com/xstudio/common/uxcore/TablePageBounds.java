package com.xstudio.common.uxcore;

import com.github.miemiedev.mybatis.paginator.domain.Order;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author xiaobiao
 * @version 1
 * @date 2017/9/26
 */
public class TablePageBounds implements Serializable {
    private static final long serialVersionUID = -5329695235556035225L;
    private Integer pageSize;

    private Integer currentPage;

    private String orderColumn;

    private String orderType;

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

    public String getOrderColumn() {
        return orderColumn;
    }

    public void setOrderColumn(String orderColumn) {
        this.orderColumn = orderColumn;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public PageBounds getPageBounds() {
        PageBounds pageBounds = new PageBounds();
        if (null != pageSize) {
            pageBounds.setLimit(this.pageSize);
        }

        if (null != currentPage) {
            pageBounds.setPage(currentPage);
        }

        if (null != orderColumn && null != orderType) {
            List<Order> orders = new ArrayList<>();
            orders.add(new Order(orderColumn, Order.Direction.valueOf(orderType.toUpperCase()), ""));
            pageBounds.setOrders(orders);
        }

        pageBounds.setContainsTotalCount(true);
        return pageBounds;
    }
}
