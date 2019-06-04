$(function() {
	right.checkLogin();
});

var right = {
	// 界面加载的时候 检查是否登录
	checkLogin : function() {
		if (location.href.endWith("index.html")) {
			$.ajax({
				url : "/BZCX/login/check",
				type : "post",
				ansyc : false,
				success : function(data) {
					if (data.code == 500) {
			        	commonUtil.addCookie("isLogin", "", 0);
			        	indexHtml.loginOrInfo("login");
					} else if (data.code == 200) {
						// TODO 将登录中心变成信息中心
						indexHtml.loginOrInfo("info");
					}
				}
			});
		}
	},
	/**
	 * 点击按钮等资源的时候，检查是否登录
	 */
	checkClick : function() {
		var isLogin = commonUtil.readCookie("isLogin");
		var bo=false;
		if(isLogin == "true"){
			bo= true;
		}else{
		layer.alert("请先登录。");
		   bo= false;
		}
		return bo;
	},
}
