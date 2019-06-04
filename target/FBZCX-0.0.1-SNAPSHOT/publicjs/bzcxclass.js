var Qkey="";

/**
 * 定义一个easyui的下拉框需要的对象模型
 * id: 下拉框的值
 * text：下拉框的显示的文本
 * selected：是否被选中
 * @returns
 */
function EasyUISelect(id, text, selected){
	this.id = id;
	this.text = text;
	if(!selected){
		selected = false;
	}
	this.selected = selected;
}
/**
 * 轮播图对应的实体类
 * @param imgUrl 显示的图片地址
 * @param href 点击之后的跳转地址
 * @param text 标题
 * @returns
 */
function Slide(imgurl, href, text){
	this.imgurl = imgurl;
	this.href = href;
	this.text = text;
}
/**
 * 将jstree树插件基于我们的业务，封装一次
 * @returns
 */
var bzcxTree = {
		/**
		 * 加载树
		 * treeId ：树的ID
		 * nodeId ：树的最父级节点的ID（用来请求树用）
		 * url : 加载树的后台接口的url
		 * callback : 点击了树节点之后的回调函数
		 */
		initTree : function(treeId, nodeId, url, callback,title){
			// 先把树销毁bzcxTree.initTree
			$('#' + treeId).jstree("destroy");
			var tree = $('#' + treeId).jstree({
				"plugins" : ["themes", "wholerow", "json_data", "search"],
				"search" : {
			                "case_insensitive" : true,
			                "ajax" : {
			                    url : url,
			                    data : {
									nodeId : nodeId 
								},
								type : "POST"
			                }
				},
				"core" : {
					'multiple' : false,
					"opened" : true,
					"animation" : 0,
					"themes" : { "dots": true, "stripes" : true,"icons":false},
					'data' : function (obj, callback) {
						var data = {
							nodeId : nodeId
						}
						$.ajax({
							type: "POST",
							url: url,
							dataType:"json",
							data:data,
							async: false,
							success:function(data) {
								if(data.length > 0 && title == "标准体系"){
									systemHtml.queryParam.nodeId = data[0].id;
									systemHtml.initStandardTable();
								}
								jsonarray=data;
							}
						});
						if(typeof(jsonarray) === "undefined"){
							layer.alert("所属公司暂未建立体系，请联系本公司管理员");
							return false;
						}else{
							callback.call(this, jsonarray);
						}
					}
				}
			});
			// 树的点击事件
			tree.on("changed.jstree", function (e, data) {
				callback(data);
			});
		return tree;
	},
}
/**
 * 将表格插件基于我们的业务，封装一次
 * @returns
 */
var bzcxTable = {
		/**
		 * 初始化表格
		 * tableId : 表格的ID
		 * url : 加载数据的后台URL
		 * data : 加载数据所需参数
		 * callBack : 加载成功后的回调函数
		 */
		initTable : function(tableId, height, url, data, callBack){
			$('#' + tableId).GM({
				supportCheckbox : false,
				supportDrag : false,// 是否拖拽
				supportAdjust : true,// 自动调整宽度
				gridManagerName : 'test',
				height : height,
				supportAjaxPage : true,// 是否支持分页
				ajax_url : url,
				ajax_type : 'POST',
				query : data,
				ajax_success : function(data){
					data = JSON.parse(data);
					callBack(data);
				},
				sizeData : [15,20,30],
				pageSize : 15,
				columnData : [
						{
							key : 'standardNo',
							text : '标准号',
							width : '200px',
							sorting : '',
							template : function(action, rowObject) {
								if(Qkey==""){
									Qkey = data.standardNo;
								}
							var No=	rowObject.standardNo.replace(Qkey,"<span style='color:red;'>"+Qkey+"</span>")
								var preview = '<a href="javascript:void(0)" onclick="searchHtml.getStandardDetail(\'' + rowObject.standardId +'\')" style="color: blue" target="_blank">' +No+ '</a>';
								return preview;
							}
						},
						{
							key : 'standardCnName',
							text : '标准名称',
							template : function(action, rowObject) {
								if(Qkey==""){
									Qkey = data.standardCnName;
								}
								var preview = rowObject.standardCnName.replace(Qkey,"<span style='color:red;'>"+Qkey+"</span>");
								return preview;
							}
								
						},
						{
							key : 'implementationDate',
							text : '实施日期',
							width : '100px',
							sorting : ''
						},
						{
							key : 'pubOrg',
							text : '发布单位',
							template : function(action, rowObject) {
								if(Qkey==""){
									Qkey = data.pubOrg;
								}
								var pubOrg=	rowObject.pubOrg.replace(Qkey,"<span style='color:red;'>"+Qkey+"</span>")
									return pubOrg;
								}
						
						},
						{
							key : 'standardStatus',
							text : '状态',
							width : '50px',
							sorting : ''
						},
						{
							key : 'action',
							text : '在线预览',
							width : '80px',
							template : function(action, rowObject) {
								var preview = '<a onclick="searchHtml.preview(\'' + rowObject.standardNo + '\')" class="preview_original_a" style="display:block;text-align:center;height:27px" href="javascript:void(0)"><img style="display: inline" src="imgs/search/preview.png"/></a>';
								return preview;
							}
						} ],
				// 分页前事件
				pagingBefore : function(query) {
					query.isPaging = true;
				},
				// 分页后事件
				pagingAfter : function(data) {
				},
				// 宽度调整前事件
				adjustBefore : function(event) {
				},
				// 宽度调整后事件
				adjustAfter : function(event) {
				},
				// 拖拽前事件
				dragBefore : function(event) {
				},
				// 拖拽后事件
				dragAfter : function(event) {
				}
			
			});
		}
}
/**
 * 检索模块对应的总对象
 */
