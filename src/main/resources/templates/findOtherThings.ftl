<!DOCTYPE html>
<html>

	<head>
		<meta charset="utf-8">
		<meta name="viewport" content="initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
		<title>拾取物品</title>
		<link rel="stylesheet" href="${ctx!}/assets/css/new_file.css" type="text/css"/>
        <script src="${ctx!}/assets/js/jquery/jquery.min.js?v=2.1.4"></script>
	
	</head>


	
	<body>
		<!--头部  star-->
		<header style="color:#fff">
			<a href="javascript:history.go(-1);">
				<div class="_left"><img src="images/left.png"></div>
				<span>其他物品</span></a>
		</header>
		<!--头部 end-->
        
        <!--内容 star-->
		<div class="contaniner fixed-cont">
			<!--1-->
            <div class="div_f">
                <div class="div_col" id="me">
                    <div class="div_c_l"><span>   物品名称</span>
                    </div>
                    <div class="div_c_r"><input type="text" name="name" id="name" placeholder="请填写物品名称">
                        <span style="float: right;margin-top: -30px;background:#fff" id="tish"></span></input>
                    </div>
                </div>
                <div style="clear:both"></div>
            </div>
            <div class="tkyy">物品主要特征说明</div>
           
			<section class="assess">
				<div class="assess_nr">
					<textarea rows="6" placeholder="请您输入拾取物品主要特征"></textarea>
				</div>
			</section>

			<!--2-->
            <a href="#"><div class="submit_button">提交上传</div></a>
		</div>
		<!--内容 end-->
        
	</body>

</html>