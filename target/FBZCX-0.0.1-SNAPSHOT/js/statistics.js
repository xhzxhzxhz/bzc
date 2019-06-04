//页面上部分数据查询   SQL 
var changeTime;
changeTime = $("#changeTime option:selected").val(); // 获取选中的项
var statisticsHtml = {
	initSlide : function(data) {
		var data = {
			"changeTime" : changeTime,
			"erjibiaozhun" : "desc",
			"erjiyonghu" : "desc",
			"page"   :1,
			"rows"   :10
		}
		$.ajax({
					url : "/BZCX/statistics/liststatistics",
					type : "post",
					data : data,
					async : false,
					success : function(data) {
						var content = data.StatisticAnalysis;
						$("#TodayAcess").html(content[0].TodayAcess[0].begin);
						$("#CumulaAcess").html(content[0].CumulaAcess[0].begin);
						/*$("#ActiveUserNumber").html(
								content[0].ActiveUserNumber[0].begin);*/
						$("#TotalUserNumber").html(
								content[0].TotalUserNumber[0].begin);
						$("#TodayRetrieval").html(
								content[0].TodayRetrieval[0].begin);
						$("#TotalRetrieval").html(
								content[0].TotalRetrieval[0].begin);
						$("#StandardView").html(
								content[0].StandardView[0].begin);
						$("#StandardDownload").html(
								content[0].StandardDownload[0].begin);
						SystemAc = data.SystemAccess;
						StandardClassificalis = data.StandardClassifica;
						for(i=0;i<StandardClassificalis.length;i++){
							if(StandardClassificalis[i][0]=="国标"){
								$("#gb").html("国标："+StandardClassificalis[i][1]);
							}else if(StandardClassificalis[i][0]=="行标"){
								$("#hb").html("行标："+StandardClassificalis[i][1]);
							}else if(StandardClassificalis[i][0]=="企标"){
								$("#qb").html("企标："+StandardClassificalis[i][1]);
							}else{
								$("#sh").html("三化："+StandardClassificalis[i][1]);
							}
						}
						SearchWordHeat = data.SearchWordHeat;
					
						TwoLevelQuantity = data.TwoLevelQuantity;
						TwoLevelAccess = data.TwoLevelAccess;
						TwoLevelUser = data.TwoLevelUser;
						StandardUsage = data.StandardUsage;
						RetWords = data.RetWords
					}
				});
	},
	SearchWord : function() {
		changeTime = $("#changeTime option:selected").val(); // 获取选中的项
		var page = $("#con3").val(); // 获取选中的项
		var data = {
				"changeTime" : changeTime,
				"erjibiaozhun" : "desc",
				"erjiyonghu" : "desc",
				"page"   :page,
				"rows"   :10
				
			}
		$.ajax({
			url : "/BZCX/statistics/SearchWord",
			type : "post",
			data : data,
			async : false,
			success : function(data) {
		        SearchWordHeat = data.SearchWordHeat;
		    	if(SearchWordHeat[0].length>0){
				chart3Option.xAxis.categories = SearchWordHeat[0];
				chart3Option.series[0].data = SearchWordHeat[1];
				Highcharts.chart('container3', chart3Option);
				}else{
					$('#con3').val(Number($('#con3').val())-1);
				}
			}
		});
	},
	/*TwoLevelQuantity : function() {
		var page = $("#con4").val(); // 获取选中的项
		var data = {
				"erjibiaozhun" : "desc",
				"erjiyonghu" : "desc",
				"page"   :page,
				"rows"   :10
			}
		$.ajax({
			url : "/BZCX/statistics/TwoLevelQuantitylis",
			type : "post",
			data : data,
			async : false,
			success : function(data) {
				TwoLevelQuantity = data.TwoLevelQuantity;
		    	if(TwoLevelQuantity[0].length>0){
				chart4Option.xAxis.categories = TwoLevelQuantity[0];
				chart4Option.series[0].data = TwoLevelQuantity[1];
				Highcharts.chart('container4', chart4Option);
				}else{
					$('#con4').val(Number($('#con4').val())-1);
				}
			}
		});
	},*/
	/*TwoLevelUser : function() {
		var page = $("#con6").val(); // 获取选中的项
		console.log(page);
		var data = {
				"erjibiaozhun" : "desc",
				"erjiyonghu" : "desc",
				"page"   :page,
				"rows"   :10
			}
		$.ajax({
			url : "/BZCX/statistics/TwoLevelUserlist",
			type : "post",
			data : data,
			async : false,
			success : function(data) {
				TwoLevelUser = data.TwoLevelUser;
		        console.log(TwoLevelUser);
		    	if(TwoLevelUser[0].length>0){
				chart6Option.xAxis.categories = TwoLevelUser[0];
				chart6Option.series[0].data = TwoLevelUser[1];
				Highcharts.chart('container6', chart6Option);
				}else{
					$('#con6').val(Number($('#con6').val())-1);
				}
			}
		});
	},*/
	StandardUsage : function() {
		var page = $("#con5").val(); // 获取选中的项
		console.log(page);
		var data = {
				"erjibiaozhun" : "desc",
				"erjiyonghu" : "desc",
				"page"   :page,
				"rows"   :10
			}
		$.ajax({
			url : "/BZCX/statistics/StandardUsage",
			type : "post",
			data : data,
			async : false,
			success : function(data) {
				StandardUsage = data.StandardUsage;
		        console.log(StandardUsage);
		    	if(StandardUsage[0].length>0){
				chart5Option.xAxis.categories = StandardUsage[0];
				chart5Option.series[0].data = StandardUsage[1];
				Highcharts.chart('container5', chart5Option);
				}else{
					$('#con5').val(Number($('#con5').val())-1);
				}
			}
		});
	},
	/*RetWords : function() {
		var page = $("#con8").val(); // 获取选中的项
		console.log(page);
		var data = {
				"erjibiaozhun" : "desc",
				"erjiyonghu" : "desc",
				"page"   :page,
				"rows"   :3
			}
		$.ajax({
			url : "/BZCX/statistics/RetWords",
			type : "post",
			data : data,
			async : false,
			success : function(data) {
				RetWords = data.RetWords
		        console.log(RetWords);
		    	if(TwoLevelUser[0].length>0){
				chart8Option.xAxis.categories = RetWords[0];
				chart8Option.series = RetWords[1];
				Highcharts.chart('container8', chart8Option);
				}else{
					$('#con8').val(Number($('#con8').val())-1);
				}
			}
		});
	}*/
};

