<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0 ,maximum-scale=1.0, user-scalable=no">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<meta content="telephone=no" name="format-detection" />
<script>
(function (doc, win) {
        var docEl = doc.documentElement,
            resizeEvt = 'orientationchange' in window ? 'orientationchange' : 'resize',
            recalc = function () {
                var clientWidth = docEl.clientWidth;
                if (!clientWidth) return;
                if(clientWidth>=750){
                    docEl.style.fontSize = '100px';
                }else{
                    docEl.style.fontSize = 100 * (clientWidth / 750) + 'px';
                }
            };
        if (!doc.addEventListener) return;
        win.addEventListener(resizeEvt, recalc, false);
        doc.addEventListener('DOMContentLoaded', recalc, false);
    })(document, window);
</script>
<link href="${ctx!}/assets/css/card.css" rel="stylesheet" type="text/css">
<script src="${ctx!}/assets/js/jquery-1.8.3.min.js"></script>
<title>一卡通</title>
</head>

<body>
<header>
    <div class="title">一卡通</div>
</header>
<div class="bank_content">
	<div class="card">
    	<div class="card_number">
        	<img class="img-responsive" src="${ctx!}/assets/img/logo.png">
            <div class="txt0"><span>我的卡号：</span><br>${userBean.card}</div>
        </div>
        <div class="youxiaoqi">有效期：2020/07/01</div>
    </div>
</div>

<div class="bank_content">
	<div class="card">
    	<div class="card_number">
        	<img class="img-responsive" src="${ctx!}/assets/img/logo.png">
            <div class="txt0"><span>亲密卡号：</span><br>${userBean.friendCard}</div>
        </div>
        <div class="youxiaoqi">有效期：2020/07/01</div>
    </div>
</div>
</body>
</html>
