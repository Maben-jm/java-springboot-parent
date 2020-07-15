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
    <title>进度条测试</title>
    <link href="https://cdn.bootcss.com/twitter-bootstrap/3.4.1/css/bootstrap.css" rel="stylesheet">

    <script src="https://cdn.bootcss.com/jquery/3.4.1/jquery.js"></script>
    <script src="https://cdn.bootcss.com/twitter-bootstrap/3.4.1/js/bootstrap.js"></script>
    <script src="https://cdn.bootcss.com/twbs-pagination/1.4.2/jquery.twbsPagination.js"></script>
    <script src="https://cdn.bootcss.com/bootbox.js/5.4.0/bootbox.js"></script>
    <title>登录页面</title>
</head>
<%@ include file="../common.jsp" %>
<style>

    .box001 {
        display: flex;
        justify-content: center;
        align-items: center;
    }

</style>
<body>
<div class="container">
    <div class="progress progress-striped active">
        <div id="prog" class="progress-bar" role="progressbar" aria-valuenow="" aria-valuemin="0" aria-valuemax="100" style="width:0%;">
            <span id="proglabel">正在启动，请稍后......</span>
        </div>
    </div>
    <div class="form-group">
        <div class="col-sm-offset-4 col-sm-6">
            <button id="pause" class="btn btn-primary" value="pause">暂停</button>
            <button id="stop" class="btn btn-primary" value="stop">停止</button>
            <!--<button id="goon" class="btn btn-primary">继续<button>-->
        </div>
    </div>
</div>
</body>
</html>
<script>
    $(document).ready(function(){
        var value = 0;
        var time = 50;
        //进度条复位函数
        function reset( ) {
            value = 0
            $("#prog").removeClass("progress-bar-success").css("width","0%").text("等待启动");
            //setTimeout(increment,5000);
        }
        //百分数增加，0-30时为红色，30-60为黄色，60-90为蓝色，>90为绿色
        function increment( ) {
            value += 1;
            $("#prog").css("width",value + "%").text(value + "%");
            if (value>=0 && value<=30) {
                $("#prog").addClass("progress-bar-danger");
            }
            else if (value>=30 && value <=60) {
                $("#prog").removeClass("progress-bar-danger");
                $("#prog").addClass("progress-bar-warning");
            }
            else if (value>=60 && value <=90) {
                $("#prog").removeClass("progress-bar-warning");
                $("#prog").addClass("progress-bar-info");
            }
            else if(value >= 90 && value<100) {
                $("#prog").removeClass("progress-bar-info");
                $("#prog").addClass("progress-bar-success");
            }
            else{
                setTimeout(reset,3000);
                return;

            }

            st = setTimeout(increment,time);
        }

        increment();
        //进度条停止与重新开始
        $("#stop").click(function () {
            if ("stop" == $("#stop").val()) {
                //$("#prog").stop();
                clearTimeout(st);
                $("#prog").css("width","0%").text("等待启动");
                $("#stop").val("start").text("重新开始");
            } else if ("start" == $("#stop").val()) {
                increment();
                $("#stop").val("stop").text("停止");
            }
        });
        //进度条暂停与继续
        $("#pause").click(function() {
            if ("pause" == $("#pause").val()) {
                //$("#prog").stop();
                clearTimeout(st);
                $("#pause").val("goon").text("继续");
            } else if ("goon" == $("#pause").val()) {
                increment();
                $("#pause").val("stop").text("暂停");
            }
        });
    });

</script>