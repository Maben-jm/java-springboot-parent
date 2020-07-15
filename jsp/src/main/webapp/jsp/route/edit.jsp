<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <!-- 设置页面的字符集 -->
    <meta charset="utf-8">
    <!-- 设置根据情况显示比例(适应移动端) -->
    <meta name="viewport" content="width=device-width,initial-scale=1">
    <title>route</title>
    <link href="https://cdn.bootcss.com/twitter-bootstrap/3.4.1/css/bootstrap.css" rel="stylesheet">

    <script src="https://cdn.bootcss.com/jquery/3.4.1/jquery.js"></script>
    <script src="https://cdn.bootcss.com/twitter-bootstrap/3.4.1/js/bootstrap.js"></script>
    <script src="https://cdn.bootcss.com/twbs-pagination/1.4.2/jquery.twbsPagination.js"></script>
</head>
<%@ include file="../common.jsp" %>
<body>
<div class="container">

    <form>
        <div class="form-group">
            <label for="routeId" class="col-sm-2 control-label">route_id</label>
            <div class="col-sm-10">
                <input type="text" class="form-control" id="routeId" placeholder="路径id" value="${model.routeId }">
            </div>
        </div>
        <div class="form-group">
            <label for="routeUri" class="col-sm-2 control-label">route_uri</label>
            <div class="col-sm-10">
                <input type="text" class="form-control" id="routeUri" placeholder="路径uri" value="${model.routeUri }">
            </div>
        </div>
        <%--<div class="form-group">--%>
            <%--<label for="routeOrder" class="col-sm-2 control-label">route_order</label>--%>
            <%--<div class="col-sm-10">--%>
                <%--<input type="text" class="form-control" id="routeOrder" placeholder="路径Order" value="${model.routeOrder }">--%>
            <%--</div>--%>
        <%--</div>--%>
        <div class="form-group">
            <label for="predicates" class="col-sm-2 control-label">predicates</label>
            <textarea class="form-control" rows="3" id="predicates">${model.predicates }</textarea>
        </div>
        <div class="form-group">
            <label for="filters" class="col-sm-2 control-label">filters</label>
            <textarea class="form-control" rows="3" id="filters">${model.filters }</textarea>
        </div>
        <div class="form-group" id="enabled">
            <div class="radio-inline">
                <label>
                    <input type="radio" name="enabled" value="1" <c:if test="${model.enabled==1 }">checked</c:if>>
                    是
                </label>
            </div>
            <div class="radio-inline">
                <label>
                    <input type="radio" name="enabled" value="0" <c:if test="${model.enabled==0 }">checked</c:if>>
                    否
                </label>
            </div>
        </div>
    </form>
    <button type="submit" class="btn btn-primary" onclick="save()">保存</button>


</div>
</body>
</html>
<script>
    var id = '${model.id }';
    var currentPage=${model.currentPage }
    function save() {
        var postData = {
            id: id,
            routeId: $("#routeId").val(),
            routeUri: $("#routeUri").val(),
            routeOrder: $("#routeOrder").val(),
            enabled: $('#enabled input:radio:checked').val()
        };
        var predicates = $("#predicates").val();
        if (predicates && predicates.replace(/\ +/g, "") !== '') {
            postData.predicates = predicates.replace(/[\r\n\t]/g, "");
        }
        var filters = $("#filters").val();
        if (filters && filters.replace(/\ +/g, "") !== '') {
            postData.filters = filters.replace(/[\r\n]/g, "");
        }
        $.ajax({
            type: "POST",
            url: basePath + "/route/save?access_token="+getQueryString("access_token"),
            async: false,
            data: postData,
            success: function (response) {
                console.log("success:" + JSON.stringify(response));
                if (response.success==="1"){
                    window.location.href="${pageContext.request.contextPath }/route/list?currentPage=" +currentPage+"&access_token="+getQueryString("access_token");
                }else {
                    alert("执行失败:"+response.msg);
                }
            },
            error: function (response) {
                console.log("error:" + JSON.stringify(response));
            }
        });

    };

    window.onload = function (ev) {
    }

</script>