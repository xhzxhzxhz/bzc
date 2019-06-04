//用于判断是否能够使用socket发送消息
var webs = null;
        var url = "ws://" +window.location.host +"/BZCX/websocket";
        //判断当前浏览器是否支持WebSocket
        if ('WebSocket' in window) {
            webs = new WebSocket(url);
            //连接发生错误的回调方法
            webs.onerror = function () {
            };
            //连接成功建立的回调方法
             webs.onopen = function () {
            }
            //接收到消息的回调方法layer.
            webs.onmessage = function (event) {
            }
            //连接关闭的回调方法
            webs.onclose = function () {
            } 
            //监听窗口关闭事件，当窗口关闭时，主动去关闭websocket连接，防止连接还没断开就关闭窗口，server端会抛异常。
            window.onbeforeunload = function () {
                closeWebSocket();
            } 
        }
        else {
        }
        
      //关闭WebSocket连接
        function closeWebSocket() {
            webs.close();
        }
        //发送消息
        function send(data) {
            webs.send(data);
        }
        
        function messageValida(){
			 var at = 'collect'
			 $.ajax({
					url :"/BZCX/collectform/message",
			        type : "post",
			        data : {"mess" : at},
			        ansyc : false,
			        dataType:"json",
			        success : function(data){
			        }
			    }); 
		 }