
$(function(){
    var standardType='全部',nature='全部',standardStatus=3
    $(".search_box>img").click(function(){
    	console.log(111)
    });
    //  点击筛选条件
    (function(){
        let li =$(".standard_content>ul>li")
        for(var i=1;i<li.length;i+=2){
            li.eq(i).css("background-color", "#eee");
        }
        var standardItem1=false,standardItem2=false,standardItem3=false;
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
            if(standardItem=='类型'){
                standardItem1=true
            }
            if(standardItem=='状态'){
                standardItem2=true
            }
            if(standardItem=='性质'){
                standardItem3=true
            }
        });
        //点击筛选条件
        $(".select_content>ul>li").click(function(e){
            e.stopPropagation();
            let getClass = $(this).attr('class');
            if(getClass == 'active'){
                $(this).removeClass('active')
            }else if(!getClass){
            //增加样式前先删除掉其它li标签的样式
                $(this).siblings('li').removeClass('active')
                $(this).siblings('li').children('div').css('display',"none")
                $(this).addClass('active')
                $(this).children('div').css('display',"block")
            }
            getStandard()
        });
        var standardType='全部',standardStatus='全部',nature='全部';
        $(".select_content1>ul>li").click(function(e){   
            $(".select_content1").css("display","none")        
            if(standardItem1==true){
                standardType=$(this).children().first().html()
            }
            getStandard()
        })
        $(".select_content2>ul>li").click(function(e){
            $(".select_content2").css("display","none")
            if(standardItem2==true){
                standardStatus=$(this).children().first().html()
                if(standardStatus=='即将实施'){
                    standardStatus=0
                }else if(standardStatus=='现行'){
                    standardStatus=1
                }else if(standardStatus=='废止'){
                    standardStatus=2
                }else if(standardStatus=='全部'){
                    standardStatus=3
                }
            } 
            getStandard()
        })
        $(".select_content3>ul>li").click(function(e){
            $(".select_content3").css("display","none")
            if(standardItem3==true){
                nature=$(this).children().first().html()
            }
        })
        function getStandard(){
        	 if(location.search.indexOf("&")!=-1){
    	        var icsCode=location.search.split("&")[0].split('=')[1];
    	       
    	    }
          var data={"moudle":"4003","icsCode":icsCode,"typeName":"标准信息","standardType":standardType,"nature":nature,"standardStatus":standardStatus}
  		  data.standardType=="全部"?delete data.standardType:data
  		  data.nature=="全部"?delete data.nature:data
  		  data.standardStatus=="全部"?delete data.standardStatus:data
	      $.ajax({
	          url : "/BZCX/standard/searchSecond",
	          type : "post",
	          async : true,
	          data:data,
	          success : function(res) {  
	              let content=res.data	            
	              var item=$(".standard_content>ul").empty();
	              if(res.data.length>0){	
	              	 let content_item=''
	        		 for(let i=0;i<content.length;i++){
	                     content_item+=` <li onclick="jump1('${da[i].standardNo}')">
	                     <span>${content[i].standardNo}</span>       
	                     <span>${content[i].standardCnName}</span>
	                    </li>`   
	        		 }
	             	item.append(content_item)	
	              	  //内容隔行变色
	                 let content_li=$(".standard_content>ul>li")
	                 for(let i=0;i<content_li.length;i++){
	                 	 if(i % 2 == 0){
	                 		 content_li[i].style.background="#fff"
	                      }else{
	                     	 content_li[i].style.background="#f5f5f5"
	                      } 
	                }
			        
		        	}else{
		        		let null_content="<img style='width:3rem;height:3.5rem;margin-top:3rem' src='./img/pic_weikong.png'>"
		        		item.append(null_content)
		        	}
	          }
	      })
	      
        }
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
                    "typeName" :"标准信息",
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
                            content_item+=` <li onclick="jump1('${da[i].standardNo}')">
                            <span>${content[i].standardNo}</span>       
                            <span>${content[i].standardCnName}</span>
                            </li>`   
                        }
                        item.append(content_item)	
                            //内容隔行变色
                        let content_li=$(".standard_content>ul>li")
                        for(let i=0;i<content_li.length;i++){
                                if(i % 2 == 0){
                                    content_li[i].style.background="#fff"
                                }else{
                                    content_li[i].style.background="#f5f5f5"
                                } 
                        }
                        
                    }else{
                        let null_content="<img style='width:3rem;height:3.5rem;margin-top:3rem' src='./img/pic_weikong.png'>"
                        item.append(null_content)
                    }
                }
            });
        }
        getStandard()
        jump1=function (noTrim){
            $.ajax({
                url : "/BZCX/standard/getStdFileId",
                type : "post",
                data : {typeName:"标准信息",noTrim:noTrim},
                async : true,
                success : function(res) {               
                    window.location.href = `details.html?id=${res.data.stdFileId}&page=1`
                }
            })	
        }
      
    })()


   
      
   
   

    
    
})

