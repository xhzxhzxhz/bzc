$(function(){
	 // 界面加载的时候，先查询部门列表
	 secondUnitHtml.initUnitList();
	 secondUnitHtml.bindTabClick();
	
})
/**
 * 二级单位对应对象
 */
var secondUnitHtml = {
	initUnitList : function(order){
		indexHtml.initSecondUnit("secoend_unit_ul_1", "standardCount");
		indexHtml.initSecondUnit("secoend_unit_ul_2");
		indexHtml.initSecondUnit("secoend_unit_ul_3");
	},
	bindTabClick : function(){
		//点击tab切换
		$(".tab-con-tit1").on('click',function(){
			var myIndex=$(this).index();
			$(this).addClass("active").siblings().removeClass("active");
			$(".tab-con-wrap .tab-con-in1").eq(myIndex).show();
			$(".tab-con-wrap .tab-con-in1").eq(myIndex).siblings().hide();
		});
		//初始化点击标准数量
		$(".tab-con-tit1").eq(0).trigger("click");
	}
}