var SystemAc;
var StandardClassificalis;
var SearchWordHeat;
var TwoLevelQuantity;
var TwoLevelAccess;
var TwoLevelUser;
var StandardUsage;
var RetWords;
// 先初始化表格
statisticsHtml.initSlide();
/*
var	chart8Option={
	title : {
		text : ''
	},
	subtitle : {
		text : ''
	},
	yAxis : {
		title : {
			text : ''
		}
	},
	xAxis : {
		categories : RetWords[0]
	},
	legend : {
		layout : 'vertical',
		align : 'right',
		verticalAlign : 'middle'
	},
	credits : {
		enabled : false
	},
	plotOptions : {
		series : {
			label : {
				connectorAllowed : false
			}
		}
	},
	series : RetWords[1],
	responsive : {
		rules : [ {
			condition : {
				maxWidth : 500
			},
			chartOptions : {
				legend : {
					layout : 'horizontal',
					align : 'center',
					verticalAlign : 'bottom'
				}
			}
		} ]
	}
};*/
var chart5Option = {	
	chart : {
		type : 'bar'
	},
	title : {
		text : ''
	},
	subtitle : {
		text : ''
	},
	xAxis : {
		categories : StandardUsage[0],
		title : {
			text : null
		}
	},
	yAxis : {
		min : 0,
		title : {
			text : '使用率 (次)',
			align : 'high'
		},
		labels : {
			overflow : 'justify'
		}
	},
	tooltip : {
		valueSuffix : '次'
	},
	plotOptions : {
		bar : {
			dataLabels : {
				enabled : true,
				allowOverlap : true
			}
		}
	},
	legend : {
		enabled : false
	},
	credits : {
		enabled : false
	},
	series : [ {
		name : '使用率',
		data : StandardUsage[1]
	} ]
};

	/*var chart6Option = {	
	chart : {
		type : 'bar'
	},
	title : {
		text : ''
	},
	subtitle : {
		text : ''
	},
	xAxis : {
		categories : TwoLevelUser[0],
		title : {
			text : null
		}
	},
	yAxis : {
		min : 0,
		title : {
			text : '用户数量',
			align : 'high'
		},
		labels : {
			overflow : 'justify'
		}
	},
	tooltip : {
		valueSuffix : ' '
	},
	plotOptions : {
		bar : {
			dataLabels : {
				enabled : true,
				allowOverlap : true
			}
		}
	},
	legend : {
		enabled : false
	},
	credits : {
		enabled : false
	},
	series : [ {
		name : '用户数量',
		data : TwoLevelUser[1]
	} ]
};*/
  /*
var chart4Option = {
	chart : {
		type : 'column'
	},
	title : {
		text : ''
	},
	subtitle : {
		text : ''
	},
	xAxis : {
		categories : TwoLevelQuantity[0]
	},
	yAxis : {
		labels : {
			x : -15
		},
		title : {
			text : ''
		}
	},
	series : [ {
		name : '标准数量',
		data : TwoLevelQuantity[1]
	} ],
	legend : {
		enabled : false
	},
	credits : {
		text : ''
	},
	responsive : {
		rules : [ {
			condition : {
				maxWidth : 500
			},
			// Make the labels less space demanding on mobile
			chartOptions : {
				xAxis : {
					labels : {
						formatter : function() {
							return this.value.replace('月', '')
						}
					}
				},
				yAxis : {
					labels : {
						align : 'left',
						x : 0,
						y : -2
					},
					title : {
						text : ''
					}
				}
			}
		} ]
	}
};
*/
var chart3Option = {
	chart : {
		type : 'column'
	},
	title : {
		text : ''
	},
	subtitle : {
		text : ''
	},
	xAxis : {
		categories : SearchWordHeat[0]
	},
	yAxis : {
		labels : {
			x : -15
		},
		title : {
			text : ''
		}
	},
	series : [ {
		name : '热度',
		data : SearchWordHeat[1]
	} ],
	legend : {
		enabled : false
	},
	credits : {
		text : ''
	},
	responsive : {
		rules : [ {
			condition : {
				maxWidth : 500
			},
			// Make the labels less space demanding on mobile
			chartOptions : {
				xAxis : {
					labels : {
						formatter : function() {
							return this.value.replace('月', '')
						}
					}
				},
				yAxis : {
					labels : {
						align : 'left',
						x : 0,
						y : -2
					},
					title : {
						text : ''
					}
				}
			}
		} ]
	}
};
var chart = null;

