//条件筛选
$(function (){
    var standardItem1=false,standardItem2=false,standardItem3=false;
    var effectivenessLevel='全部',pubOrg='全部',sort='全部';
    $('.classfiy>ul>li').click(function(e){
        e.stopPropagation();
        let getClass = $(this).attr('class');
        if(getClass == 'active'){
            $(this).removeClass('active')
            $(this).children('i').removeClass('active_bottom')
            $(this).children('div').css('display',"none")
        }else if(!getClass){
        //增加样式前先删除掉其它li标签的样式
            $(this).siblings('li').removeClass('active')
            $(this).siblings('li').children('i').removeClass('active_bottom')
            $(this).siblings('li').children('div').css('display',"none")
            $(this).addClass('active')
            $(this).children('i').addClass('active_bottom') 
            $(this).children('div').css('display',"block")
        }
        let standardItem=$(this).children().first().html();
        if(standardItem=='效力级别'){
            standardItem1=true
        }
        if(standardItem=='公布机关'){
            standardItem2=true
        }
        if(standardItem=='发布日期'){
            standardItem3=true
        }
        
    });
    $(".select_content>ul>li").click(function(e){
        e.stopPropagation();
        let getClass = $(this).attr('class');
        if(getClass == 'active'){
            $(this).removeClass('active')
           // $(this).children('div').css('display',"none")
        }else if(!getClass){
        //增加样式前先删除掉其它li标签的样式
            $(this).siblings('li').removeClass('active')
            $(this).siblings('li').children('div').css('display',"none")
            $(this).addClass('active')
            $(this).children('div').css('display',"block")
        }
      
    });
    $(".select_content1>ul>li").click(function(e){   
        $(".select_content1").css("display","none")
        if(standardItem1==true){
            effectivenessLevel=$(this).children().first().html()
        }
        getLaws()
    })
    $(".select_content2>ul>li").click(function(e){
        $(".select_content2").css("display","none")
        if(standardItem2==true){
            pubOrg=$(this).children().first().html()
        } 
        getLaws()
    })
    $(".select_content3>ul>li").click(function(e){
        $(".select_content3").css("display","none")
        if(standardItem3==true){
            sort=$(this).children().first().html()
            if(sort=='正序'){
                sort='desc'
            }else if(sort=='倒序'){
                sort='asc'
            }
        }
        getLaws()
    })   
    function getLaws(){
        if(location.search.indexOf("&")!=-1){
            var icsCode=location.search.split("&")[0].split('=')[1];  
        }
    	var data={"moudle":"4003","icsCode":icsCode,"typeName":"法律法规","effectivenessLevel":effectivenessLevel,"pubOrg":pubOrg,"sort":sort}
        data.effectivenessLevel=="全部"?delete data.effectivenessLevel:data
        data.pubOrg=="全部"?delete data.pubOrg:data
        data.sort=="全部"?delete data.sort:data
    	 $.ajax({
	        url : `/BZCX/standard/searchSecond`,
	        type : "post",
	        data:data,
	        success : function(res) {  
	        	console.log(res.data.length)
	        	var item=$(".news>ul").empty();
	        	if(res.data.length>0){	
		        	law_li=``
		        	for(let i=0;i<res.data.length;i++){
		        		law_li+=` <li onclick="jump1('${res.data[i].lawsNo}')">
		                    <span class='news_title'>${res.data[i].lawsCnName}</span>
		                   <span class='news_time'>发布日期：${res.data[i].publishDate==null?'':getTime(res.data[i].publishDate)}</span>
		                   <span class='news_class'>效力级别：${res.data[i].effectivenessLevel}</span>
		                </li>`
		        	}
		        	item.append(law_li)	
	        	}else{
	        		//let wei_null=$(".news")
	        		let null_content="<img style='width:3rem;height:3.5rem;margin-top:3rem' src='./img/pic_weikong.png'>"
	        		item.append(null_content)
	        	}
	 
	        },
	    })
    };
    $(".search_box>img").click(function(){
        simple_search($(".search_box>input").val())
    })
    $(".search_box").keydown(function(e){
        if(event.keyCode == 13)    {
            simple_search($(".search_box>input").val())
        }
    })
    //搜索
    function simple_search(input) {
        var data = {
                "moudle" : "4001",
                "typeName" :"法律法规",
                "paramNo":input,
                "paramCnName" :input,
        }
        $.ajax({
            url : "/BZCX/standard/searchSecond",
            type : "post",
            data : data,
            async : true,
            success : function(res) {
                let content=res.data	            
                var item=$(".standard_content>ul").empty();
                if(res.data.length>0){	
                        let content_item=''
                    for(let i=0;i<content.length;i++){
                        content_item+=`  <li onclick="jump1('${res.data[i].lawsNo}')">
                        <span class='news_title'>${res.data[i].lawsCnName}</span>
                       <span class='news_time'>发布日期：${res.data[i].publishDate==null?'':getTime(res.data[i].publishDate)}</span>
                       <span class='news_class'>效力级别：${res.data[i].effectivenessLevel}</span>
                     </li>`   
                    }
                    item.append(content_item)	
                    
                }else{
                    let null_content="<img style='width:3rem;height:3.5rem;margin-top:3rem' src='./img/pic_weikong.png'>"
                    item.append(null_content)
                }
            }
        });
    }
    jump1=function (noTrim){
    	console.log(0)
       $.ajax({
           url : "/BZCX/standard/getStdFileId",
           type : "post",
           data : {typeName:"法律法规",noTrim:noTrim},
           async : true,
           success : function(res) {
               window.location.href = `law_details.html?id=${res.data.stdFileId}&page=1`
           }
       })	
    }
    //处理时间戳
     function getTime(time){
        function add0(m){return m<10?'0'+m:m };
        var time = new Date(time);
        var y = time.getFullYear();
        var m = time.getMonth()+1;
        var d = time.getDate();
        var endTime=y+'-'+add0(m)+'-'+add0(d);
        return endTime
      }
    getLaws()
})

