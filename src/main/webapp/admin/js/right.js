$(function(){
	// 检查用户是否登录
	adminRight.checkLogin();
	// 根据用户资源来控制菜单
	adminRight.checkMenu();
});
var showad;
/**
 * 后台管理 权限相关的对象
 */
var adminRight = {
	/**
	 * 检查用户是否登录
	 */
	checkLogin : function(){
		$.ajax({
	        type : "post",
	        async : false,
	        url :"/BZCX/login/check",
	        cache : false,
	        dataType : "json",
	        success : function(data){
	            var code = data.code;
	            if(code == 200){
	            	showad = data.data.showadmin;
	            	window.user = data.data;
	            	$("#user_name_p").html("欢迎 " + user.realname);
	            	var resourceList = data.data.resourceList
	            	var resourceNameArray = [];
	            	var resourceIdArray = [];
	            	for(var i = 0; i < resourceList.length; i ++){
	            		resourceNameArray.push(resourceList[i].name);
	            		resourceIdArray.push(resourceList[i].id);
	            	}
	            	window.resourceNameArray = resourceNameArray;
	            	window.resourceIdArray = resourceIdArray;
	            }
	        }
	    });
	},
	/**
	 * 根据权限展示菜单
	 */
	checkMenu : function(){
		// 父级菜单
		for(var i = 0; i < menuArray.length; i ++){
			var menuName = menuArray[i];
			if(!resourceNameArray.contains(menuName)){
				$("#left_menu").accordion('remove', menuArray[i]);
			}
		}
		// 子级菜单
		$('#left_menu a').each(function(){
			var name = $(this).html();
			if(name != null && $.trim(name) != "" && !resourceNameArray.contains(name)){
				$(this).parent().parent().parent().hide();
			}
		});
	},
	/**
	 * 根据权限来确定界面上的一些元素例如按钮 复选框等是否要显示
	 */
	checkElement : function(){
		$('.right_element').each(function(){
			var resourceId = $(this).attr("resourceId");
			if(resourceId != null && $.trim(resourceId) != "" && !resourceIdArray.contains(resourceId)){
				$(this).hide();
			}
		});
	}
}