chart = Highcharts.chart('container1', {
	chart : {
		zoomType : 'x'
	},
	title : {
		text : ''
	},
	subtitle : {
		text : document.ontouchstart === undefined ? '鼠标拖动可以进行缩放' : '手势操作进行缩放'
	},
	xAxis : {
		type : 'category'
	},
	tooltip : {

	},
	yAxis : {
		title : {
			text : ''
		}
	},
	legend : {
		enabled : false
	},
	credits : {
		text : ''
	},
	plotOptions : {
		area : {
			fillColor : {
				linearGradient : {
					x1 : 0,
					y1 : 0,
					x2 : 0,
					y2 : 1
				},
				stops : [
						[ 0, Highcharts.getOptions().colors[0] ],
						[
								1,
								Highcharts.Color(
										Highcharts.getOptions().colors[0])
										.setOpacity(0).get('rgba') ] ]
			},
			marker : {
				radius : 2
			},
			lineWidth : 1,
			states : {
				hover : {
					lineWidth : 1
				}
			},
			threshold : null
		}
	},
	series : [ {
		type : 'area',
		name : '系统访问量排行',
		data : SystemAc
	} ]
});
$('#container2')
		.highcharts(
				{
					chart : {
						plotBackgroundColor : null,
						plotBorderWidth : null,
						plotShadow : false,
						spacing : [ 40, 0, 40, 0 ]
					},
					title : {
						floating : true,
						text : '100%'
					},
					tooltip : {
						pointFormat : '{series.name}: <b>{point.percentage:.1f}%</b>'
					},
					credits : {
						text : ''
					},
					plotOptions : {
						pie : {
							allowPointSelect : true,
							cursor : 'pointer',
							dataLabels : {
								enabled : true,
								format : '<b>{point.name}</b>: {point.percentage:.1f} %',
								style : {
									color : (Highcharts.theme && Highcharts.theme.contrastTextColor)
											|| 'black'
								}
							}
						/*
						 * point: { events: { mouseOver: function(e) { //
						 * 鼠标滑过时动态更新标题 // 标题更新函数，API
						 * 地址：https://api.hcharts.cn/highcharts#Chart.setTitle
						 * chart.setTitle({ text: e.target.name+ '\t'+
						 * e.target.y + ' %' }); } , click: function(e) { //
						 * 同样的可以在点击事件里处理 chart.setTitle({ text: e.point.name+
						 * '\t'+ e.point.y + ' %' }); } } },
						 */
						}
					},
					series : [ {
						type : 'pie',
						innerSize : '70%',
						name : '占比',
						data : StandardClassificalis
					} ]
				},
				function(c) {
					// 环形图圆心
					var centerY = c.series[0].center[1], titleHeight = parseInt(c.title.styles.fontSize);
					c.setTitle({
						y : centerY + titleHeight / 2
					});
					chart = c;
				});

