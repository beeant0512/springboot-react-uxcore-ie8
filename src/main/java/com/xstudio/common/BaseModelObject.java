package com.xstudio.common;


import com.xstudio.config.converter.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;


/**
 * Created by xiaobiao on 2017/2/24.
 */
public class BaseModelObject implements Serializable {

    private static final long serialVersionUID = 935758400379221981L;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.S")
    private Date createAt;

    private Long createBy;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.S")
    private Date updateAt;

    private Long updateBy;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.S")
    private Date createAtBegin;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.S")
    private Date createAtEnd;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.S")
    private Date updateAtBegin;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.S")
    private Date updateAtEnd;

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public Long getCreateBy() {
        return createBy;
    }

    public void setCreateBy(Long createBy) {
        this.createBy = createBy;
    }

    public Date getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Date updateAt) {
        this.updateAt = updateAt;
    }

    public Long getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(Long updateBy) {
        this.updateBy = updateBy;
    }

    public Date getCreateAtBegin() {
        return createAtBegin;
    }

    public void setCreateAtBegin(Date createAtBegin) {
        this.createAtBegin = createAtBegin;
    }

    public Date getCreateAtEnd() {
        return createAtEnd;
    }

    public void setCreateAtEnd(Date createAtEnd) {
        this.createAtEnd = createAtEnd;
    }

    public Date getUpdateAtBegin() {
        return updateAtBegin;
    }

    public void setUpdateAtBegin(Date updateAtBegin) {
        this.updateAtBegin = updateAtBegin;
    }

    public Date getUpdateAtEnd() {
        return updateAtEnd;
    }

    public void setUpdateAtEnd(Date updateAtEnd) {
        this.updateAtEnd = updateAtEnd;
    }
}
