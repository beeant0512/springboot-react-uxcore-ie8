package com.xstudio.aop.dbchange;

import java.io.Serializable;

/**
 * @author xiaobiao
 * @version 1
 * @date 2017/9/29
 */
public class DBLogOption implements Serializable {

    /**
     * 日志ID
     */
    private Long logId;

    /**
     * 客户端IP
     */
    private String clientIp;

    /**
     * 客户端版本信息
     */
    private String userAgent;

    /**
     * 操作员ID
     */
    private String actorId;

    /**
     * 执行方法名称
     */
    private String method;

    /**
     * 服务器名称
     */
    private String serverName;

    /**
     * 服务器端口
     */
    private Integer serverPort;

    /**
     * 修改的表名
     */
    private String tableName;

    /**
     * 修改前后字段值对比
     */
    private String diff;

    /**
     * update sql
     */
    private String sql;

    public Long getLogId() {
        return logId;
    }

    public void setLogId(Long logId) {
        this.logId = logId;
    }

    public String getClientIp() {
        return clientIp;
    }

    public void setClientIp(String clientIp) {
        this.clientIp = clientIp;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public String getActorId() {
        return actorId;
    }

    public void setActorId(String actorId) {
        this.actorId = actorId;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    public Integer getServerPort() {
        return serverPort;
    }

    public void setServerPort(Integer serverPort) {
        this.serverPort = serverPort;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getDiff() {
        return diff;
    }

    public void setDiff(String diff) {
        this.diff = diff;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }
}
