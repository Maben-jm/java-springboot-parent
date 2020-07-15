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
    <title>登录页面</title>
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
        <h2>欢迎登录</h2>
    </div>
    <form class="form-horizontal">
        <div class="form-group">
            <label for="userName" class="col-sm-4 control-label">用户名</label>
            <div class="col-sm-4">
                <input type="text" class="form-control" id="userName" placeholder="请输入用户名">
            </div>
        </div>
        <div class="form-group">
            <label for="password" class="col-sm-4 control-label">密码</label>
            <div class="col-sm-4">
                <input type="password" class="form-control" id="password" placeholder="请输入密码">
            </div>
        </div>
    </form>
    <div class="box001">
        <button type="button" class="btn btn-primary" onclick="login()">登录</button>
    </div>
</div>
</body>
</html>
<script>
    function login() {
        var postData = {
            userName: $("#userName").val(),
            password: $("#password").val()
        };
        $.ajax({
            type: "POST",
            url: basePath + "/login/handleLogin",
            async: false,
            data: postData,
            success: function (response) {
                console.log("success:" + JSON.stringify(response));
                if (response.success==="1"){
                    window.location.href="${pageContext.request.contextPath }/login/index?access_token=" +response.access_token;
                }else {
                    alert("执行失败:"+response.msg);
                }
            },
            error: function (response) {
                console.log("error:" + JSON.stringify(response));
            }
        });
    }

</script>