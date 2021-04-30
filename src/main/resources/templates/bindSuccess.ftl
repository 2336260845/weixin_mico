<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>绑定结果</title>
        <meta content="width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=0" name="viewport"/>
        <meta content="yes" name="apple-mobile-web-app-capable"/>
        <meta content="black" name="apple-mobile-web-app-status-bar-style"/>
        <meta content="telephone=no" name="format-detection"/>
        <link href="${ctx!}/assets/css/style.css" rel="stylesheet" type="text/css"/>
    </head>
    <body>
        <section class="aui-flexView">
            <header class="aui-navBar aui-navBar-fixed b-line">

            </header>
            <section class="aui-scrollView">
                <div class="aui-back-box">
                    <div class="aui-back-pitch">
                        <img src="${ctx!}/assets/img/icon-pitch.png" alt="">
                    </div>
                    <div class="aui-back-title">
                        <h2>绑定成功</h2>
                        <p>个人账号信息成功</p>
                    </div>
            </section>
        </section>

        <script type="text/javascript">
            function close_this() {
                if (confirm("您确定要关闭本页吗？")) {

                    window.opener = null;
                    window.open('', '_self');
                    window.close();
                }
            }
        </script>
    </body>
</html>
