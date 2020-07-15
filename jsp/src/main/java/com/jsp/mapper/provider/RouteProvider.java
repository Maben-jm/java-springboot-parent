package com.jsp.mapper.provider;

import com.maben.query.RouteModelQuery;

/**
 * @description:
 * @author: maben
 * @createDate: 2020/4/9
 */
public class RouteProvider {

    public String update(RouteModelQuery routeModelQuery)throws Exception{
        StringBuilder sql = new StringBuilder("update t_route_config set id = ");
        sql.append(routeModelQuery.getId());
        if (routeModelQuery.getDeleted()!=null){
            sql.append(", deleted = ").append(routeModelQuery.getDeleted());
        }
        if (routeModelQuery.getEnabled()!=null){
            sql.append(", enabled = ").append(routeModelQuery.getEnabled());
        }
        if (routeModelQuery.getFilters()!=null){
            sql.append(", filters='").append(routeModelQuery.getFilters()).append("'");
        }
        if (routeModelQuery.getPredicates()!=null){
            sql.append(", predicates ='").append(routeModelQuery.getPredicates()).append("'");
        }
        if (routeModelQuery.getRouteId()!=null){
            sql.append(", route_id='").append(routeModelQuery.getRouteId()).append("'");
        }
        if (routeModelQuery.getRouteOrder()!=null){
            sql.append(", route_order=").append(routeModelQuery.getRouteOrder());
        }
        if (routeModelQuery.getRouteUri()!=null){
            sql.append(", route_uri='").append(routeModelQuery.getRouteUri()).append("'");
        }
        if (routeModelQuery.getUpdateTime()!=null){
            sql.append(", update_time = STR_TO_DATE('").append(routeModelQuery.getUpdateTime()).append("','%Y-%m-%d %H:%i:%s')");
        }
        sql.append(" where id = ").append(routeModelQuery.getId());
        System.out.println("update sql ::::"+sql.toString());
        return sql.toString();
    }

    public String getRouteModel(RouteModelQuery routeModelQuery)throws Exception{
        StringBuilder sql = new StringBuilder("select * from t_route_config where deleted = 0 ");
        final Integer currentPage = routeModelQuery.getCurrentPage();
        final Integer pageLimit = routeModelQuery.getPageLimit();
        if (currentPage!=null ){
            sql.append(" limit ").append((currentPage-1)*pageLimit).append(" , ").append(pageLimit);
        }
        return sql.toString();
    }

    public String getRouteModelCount(RouteModelQuery routeModelQuery)throws Exception{
        StringBuilder sql = new StringBuilder("select count(*) from t_route_config where deleted = 0 ");
        return sql.toString();
    }

}
