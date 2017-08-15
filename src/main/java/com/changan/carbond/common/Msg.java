package com.changan.carbond.common;


import com.changan.carbond.common.enums.EnError;

import java.io.Serializable;
import java.util.List;

/**
 * 统一消息返回对象
 * <p>
 * Created by xiaobiao on 2016/12/28.
 */
public class Msg<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    /* 错误码 */
    private Integer code = EnError.DEFAULT.getCode();

    /* 返回的数据，可以任意集合或对象 */
    private T data;

    /* 结果说明 */
    private String msg = EnError.DEFAULT.getDescription();

    private List<String> msgs;

    public Boolean getSuccess() {
        return 0 == this.code;
    }

    public void setResult(EnError result) {
        this.code = result.getCode();
        this.msg = result.getDescription();
    }

    /**
     * Getter for property 'code'.
     *
     * @return Value for property 'code'.
     */
    public Integer getCode() {
        return code;
    }

    /**
     * Setter for property 'code'.
     *
     * @param code Value to set for property 'code'.
     */
    public void setCode(Integer code) {
        this.code = code;
    }

    /**
     * Getter for property 'data'.
     *
     * @return Value for property 'data'.
     */
    public T getData() {
        return data;
    }

    /**
     * Setter for property 'data'.
     *
     * @param data Value to set for property 'data'.
     */
    public void setData(T data) {
        this.data = data;
    }

    /**
     * Getter for property 'msg'.
     *
     * @return Value for property 'msg'.
     */
    public String getMsg() {
        return msg;
    }

    /**
     * Setter for property 'msg'.
     *
     * @param msg Value to set for property 'msg'.
     */
    public void setMsg(String msg) {
        this.msg = msg;
    }

    /**
     * Getter for property 'msgs'.
     *
     * @return Value for property 'msgs'.
     */
    public List<String> getMsgs() {
        return msgs;
    }

    /**
     * Setter for property 'msgs'.
     *
     * @param msgs Value to set for property 'msgs'.
     */
    public void setMsgs(List<String> msgs) {
        this.msgs = msgs;
    }
}
