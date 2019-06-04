var statisticsDetailHtml = {
		initSlide : function(data){
			var data = {
					"changeTime":'30',
					"erjibiaozhun":"asc",
					"erjiyonghu":"asc"
			}
			$.ajax({
				url :"/BZCX/statistics/More",
		        type : "post",
		        data : data,
		        async : false,
		        success : function(data){
		        	 TwoLevelQuantity = data.TwoLevelQuantity
		        	 TwoLevelUser= data.TwoLevelUser
		        }
		    });
		},
		
		getQueryString : function(name) {
		    var reg = new RegExp('(^|&)' + name + '=([^&]*)(&|$)', 'i');
		    var r = window.location.search.substr(1).match(reg);
		    if (r != null) {
		        return unescape(r[2]);
		    }
		    return null;
		},
		chart1 : function(){
			Highcharts.chart('container', {
		        chart: {
		            type: 'column'
		        },
		        title: {
		            text: ''
		        },
		        subtitle: {
		            text: ''
		        },
		        xAxis: {
		            categories: TwoLevelQuantity[0]
		        },
		        yAxis: {
		            labels: {
		                x: -15
		            },
		            title: {
		                text: ''
		            }
		        },
		        legend: {
			        enabled: false
			    },
			    credits: {
			        text: ''
			    },
		        series: [{
		            name: '标准数量',
		            data: TwoLevelQuantity[1]
		        }],
		        responsive: {
		            rules: [{
		                condition: {
		                    maxWidth: 500
		                },
		                // Make the labels less space demanding on mobile
		                chartOptions: {
		                    xAxis: {
		                        labels: {
		                            formatter: function () {
		                                return this.value.replace('月', '')
		                            }
		                        }
		                    },
		                    yAxis: {
		                        labels: {
		                            align: 'left',
		                            x: 0,
		                            y: -2
		                        },
		                        title: {
		                            text: ''
		                        }
		                    }
		                }
		            }]
		        }
		    });
		},
		chart2 : function(){
			$('#container').highcharts({
		        chart: {
		            type: 'bar'
		        },
		        title: {
		            text: ''
		        },
		        subtitle: {
		            text: ''
		        },
		        xAxis: {
		            categories: TwoLevelUser[0],
		            title: {
		                text: null
		            }
		        },
		        yAxis: {
		            min: 0,
		            title: {
		                text: '用户数量',
		                align: 'high'
		            },
		            labels: {
		                overflow: 'justify'
		            }
		        },
		        tooltip: {
		            valueSuffix: ' '
		        },
		        plotOptions: {
		            bar: {
		                dataLabels: {
		                    enabled: true,
		                    allowOverlap: true
		                }
		            }
		        },
		        legend: {
			        enabled: false
			    },
		        credits: {
		            enabled: false
		        },
		        series: [{
		            name: '用户数量',
		            data: TwoLevelUser[1]
		        }]
		    });
		},
		chart3 : function(){
			Highcharts.chart('container', {
			    title: {
			        text: ''
			    },
			    subtitle: {
			        text: ''
			    },
			    yAxis: {
			        title: {
			            text: ''
			        }
			    },
			    xAxis: {
			        categories: ['标准1', '标准1', '标准3', '标准4', '标准5', '标准6', '标准7', '标准8']
			    },
			    legend: {
			        layout: 'vertical',
			        align: 'right',
			        verticalAlign: 'middle'
			    },
			    credits: {
			        enabled: false
			    },
			    plotOptions: {
			        series: {
			            label: {
			                connectorAllowed: false
			            }
			        }
			    },
			    series: [{
			        name: '单位1',
			        data: [43934, 52503, 57177, 69658, 97031, 119931, 137133, 154175]
			    }, {
			        name: '单位2',
			        data: [24916, 24064, 29742, 29851, 32490, 30282, 38121, 40434]
			    }, {
			        name: '单位3',
			        data: [11744, 17722, 16005, 19771, 20185, 24377, 32147, 39387]
			    }, {
			        name: '单位4',
			        data: [null, null, 7988, 12169, 15112, 22452, 34400, 34227]
			    }, {
			        name: '单位5',
			        data: [12908, 5948, 8105, 11248, 8989, 11816, 18274, 18111]
			    }],
			    responsive: {
			        rules: [{
			            condition: {
			                maxWidth: 500
			            },
			            chartOptions: {
			                legend: {
			                    layout: 'horizontal',
			                    align: 'center',
			                    verticalAlign: 'bottom'
			                }
			            }
			        }]
			    }
			});
		}
};



//确定当前展示哪个图表
var chart = statisticsDetailHtml.getQueryString("chart");
statisticsDetailHtml.initSlide(chart);
switch(chart)
{
case "1":
	statisticsDetailHtml.chart1();
	$(".chart-page").show();
	break;
case "2":
	statisticsDetailHtml.chart2();
	break;
case "3":
	statisticsDetailHtml.chart3();
	break;
}


	
