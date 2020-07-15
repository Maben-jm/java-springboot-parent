<script>
    var basePath = 'http://localhost:9050' + '${pageContext.request.contextPath }';

    /**
     * 获取url上的参数
     * @param name 参数名
     * @returns {*}
     */
    function getQueryString(name) {
        var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
        var r = window.location.search.substr(1).match(reg);
        if (r != null) return decodeURI(r[2]); return null;
    }
</script>