var searchHtml = {
		/**
		 * 简单搜索
		 */
		simpleSearch : {
			init : function(){
				// 初始化简单检索的热词
				this.initHotWord();
				// 给简单检索按钮绑定事件
				this.bindSearchBtn();
				// 给简单检索的重置按钮绑定事件
				this.bindResetBtn();
			},
			searchParam : {
				moudle : "简单检索",
				relation : "or"
			},
			search : function(keyword){
				if(right.checkClick()){
				this.searchParam.standardNo = keyword;
				this.searchParam.standardCnName = keyword;
				var paramStr = JSON.stringify(this.searchParam);
				window.location.href = encodeURI("/BZCX/result.html?paramStr=" + paramStr);
				}
			},
			/**
			 * 初始化简单检索的热词
			 */
			initHotWord : function(){
				var data = {
					moudle : "简单检索"
				}
				$.ajax({
					url :"/BZCX/history/hotword",
			        type : "post",
			        data : data,
			        ansyc : false,
			        success : function(data){
			        	// 得到搜索结果之后 将搜索结果放到界面上
			        	var wordList = data;
			        	var parent = $("#simple_search_hotword_p");
			        	parent.html("热门搜索：");
			        	for(var i = 0; i < wordList.length; i++){
			        		 var word=	wordList[i].replace(".doc","").replace(".docx","");
			        		var childStr = "<span>" + word + "</span>"
			        		parent.append($(childStr));
			        	}
			        	// 给热词点击绑定事件
			        	$("#simple_search_hotword_p span").click(function(){
			        		var keyword = this.innerText;
			        		$("#simple_search_keyword_input").val(keyword);
			        		searchHtml.simpleSearch.search(keyword);
			        	});
			        }
			    });
				
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
		        	var parent = $("#simple_search_hotword_div");
		        	parent.html("");
		        	for(var i = 0; i < wordList.length; i++){
		        		len += wordList[i].length;
		        		 var word=	wordList[i].replace(".doc","").replace(".docx","");
		        		var childStr = "<span>" + word + "</span>"
		        		if((len+ll) > 24){
		        			if(io == 0){
		        				o = len+ll
		        			}
		        			if(o <= 27){
		        				break;
		        			}else{
		        				if(wordList[i].length == 2){
				        			parent.append($(childStr));
				        			break;
				        		}else{
				        			continue;
				        		}
		        			}
		        		}else{
		        			ll += 1;
		        			parent.append($(childStr));
		        		}
		        	}
		        	// 给热词绑定点击事件
		        	$("#simple_search_hotword_div span").click(function(){
		        		var keyword = this.innerText;
		        		$("#simple_search_keyword_input").val(keyword);
		        		searchHtml.simpleSearch.search(keyword);
		        	});
		        }
		    });
			},
			/**
			 * 给简单检索的检索按钮绑定事件
			 */
			bindSearchBtn : function(){
				$("#simple_search_btn").click(function(){
					var keyword = $("#simple_search_keyword_input").val();
					if(commonUtil.isEmpty(keyword)){
						layer.alert("请输入关键字。");
						return;
					}
					searchHtml.simpleSearch.search(keyword);
				});
			},
			/**
			 * 给简单检索的重置按钮绑定事件
			 */
			bindResetBtn : function(){
				$("#simple_reset_btn").click(function(){
					 $("#simple_search_keyword_input").val("");
				});
			}
		},
		/**
		 * 高级检索
		 */
		advancedSearch : {
			/**
			 * 初始化高级检索的tab
			 */
			init : function(){
				this.bindSearchBtn();
				this.bindSearchResetBtn();
			},
			/**
			 * 给高级检索的检索按钮绑定事件
			 */
			bindSearchBtn : function(){
				$("#advanced_search_btn").click(function(){
					var paramJson = commonUtil.form2Json($('#advanced_search_form'));
					if(paramJson.standardStatus == 0){
						delete paramJson["standardStatus"];  
					}
					searchHtml.advancedSearch.search(paramJson);
				});
			},
			/**
			 * 给高级检索表单的重置按钮绑定事件
			 */
			bindSearchResetBtn : function(){
				$("#advanced_search_reset_btn").click(function(){
					$('#advanced_search_form')[0].reset();
				});
			},
			/**
			 * 高级检索的检索方法
			 */
			search : function(data){
				var paramStr = JSON.stringify(data);
				window.location.href = encodeURI("result.html?paramStr=" + paramStr);
			}
		},
		/**
		 * 分类检索
		 */
		cateSearch : {
			init : function(){
				// 初始化最父级下拉列表
				this.initCateList();
				// 给分类检索的检索项添加change事件
				this.bindSearchItemSelect();
				// 给分类检索的表单添加重置事件
				this.bindSearchResetBtn();
				// 给分类检索的检索按钮添加click事件
				this.bindSearchBtn();
				// 初始化分类检索的检索热词
				this.initHotWord();
			},
			/**
			 * 初始化分类的最父级节点下拉列表
			 */
			initCateList : function(){
				searchHtml.initCateList("cate_search_parent_select", "cate_search_tree", "查询模块体系", function(data){
					if(data.selected.length) {
						$("#cate_search_nodeId_hidden").val(data.instance.get_node(data.selected[0]).id);
					}
				});
			},
			/**
			 * 初始化分类检索的热词
			 */
			initHotWord : function(){
				var data = {
					moudle : "分类检索"
				}
				$.ajax({
					url :"history/hotword",
			        type : "post",
			        data : data,
			        ansyc : false,
			        success : function(data){
			        	// 得到搜索结果之后 将搜索结果放到界面上
			        	var wordList = data;
			        	var parent = $("#cate_search_hotword_p");
			        	parent.html("热门搜索：");
			        	for(var i = 0; i < wordList.length; i++){
			        		 var word=	wordList[i].replace(".doc","").replace(".docx","");
			        		var childStr = "<span>" + word + "</span>"
			        		parent.append($(childStr));
			        	}
			        	// 给热词点击绑定事件
			        	$("#cate_search_hotword_p span").click(function(){
			        		var keyword = this.innerText;
			        		$("#cate_seaerch_keyword_input").val(keyword);
			        	});
			        }
			    });
			},
			/**
			 * 分类检索的检索方法
			 */
			search : function(data){
				var paramStr = JSON.stringify(data);
				window.location.href = encodeURI("result.html?paramStr=" + paramStr);
			},
			/**
			 * 给分类检索的检索按钮绑定事件
			 */
			bindSearchBtn : function(){
				$("#cate_search_btn").click(function(){
					var paramJson = commonUtil.form2Json($('#cate_search_form'));
					if(paramJson.standardStatus == 0){
						delete paramJson["standardStatus"];  
					}
					searchHtml.cateSearch.search(paramJson);
				});
			},
			/**
			 * 给检索项绑定change事件
			 * 当检索项发生变化时候，检索词的name也发生变化
			 */
			bindSearchItemSelect : function(){
				$("#cate_searchItem_select").change(function(){
					  var searchItem = $(this).val();
					  // 将关键词的name设置为searchItem
					  $("#cate_seaerch_keyword_input").attr("name", searchItem);
				});
			},
			/**
			 * 给分类检索表单重置按钮 添加事件
			 */
			bindSearchResetBtn : function(){
				$("#cate_search_reset_btn").click(function(){
					$('#cate_search_form')[0].reset();
				});
			}
		},
		/**
		 * 图形检索
		 */
		visionSearch : {
			
		},
		/**
		 * 全文检索对象
		 */
		solrSearch : {
			init : function(){
				// 初始化全文检索热词
				this.initHotWord();
				// 给全文搜索按钮绑定事件
				this.bindSearchBtn();
			},
			searchParam : {
				moudle : "全文检索",
				rows : 10,
				page : 1
			},
			/**
			 * 给全文检索的检索按钮 绑定事件
			 */
			bindSearchBtn : function(){
				$("#solr_search_btn").click(function(){
					var keyword = $("#solr_search_keyword_input").val();
					if(commonUtil.isEmpty(keyword)){
						layer.alert("请输入关键字。");
						return;
					}
					searchHtml.solrSearch.search(keyword);
				});
			},
			/**
			 * 初始化全文搜索的搜索热词
			 */
			initHotWord : function(){
				var data = {
					moudle : "全文检索"
				}
				$.ajax({
					url :"/BZCX/history/hotword",
			        type : "post",
			        data : data,
			        ansyc : false,
			        success : function(data){
			        	// 得到搜索结果之后 将搜索结果放到界面上
			        	var wordList = data;
			        	var parent = $("#solr_search_hotword_div");
			        	parent.html("");
			        	for(var i = 0; i < wordList.length; i++){
			        	 var word=	wordList[i].replace(".doc","").replace(".docx","");
			        		var childStr = "<span>" +word+ "</span>"
			        		parent.append($(childStr));
			        	}
			        	// 给热词点击绑定事件
			        	$("#solr_search_hotword_div span").click(function(){
			        		var keyword = this.innerText;
			        		$("#solr_search_keyword_input").val(keyword);
			        		searchHtml.solrSearch.search(keyword);
			        	});
			        }
			    });
			},
			
			/**
			 * 全文检索的搜索方法
			 */
			search : function(keyword, isPaging){
				if(isPaging){
					this.searchParam.page = isPaging;
				}else{
					this.searchParam.keyword = keyword;
					this.searchParam.page = 1;
				}
				var searchData = this.searchParam;
				// 发送请求，得到搜索结果
				$.ajax({
					url :"/BZCX/solr/search",
			        type : "post",
			        data : searchData,
			        ansyc : false,
			        success : function(data){
			        	// 结果总数显示在界面上
			        	$("#main_bot_in2").show();
			        	
			        	// id="main_bot_in3"
			        	$("#main_bot_in3").show();
			        	var list = data.data;
			        	var parent = $("#main_bot_in3");
			        	parent.html("");
			        	if(list!=null){
			        		$("#solr_search_result_total").html(data.total);
			        	for(var i = 0; i < list.length; i ++){
			        		
			        		var childStr ='<dl class="solr_result_dl" standardNo="' + list[i].standardNo + '">' + 
			        					  '<dt><i></i><span>' + list[i].standardNo + '   ' +  list[i].standardName + '</span></dt>' +
			        					  '<dd>' + list[i].fileContent + '</dd>' +
			        					  '</dl>';
			        		parent.append($(childStr));
			        	}
			        	}else{
			        		var strp = '<p style="font-size:36px;font-weight:bold;text-align:center;margin:96px 0">数据正在更新中...</p>';
			        		parent.append($(strp));
			        		$("#solr_search_result_total").html(0 );
			        		//data.tota=0;
			        	}
			        	// 给搜索结果添加点击事件
			        	$(".solr_result_dl").click(function(){
			        		var standardNo = $(this).attr("standardNo");
			        		searchHtml.preview(standardNo);
			        	});
			        	// 得到搜索结果之后 将搜索结果放到界面上
			        	$("#main_bot_in4").show();
			        	$("#main_bot_in5").show();
			        	// 显示相关搜索和为你推荐
			        	
			        	searchHtml.solrSearch.initRelationWord(keyword);
			        	searchHtml.solrSearch.initPagination(Math.ceil(data.total/searchHtml.solrSearch.searchParam.rows), searchData.page);
			        }
			    });
			},
			/**
			 * 将和此次搜索结果的相关搜索此和为你推荐词放到界面上
			 */
			initRelationWord : function(keyword){
				var data = {
					keyword : keyword
				}
				// 发送ajax 请求相关词汇
				$.ajax({
					url :"/BZCX/solr/relation/word",
			        type : "post",
			        data : data,
			        ansyc : false,
			        success : function(data){
			        	var wordList = data;
			        	var recommendParent = $("#solr_recommend_word_div");
			        	var relationParent = $("#solr_relation_word_ul");
			        	recommendParent.html("");
			        	relationParent.html("");
			        	var recommendChildStr = "";
			        	var relationChildStr = "";
			        	for(var i = 0; i < wordList.length; i++){
			        		if(i < 5){
			        			recommendChildStr += "<span>" + wordList[i] + "</span>"
			        		}
			        		relationChildStr += "<li>" + wordList[i] + "</li>"
			        	}
			        	recommendParent.append($(recommendChildStr));
			        	relationParent.append($(relationChildStr));
			        	// 给热词点击绑定事件
			        	$("#solr_recommend_word_div span, #solr_relation_word_ul li").click(function(){
			        		var keyword = this.innerText;
			        		$("#solr_search_keyword_input").val(keyword);
			        		searchHtml.solrSearch.search(keyword);
			        	});
			        }
			    });
			},
			/**
			 * 初始化分页插件
			 */
			initPagination : function(totalPage, current){
		    	$("#main_bot_in5").html("");
		    	var page=$('<div id="pagination" class="page"></div>');
		    	$("#main_bot_in5").append(page);
		    	$("#pagination").pagination({
		    		currentPage: current, // 加载界面时候 默认第几页
		            totalPage: totalPage, // 一共多少页 由后台返回的total/pageSize得到
		            isShow: true,
		            count: 5,// 默认分页按钮 显示几个
		            homePageText: "首页",
		            endPageText: "尾页",
		            prevPageText: "上一页",
		            nextPageText: "下一页",
		            callback: function(current) {
				    	searchHtml.solrSearch.search(null, current);
		            }
		       });
			}
		},
		/**
		 * 浏览全库
		 */
		viewSearch : {
			init : function(){
				this.initCateList();
				this.initTable();
				this.bindSearchBtn();
			},
			queryParam : {
				moudle : "游览全库"
			},
			/**
			 * 加载浏览界面的标准分类下拉列表
			 */
			initCateList : function(){
				searchHtml.initCateList("view_search_parent_select", "view_search_tree", "查询模块体系", function(data){
					var nodeId = data.instance.get_node(data.selected[0]).id;
					// 点击的时候 重新加载表格
					searchHtml.viewSearch.queryParam.nodeId = nodeId;
					searchHtml.viewSearch.initTable();
				});
			},
			/**
			 * 初始化表格
			 */
			initTable : function(){
                var parent = $("#view_search_table_div").html("");
                var childStr = '<table id="view_search_table"></table>'
                parent.append(childStr);
				var url = "/BZCX/standard/search";
				bzcxTable.initTable("view_search_table", "570px", url, this.queryParam, function(data){
					if(searchHtml.viewSearch.queryParam.nodeId){
						$('#view_search_result_div').show();
						$('#view_search_nodename_span').html(data.attr.nodeName);
						$('#result_totalcount_span').html(data.attr.result.totalCount);
						$('#result_gbcount_span').html(data.attr.result.gbCount);
						$('#result_hbcount_span').html(data.attr.result.hbCount);
						$('#result_qbcount_span').html(data.attr.result.qbCount);
						$('#result_othercount_span').html(data.attr.result.otherCount);
						
					}else{
						$('#view_search_result_div').hide();
					}
					$('#total_totalcount_span').html(data.attr.total.totalCount);
					$('#total_archcount_span').html(data.attr.total.archCount);
					$('#total_gbcount_span').html(data.attr.total.gbCount);
					$('#total_hbcount_span').html(data.attr.total.hbCount);
					$('#total_qbcount_span').html(data.attr.total.qbCount);
					$('#total_othercount_span').html(data.attr.total.otherCount);
				});
			},
			/**
			 * 给搜索按钮绑定事件
			 */
			bindSearchBtn : function(){
				$('#view_search_btn').click(function(){
					var searchItem = $('#view_search_item_select').val();
					var keyword = $('#view_search_keyword_input').val();
					if(searchItem == "publishDate"){
						keyword = $('#view_search_publishdate_input').val();
					}
					searchHtml.viewSearch.queryParam[searchItem] = keyword;
					Qkey = keyword;
					searchHtml.viewSearch.initTable();		
					delete searchHtml.viewSearch.queryParam[searchItem];  
				});
			}
		},
		/**
		 * 初始化标准分类下拉列表
		 * selectId :下拉列表的ID
		 * treeId : 下拉列表对应的树的ID
		 * moudle : 昆仑公司标准体系 或者 本单位标准体系 或者 具体的体系ID
		 * callBack : 点击树之后的回调
		 */
		initCateList : function(selectId, treeId, moudle, callBack){
			// 发送ajax 得到昆仑总树的所有最父级
			var listUrl;
			var treeUrl;
			var title;
			if(moudle == "昆仑公司标准体系"){
				listUrl = "/BZCX/department/get?type=list";
				treeUrl = "/BZCX/architecture/tree?type=max";
				title = "标准体系";
			}else if(moudle == "本单位标准体系"){
				listUrl = "/BZCX/department/get?type=user";
				treeUrl = "/BZCX/architecture/tree?type=user";
				title = "标准体系";
			}else if(moudle == "查询模块体系"){
				listUrl = "/BZCX/department/get?type=max";
				treeUrl = "/BZCX/architecture/tree?type=max";
			}else{
				listUrl = "/BZCX/department/get?architectureId=" + moudle;
				treeUrl = "/BZCX/architecture/tree?architectureId=" + moudle;
			}
			// 先加载出来树
    		if(bzcxTree.initTree(treeId, "0", treeUrl, callBack,title)){
    			// 再填充下拉列表
    			$.ajax({
    				url : listUrl,
    		        type : "post",
    		        ansyc : false,
    		        success : function(data){
    		        	// 将部门列表放到下拉框里面
    		        	var parent = $("#" + selectId);
    		        	parent.html("");
    		        	if(data.data.length){
    		        		for(var i = 0; i < data.data.length; i ++){
    		        			var childStr = "";
    		        			if(i == 0){
    		        				childStr = '<option value="' + data.data[i].architectureId + '" selected>' + data.data[i].departmentName + "标准体系" + "</option>";
    		        			}else{
    		        				childStr = '<option value="' + data.data[i].architectureId + '" >' + data.data[i].departmentName + "标准体系" + "</option>";
    		        			}
    		        			parent.append($(childStr));
    		        		}
    		        	}else{
            				var childStr = '<option value="' + data.data.architectureId + '" selected>' + data.data.departmentName + "标准体系" + "</option>";
    	        			parent.append($(childStr));
    		        	}
    		        	// 给最父级分类下拉框增加change事件
    		        	$("#" + selectId).change(function(){
    		        		var architectureId = $(this).children('option:selected').val();
    		        		bzcxTree.initTree(treeId, "0", '/BZCX/architecture/tree?architectureId=' + architectureId, callBack,title);
    		        	})
    		        }
    		    });
    		}
		},
		/**
		 * 得到题录详情方法
		 */
		getStandardDetail : function(standardId){
			var data = {
				standardId : standardId	
			}
			$.ajax({
				url :"/BZCX/standard/detail",
		        type : "post",
		        data : data,
		        async : false,
		        success : function(data){
		        	if(data.code == 10001){
		        		layer.alert(data.msg);
		        		return;
		        	}
		        	data = data.data;
		        	window.open(encodeURI("resultDetail.html?standardId=" + standardId),"_self");
		        }
		    });
		},
		/**
		 * 原文预览方法
		 */
		preview : function(standardNo){
			var data = {
					standardNo : standardNo
				}
				// 发送ajax请求，查询当前的题录是否有pdf预览版本
				$.ajax({
					url :"standard/preview",
			        type : "post",
			        data : data,
			        ansyc : false,
			        success : function(data){
			        	if(data.code == 200){
			        		var path = data.data;
			        		window.open("admin/pdf/web/viewer.html?a=/pdf/"+path.substring(path.lastIndexOf("\\")+1,path.length));
			        		//window.open(data.data);
			        	}else if(data.code == 10002){
			        		layer.alert("没有权限查看当前原文。");
			        	}else if(data.code == 10004){
			        		layer.alert("当前原文没有PDF预览版，请联系管理员。");
			        	}else if(data.code == 10001){
			        		layer.alert(data.msg);
			        	}
			        }
			    });
		},
		/**
		 * 根据URL确定当前要展示什么模块
		 */
		currMoudle : function(){
			var moudle = commonUtil.getRequestParam("moudle");
			switch(moudle)
			{
			case "jdjs":
				$(".jdjs-a").trigger('click');
				break;
			case "gjjs":
				$(".gjjs-a").trigger('click');
				break;
			case "fljs":
				$(".fljs-a").trigger('click');
				break;
			case "txjs":
				$(".txjs-a").trigger('click');
				break;
			case "qwjs":
				$(".qwjs-a").trigger('click');
				break;
			case "ylqk":
				$(".ylqk-a").trigger('click');
				break;
			// 默认是打开简单检索界面
			default:
				$(".jdjs-a").trigger('click');
			}
		},
}

 /**
 * 图形查询
 */
