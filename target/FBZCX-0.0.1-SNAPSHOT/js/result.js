$(function() {
	var paramStr = commonUtil.getRequestParam("paramStr");
	var paramData = JSON.parse(paramStr);
	resultHtml.init(paramData);
});

var resultHtml = {
	init : function(data) {
		// 给检索按钮绑定事件
		this.bindResultSearchBtn();
		// 填充检索结果表格
		this.initTable(data);
		// 设置二次检索的
		this.setSecondSearchBox(data.isSecond);
		// 界面搜索词的显示 界面有个搜索XX关键词
		this.initKeyword(data.standardNo || data.standardCnName || data.pubOrg);
		// 界面填充简单检索的检索热词
		this.initHotWord("简单检索");
		// 界面上当前模块 例如：首页/标准查询/简单检索/检索结果
		this.currMoudle(data.moudle);
	},
	/**
	 * 在界面路径显示当前模块
	 * moudle :检索的模块名称 (简单检索，高级检索等)
	 */
	currMoudle : function(moudle){
		var parent = $('#result_moudle_li');
		parent.html("");
		var childStr = '<span>/</span>' + 
						'<a href="search.html?moudle=' + moudle +'">' + moudle + '</a>';
		parent.append($(childStr));
	},
	/**
	 * isSecond：true false
	 */
	setSecondSearchBox : function(isSecond){
		$("#second_search_checkbox").attr("checked", isSecond);
	},
	/**
	 * 给检索结果页的检索按钮绑定事件
	 */
	bindResultSearchBtn : function(){
		$('#result_search_btn').click(function(){
			// 先得到当前的检索条件
			var keyword = $('#result_search_keyword_input').val();
			if(!keyword){
				layer.alert("请输入关键字。");
				return;
			}
			// 判断是不是二次检索
			var isSecond = $("#second_search_checkbox").get(0).checked;
			searchHtml.simpleSearch.searchParam.isSecond = isSecond;
			searchHtml.simpleSearch.search(keyword);
			
		});
	},
	initKeyword : function(keyword) {
		if(keyword){
			$("#result_keyword_span").html('\"' + keyword + '\"');
			$('#result_search_keyword_input').val(keyword);
		}else{
			$("#result_search_keyword_p").hide();
		}
	},
	/**
	 * 初始化界面的检索热词
	 */
	initHotWord : function(moudle){
		var data = {
			moudle : moudle
		}
		/*$.ajax({
			url :"/BZCX/history/hotword",
	        type : "post",
	        data : data,
	        ansyc : false,
	        success : function(data){
	        	
	        	var wordList = data;
	        	var parent = $("#result_hotword_div");
	        	parent.html("");
	        	for(var i = 0; i < wordList.length; i++){
	        		var childStr = "<span>" + wordList[i] + "<span/>"
	        		parent.append($(childStr));
	        	}
	        	
	        	$("#result_hotword_div span").click(function(){
	        		var keyword = this.innerText;
	        		$("#result_search_keyword_input").val(keyword);
	        		$("#result_search_btn").click();
	        	});
	        }
	    });*/
		$.ajax({
			url :"/BZCX/history/hotword",
	        type : "post",
	        data : data,
	        ansyc : false,
	        success : function(data){
	        	// 得到搜索结果之后 将搜索结果放到界面上
	        	var wordList = data;
	        	var len = 0;
	        	var ll = 1;
	        	var io = 0;
	        	var o = 0;
	        	var parent = $("#result_hotword_div");
	        	parent.html("");
	        	for(var i = 0; i < wordList.length; i++){
	        		len += wordList[i].length;
	        		var childStr = "<span>" + wordList[i] + "<span/>"
	        		if((len+ll) > 33){
	        			if(io == 0){
	        				o = len+ll
	        				io++;
	        			}
	        			if(o <= 36){
	        				if(wordList[i].length == 2){
			        			parent.append($(childStr));
			        			break;
			        		}else{
			        			continue;
			        		}
	        			}else{
	        				break;
	        			}
	        		}else{
	        			ll += 2;
	        			parent.append($(childStr));
	        		}
	        	}
	        	// 给热词点击绑定事件
	        	$("#result_hotword_div span").click(function(){
	        		var keyword = this.innerText;
	        		$("#result_search_keyword_input").val(keyword);
	        		$("#result_search_btn").click();
	        	});
	        }
	    });
	},
	/**
	 * 发送求情填充表格
	 */
	initTable : function(data) {
		var url = "standard/search";
		if(data.isSecond){
			url = "standard/second/search"
		}
		bzcxTable.initTable("basic_table","570px", url, data, function(data){
			$("#result_total_span").html(data.totals);
		});
	}
}
