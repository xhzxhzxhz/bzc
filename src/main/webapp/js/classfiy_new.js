
//循环添加不同行业标准的item
$(function(){
	   $.ajax({
           url : "/BZCX/icstype/showTypeByTree",
           type : "post",
           async : true,
           success : function(res) {
               var classfiy_item=$('.classfiy_modules')
               classfiy_items=eval("("+res.data+")");
               var classfiy=''
               for(var i=0;i<classfiy_items.length;i++){
                   classfiy+=`<div class='classfiy_title'>${classfiy_items[i].icsName}</div>
                               <div class='classfiy_content'>
                                   <ul>  

                                   </ul>
                               </div>`
               }
               var first=classfiy_items[0].children,second=classfiy_items[1].children,third=classfiy_items[2].children;
               classfiy_item.append(classfiy)
               var classfiy_li=$('.classfiy_content>ul')
               var path=''
               for(var j=0;j<first.length;j++){
            	   	 if(first[j].type==0){
            	   		path='country_standard.html'
	                 }else if(first[j].type==1){
	                     path='classify_law.html'
	                 }
                   classfiy_li[0].innerHTML+=`<li class='classfiy_item'>
                     <a href="${path}?icsCode=${first[j].icsCode}&moudle='4003'&typeName=${first[j].type}">
	                   <img src="${first[j].img}" />
	                   <span>${first[j].icsName}</span>
	                  </a>
                   </li>`
               }
               for(var d=0;d<second.length;d++){
            		if(second[d].type==0){
            	   		path='country_standard.html'
	                 }else if(second[d].type==1){
	                     path='classify_law.html'
	                 }
                   classfiy_li[1].innerHTML+=`<li>
                     <a href="${path}?icsCode=${second[d].icsCode}&moudle='4003'&typeName=${second[d].type}">
	                   <img src="${second[d].img}" />
	                   <span>${second[d].icsName}</span>
	                  </a>
                   </li>`
               }
               for(var h=0;h<third.length;h++){
            		if(third[h].type==0){
            	   		path='country_standard.html'
	                 }else if(third[h].type==1){
	                     path='classify_law.html'
	                 }
                   classfiy_li[2].innerHTML+=`<li>
                    <a href="${path}?icsCode=${third[h].icsCode}&moudle='4003'&typeName=${third[h].type}">
                   <img src="${third[h].img}" />
                   <span>${third[h].icsName}</span>
                   </a>
                   </li>`
               }

           }
       })
})

