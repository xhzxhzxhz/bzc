package com.folkestone.bzcx.controller.front.userlogin.wechatinterface;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.folkestone.bzcx.bean.bean_vo.user.UserVo;

import net.sf.json.JSONObject;

public class WeinXinUtil {
	
	 static String AppId;//第三方用户唯一凭证  
     static String secret;//第三方用户唯一凭证密钥，即appsecret 
	
	WeinXinUtil(String appid,String sectet){
		AppId = appid;
		secret = sectet;
	}
	
	public static WinXinEntity getWinXinEntity(String url,String openid) {
        WinXinEntity wx = new WinXinEntity();
        String access_token = getAccessToken();
        //查看用户是否关注公众号
        Map<String, String> subscribe = subscribe( access_token, openid);
        subscribe.put("appid", AppId);
        String ticket = getTicket(access_token);
        Map<String, String> ret = Sign.sign(ticket, url);
        //System.out.println(ret.toString());
        wx.setTicket(ret.get("jsapi_ticket"));
        wx.setSignature(ret.get("signature"));
        wx.setNoncestr(ret.get("nonceStr"));
        wx.setTimestamp(ret.get("timestamp"));
        wx.setMa(subscribe);
        return wx;
    }


    //获取token
    private static Map<String, String> subscribe(String token, String openid) {  
        //这个url链接地址和参数皆不能变  
        String url = "https://api.weixin.qq.com/cgi-bin/user/info?access_token="+token+"&openid="+openid+"&lang=zh_CN";  //访问链接
        Map<String, String> ma = new HashMap<>();
        try {  
            URL urlGet = new URL(url);  
            HttpURLConnection http = (HttpURLConnection) urlGet.openConnection();  
            http.setRequestMethod("GET"); // 必须是get方式请求  
            http.setRequestProperty("Content-Type","application/x-www-form-urlencoded");  
            http.setDoOutput(true);  
            http.setDoInput(true);  
            /*System.setProperty("sun.net.client.defaultConnectTimeout", "30000");// 连接超时30秒  
            System.setProperty("sun.net.client.defaultReadTimeout", "30000"); // 读取超时30秒 */
            http.connect();  
            InputStream is = http.getInputStream();  
            int size = is.available();  
            byte[] jsonBytes = new byte[size];  
            is.read(jsonBytes);  
            String message = new String(jsonBytes, "UTF-8");
            System.out.println("------------------------------------------------"+message);
            JSONObject demoJson = JSONObject.fromObject(message);  
            ma.put("subscribe", demoJson.getString("subscribe"));
            ma.put("subscribe_scene", demoJson.getString("subscribe_scene"));
            ma.put("subscribe_time", demoJson.getString("subscribe_time"));
            /*user.setSubscribe(Integer.valueOf(demoJson.getString("subscribe")));
            user.setSubscribe_scene(demoJson.getString("subscribe_scene"));
            String date = timeStamp2Date(demoJson.getString("subscribe_time"), "yyyy-MM-dd HH:mm:ss");  
	        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	        Date parse = sdf.parse(date);
	        user.setSubscribe_time(parse);*/
            /*Iterator iterator = demoJson.keys();
            while(iterator.hasNext()){
               String key = (String) iterator.next();
                String value = demoJson.getString(key);
                System.out.println("--------------------key:"+key);
                System.out.println("--------------value:"+value);
            }*/
            ///access_token = demoJson.getString("access_token");  
            is.close();  
        } catch (Exception e) {  
                e.printStackTrace();  
        }  
       return ma;  
    }  
	
    public static String timeStamp2Date(String seconds,String format) {  
        if(seconds == null || seconds.isEmpty() || seconds.equals("null")){  
            return "";  
        }  
        if(format == null || format.isEmpty()){
            format = "yyyy-MM-dd HH:mm:ss";
        }   
        SimpleDateFormat sdf = new SimpleDateFormat(format);  
        return sdf.format(new Date(Long.valueOf(seconds+"000")));  
    } 
	
    //获取token
    private static String getAccessToken() {  
        String access_token = "";  
        String grant_type = "client_credential";//获取access_token填写client_credential   
        //这个url链接地址和参数皆不能变  
        String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type="+grant_type+"&appid="+AppId+"&secret="+secret;  //访问链接

        try {  
            URL urlGet = new URL(url);  
            HttpURLConnection http = (HttpURLConnection) urlGet.openConnection();  
            http.setRequestMethod("GET"); // 必须是get方式请求  
            http.setRequestProperty("Content-Type","application/x-www-form-urlencoded");  
            http.setDoOutput(true);  
            http.setDoInput(true);  
            /*System.setProperty("sun.net.client.defaultConnectTimeout", "30000");// 连接超时30秒  
            System.setProperty("sun.net.client.defaultReadTimeout", "30000"); // 读取超时30秒 */
            http.connect();  
            InputStream is = http.getInputStream();  
            int size = is.available();  
            byte[] jsonBytes = new byte[size];  
            is.read(jsonBytes);  
            String message = new String(jsonBytes, "UTF-8");
            System.out.println("------------------------------------------------"+message);
            JSONObject demoJson = JSONObject.fromObject(message);  
            access_token = demoJson.getString("access_token");  
            is.close();  
        } catch (Exception e) {  
                e.printStackTrace();  
        }  
        return access_token;  
    }  

    //获取ticket
    private static String getTicket(String access_token) {  
        String ticket = null;  
        String url = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token="+ access_token +"&type=jsapi";//这个url链接和参数不能变  
        try {  
            URL urlGet = new URL(url);  
            HttpURLConnection http = (HttpURLConnection) urlGet.openConnection();  
            http.setRequestMethod("GET"); // 必须是get方式请求  
            http.setRequestProperty("Content-Type","application/x-www-form-urlencoded");  
            http.setDoOutput(true);  
            http.setDoInput(true);  
            System.setProperty("sun.net.client.defaultConnectTimeout", "30000");// 连接超时30秒  
            System.setProperty("sun.net.client.defaultReadTimeout", "30000"); // 读取超时30秒  
            http.connect();  
            InputStream is = http.getInputStream();  
            int size = is.available();  
            byte[] jsonBytes = new byte[size];  
            is.read(jsonBytes);  
            String message = new String(jsonBytes, "UTF-8");  
            JSONObject demoJson = JSONObject.fromObject(message);  
            ticket = demoJson.getString("ticket");  
            is.close();  
        } catch (Exception e) {  
                e.printStackTrace();  
        }  
        return ticket;  
    } 

}
