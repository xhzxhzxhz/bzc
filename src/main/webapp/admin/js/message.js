
function messages(){
        	var da = {
        	}
        	$.ajax({
				url : "/BZCX/admin/message/Sum",
				type : "post",
				data : da,
				ansyc : false,
				success : function(data) {
					var att = data.rows;
					var htm = "";
					if(att[0].summessage == 0 && att[1].summessage == 0 && att[2].summessage == 0){
						$("#pending_click").attr("style","display:none;");
					}else{
						$("#pending_click").attr("style","display:block;");
						$("#messageLi").empty();
						for (var i = 0; i < 3; i++) {
							htm += '<li><b id="message'+i+'">'+att[i].namemessage+'</b><em>'+att[i].summessage+'</em></li>';
						}
						$("#messageLi").append(htm);
						if(att[0].summessage != 0){
							$("#message0").click(function(){
								$("#demandMessage").trigger('click');
								$("#pending_div").dialog('close');
							});
						}
						if(att[1].summessage != 0){
							$("#message1").click(function(){
								$("#feedbackMessage").trigger('click');
								$("#pending_div").dialog('close');
							});
						}
						if(att[2].summessage != 0){
							$("#message2").click(function(){
								$("#reqMessage").trigger('click');
								$("#pending_div").dialog('close');
							});
						}
					}
				}
			});
        };
        
   function messagePolling(){
	   var timer = setInterval(function () {
        	var da = {
        	}
        	$.ajax({
				url : "/BZCX/admin/message/Sum",
				type : "post",
				data : da,
				ansyc : false,
				success : function(data) {
					var att = data.rows;
					var htm = "";
					if(att[0].summessage == 0 && att[1].summessage == 0 && att[2].summessage == 0){
						$("#pending_click").attr("style","display:none;");
					}else{
						$("#pending_click").attr("style","display:block;");
						$("#messageLi").empty();
						for (var i = 0; i < 3; i++) {
							htm += '<li><b id="message'+i+'">'+att[i].namemessage+'</b><em>'+att[i].summessage+'</em></li>';
						}
						$("#messageLi").append(htm);
						//需求征集维护
						if(att[0].summessage != 0){
							$("#message0").click(function(){
								$("#demandMessage").trigger('click');
								$("#pending_div").dialog('close');
							});
						}
						//意见反馈维护
						if(att[1].summessage != 0){
							$("#message1").click(function(){
								$("#feedbackMessage").trigger('click');
								$("#pending_div").dialog('close');
							});
						}
						//有效性确认维护
						if(att[2].summessage != 0){
							$("#message2").click(function(){
								$("#reqMessage").trigger('click');
								$("#pending_div").dialog('close');
							});
						}
					}
				}
			});
       }, 5000);
   }    
