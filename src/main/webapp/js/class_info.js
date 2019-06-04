
$(function(){
	//行业图片
	let imgArr=[
		{
			number:"01",
			name:'综合、术语学、标准化、文献',
			path:'img/pic01.png'
		},{
			number:"03",
			name:'社会学、服务、公司(企业)的组织和管理、行政、运输',
			path:'img/pic03.png'
		},{
			number:"07",
			name:'数学、自然科学',
			path:'img/pic07.png'
		},{
			number:11,
			name:'医药卫生技术',
			path:'img/pic11.png'
		},{
			number:13,
			name:'环保、保健与安全',
			path:'img/pic13.png'
		},{
			number:17,
			name:'计量学与测量、物理现象',
			path:'img/pic17.png'
		},{
			number:19,
			name:'试验',
			path:'img/pic19.png'
		},{
			number:21,
			name:'机械系统和通用件',
			path:'img/pic21.png'
		},{
			number:23,
			name:'流体系统和通用件',
			path:'img/pic23.png'
		},{
			number:25,
			name:'机械制造',
			path:'img/pic25.png'
		},{
			number:27,
			name:'能源和热传导工程',
			path:'img/pic27.png'
		},{
			number:29,
			name:'电气工程',
			path:'img/pic29.png'
		},{
			number:31,
			name:'电子学',
			path:'img/pic31.png'
		},{
			number:33,
			name:'电信、音频和视频技术',
			path:'img/pic33.png'
		},{
			number:35,
			name:'信息技术、办公机械设备',
			path:'img/pic35.png'
		},{
			number:37,
			name:'成像技术',
			path:'img/pic37.png'
		},{
			number:39,
			name:'精密机械、珠宝',
			path:'img/pic39.png'
		},{
			number:43,
			name:'道路车辆工程',
			path:'img/pic43.png'
		},{
			number:45,
			name:'铁路工程',
			path:'img/pic45.png'
		},{
			number:47,
			name:'造船和海上建筑物',
			path:'img/pic47.png'
		},{
			number:49,
			name:'航空器和航天器工程',
			path:'img/pic49.png'
		},{
			number:53,
			name:'材料储运设备',
			path:'img/pic53.png'
		},{
			number:55,
			name:'货物的包装和调运',
			path:'img/pic55.png'
		},{
			number:59,
			name:'纺织和皮革技术',
			path:'img/pic59.png'
		},{
			number:61,
			name:'服装工业',
			path:'img/pic61.png'
		},{
			number:65,
			name:'农业',
			path:'img/pic65.png'
		},{
			number:67,
			name:'食品技术',
			path:'img/pic67.png'
		},{
			number:71,
			name:'化工技术',
			path:'img/pic71.png'
		},{
			number:73,
			name:'采矿和矿产品',
			path:'img/pic73.png'
		},{
			number:75,
			name:'石油及相关技术',
			path:'img/pic75.png'
		},{
			number:77,
			name:'冶金',
			path:'img/pic77.png'
		},{
			number:79,
			name:'木材技术',
			path:'img/pic79.png'
		},{
			number:81,
			name:'玻璃和陶瓷工业',
			path:'img/pic81.png'
		},{
			number:83,
			name:'橡胶和塑料工业',
			path:'img/pic83.png'
		},{
			number:85,
			name:'造纸技术',
			path:'img/pic85.png'
		},{
			number:87,
			name:'涂料和颜料工业',
			path:'img/pic87.png'
		},{
			number:91,
			name:'建筑材料和建筑物',
			path:'img/pic91.png'
		},{
			number:93,
			name:'土木工程',
			path:'img/pic93.png'
		},{
			number:95,
			name:'军事工程',
			path:'img/pic95.png'
		},{
			number:97,
			name:'家用和商用设备、文娱、体育',
			path:'img/pic97.png'
		}
	]	
	let loading='img/5.png';
	//循环生成行业图标
	for(let i=0;i<imgArr.length;i++){
		let imgDiv=$('<div class="imgDiv"  onclick="clickimg(\''+imgArr[i].number+'\',\''+imgArr[i].name+'\')"></div>').css('background-image','url('+icspath+')');
		let div=$('<div></div>').text(imgArr[i].number);
		let p=$('<p></p>').text(imgArr[i].name);
		imgDiv.append(div).append(p);
		$('.content').append(imgDiv);
	}
	
	//首页显示图片
	//firstLoad();
	
	//滚动加载
	
	//搜索框点击
	$(".top_help").click(function(){
		$('.top_help').css('display','none');
		$('.top_inputDiv input').focus();
	});
	
	//输入框回车键按下
	$('.top_inputDiv input').keypress(function(){
		if($(event.keyCode)[0]=='13'){
			var keyword = $("#ics_keyword").val();
			//重新渲染数据
			$.ajax({
				url : "/BZCX/icstype/listByQuery",
				type : "post",
				data : {"icsCode" : keyword,
						"icsName" : keyword,
						"likeS" : keyword,
						"icsParendid" : "0"
					},
				async : true,
				success : function(data) {
					if(data.length > 0){
						$('.content').empty();
						for (var i = 0; i < data.length; i++) {
							var icspath = 'img/pic'+data[i].icsCode+'.png';
							let imgDiv=$('<div class="imgDiv"  onclick="clickimg(\''+data[i].icsCode+'\',\''+data[i].icsName+'\')"></div>').css('background-image','url('+icspath+')');
							let div=$('<div></div>').text(data[i].icsCode);
							let p=$('<p></p>').text(data[i].icsName);
							imgDiv.append(div).append(p);
							$('.content').append(imgDiv);
						}
					}else{
						alert("空")
					}
				}
			})
		}
	})
	//搜索框失焦
	$('.top_inputDiv input').blur(function(){
		if($('.top_inputDiv input').val()==''){
			$('.top_help').css('display','flex').css('display','-webkit-flex');
		}else{
			$('.top_help').css('display','none');
		}
	})
})

