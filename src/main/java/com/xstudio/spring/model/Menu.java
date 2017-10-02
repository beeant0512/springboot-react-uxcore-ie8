package com.xstudio.spring.model;

import com.xstudio.common.BaseModelObject;
import java.io.Serializable;

/**
 *
 * This class was generated by MyBatis Generator.
 * This class corresponds to the database table application_menu
 *
 * @mbg.generated do_not_delete_during_merge
 */
public class Menu extends BaseModelObject implements Serializable {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column application_menu.menu_id
     *
     * @mbg.generated
     */
    private Long menuId;

    /**
     * Database Column Remarks:
     *   parent menu id in a tree node
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column application_menu.parent_menu_id
     *
     * @mbg.generated
     */
    private Long parentMenuId;

    /**
     * Database Column Remarks:
     *   orders number
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column application_menu.order_number
     *
     * @mbg.generated
     */
    private Integer orderNumber;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column application_menu.type
     *
     * @mbg.generated
     */
    private String type;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column application_menu.menu_name
     *
     * @mbg.generated
     */
    private String menuName;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column application_menu.menu_url
     *
     * @mbg.generated
     */
    private String menuUrl;

    /**
     * Database Column Remarks:
     *   permision
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column application_menu.menu_perm
     *
     * @mbg.generated
     */
    private String menuPerm;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column application_menu.full_path
     *
     * @mbg.generated
     */
    private String fullPath;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column application_menu.icon
     *
     * @mbg.generated
     */
    private String icon;

    /**
     * Database Column Remarks:
     *   menu level in a tree node
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column application_menu.level
     *
     * @mbg.generated
     */
    private Integer level;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column application_menu.position
     *
     * @mbg.generated
     */
    private String position;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table application_menu
     *
     * @mbg.generated
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column application_menu.menu_id
     *
     * @return the value of application_menu.menu_id
     *
     * @mbg.generated
     */
    public Long getMenuId() {
        return menuId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column application_menu.menu_id
     *
     * @param menuId the value for application_menu.menu_id
     *
     * @mbg.generated
     */
    public void setMenuId(Long menuId) {
        this.menuId = menuId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column application_menu.parent_menu_id
     *
     * @return the value of application_menu.parent_menu_id
     *
     * @mbg.generated
     */
    public Long getParentMenuId() {
        return parentMenuId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column application_menu.parent_menu_id
     *
     * @param parentMenuId the value for application_menu.parent_menu_id
     *
     * @mbg.generated
     */
    public void setParentMenuId(Long parentMenuId) {
        this.parentMenuId = parentMenuId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column application_menu.order_number
     *
     * @return the value of application_menu.order_number
     *
     * @mbg.generated
     */
    public Integer getOrderNumber() {
        return orderNumber;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column application_menu.order_number
     *
     * @param orderNumber the value for application_menu.order_number
     *
     * @mbg.generated
     */
    public void setOrderNumber(Integer orderNumber) {
        this.orderNumber = orderNumber;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column application_menu.type
     *
     * @return the value of application_menu.type
     *
     * @mbg.generated
     */
    public String getType() {
        return type;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column application_menu.type
     *
     * @param type the value for application_menu.type
     *
     * @mbg.generated
     */
    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column application_menu.menu_name
     *
     * @return the value of application_menu.menu_name
     *
     * @mbg.generated
     */
    public String getMenuName() {
        return menuName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column application_menu.menu_name
     *
     * @param menuName the value for application_menu.menu_name
     *
     * @mbg.generated
     */
    public void setMenuName(String menuName) {
        this.menuName = menuName == null ? null : menuName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column application_menu.menu_url
     *
     * @return the value of application_menu.menu_url
     *
     * @mbg.generated
     */
    public String getMenuUrl() {
        return menuUrl;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column application_menu.menu_url
     *
     * @param menuUrl the value for application_menu.menu_url
     *
     * @mbg.generated
     */
    public void setMenuUrl(String menuUrl) {
        this.menuUrl = menuUrl == null ? null : menuUrl.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column application_menu.menu_perm
     *
     * @return the value of application_menu.menu_perm
     *
     * @mbg.generated
     */
    public String getMenuPerm() {
        return menuPerm;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column application_menu.menu_perm
     *
     * @param menuPerm the value for application_menu.menu_perm
     *
     * @mbg.generated
     */
    public void setMenuPerm(String menuPerm) {
        this.menuPerm = menuPerm == null ? null : menuPerm.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column application_menu.full_path
     *
     * @return the value of application_menu.full_path
     *
     * @mbg.generated
     */
    public String getFullPath() {
        return fullPath;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column application_menu.full_path
     *
     * @param fullPath the value for application_menu.full_path
     *
     * @mbg.generated
     */
    public void setFullPath(String fullPath) {
        this.fullPath = fullPath == null ? null : fullPath.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column application_menu.icon
     *
     * @return the value of application_menu.icon
     *
     * @mbg.generated
     */
    public String getIcon() {
        return icon;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column application_menu.icon
     *
     * @param icon the value for application_menu.icon
     *
     * @mbg.generated
     */
    public void setIcon(String icon) {
        this.icon = icon == null ? null : icon.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column application_menu.level
     *
     * @return the value of application_menu.level
     *
     * @mbg.generated
     */
    public Integer getLevel() {
        return level;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column application_menu.level
     *
     * @param level the value for application_menu.level
     *
     * @mbg.generated
     */
    public void setLevel(Integer level) {
        this.level = level;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column application_menu.position
     *
     * @return the value of application_menu.position
     *
     * @mbg.generated
     */
    public String getPosition() {
        return position;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column application_menu.position
     *
     * @param position the value for application_menu.position
     *
     * @mbg.generated
     */
    public void setPosition(String position) {
        this.position = position == null ? null : position.trim();
    }
}