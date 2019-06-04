$(function(){
	var moudle = commonUtil.getRequestParam("moudle");
	systemHtml.moudle = moudle;
	// 首先根据当前的url来初始化树(首页转到体系界面的传体系的architectureId，其他的传"昆仑公司标准体系"或"本单位标准体系")
	systemHtml.initTree();
	// 加载标准表格数据
	systemHtml.initStandardTable();
	// 加载统计表格数据
	/*systemHtml.initStandardTable();*/
	// 给搜索按钮绑定事件
	systemHtml.bindStandardSearchBtn();
	// 给节点搜索按钮绑定按钮
	systemHtml.bindNodeSearchBtn();
	// 给标准统计好标准浏览tab添加事件
	systemHtml.bindTabClick();
});
var clicktitle = false;
var Qkey ="";
var systemHtml = {
		queryParam : {
			
		},
		/**
		 * moudle：昆仑公司标准体系，二级单位标准体系，本单位标准体系三种值
		 */
		initTree : function(moudle){
			searchHtml.initCateList("system_cate_select", "system_tree", this.moudle, function(data){
				var nodeId = data.instance.get_node(data.selected[0]).id;
				// 点击的时候 重新加载表格
				systemHtml.queryParam.nodeId = nodeId;
				systemHtml.queryParam.moudle = "体系查询";
				systemHtml.initStandardTable();
				if(clicktitle){
					systemHtml.initStatisticTable();
				}
			});
		},
		/**
		 * 初始化标准浏览表格
		 */
		initStandardTable : function(){
			if(!this.queryParam.nodeId){
				var url = "";
				// 第一次加载表格 表格中的数据是当前体系最父节点下的ID
				if(this.moudle == "昆仑公司标准体系"){
					url = "/BZCX/architecture/node/list?type=max";
				}else if(this.moudle == "本单位标准体系"){
					url = "/BZCX/architecture/node/list?type=user";
				}else if(this.moudle == "null"){
					systemHtml.queryParam.nodeId = '';
		        	systemHtml.initStandardTable();
				}else{
					url = "/BZCX/architecture/node/list?architectureId=" + this.moudle;
				}
				$.ajax({
					url : url,
			        type : "post",
			        ansyc : false,
			        success : function(data){
			        	if(data != ""){
			        		systemHtml.queryParam.nodeId = data[0].nodeId;
				        	systemHtml.initStandardTable();
				        	if(clicktitle){
								systemHtml.initStatisticTable();
							}
			        	}
			        }
			    });
			}else{
				// 初始化标准浏览表格
				var parent = $("#system_standard_table_div").html("");
				var child = '<table id="system_standard_table"></table>';
				parent.append($(child));
				systemHtml.queryParam.moudle = "体系查询";
				bzcxTable.initTable("system_standard_table", "570px", "/BZCX/standard/search", this.queryParam, function(){});
			}
		},
		/**
		 * 初始化标准统计表格
		 */
		initStatisticTable : function(){
			var parent = $("#system_standard_statistic_table_div").html("");
	        var child = '<table id="system_standard_statistic_table"></table>';
	        parent.append(child);
			var url = "architecture/statistic";
			var data = this.queryParam;
			$('#system_standard_statistic_table').GM({
			    supportCheckbox : false,
			    supportDrag : false,// 是否拖拽
			    supportAdjust : true,// 自动调整宽度
			    gridManagerName : 'test',
			    height : '605px',
			    supportAjaxPage : true,// 是否支持分页
			    ajax_url :url,
			    ajax_type : 'POST',
			    query : data,
			    ajax_success : function(data){
		    	data = JSON.parse(data);
		    	// 在界面显示标准体系名称
		    	var ss= data.taotaldata[0].nodeName;
		    	var nodeName =ss.replace(/\(.*?\)/g,'');
		    	$('#system_statistic_nodename_div').html('体系  <span style = "color:blue">' + nodeName + '</span>标准统计表'+
		         '<p>其中：国家标准 <span id="result_gbcount_span">'+data.taotaldata[0].gbCount+'</span> 条，'+
		                 '行业标准 <span id="result_hbcount_span">'+data.taotaldata[0].hbCount+'</span> 条，'+
		                 '企业标准 <span id="result_qbcount_span">'+data.taotaldata[0].qbCount+'</span> 条，'+	
		                 '三化标准 <span id="result_qbcount_span">'+data.taotaldata[0].otherCount+'</span> 条，'+
		                 '合计:<span id="result_qbcount_span">'+data.taotaldata[0].totalCount+'</span>条。</p>');
			        },
			    sizeData : [15,10,20],
			    pageSize : 15,
			    columnData : [
			        {
			            key : 'nodeName',
			            text : '分类',
			            width : '200px',
			            sorting : ''
			        },
			        {
			            key : 'publishDate',
			            text : '国际标准',
			            sorting : '',
			            template : function(action, rowObject) {
							return "";
						}
			        },
			        {
			            key : 'gbCount',
			            text : '国家标准',
			            sorting : ''
			        },
			        
			        {
			            key : 'hbCount',
			            text : '行业标准',
			            sorting : ''
			        },
			        {
			            key : 'qbCount',
			            text : '企业标准',
			            sorting : ''
			        },
			        {
			            key : 'otherCount',
			            text : '三化标准',
			            sorting : '',
			        },
			        {
			            key : 'totalCount',
			            text : '合计',
			            sorting : ''
			        }
			    ],
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
		},
		/**
		 * 给标准检索按钮绑定事件
		 */
		bindStandardSearchBtn : function(){
			$('#system_search_btn').click(function(){
				var searchItem = $('#system_search_item_select').val();
				var keyword = $('#system_search_keyword_input').val();
				systemHtml.queryParam[searchItem] = keyword;
				Qkey = keyword;
				systemHtml.initStandardTable();
				delete systemHtml.queryParam[searchItem];  
			});
		},
		/**
		 * 给节点检索按钮绑定事件
		 */
		bindNodeSearchBtn : function(){
			$("#system_node_search_btn").click(function () {
				var keyword = $("#system_node_search_keyword_input").val();
				$("#system_tree").jstree("search", keyword);
			});
		},
		bindTabClick : function(){
			// tab切换
			$(".search-p-tab").on('click',function(){
				var title = $(this).find("span").text();
				if(title == "标准统计"){
					if(!clicktitle){
						systemHtml.initStatisticTable();
					}
					clicktitle = true;
				}else{
					clicktitle = false;
				}
			    $(this).addClass("active").siblings().removeClass("active");
			    $(this).siblings().css("border-right","1px solid rgba(0,0,0,0)");
			    $(this).css("border-right","1px solid #95B9E7");
			    $(this).prev().css("border-right","1px solid #95B9E7");
			    var myHtml = $(this).find("span").html();
			    $("#guide_last").html(myHtml);
			    var myIndex=$(this).index();
			    $(".tab-con-wrap .tab-con").eq(myIndex).css("display","block");
			    $(".tab-con-wrap .tab-con").eq(myIndex).siblings().css("display","none");
			});

		}
}
