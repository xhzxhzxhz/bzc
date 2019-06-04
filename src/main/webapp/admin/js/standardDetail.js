$(function(){
	// 得到当前的题录信息
	var standardId = commonUtil.getRequestParam("standardId");
	$.ajax({
		url :"/BZCX/standard/detail",
        type : "post",
        data : {
        	standardId : standardId
        },
        ansyc : false,
        success : function(data){
        	var standardJson = data.data;
        	standardDetailHtml.standard = standardJson;
        	// 初始化首页界面
        	standardDetailHtml.showStandardDetail(standardJson);
        	// 给查看原文链接绑定事件
        	standardDetailHtml.bindPreviewLink();
        	// 给下载原文链接绑定事件
        	standardDetailHtml.downloadStandard();
        }
    });
});

var standardDetailHtml = {
		/**
		 * 将原文信息填充到界面
		 */
		showStandardDetail : function(data){
			$('#standard_detail_table td').each(function(){
	    		var tdId = $(this).attr('id');
	    		// 遍历json
	    		for(var key in data){
	    		     if(tdId == key){
	        			$('#' + tdId).html(data[key]);
	        		 }
	    		}
	    	})
		},
		// 给查看原文链接绑定事件
		bindPreviewLink : function(){
			$('#standard_detail_preview_link').click(function(){
				searchHtml.preview(standardDetailHtml.standard.standardNo);
			});
		},
		// 给下载原文链接绑定事件
		downloadStandard : function(){
			$('#standard_detail_download_link').click(function(){
				// 发送请求 下载原文
				var data = {
					standardId : standardDetailHtml.standard.standardId	
				}
				// 发送ajax请求，下载原文
				$.ajax({
					url :"/BZCX/standard/download",
			        type : "post",
			        data : data,
			        ansyc : false,
			        success : function(data){
			        	if(data.code == 200){
			        		window.open("/BZCX/standard/download?stdFileId=" + data.data);
			        	}else if(data.code == 10005){
			        		layer.alert("原文不存在，请联系管理员");
			        	}else if(data.code == 10003){
			        		layer.alert("没有下载此原文的权限。");
			        	}else if(data.code == 10001){
			        		layer.alert(data.msg);
			        	}
			        }
			    });
			});
		}
}