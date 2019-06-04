//点击tab切换
$(".tab-con-tit1").on('click',function(){
    var myIndex=$(this).index();
    $(this).addClass("active").siblings().removeClass("active");
    $(".tab-con-top1").html($(this).html());
    //内容区域对应替换
    $(".tab-con-vot .tab-con-vot-in").eq(myIndex).css("display","block");
    $(".tab-con-vot .tab-con-vot-in").eq(myIndex).siblings().css("display","none");
});
//初始化点击标准数量
$(".tab-con-tit1").eq(0).trigger("click");