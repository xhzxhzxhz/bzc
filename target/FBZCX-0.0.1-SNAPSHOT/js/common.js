$("#header").load("common.html #header_inner", function() {

});
$("#nav").load("common.html #nav_inner", function() {
    //获得当前url
    var myurl = window.location.href;
    //判断当前为模块
    if(myurl.search(/index/) >= 0){
        $("#nav_inner>ul>li:eq(0) a").addClass("current");
        return false;
    }
    if(myurl.search(/search/) >= 0 || myurl.search(/result/) >= 0){
        $("#nav_inner>ul>li:eq(1) a").addClass("current");
        return false;
    }
    if(myurl.search(/secondUnit/) >= 0 || myurl.search(/system/) >= 0){
        $("#nav_inner>ul>li:eq(2) a").addClass("current");
        return false;
    }
    if(myurl.search(/newsCenter/) >= 0 || myurl.search(/newsList/) >= 0 || myurl.search(/newsDetail/) >= 0){
        $("#nav_inner>ul>li:eq(3) a").addClass("current");
        return false;
    }
    if(myurl.search(/validation/) >= 0 || myurl.search(/collect/) >= 0){
        $("#nav_inner>ul>li:eq(4) a").addClass("current");
        return false;
    }
    if(myurl.search(/communicate/) >= 0){
        $("#nav_inner>ul>li:eq(5) a").addClass("current");
        return false;
    }
    if(myurl.search(/share/) >= 0){
        $("#nav_inner>ul>li:eq(6) a").addClass("current");
        return false;
    }
    if(myurl.search(/statistics/) >= 0){
        $("#nav_inner>ul>li:eq(7) a").addClass("current");
        return false;
    }
    if(myurl.search(/help/) >= 0){
        $("#nav_inner>ul>li:eq(8) a").addClass("current");
        return false;
    }
});
$("#footer").load("common.html #footer_inner", function() {

});

