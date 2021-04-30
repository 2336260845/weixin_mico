<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title></title>
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <script src="${ctx!}/assets/js/jquery/jquery.min.js?v=2.1.4"></script>
</head>
<body>

<!-- 你的HTML代码 -->
index

<script type="text/javascript">
    var code = GetQueryString("code");



    //获取地址栏后面的参数
    function GetQueryString(name) {
        var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
        var r = window.location.search.substr(1).match(reg);
        if(r != null)
            return unescape(r[2]);
        return null;
    }

    $.ajax({
        url : "/api/v1/wechat/getOpenId",
        dataType : "json",
        type : "get",
        data : "code=" + code,
        success : function (data) {
            //返回的data即为openid，拿到openid实现业务
        }

    })


</script>

</body>