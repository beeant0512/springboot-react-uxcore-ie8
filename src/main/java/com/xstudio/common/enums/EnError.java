package com.xstudio.common.enums;

/**
 * Created by Beeant on 2017/2/26.
 */
public enum EnError {
    DEFAULT(0,""),
    INVALID_PASSWORD(1,"密码错误"),
    INVALID_USERNAME(2,"用户不存在"),
    CONFLICT(3, "已存在"), INSERT_NONE(4, "插入失败"), DELETE_NONE(5, "删除失败"), UPDATE_NONE(6, "更新失败"), NO_MATCH(7,"没有匹配数据" );

    private Integer code = 0;

    private String description = "";

    /**
     * Getter for property 'code'.
     *
     * @return Value for property 'code'.
     */
    public Integer getCode() {
        return code;
    }

    /**
     * Getter for property 'description'.
     *
     * @return Value for property 'description'.
     */
    public String getDescription() {
        return description;
    }

    EnError(Integer code, String description) {
        this.code = code;
        this.description = description;
    }
}