var graphicalHtml = {
		getInfo : function(){
			$.ajax({
				url :"/BZCX/standard/graphicalSearch",
		        type : "post",
		        async : false,
		        success : function(data){
		        	var boss = data.data[0].children[1];
		        	for (var i = 0; i < boss.children.length; i++) {
		        		if(i == 2){
		        			var graphical = "";
		        			//资产管理通用标准
		        			$("#graphical_top").empty();
		        			$("#graphical_top_text").empty();
		        			$("#graphical_top_text").append(boss.children[i].text);
		        			for (var j = 0; j < boss.children[i].children.length && j<4 ; j++) {
			        			var childrena = boss.children[i].children[j];
			        			graphical += '<li>'
			        						+'<div title='+childrena.text+' class="graph-box-top-li-t">'
			        						+'<img src="imgs/search/t1.png"/>'
			        						+'<p>'+childrena.text+'</p>'
			        						+'</div>'
			        						+'<div class="graph-box-top-li-b">'
			        						+'<b></b>'
			        						+'<span></span>'
			        						+'</div>'
			        						+'<div class="graph-li-menu-wrap">'
			        						+'<ol class="graph-li-menu scrollbar style-1">'
			        						+'<div class="ol-circle"></div>'
			        						+'<div class="ol-line"></div>';
			        			            if(childrena.children!=null){
			        						for (var k = 0; k < childrena.children.length; k++) {
			        							if(childrena.children[k].checked){
			        								//如果是标准号的话
			        								graphical+='<li onclick="graphicalHtml.simpleSeach(\''+childrena.children[k].id+'\')">'+childrena.children[k].text+'</li>';
			        							}else{
			        								graphical+='<li onclick="graphicalHtml.nodeIdSeach(\''+childrena.children[k].id+'\')">'+childrena.children[k].text+'</li>';
			        							}
											}
			        			             }
			        						graphical+='</ol>'
			        						+'</div>'
			        						+'</li>'
							}
		        			graphical +='<div class="clear"></div>';
		        			$("#graphical_top").append(graphical);
		        		}else if(i == 1){
		        			//运行与通用
		        			var graphicalbot = "";
		        			$("#graphical_bot").empty();
		        			$("#graphical_bot_text").empty();
		        			$("#graphical_bot_text").append(boss.children[i].text);
		        			for (var j = 0; j < boss.children[i].children.length; j++) {
			        			var childrena = boss.children[i].children[j];
			        			graphicalbot += '<li>'
			        						+'<div class="graph-box-top-li-b">'
			        						+'<b></b>'
			        						+'<span></span>'
			        						+'</div>'
			        						+'<div title='+childrena.text+' class="graph-box-top-li-t">'
			        						+'<img src="imgs/search/t2.png"/>'
			        						+'<p>'+childrena.text+'</p>'
			        						+'</div>'
			        						+'<div class="graph-li-menu-wrap">'
			        						+'<ol class="graph-li-menu scrollbar style-1">'
			        						+'<div class="ol-circle"></div>'
			        						+'<div class="ol-line"></div>';
			        						for (var k = 0; k < childrena.children.length; k++) {
			        							//graphicalbot+='<li>'+childrena.children[k].text+'</li>';
			        							if(childrena.children[k].checked){
			        								//如果是标准号的话
			        								graphicalbot+='<li onclick="graphicalHtml.simpleSeach(\''+childrena.children[k].id+'\')">'+childrena.children[k].text+'</li>';
			        							}else{
			        								graphicalbot+='<li onclick="graphicalHtml.nodeIdSeach(\''+childrena.children[k].id+'\')">'+childrena.children[k].text+'</li>';
			        							}
											}
			        						graphicalbot+='</ol>'
			        						+'</div>'
			        						+'</li>';
							}
		        			graphicalbot +='<div class="clear"></div>';
		        			$("#graphical_bot").append(graphicalbot);
		        		}else if(i == 0){
		        			var graphicalleft = "";
		        			//燃气工程建设
		        			$("#graphical_left").empty();
		        			$("#graphical_left_text").empty();
		        			$("#graphical_left_text").append(boss.children[i].text);
		        			for (var j = 0; j < boss.children[i].children.length; j++) {
			        			var childrena = boss.children[i].children[j];
			        			//graphical_top
			        			var childtexttop = childrena.text.substring(0,9)+"...";
			        			graphicalleft += '<li>'
			        						+'<div title='+childrena.text+' class="graph-box-left-l">'
			        						+'<img src="imgs/search/t3.png"/>'
			        						+'<p>'+childtexttop+'</p>'
			        						+'</div>'
			        						+'<div class="graph-box-left-r">'
			        						+'<b></b>'
			        						+'<span></span>'
			        						+'</div>'
			        						+'<div class="l-graph-li-menu-wrap">'
			        						+'<ol class="l-graph-li-menu scrollbar style-1">'
			        						+'<div class="l-ol-circle"></div>'
			        						+' <div class="l-ol-line"></div>';
			        						for (var k = 0; k < childrena.children.length ; k++) {
			        							//var childtext = childrena.children[k].text.split("(")[0];
			        							if(childrena.children[k].checked){
			        								//如果是标准号的话
			        								graphicalleft+='<li onclick="graphicalHtml.simpleSeach(\''+childrena.children[k].id+'\')">'+childrena.children[k].text+'</li>';
			        							}else{
			        								graphicalleft+='<li onclick="graphicalHtml.nodeIdSeach(\''+childrena.children[k].id+'\')">'+childrena.children[k].text+'</li>';
			        							}
											}
			        						graphicalleft+='</ol>'
			        						+'</div>'
			        						+'</li>'
							}
		        			graphicalleft +='<div class="clear"></div>';
		        			$("#graphical_left").append(graphicalleft);
		        		}else{
		        			var graphicalright = "";
		        			//燃气工程建设
		        			$("#graphical_right").empty();
		        			$("#graphical_right_text").empty();
		        			var first = boss.children[i].text.substring(0,5);
		        			var last = boss.children[i].text.substring(5,boss.children[i].length);
		        			$("#graphical_right_text").append(first+"</br>"+last);
		        			for (var j = 0; j < boss.children[i].children.length ; j++) {
			        			var childrena = boss.children[i].children[j];
			        			var childtexttop = childrena.text.substring(0,9)+"...";
			        			graphicalright += '<li>'
			        						+'<div title='+childrena.text+' class="graph-box-left-l">'
			        						+'<img src="imgs/search/t3.png"/>'
			        						+'<p>'+childtexttop+'</p>'
			        						+'</div>'
			        						+'<div class="graph-box-left-r">'
			        						+'<b></b>'
			        						+'<span></span>'
			        						+'</div>'
			        						+'<div class="l-graph-li-menu-wrap">'
			        						+'<ol class="l-graph-li-menu scrollbar style-1">'
			        						+'<div class="l-ol-circle"></div>'
			        						+' <div class="l-ol-line"></div>';
			        			              if(childrena.children!=null){
			        			
			        						for (var k = 0; k < childrena.children.length ; k++) {
			        							//var childtext = childrena.children[k].text.split("(")[0];
			        							if(childrena.children[k].checked){
			        								//如果是标准号的话
			        								graphicalright+='<li onclick="graphicalHtml.simpleSeach(\''+childrena.children[k].id+'\')">'+childrena.children[k].text+'</li>';
			        							}else{
			        								graphicalright+='<li onclick="graphicalHtml.nodeIdSeach(\''+childrena.children[k].id+'\')">'+childrena.children[k].text+'</li>';
			        							}
											}
			        			             }
			        						graphicalright+='</ol>'
			        						+'</div>'
			        						+'</li>'
							}
		        			graphicalright +='<div class="clear"></div>';
		        			$("#graphical_right").append(graphicalright);
		        		}
					}
		        	
		        	var boos = data.data[0].children[0];
		        	for (var i = 0; i < boos.children.length; i++) {
						if(i == 2){
							//术语、代码
							$("#graphical_top_leftp").empty();
							$("#graphical_top_leftp").append(boos.children[i].text);
							$("#graphical_title_left").attr("onclick",'graphicalHtml.nodeIdSeach(\''+boos.children[i].id+'\')');
						}else if(i == 1){
							$("#graphical_pic_right2").empty();
							$("#graphical_pic_right2").append(boos.children[i].text);
							$("#graphical_title_right2").attr("onclick",'graphicalHtml.nodeIdSeach(\''+boos.children[i].id+'\')');
						}else{
							$("#graphical_pic_right1").empty();
							$("#graphical_pic_right1").append(boos.children[i].text);
							$("#graphical_title_right1").attr("onclick",'graphicalHtml.nodeIdSeach(\''+boos.children[i].id+'\')');
						}
					}
		        }
			});
		},
		simpleSeach : function(keyword) {
			//searchHtml.simpleSearch.search(keyword);
			window.open(encodeURI("resultDetail.html?standardId=" + keyword),"_self");
		},
		nodeIdSeach : function(data) {
			$(".ylqk-a").trigger('click');
			$.jstree.reference('#view_search_tree').select_node(data);
		}
}

