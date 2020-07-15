<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <!-- 设置页面的字符集 -->
    <meta charset="utf-8">
    <!-- 设置根据情况显示比例(适应移动端) -->
    <meta name="viewport" content="width=device-width,initial-scale=1">
    <link href="https://cdn.bootcss.com/twitter-bootstrap/3.4.1/css/bootstrap.css" rel="stylesheet">

    <script src="https://cdn.bootcss.com/jquery/3.4.1/jquery.js"></script>
    <script src="https://cdn.bootcss.com/twitter-bootstrap/3.4.1/js/bootstrap.js"></script>
    <script src="https://cdn.bootcss.com/twbs-pagination/1.4.2/jquery.twbsPagination.js"></script>
    <script src="https://cdn.bootcss.com/bootbox.js/5.4.0/bootbox.js"></script>
    <title>index</title>
</head>
<%@ include file="common.jsp" %>
<style>
    .box001 {
        display: flex;
        justify-content: center;
        align-items: center;
    }

</style>
<body>
<div class="container">
    <div class="box001">
        <h2>哈哈哈哈哈</h2>
    </div>
    <div class="box001">
        <button type="button" class="btn btn-primary" onclick="routeManager()">route管理</button>
        &nbsp;&nbsp;&nbsp;
        <button type="button" class="btn btn-primary" onclick="progressBarManager()">进度条管理</button>
    </div>
</div>
</body>
</html>
<script>
    function routeManager() {
        window.location.href="${pageContext.request.contextPath }/route/list?access_token=" +getQueryString("access_token");
    }
    function progressBarManager() {
        window.location.href="${pageContext.request.contextPath }/progressbar?access_token=" +getQueryString("access_token");
    }

</script>