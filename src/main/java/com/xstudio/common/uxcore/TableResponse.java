package com.xstudio.common.uxcore;

import com.xstudio.common.Msg;
import com.github.miemiedev.mybatis.paginator.domain.PageList;

import java.io.Serializable;

/**
 * @author xiaobiao
 * @version 1
 * @date 2017/9/26
 */
public class TableResponse<T> implements Serializable {

    private static final long serialVersionUID = -3606980362737413691L;
    private Boolean success;

    private Integer errorCode;

    private String errorMsg;

    private PageContent<T> content;

    public TableResponse() {
    }

    public TableResponse(Msg<PageList<T>> msg) {
        this.success = msg.getSuccess();
        this.errorCode = msg.getCode();
        this.errorMsg = msg.getMsg();
        PageContent<T> pageContent = new PageContent<>();
        PageList<T> data = msg.getData();
        pageContent.setData(data);
        pageContent.setTotalCount(data.size());
        pageContent.setCurrentPage(1);
        if (data.getPaginator() != null) {
            pageContent.setTotalCount(data.getPaginator().getTotalCount());
            pageContent.setCurrentPage(data.getPaginator().getPage());
        }
        this.content = pageContent;
    }

    public Boolean getSuccess() {
        return errorCode == null || errorCode == 0;
    }

    public Integer getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public PageContent<T> getContent() {
        return content;
    }

    public void setContent(PageContent<T> content) {
        this.content = content;
    }
}
