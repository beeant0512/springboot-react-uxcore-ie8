package com.changan.carbond.spring.model;

import com.changan.carbond.common.BaseModelObject;
import java.io.Serializable;

/**
 *
 * This class was generated by MyBatis Generator.
 * This class corresponds to the database table application_log_user_login
 *
 * @mbg.generated do_not_delete_during_merge
 */
public class LogUserLogin extends BaseModelObject implements Serializable {
    /**
     * Database Column Remarks:
     *   id
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column application_log_user_login.log_login_id
     *
     * @mbg.generated
     */
    private Long logLoginId;

    /**
     * Database Column Remarks:
     *   ip address
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column application_log_user_login.ip
     *
     * @mbg.generated
     */
    private String ip;

    /**
     * Database Column Remarks:
     *   login agent info
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column application_log_user_login.agent
     *
     * @mbg.generated
     */
    private String agent;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table application_log_user_login
     *
     * @mbg.generated
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column application_log_user_login.log_login_id
     *
     * @return the value of application_log_user_login.log_login_id
     *
     * @mbg.generated
     */
    public Long getLogLoginId() {
        return logLoginId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column application_log_user_login.log_login_id
     *
     * @param logLoginId the value for application_log_user_login.log_login_id
     *
     * @mbg.generated
     */
    public void setLogLoginId(Long logLoginId) {
        this.logLoginId = logLoginId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column application_log_user_login.ip
     *
     * @return the value of application_log_user_login.ip
     *
     * @mbg.generated
     */
    public String getIp() {
        return ip;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column application_log_user_login.ip
     *
     * @param ip the value for application_log_user_login.ip
     *
     * @mbg.generated
     */
    public void setIp(String ip) {
        this.ip = ip == null ? null : ip.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column application_log_user_login.agent
     *
     * @return the value of application_log_user_login.agent
     *
     * @mbg.generated
     */
    public String getAgent() {
        return agent;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column application_log_user_login.agent
     *
     * @param agent the value for application_log_user_login.agent
     *
     * @mbg.generated
     */
    public void setAgent(String agent) {
        this.agent = agent == null ? null : agent.trim();
    }
}