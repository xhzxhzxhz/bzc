String.prototype.endWith = function(s){
	  if(s==null || s=="" || this.length==0 || s.length > this.length){
		  return false;
	  }
	  if(this.substring(this.length - s.length) == s){
		  return true;
	  }else{
		  return false;
	  }
	  return true;
}
String.prototype.startWith = function(s){
	  if(s==null || s=="" || this.length==0 || s.length>this.length){
		  return false;
	  }
	  if(this.substr(0,s.length)==s){
		  return true;
	  }else{
		  return false;
	  }
	  return true;
}
Array.prototype.contains = function(item){
    for(i = 0; i < this.length; i++){
        if(this[i]==item){
        	return true;
        }
    }
    return false;
};
var commonUtil = {
		/**
		 * 得到请求的URL中的参数值
		 * @param name 参数名
		 * @returns
		 */
		getRequestParam : function(name){
			var url = decodeURI(window.location.search);
		    url = url.substr(1, url.length);
		    var paramArray = url.split("&");
		    for(var i = 0; i < paramArray.length; i++){
		    	var key = paramArray[i].split("=")[0];
		    	var value = paramArray[i].split("=")[1];
	 	    	if(key == name){
	 	    		return value;
	 	    	}
		    }
		    return ;
		},
		/**
		 * 获得UUID
		 * @returns
		 */
		getUUID : function(){
				return 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, function(c) {
			        var r = Math.random()*16|0, v = c == 'x' ? r : (r&0x3|0x8);
			        return v.toString(16);
			 });
		},
		isTrident : function(){
			var u = navigator.userAgent;
			if(u.indexOf('Trident') > -1){
				return true;
			}else{
				return false;
			}
		},
		/**
		 * 验证是否是邮件
		 */
		isEmail : function(){
			var reg = new RegExp("^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$");
			return reg.test(emailStr);
		},
		/**
		 * 验证一个字符串是否是空字符串
		 */
		isEmpty : function(str){
			if(str === null || str === undefined || $.trim(str) === ''){
				return true
			}
			return false;
		},
		map : {
			data : ['中文-1',
			        '英文-2',
			        '其他-3',
			        'newsCate_通知公告-1',
			        'newsCate_城镇燃气标准动态-2',
			        'newsCate_中国石油标准动态-3',
			        'newsCate_国家标准动态-4',
			        'newsCate_行业标准动态-5',
			        'newsCate_其他信息-6',
			        'newsStatus_正常-0',
			        'newsStatus_下架-0',
			        'newsHot_是-1',
			        'newsHot_否-0',
			        'standardStatus_现行-1',
			        'standardStatus_暂行-2',
			        'standardStatus_废止-3',
			        'userStatus_1-正常',
			        'userStatus_2-待审核',
			        'userStatus_3-驳回',
			        'userStatus_4-被删除',
			        'userLastOperType_0-注册申请',
			        'userLastOperType_1-注册审核通过',
			        'userLastOperType_2-注册审核不通过',
			        'userLastOperType_3-二级管理员新增申请',
			        'userLastOperType_4-新增申请通过',
			        'userLastOperType_5-新增申请不通过',
			        'userLastOperType_6-删除申请',
			        'userLastOperType_7-删除申请通过',
			        'userLastOperType_8-删除申请不通过'
			        ],
			get : function(name){
				for(var i = 0; i < this.data.length; i ++){
					var key = this.data[i].split("-")[0];
					var value = this.data[i].split("-")[1];
					if(key == name){
						return value;
					}
				}
				return "";
			},
			put : function(key, value){
				if(key === null || key === undefined || $.trim(key) === ''){
					return;
				}
				this.data.push(key + "-" + value);
			}
		},
		/**
		 * 得到EasyUI表格的选取的节点
		 */
		getCheckedIds : function(datagridId, key){
			var ids = [];
			var items = $('#' + datagridId).datagrid('getSelections');
			$(items).each(function(){
				ids.push(this[key]);
			});
			return ids;
		},
		/**
		 * 得到easyUI表格的树的选取节点
		 */
		getTreeCheckedIds : function(treeId){
			var ids = []
			var nodes = $('#' + treeId).tree("getChecked", ['checked', 'indeterminate']);
			if(nodes){
				for(var i = 0; i < nodes.length; i ++){
					ids.push(nodes[i].id);
				}
			}
			return ids;
		},
		isPdf : function(fileName){
			if(fileName.toLowerCase().endWith(".pdf")){
				return true;
			}
			return false;
		},
		isTxt : function(fileName){
			if(fileName.toLowerCase().endWith(".txt")){
				return true;
			}
			return false;
		},
		isWord : function(fileName){
			if(fileName.toLowerCase().endWith(".doc") || fileName.toLowerCase().endWith(".docx")){
				return true;
			}
			return false;
		},
		/**
		 * 参数：from form对象
		 * return json
		 */
		form2Json : function(form){
			var paramArray = form.serializeArray();
			var paramJson = {};
			for(var i = 0; i < paramArray.length; i++){
				paramJson[paramArray[i].name] = paramArray[i].value;
			}
			return paramJson;
		},
		/**
		 * 让其调用者的线程等待，numberMillis 毫秒
		 */
		sleep : function(numberMillis){
			var now = new Date(); 
			var exitTime = now.getTime() + numberMillis; 
			while (true) { 
				now = new Date(); 
				if (now.getTime() > exitTime) 
				return;
			} 
		},
		/**
		 * 检查权限 隐藏图标(后台维护使用的)
		 */
		checkRight : function(){
			$.ajax({
		        type : "post",
		        async : false,
		        url :"/BZCX/admin/resource/list",
		        cache : false,
		        data:{
		        	page:1,
		        	rows:200
		        },
		        dataType : "json",
		        success : function(data){
		            for(var i = 0; i < data.rows.length; i ++){
		            	var id = data.rows[i].id;
		            	if(!resourceIdArray.contains(id)){
		        			$("#" + id).hide();
		            	}
		            }
		        }
		    });
		},
		/**
		 * 向浏览器增加cookie validTime < 0 时候,关闭浏览器则cookie失效
		 * 					  validTime为0 的时候。即可失效
		 * key：cookie的键
		 * value：cookie的值
		 * validTime：cookie的过期时间。以s为单位，例如过期时间一小时 validTime=3600
		 */
		addCookie : function(key, value, validTime){
			var cookieStr = key + "=" + value;
			if(!isNaN(validTime) && validTime > 0){
				var date = new Date();
				var ms = validTime * 1000;
				date.setTime(date.getTime() + ms);
				cookieStr += "; expires=" + date.toGMTString();
			}
			document.cookie = cookieStr;
			return true;
		},
		/**
		 * 读取cookie 参数 cookie的key
		 */
		readCookie : function(key){
			var arr,reg=new RegExp("(^| )"+key+"=([^;]*)(;|$)");
			if(arr=document.cookie.match(reg))
			return unescape(arr[2]);
			else
			return null;
		}
}
var menuArray = ["数据维护",
                 "互动维护", 
                 "标准动态维护", 
                 "权限管理", 
                 "数据备份", 
                 "计费管理",
                 "操作日志"];
