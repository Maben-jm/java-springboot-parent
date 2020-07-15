package com.jsp.mapper;

import com.maben.pojo.RouteModel;
import com.maben.query.RouteModelQuery;
import com.jsp.mapper.provider.RouteProvider;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @description:
 * @author: maben
 * @createDate: 2020/4/9
 */
public interface RouteMapper {

    @SelectProvider(type = RouteProvider.class, method = "getRouteModel")
    List<RouteModel> getRouteModel(RouteModelQuery routeModelQuery)throws Exception;

    @SelectProvider(type = RouteProvider.class, method = "getRouteModelCount")
    Integer getRouteModelCount(RouteModelQuery routeModelQuery)throws Exception;

    @Select("select * from t_route_config where deleted=0 and id = #{id}")
    RouteModel getRouteById(@Param("id") Integer id);

    @Insert("INSERT INTO `t_route_config` ( `route_id`, `route_uri`, `route_order`, `predicates`, `filters`, `enabled`, `deleted`, `create_time`, `update_time`) "
                                + "VALUES ( #{routeId},#{routeUri},#{routeOrder},#{predicates},#{filters},#{enabled},#{deleted},STR_TO_DATE(#{createTime},'%Y-%m-%d %H:%i:%s'),STR_TO_DATE(#{updateTime},'%Y-%m-%d %H:%i:%s')   )")
    void insert(RouteModelQuery routeModelQuery);

    @UpdateProvider(type = RouteProvider.class,method = "update")
    void update(RouteModelQuery routeModelQuery);
}