//分类点击事件
function clickimg(icsCode,icsName) {
	var info = {
		"icsCode" : icsCode,
		"icsName" : icsName
	}
	var nextinfo = JSON.stringify(info); 
	nextinfo = myencodeURI(nextinfo);
	window.location.href = "class_info_two.html?icsCode="+nextinfo;
}

function myencodeURI(str){  
    return  encodeURIComponent(encodeURIComponent(str));  
} 
//首页图片
function firstLoad() {
    //设备可用高度
    var availHeight = window.screen.availHeight;
    //遍历#one下的img，然后替换路径
    for (var x = 0; x < document.querySelectorAll('.imgDiv').length; x++) {
        var el = document.querySelectorAll('.imgDiv')[x];
        //如果是首屏显示的图片
        if (el.offsetTop < availHeight) {
    		var url=el.getAttribute('data-url');
    		el.style.backgroundImage='url(\''+url+'\')';
	        el.removeAttribute('data-url');
        } else {
            break;
        }
    }
}
//滚动加载
function bindScroll() {
	alert('xxx')
    // 设备可用高度
    var availHeight = window.screen.availHeight;
    // 滚动的高度
    var scrollHeight = document.documentElement.scrollTop;
    // 距img元素显露出的距离
    var diff = 50;
    // 判断如果显示出来了#two
    for (var x = 0; x < document.querySelectorAll('.imgDiv').length; x++) {
        var el = document.querySelectorAll('.imgDiv')[x];
        // img距顶部的高度
        var contentTop = el.offsetTop;
        // 判断此时显示出img
        if (scrollHeight + diff > contentTop - availHeight) {
            if (el.hasAttribute('data-url')) {
            	var url=el.getAttribute('data-url');
        		el.style.backgroundImage='url(\''+url+'\')';
    	        el.removeAttribute('data-url');
            }
        }
    }
}