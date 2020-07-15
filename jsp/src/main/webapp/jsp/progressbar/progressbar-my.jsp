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
<div id="loading"
     style="display: none; width: 100%; min-height: 100%; text-align: center; padding: 5px 5px 5px 5px; margin-top: 0px; position: absolute; opacity: 0.45; background-color: rgb(255, 255, 255); z-index: 1000; height: auto !important;">
    <table style="margin: auto;">
        <tbody>
        <tr>
            <td><span>查询中,请等待...</span></td>
        </tr>
        <tr>
            <td><img src="/img/loading.gif" rel="nofollow"></td>
        </tr>
        </tbody>
    </table>
</div>
<div class="container">
    <div class="box001">
        <h2>测试数据库备份</h2>
    </div>
    <div class="box001">
        <button type="button" class="btn btn-primary" onclick="exportTest()">导出</button>
        &nbsp;&nbsp;&nbsp;
        <button type="button" class="btn btn-primary" onclick="importTest()">导入</button>
    </div>
    <div id="progressbar" class="progress progress-striped active" style="display:none ">
        <div id="prog" class="progress-bar" role="progressbar" aria-valuenow="" aria-valuemin="0" aria-valuemax="100" style="width:0%;">
            <span id="proglabel">正在启动，请稍后......</span>
        </div>
    </div>
</div>
</body>
</html>
<script>
    var value = 0;
    var isSuccess = false;

    function importTest() {
        $("#progressbar").css("display", "block");
        //数据库恢复测试
        reset();
        $.ajax({
            type: "POST",
            url: basePath + "/progressbar/importTest?access_token=" + getQueryString("access_token"),
            async: true,
            data: {},
            success: function (response) {
                response = response.replace(/(^\s*)|(\s*$)/g, "");
                response = JSON.parse(response);
                if (response.code === 0) {
                    //成功
                    isSuccess = true;
                } else {
                    //失败
                    alert("执行失败:" + response.msg);
                    clearTimeout(st);
                }
            },
            error: function (response) {
                console.log("error:" + JSON.stringify(response));
                clearTimeout(st);
            }
        });
        increment();
    }

    function exportTest() {
        $("#progressbar").css("display", "block");
        reset();
        //数据库备份测试
        $.ajax({
            type: "POST",
            url: basePath + "/progressbar/exportTest?access_token=" + getQueryString("access_token"),
            async: true,
            data: {},
            success: function (response) {
                response = response.replace(/(^\s*)|(\s*$)/g, "");
                response = JSON.parse(response);
                if (response.code === 0) {
                    //成功
                    isSuccess = true;
                } else {
                    //失败
                    alert("执行失败:" + response.msg);
                    clearTimeout(st);
                }
            },
            error: function (response) {
                console.log("error:" + JSON.stringify(response));
                clearTimeout(st);
            }
        });
        increment();
    }

    //进度条复位函数
    function reset() {
        value = 0;
        isSuccess = false;
        $("#prog").removeClass("progress-bar-success").css("width", "0%").text("等待启动");
    }

    var start;
    var end;

    //百分数增加，0-30时为红色，30-60为黄色，60-90为蓝色，>90为绿色
    function increment() {
        if (!isSuccess && value === 99) {
        } else {
            value += 1;
        }
        if (value===1){
            start = new Date();
        }
        end = new Date();
        var msg = value + "%";
        if (value!=100){
            msg=msg+";已经过了"+getTimeDiff(start,end);
        } else {
            msg=msg+";一共用了"+getTimeDiff(start,end);
        }
        $("#prog").css("width", value + "%").text(msg);
        if (value >= 0 && value <= 30) {
            $("#prog").addClass("progress-bar-danger");
        } else if (value >= 30 && value <= 60) {
            $("#prog").removeClass("progress-bar-danger");
            $("#prog").addClass("progress-bar-warning");
        } else if (value >= 60 && value <= 90) {
            $("#prog").removeClass("progress-bar-warning");
            $("#prog").addClass("progress-bar-info");
        } else if (value >= 90 && value < 100) {
            $("#prog").removeClass("progress-bar-info");
            $("#prog").addClass("progress-bar-success");
        } else {
            return;
        }
        var time = getRandomTime();
        st = setTimeout(increment, time);
    }

    function getTimeDiff(start, end) {
        var difftime = (end - start) / 1000; //计算时间差,并把毫秒转换成秒
        var days = parseInt(difftime / 86400); // 天  24*60*60*1000
        var hours = parseInt(difftime / 3600) - 24 * days;    // 小时 60*60 总小时数-过去的小时数=现在的小时数
        var minutes = parseInt(difftime % 3600 / 60); // 分钟 -(day*24) 以60秒为一整份 取余 剩下秒数 秒数/60 就是分钟数
        var seconds = parseInt(difftime % 60);  // 以60秒为一整份 取余 剩下秒数
        return days + "天, " + hours + "小时, " + minutes + "分钟, " + seconds + "秒";
    }

    function getRandomTime() {
        if (isSuccess) {
            return 1;
        } else {
            var time = (Math.floor(Math.random() * (5000)) + 1000);
            console.log('time', time);
            return time;
        }
    }

</script>