package com.maben.pojo;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @description:
 * @author: maben
 * @createDate: 2020/4/9
 */
public class RouteModel  extends BasePo{

    private Integer id;
    private String routeId;
    private String routeUri;
    private Integer routeOrder;
    private String predicates;
    private String filters;
    /**
     * 是否启用 0:未启用 1:已启用
     */
    private Integer enabled;
    /**
     * 是否删除 0:未删除 1:已删除
     */
    private Integer deleted;
    private String createTime;
    private String updateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRouteId() {
        return routeId;
    }

    public void setRouteId(String routeId) {
        this.routeId = routeId;
    }

    public String getRouteUri() {
        return routeUri;
    }

    public void setRouteUri(String routeUri) {
        this.routeUri = routeUri;
    }

    public Integer getRouteOrder() {
        return routeOrder;
    }

    public void setRouteOrder(Integer routeOrder) {
        this.routeOrder = routeOrder;
    }

    public String getPredicates() {
        return predicates;
    }

    public void setPredicates(String predicates) {
        this.predicates = predicates;
    }

    public String getFilters() {
        return filters;
    }

    public void setFilters(String filters) {
        this.filters = filters;
    }

    public Integer getEnabled() {
        return enabled;
    }

    public void setEnabled(Integer enabled) {
        this.enabled = enabled;
    }

    public Integer getDeleted() {
        return deleted;
    }

    public void setDeleted(Integer deleted) {
        this.deleted = deleted;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:ss:mm");
        if (createTime==null){
            this.createTime=null;
        }else {
            this.createTime = format.format(createTime);
        }
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:ss:mm");
        if (updateTime==null){
            this.updateTime=null;
        }else {
            this.updateTime = format.format(updateTime);
        }
    }
}
