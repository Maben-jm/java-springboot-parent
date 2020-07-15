package com.jsp.controller;

import com.maben.pojo.RouteModel;
import com.maben.query.RouteModelQuery;
import com.jsp.service.RouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

/**
 * @description:
 * @author: maben
 * @createDate: 2020/4/9
 */
@RestController
@RequestMapping("route")
public class RouteController {
    @Autowired
    private RouteService routeService;

    @RequestMapping(value = "save", method = RequestMethod.POST)
    public Map<String, Object> save(RouteModelQuery routeModelQuery) {
        Map<String, Object> result = new HashMap<>();
        try {
            if (routeModelQuery == null) {
                throw new Exception("参数为空!!!");
            }
            routeService.save(routeModelQuery);
            result.put("success", "1");
        } catch (Exception e) {
            e.printStackTrace();
            result.put("success", "0");
            result.put("msg", "程序报错");
        }
        return result;

    }

    @RequestMapping("edit")
    public ModelAndView goEditJsp(RouteModelQuery routeModelQuery) throws Exception {
        ModelAndView model = new ModelAndView("route/edit");
        if (routeModelQuery == null) {
            return model;
        }
        RouteModel routeModel = new RouteModel();
        final Integer currentPage = routeModelQuery.getCurrentPage();
        if (routeModelQuery.getId() != null) {
            final RouteModel routeById = routeService.getRouteById(routeModelQuery.getId());
            if (routeById != null) {
                routeModel = routeById;
            }
        }
        routeModel.setCurrentPage(currentPage == null ? 0 : currentPage);
        model.addObject("model", routeModel);
        return model;
    }

    @RequestMapping("list")
    public ModelAndView goRouteJsp(RouteModelQuery routeModelQuery) throws Exception {
        ModelAndView model = new ModelAndView("route/route");
        if (routeModelQuery == null) {
            routeModelQuery = new RouteModelQuery();
        }
        if (routeModelQuery.getCurrentPage() == null) {
            routeModelQuery.setCurrentPage(1);
        }
        final Map<String, Object> result = routeService.getRoutes(routeModelQuery);
        model.addObject("list", result.get("list"));
        final Integer count = (Integer) result.get("count");
        final Integer pageSize = count % routeModelQuery.getPageLimit() == 0 ? count / routeModelQuery.getPageLimit() : count / routeModelQuery.getPageLimit() + 1;
        model.addObject("pageSize", pageSize);
        model.addObject("currentPage", routeModelQuery.getCurrentPage());
        return model;
    }
}
