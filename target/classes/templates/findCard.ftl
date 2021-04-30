<!DOCTYPE html>
<html>

<head>
<meta charset="utf-8">
<meta name="viewport" content="maximum-scale=1.0,minimum-scale=1.0,user-scalable=0,width=device-width,initial-scale=1.0" />
    <script src="${ctx!}/assets/js/jquery/jquery.min.js?v=2.1.4"></script>

<title>找到一卡通</title>


<!--<link href="css/owl.carousel.css" rel="stylesheet">-->
<!--<link href="css/lz_gg.css" rel="stylesheet" type="text/css" />-->
<style>
html,
body {
	margin: 0px;
	height: 100%;
}

body {
		background: #f1f1f1;
	
	font-family: "Microsoft YaHei", "MicrosoftJhengHei", STHeiti, MingLiu;
}

.div_c_l {
	float: left;
	padding: 5px;
	font-size: 15px;
	font-weight: 500;
}

.div_c_l span {
	line-height: 38px;
	padding: 0 5px;
	width: 60px;
}

.div_c_l img {
	height: 30px;
	margin: 2px;
	padding: 0px 7px;
}

.div_c_r {
	float: right;
	padding: 5px;
	width: calc(100% - 100px);
}

.div_c_r input {
	border: 0px;
	margin: 5px;
	float: left;
	line-height: 28px;
	font-size: 15px;
	text-align: right;
	padding-right: 5px;
	width: 100%;
	outline: none;
	text-align: left;
}

.div_c_r button {
	width: 88px;
	float: right;
	background-color: #F2F2F2;
	height: 28px;
	padding: 4px 8px;
	line-height: 20px;
	font-size: 14px;
	font-weight: 500;
	margin: 5px 5px 0 0;
	border: 1px solid #DBDBDB;
	border-radius: 6px;
	outline: none;
}

.div_f {
	border-bottom: 1px solid #E8E8E8;
	background-color: #fff;
}

.btn_1 {
	border: none;
	margin: 40px 6% 16px 6%;
	width: 88%;
	height: 2.2em;
	background-color: #05a0e0;
	border-radius: 4px;
	font-size: 25px;
	color: #FFFFFF;
	outline: none;
	-webkit-tap-highlight-color: transparent;
}

.div_b_l {
	float: left;
	margin-left: 6%;
	padding: 5px;
	width: calc(44% - 10px);
}

.div_b_r {
	float: right;
	margin-right: 6%;
	padding: 5px;
	width: calc(44% - 10px);
	text-align: right;
}

.span_b {
	font-size: 16px;
	font-weight: 500;
	color: #000;
	padding: 0 2px;
	float: left;
}

.span_br {
	font-size: 16px;
	font-weight: 500;
	color: #05a0e0;
	float: left;
}

input[type=checkbox] {
	visibility: hidden;
}

.checkboxforget {
	top: 0px;
	float: left;
	width: 20px;
	height: 20px;
	background: #05a0e0;
	margin: 0 5px 0 0;
	border-radius: 100%;
	position: relative;
}

.checkboxforget label {
	display: block;
	width: 18px;
	height: 18px;
	border-radius: 100px;
	-webkit-transition: all .5s ease;
	-moz-transition: all .5s ease;
	-o-transition: all .5s ease;
	-ms-transition: all .5s ease;
	transition: all .5s ease;
	cursor: pointer;
	position: absolute;
	top: 1px;
	left: 1px;
	z-index: 1;
	background: #fff;
}

.checkboxforget input[type=checkbox]:checked+label {
	background: #05a0e0;
	width: 20px;
	height: 20px;
	top: 0px;
	left: 0px;
}

.inp_radio {
	float: left;
	line-height: 48px;
}

input[type=radio] {
	visibility: hidden;
}

.checkboxforget1 {
	top: 13px;
	float: left;
	width: 20px;
	height: 20px;
	background: #AFAFAF;
	margin: 0 5px 0 0;
	border-radius: 100%;
	position: relative;
	/*            -webkit-box-shadow: 0px 1px 1px rgba(0,0,0,0.5);
-moz-box-shadow: 0px 1px 1px rgba(0,0,0,0.5);
box-shadow: 0px 1px 1px rgba(0,0,0,0.5);*/
}

.checkboxforget1 label {
	display: block;
	width: 18px;
	height: 18px;
	border-radius: 100px;
	-webkit-transition: all .5s ease;
	-moz-transition: all .5s ease;
	-o-transition: all .5s ease;
	-ms-transition: all .5s ease;
	transition: all .5s ease;
	cursor: pointer;
	position: absolute;
	top: 1px;
	left: 1px;
	z-index: 1;
	background: #fff;
	/*            -webkit-box-shadow:inset 0px 1px 1px rgba(0,0,0,0.5);
-moz-box-shadow:inset 0px 1px 1px rgba(0,0,0,0.5);
box-shadow:inset 0px 1px 1px rgba(0,0,0,0.5);*/
}

.checkboxforget1 input[type=radio]:checked+label {
	background: #05a0e0;
	width: 20px;
	height: 20px;
	top: 0px;
	left: 0px;
}

.red {
	color: #ff6600;
	display: inline-block;
}
</style>


</head>

<body sroll="no" onclick="onload">
<form name="form1" onSubmit="return checkreg()" action="${ctx!}/api/v1/wechat/findcard/find" method="post">
    <div class="div_f">
        <div class="div_col" id="me">
            <div class="div_c_l"><span>一卡通</span>
            </div>
            <div class="div_c_r"><input type="tel" name="card" id="card" onKeyDown="" value="" placeholder="请输入拾取一卡通的卡号">
                <span style="float: right;margin-top: -30px;background:#fff" id="sp_card"></span></input>
            </div>
        </div>
        <div style="clear:both"></div>
    </div>

    <div class="div_f">
        <div class="div_f" id="div_code" style="display: none">
            <div class="div_col" id="me">
                <div class="div_c_l"><span style="width:70px;">用户代号</span>
                </div>
                <div class="div_c_r"><input type="text" id="code" name="code" onkeydown="" id="code" value=${bindUser.code} placeholder="为了在QQ及时精准推荐给您">
                    <span style="float: right;margin-top: -30px;background:#fff" id="sp_code"></span>
                </div>
            </div>
            <div style="clear:both"></div>
        </div>
        <div style="clear:both"></div>
    </div>
    <button id="submit" class="btn_1">确定</button>
</form>


<script type="text/javascript">

    //获取地址栏后面的参数
    function GetQueryString(name) {
        var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
        var r = window.location.search.substr(1).match(reg);
        if(r != null)
            return unescape(r[2]);
        return null;
    }

   // var code = GetQueryString("code");
   // document.getElementById("code").value = code;

    function checkreg() {

        var cardNumber = document.form1.card.value;

        if(cardNumber == ""){
           if(cardNumber == ""){
                document.getElementById("sp_card").innerHTML = "<font color='red'>请输入一卡通</font>";
                form1.card.focus();
                return false;
            }
            return false;
        }

        if(cardNumber.length != 10) {
            //alert("请输入一卡通!");
            document.getElementById("sp_card").innerHTML = "<font color='red'>请输入有效一卡通</font>";
            document.form1.card.focus();
            return false;
        }

        var myreg = /^(((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1})|(17[0-9]{1}))+\d{8})$/;
        if(!myreg.test(telePhone)){
            document.getElementById("sp_phone").innerHTML = "<font color='red'>请输入有效的手机号码</font>";
            document.form1.phone.focus();
            return false;
        }
        document.getElementById("sp_phone").innerHTML = "<font color='green'>正确</font>";

    }


</script>

</body>
</html>