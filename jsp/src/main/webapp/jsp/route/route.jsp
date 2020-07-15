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
    <script src="https://cdn.bootcss.com/bootbox.js/5.4.0/bootbox.js"></script>
</head>
<%@ include file="../common.jsp" %>
<body>
<div class="container">

    <div style="float: right;padding-top: 20px;">
        <button class="btn btn-default" type="submit" onclick="editRoute()">新增</button>
    </div>
    <table class="table table-bordered table-hover">
        <caption>route表</caption>
        <thead>
        <tr class="danger">
            <th>序号</th>
            <th>route_id</th>
            <th>route_uri</th>
            <th>route_order</th>
            <th>是否启用</th>
            <th>创建时间</th>
            <th>操作哦</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${list}" var="model">
            <tr class="info">
                <td>${model.id } </td>
                <td>${model.routeId } </td>
                <td>${model.routeUri } </td>
                <td>${model.routeOrder } </td>
                <td>
                    <c:choose>
                        <c:when test="${model.enabled==0 }">
                            <span>否</span>
                        </c:when>
                        <c:otherwise>
                            <span>是</span>
                        </c:otherwise>
                    </c:choose>
                </td>
                <td>${model.createTime } </td>
                <td>
                    <button class="btn btn-default" type="submit" onclick="editRoute(${model.id })">编辑</button>
                    <button type="button" class="btn btn-primary" onclick="deleteRoute(${model.id })">删除</button>
                        <%--<button type="button" class="btn btn-danger" data-toggle="modal" data-target="#exampleModal" data-whatever="@mdo">删除</button>--%>
                </td>
            </tr>
        </c:forEach>

        </tbody>
    </table>
    <div id="example" style="text-align: center">
        <ul id="pageLimit"></ul>
    </div>

    <div class="modal fade" id="exampleModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">×</span></button>
                    <h4 class="modal-title" id="exampleModalLabel">确认框</h4>
                </div>
                <div class="modal-body">
                    <form>
                        <div class="form-group">
                            <label for="message-text" class="control-label">确定要删除联系人？</label>
                        </div>
                    </form>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">返回</button>
                    <button type="button" class="btn btn-primary" onclick="deleteRoute(${model.id })">确认</button>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
<script>
    var pageSize = ${pageSize };
    var currentPage =${currentPage };
    window.onload = function () {
        $('#pageLimit').twbsPagination({
            startPage: currentPage,
            visiblePages: pageSize > 5 ? 5 : pageSize,
            totalPages: pageSize,
            onPageClick: function (evt, page) {
                if (page !== currentPage) {
                    console.log('evt', evt);
                    console.log('page', page);
                    window.location.href = "${pageContext.request.contextPath }/route/list?currentPage=" + page+"&access_token="+getQueryString("access_token");
                }
            }
        });
    };

    function editRoute(modelId) {
        if (!currentPage || currentPage === '') {
            currentPage = 1;
        }
        if (modelId) {
            window.location.href = "${pageContext.request.contextPath }/route/edit?id=" + modelId + "&currentPage=" + currentPage+"&access_token="+getQueryString("access_token");
        } else {
            window.location.href = "${pageContext.request.contextPath }/route/edit?currentPage=" + currentPage+"&access_token="+getQueryString("access_token");
        }
    }

    function deleteRoute(modelId) {
        console.log('modelId', modelId);
        bootbox.confirm("确认删除", function (result) {
            console.log('result', result);
            if (result) {
                var postData = {
                    id: modelId,
                    deleted: 1
                };
                $.ajax({
                    type: "POST",
                    url: basePath + "/route/save?access_token="+getQueryString("access_token"),
                    async: false,
                    data: postData,
                    success: function (response) {
                        console.log("success:" + JSON.stringify(response));
                        if (response.success === "1") {
                            window.location.href = "${pageContext.request.contextPath }/route/list?currentPage=" + currentPage+"&access_token="+getQueryString("access_token");
                        } else {
                            alert("执行失败:" + response.msg );
                        }
                    },
                    error: function (response) {
                        console.log("error:" + JSON.stringify());
                    }
                });
            }
        });
    }

</script>