$('#container7').highcharts(
				{
					chart : {
						type : 'funnel',
						marginRight : 100
					},
					title : {
						text : '',
						x : -50
					},
					credits : {
						enabled : false
					},
					plotOptions : {
						series : {
							dataLabels : {
								enabled : true,
								format : '<b>{point.name}</b>',
								color : (Highcharts.theme && Highcharts.theme.contrastTextColor)
										|| 'black',
								softConnector : true
							},
							neckWidth : '30%',
							neckHeight : '25%'
						}
					},
					legend : {
						enabled : false
					},
					series : [ {
						name : '访问量',
						data : TwoLevelAccess
					} ]
				});

function Stacexport() {
	window.location = "/BZCX/statistics/Export";

}
$(".chart-prev").click(function(){
	var name= $(this).attr("name");
	var ind= $("input[name='"+name+"']").val();
	if(ind>1){
		$("input[name='"+name+"']").val(Number(ind)-1);
		jump(name);	
	}else{
		$("input[name='"+name+"']").val(1);
	}
});
$(".chart-next").click(function(){
	var name= $(this).attr("name");
	var ind= $("input[name='"+name+"']").val();
	$("input[name='"+name+"']").val(Number(ind)+1);
	 jump(name);
});
//页面刚加载出来的数据
var chart3 = Highcharts.chart('container3', chart3Option);
/*var chart4 = Highcharts.chart('container4', chart4Option);*/
/*var chart6 = Highcharts.chart('container6', chart6Option);*/
var chart5 = Highcharts.chart('container5', chart5Option);
/*var chart8 = Highcharts.chart('container8', chart8Option);*/
// select 的change事件
$("#changeTime").change(function() {
	statisticsHtml.SearchWord();
});
function  jump(name){
	if(name=='container3'){
		statisticsHtml.SearchWord();
	}
	/*if(name=='container4'){
		statisticsHtml.TwoLevelQuantity();
	}*/
	/*if(name=='container6'){
		statisticsHtml.TwoLevelUser();
	}*/
	if(name=='container5'){
		statisticsHtml.StandardUsage();
	}
	/*if(name=='container8'){
		statisticsHtml.RetWords();
	}*/
}