/**
 * 首页对应的html对象
 */
var indexHtml = {
		init : function(){
			// 页面加载的时候,先初始化轮播图。
			this.initSlide();
			// 再初始化滚动条
			this.initNotice();
			// 初始化界面的简单搜索的热词
			this.initSimpleHotWord();
			// 给简单检索按钮添加点击事件
			this.bindSimpleSearchBtn();
			// 初始化昆仑标准动态 就是新闻可以切换tab的左边一个
			this.initNewsTabLeft();
			// 初始化动态信息 就是新闻可以切换tab的右边一个
			this.initNewsTabRight();
			// 初始化共享资源列表
			this.initFileShare();
			// 初始化二级单位
			this.initSecondUnit("secoend_unit_ul");
			// 给标准动态的tab切换绑定事件
			this.bindNewsTab();
			
			this.login.bindLoginInBtn();
			
			this.login.bindLoginOutBtn();
			//注册按钮绑定事件
			this.registerBtn();
			//修改密码绑定事件
			this.psdEditBtn();
		},
		//注册按钮点击出现弹窗
		registerBtn : function(){
			$("#index_regist_btn").click(function(){
				layer.open({
		            type: 2,
		            title: '注册',
		            shadeClose: true,
		            shade: 0.8,
		            maxmin: false, //开启最大化最小化按钮
		            area: ['680px', '600px'],
		            content: ['register.html', 'no']
		        });
			});
		},
		//修改密码点击事件
		psdEditBtn : function(){
			$("#psd_edit_btn").click(function(){
				layer.open({
		            type: 2,
		            title: '修改密码',
		            shadeClose: true,
		            shade: 0.8,
		            maxmin: false, //开启最大化最小化按钮
		            area: ['400px', '260px'],
		            content: ['pswEdit.html', 'no']
		        });
			});
		},
		// 根据传入要显示的info 还是login 显示登录框或者是用户信息框
		loginOrInfo : function(type){
			if(type == "info"){
				// 登录框隐藏
				$(".login-p-con-in").hide();
				// 个人信息框展现
				$(".login-p-con-login").show();
				// 从本地取出用户信息
				var userInfoStr = commonUtil.readCookie("userInfo");
				var user = JSON.parse(userInfoStr);
				$("#index_user_name_span").html(user.userName);
				$("#index_user_departmentname_span").html(user.departmentName);
			}else if(type == "login"){
				// 登录框展现
				$(".login-p-con-in").show();
				// 信息框隐藏
				$(".login-p-con-login").hide();
				$("#index_user_departmentname_span").html("");
				$("#index_user_name_span").html("");
			}
		},
		login : {
			bindLoginInBtn : function(){
				// 得到用户名和密码，发送登录请求
				$('#index_login_btn').click(function(){
					var account = $('#index_login_accout_input').val();
					var password = $('#index_login_password_input').val();
					var data = {
						account	: account,
						password : password
					}
					$.ajax({
						url :"/BZCX/login/in",
				        type : "post",
				        data : data,
				        ansyc : false,
				        success : function(data){
				        	if(data.code == 200){
				        		// 将登录成功标识写入cookie
				        		commonUtil.addCookie("isLogin", "true", -1);
								// 将用户名和id和单位名 写入cookie
				        		var user = {
				        			userId : data.data.id,	
				        			userName : data.data.realname,
				        			departmentName : data.data.departmentName
				        		}
				        		var userInfoStr = JSON.stringify(user);
				        		commonUtil.addCookie("userInfo", userInfoStr, -1);
				        		setTimeout(function(){
				        			location.reload();
				        		},500)
				        	}else{
				        		layer.alert(data.msg);
				        	}
				        }
				    });
				});
			},
			/**
			 * 退出登录
			 */
			bindLoginOutBtn : function(){
				$('#index_login_out_btn').click(function(){
					$.ajax({
						url :"/BZCX/login/user/out",
				        type : "post",
				        ansyc : false,
				        success : function(data){
				        	// 销毁本地登录的标识
				        	commonUtil.addCookie("isLogin", "", 0);
			        		commonUtil.addCookie("userInfo", "", -1);
			        		location.reload();
				        }
				    });
				});
			}
		},
		// 初始化界面的简单检索热词
		initSimpleHotWord : function(){
			var data = {
				moudle : "简单检索"
			}
			// 发送ajax请求简单检索的热词
			$.ajax({
				url :"/BZCX/history/hotword",
		        type : "post",
		        data : data,
		        ansyc : false,
		        success : function(data){
		        	// 得到搜索结果之后 将搜索结果放到界面上
		        	var wordList = data;
		        	var parent = $("#simple_search_hotword_div");
		        	parent.html("");
		        	for(var i = 0; i < wordList.length; i++){
		        		 var word=	wordList[i].replace(".doc","").replace(".docx","");
		        		var childStr = "<span>" + word+ "</span>"
		        		parent.append($(childStr));
		        	}
		        	// 给热词绑定点击事件
		        	$("#simple_search_hotword_div span").click(function(){
		        		var keyword = this.innerText;
		        		$("#simple_search_keyword_input").val(keyword);
		        		searchHtml.simpleSearch.search(keyword);
		        	});
		        }
		    });
		},
		// 给简单检索按钮绑定事件
		bindSimpleSearchBtn : function(){
			$("#index_simple_search_btn").on('click',function(){
				var keyword = $("#simple_search_keyword_input").val();
				searchHtml.simpleSearch.search(keyword);
			});
		},
		// 初始化轮播图	
		initSlide : function(data){
			var data = {
				rows : 3,
				hasImg : 1
			}
			$.ajax({
				url :"/BZCX/news/list",
		        type : "post",
		        data : data,
		        success : function(data){
		            var slideArray = [];
		            var newsList = data.rows;
		            if(newsList.length != 0){
						for(var i = 0; i < newsList.length; i ++){
							var src = "/newsImg/" + newsList[i].imgPath.split("&")[1];
							var href = "/BZCX/newsDetail.html?newsId=" + newsList[i].newsId;
							slideArray.push(new Slide(src, href, newsList[i].title));
						}
					}else{
						slideArray.push(new Slide("imgs/index/ban1.jpg", "#", "当前未设置轮播图"));
					}
		            $("#mySlide").mySlide(slideArray,3000);
		        	$("#mySlide a").attr("target","_blank");
		        }
		    });
		},
		// 初始化通知公告
		initNotice : function(){
			// 发送ajax 请求通知公告
			var data = {
					rows : 8,
					newsCate : 2
			}
			$.ajax({
				url :"/BZCX/news/list",
		        type : "post",
		        data : {"newsCate":1},
		        success : function(data){
		        	var newsList = data.rows;
		        	var parentDiv = $('#news_notice_div');
		        	parentDiv.html("");
		        	var count = Math.ceil(newsList.length/2);
		        	for(var i = 0; i < count; i ++){
		        		var childStr = '<div class="main2-inner-cen-inner-item">'
		        						+ '<p title="'+newsList[2 * i].title+'" onclick="IndexNewsInfo(\'' + newsList[2 * i].newsId + '\')"><b>' + newsList[2 * i].title + '</b><span>' + newsList[2 * i].publishDate + '</span></p>';
		        		if(2 * i + 1 <= newsList.length - 1){
		        			childStr += '<p title="'+newsList[2 * i + 1].title+'"  onclick="IndexNewsInfo(\'' + newsList[2 * i + 1].newsId + '\')"><b>' + newsList[2 * i + 1].title + '</b><span>' + newsList[2 * i + 1].publishDate + '</span></p>' 
							+ '</div>';
		        		}
		        		parentDiv.append($(childStr));
		        		// 未实现滚动条好的效果 追加一条数据在parentDiv里面
		        		if(2 * i == newsList.length - 1 || 2 * i + 1 == newsList.length - 1){
		        			var childStr = '<div class="main2-inner-cen-inner-item">'
							+ '<p title="'+newsList[0].title+'"  onclick="IndexNewsInfo(\'' + newsList[0].newsId + '\')"><b>' + newsList[0].title + '</b><span>' + newsList[0].publishDate + '</span></p>'
		        			+ '<p title="'+newsList[1].title+'" onclick="IndexNewsInfo(\'' + newsList[1].newsId + '\')"><b>' + newsList[1].title + '</b><span>' + newsList[1].publishDate + '</span></p>'
							+ '</div>';
		        			parentDiv.append($(childStr));
		        		}
		        	}
		        	var titleIndex = 0;
		    		var titleS = setInterval(function(){
		    			if(titleIndex > count){
		    				$(".main2-inner-cen-inner").css({top:"0px"});
		    				titleIndex = 1;
		    			}
	    		    	$(".main2-inner-cen-inner").animate({top:-54 * titleIndex + "px"});
	    		    	titleIndex ++;
		    		},2000);
		        }
		    });
		},
		// 初始化昆仑动态信息
		initNewsTabLeft : function(){
			// 请求后台 得到所有昆仑标准动态
			var data = {
				rows : 9,
				newsCate : 2
			}
			$.ajax({
				url :"/BZCX/news/list",
		        type : "post",
		        data : {"newsCate":2},
		        success : function(data){
		        	var newsList = data.rows;
					var parent = $("#news_tab_left");
					parent.html("");
					var date = new Date().toLocaleDateString().split("/");
					var da = "";
					for (var i = 0; i < date.length; i++) {
						da += date[i];
						if(i != (date.length - 1)){
							da += "-";
						}
					}
					for(var i = 0; i < newsList.length; i ++){
						var news = newsList[i];
						if(news.publishDate == da){
							da = '<em></em>';
						}else{
							da = '';
						}
						var childStr = '<li title="'+news.title+'" onclick="IndexNewsInfo(\'' + news.newsId + '\')">' 
										+'<i></i> '
										+'<b>'+news.title+'</b>'
										+da
										+'<span>[' + news.publishDate +']</span>'
										+'</li>';
							/*"<li onclick='IndexNewsInfo(\"" + news.newsId + "\")'>
							 * <i></i>
							 * <b>" + news.title + "<b/>
							 * <em></em>"
							+ "<span>[" "]</span>
								</li>";*/
						parent.append($(childStr));
					}
		        }
		    });
		},
		// 初始化动态
		initNewsTabRight : function(){
			var data = {
					rows : 9,
					newsCate : 3
				}
				$.ajax({
					url :"/BZCX/news/list",
			        type : "post",
			        data : {"newsCate":3},
			        success : function(data){
			        	var newsList = data.rows;
						var parent = $("#news_tab_right");
						parent.html("");
						var date = new Date().toLocaleDateString().split("/");
						var da = "";
						for (var i = 0; i < date.length; i++) {
							da += date[i];
							if(i != (date.length - 1)){
								da += "-";
							}
						}
						for(var i = 0; i < newsList.length; i ++){
							var news = newsList[i];
							if(news.publishDate == da){
								da = '<em></em>';
							}else{
								da = '';
							}
							var childStr ='<li title="'+news.title+'" onclick="IndexNewsInfo(\'' + news.newsId + '\')">' 
							+'<i></i> '
							+'<b>'+news.title+'</b>'
							+da
							+'<span>[' + news.publishDate +']</span>'
							+'</li>';
							parent.append($(childStr));
						}
			        }
			    });
		},
		// 初始化共享资源列表
		initFileShare : function(){
				var data = {
						rows : 6	
					}
					$.ajax({
						url :"/BZCX/share/list",
				        type : "post",
				        data : data,
				        success : function(data){
				        	var id = commonUtil.getUUID();
							var fileList = data.attr.hostUpload;
			        		var parent = $("#file_share_ul");
			        		parent.html("");//if(right.checkClick()){/BZCX/share/downlod?id='+file.fileId+'
			        		for(var i = 0; i < fileList.length; i ++){
			        			if(fileList[i].status == 2){
			        				var file = fileList[i];
			        				var childStr = '<li title="'+file.fileName+'" id="' + file.fileId + '" class="' + id + '"><a href="#" style="color: #666666" onclick="shareDown(\'' + file.fileId + '\')"><i></i><b>' + file.fileName + '</b></a>';
			        				childStr += '</li>';
			        				parent.append($(childStr));
			        			}
			        		}
			        		// 给二级单位的列表 添加点击事件
			        		$('.' + id).click(function(){
			        			if(location.href.indexOf("index.html") == -1 || right.checkClick()){
			        				var architectureId = $(this).attr("id");
			        				// 点击跳转到资源详情页
			        				
			        			}
			        		});
				        }
				    });
		},
		/**
		 * 初始化二级单位列表
		 * id: 二级单位所在列表的ID
		 */
		initSecondUnit : function(id, order){
			// 请求后台 得到所有二级单位
			var data = {};
			if(order){
				data.order = order;
			}
			$.ajax({
				url :"/BZCX/department/second/unit",
		        type : "post",
		        data : data,
		        success : function(data){
	        		// 将二级单位放到界面上
	        		var parent = $("#" + id);
	        		parent.html("");
	        		for(var i = 0; i < data.length; i ++){
	        			var secondUnit = data[i];
	        			var childStr = '<li id="' + secondUnit.architectureId + '" class="' + id + '"><i>' + (i + 1) + "</i><b>" + secondUnit.departmentName + "</b>";
	        			if(i < 3){
	        				childStr += "<em></em>";
	        			}
	        			childStr += "</li>";
	        			parent.append($(childStr));
	        		}
	        		// 给二级单位的列表 添加点击事件
	        		$("."+id).click(function(){
	        			if(location.href.indexOf("index.html") == -1 || right.checkClick()){
	        				var architectureId = $(this).attr("id");
	        				if(architectureId == "null"){
	        					/*$.ajax({
	        						url :"/BZCX/department/parent",
	        				        type : "post",
	        				        success : function(result){
	        				        	if(result.code == 200){
	        				        		window.open("system.html?moudle=" + result.data.architectureId,"_self");
	        				        	}else{
	        				        		layer.alert("查看体系失败，没有总体系或本单位体系");
	        				        	}
	        				        }
	        				    });*/
	        					window.open("system.html?moudle=昆仑公司标准体系","_self");
	        				}else{
	        					// 点击跳转到标准体系页
		        				window.open("system.html?moudle=" + architectureId,"_self");	
	        				}
	        			}
	        		});
		        }
		    });
		},
		// 给新闻的tab切换绑定事件
		bindNewsTab : function(){
			$(".news-p-tab1").on('click',function(){
				$(this).addClass("active").next().removeClass("active");
				$(".main3_right_content-inner .news-ul").eq(0).css("display","block");
				$(".main3_right_content-inner .news-ul").eq(1).css("display","none");
				$("#indexmoreNews").attr("onclick","indexmore(2)");
			});
			$(".news-p-tab2").on('click',function(){
				$(this).addClass("active").prev().removeClass("active");
				$(".main3_right_content-inner .news-ul").eq(0).css("display","none");
				$(".main3_right_content-inner .news-ul").eq(1).css("display","block");
				$("#indexmoreNews").attr("onclick","indexmore(3)");
			});
		}
	}
function shareDown(datafileid){
	if(right.checkClick()){
		$.ajax({
			url :"/BZCX/share/downlod",
	        type : "post",
	        data : {"id":datafileid},
	        ansyc : false,
	        success : function(data){
	        	location.href="/BZCX/share/downlod?id="+datafileid;
	        },
	        error : function() {
	        	layer.alert("文件不存在，请联系管理员")
			}
	    })
	}
}
