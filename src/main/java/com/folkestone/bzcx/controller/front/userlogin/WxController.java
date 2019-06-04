package com.folkestone.bzcx.controller.front.userlogin;

import java.io.BufferedReader;
import java.io.UnsupportedEncodingException;
import java.util.Formatter;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.folkestone.bzcx.bean.bean_do.user.R_UserDo;
import com.folkestone.bzcx.bean.bean_dto.common.Query;
import com.folkestone.bzcx.bean.bean_dto.user.Oauth2Token;
import com.folkestone.bzcx.bean.bean_dto.user.R;
import com.folkestone.bzcx.bean.bean_vo.user.UserVo;
import com.folkestone.bzcx.common.util.ContantFinalUtil;
import com.folkestone.bzcx.common.util.NetUtil;
import com.folkestone.bzcx.controller.base.BaseController;
import com.folkestone.bzcx.service.user.OperLogService;
import com.folkestone.bzcx.service.user.UserService;

@Controller
@RequestMapping("/wx")
@ResponseBody
public class WxController extends BaseController{

    private static  Logger log = LoggerFactory.getLogger(WxController.class);
    private static String appid = ContantFinalUtil.WX_APPID;//"wx8c2232787ae62253";
    private final static String secret = ContantFinalUtil.WX_SECRET;
    private final static String baseurl = ContantFinalUtil.WX_URL;
    private final static String roleIds[] = {"3232bb649efd4d91b34135db754b5dab"}; 
  // private final static String baseurls = ContantFinalUtil.baseurl;
   // private final static String projectname = ContantFinalUtil.projectname;
    
    
    @Autowired
    private UserService user;
    
    @Autowired
	private OperLogService operLogService;
    
    /**
     * 向指定URL发送GET方法的请求
     * 
     * @param url
     *            发送请求的URL
     * @param param
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return URL 所代表远程资源的响应结果
     * 
     * 用户同意授权，获取code
     */ 
    @RequestMapping("/authorize")
    @ResponseBody
    public static R authorize(HttpServletResponse sopn) {
        ///String uri = urlEncodeUTF8("192.168.3.77:8080/BZCX/index.html");
        String result = "";
        BufferedReader in = null;
        try {
        	String appi = urlEncodeUTF8(appid);
        	String sec = urlEncodeUTF8(secret);
        	String urlNameString = "https://open.weixin.qq.com/connect/oauth2/authorize?appid="+appi+"&redirect_uri="+baseurl+"&response_type=code&scope="+sec+"&state=STATE#wechat_redirect";
            //String urlNameString = "https://www.baidu.com";
            //urlNameString = urlEncodeUTF8(urlNameString);
            System.out.println(urlNameString);
         /*  URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();*/
            // 设置通用的请求属性
           //connection.setRequestProperty("accept", "*/*");
           /* connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent",
                    "Mozilla/5.0 (Linux; Android 6.0; Nexus 5 Build/MRA58N) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/48.0.2564.23 Mobile Safari/537.36 wechatdevtools/1.02.1808101 MicroMessenger/6.5.7 webview/15433950112148754 webdebugger port/15292");
            
            // 建立实际的连接
            // 获取所有响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();
            // 遍历所有的响应头字段
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String line =null;
            while ((line = in.readLine()) != null) {
                result += line;
            }*/
            /*  com.alibaba.fastjson.JSONObject jsonObj= FastJSONUtils.getJSONObject(result);  
                String access_token = jsonObj.getString("access_token");
                long expires_in = Long.valueOf(jsonObj.getString("expires_in"));
            */
            sopn.sendRedirect(urlNameString);
        } catch (Exception e) {
            System.out.println("发送GET请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return  R.ok(result);
    }    
    
    @RequestMapping("/weixinLogin")
    @ResponseBody
    public void weixinLogin(HttpServletRequest request,HttpServletResponse response) throws Exception {  
    	Object URL = request.getSession().getAttribute("userUrl");
        // 用户同意授权后，能获取到code
        Map<String, String[]> params = request.getParameterMap();//针对get获取get参数  
        String[] codes = params.get("code");//拿到code的值 
        String code = codes[0];//code  
        //String[] states = params.get("state");
        //String state = states[0];//state 
        
        System.out.println("****************code:"+code);
        // 用户同意授权
        if (!"authdeny".equals(code)) {
             // 获取网页授权access_token
            Oauth2Token oauth2Token = getOauth2AccessToken(appid, secret, code);
            //System.out.println("***********************************oauth2Token信息："+oauth2Token.toString());
            // 网页授权接口访问凭证
            String accessToken = oauth2Token.getAccessToken();
            // 用户标识
            String openId = oauth2Token.getOpenId();
            // 获取用户信息
            //数据库查下openid有没有，有的话就更新信息，没有的话就插入
            R_UserDo snsUserInfo = getSNSUserInfo(accessToken, openId);
            //System.out.println("***********************************用户信息unionId："+snsUserInfo.getUnionid()+"***:"+snsUserInfo.getNickname());
            // 设置要传递的参数
            //先查询用户信息，如果有的话就更新信息，没有的话就插入，先查询数据库的话，前端用户信息会延迟，但是目前业务还不需要用户信息，所以先这么写，以后再改
            	Query quer = new Query();
            	quer.put("openid", snsUserInfo.getOpenid());
            	UserVo userById = user.getUserByQuery(quer);
            	Boolean binding = false;
            	//我们需要设置session中存储的微信用户的有效期
            	if(userById != null) {//表示在数据库中已经有了这个的数据
            		//loginUser
            		//request.getSession().setAttribute("loginUserstate", true);
            		request.getSession().setAttribute("loginUser", userById);
            		//将用户的信息进行更新
            		user.update(snsUserInfo, null);
            		
            		//具体的业务
            		//账号绑定了账号，说明输入过公司账号密码，可以直接登录
            		if(null != userById.getDepartmentId()) {//表示这个是企业员工，限制登录人数使用，暂时先不使用这点
            			//request.getSession().setAttribute("loginUserstate", true);
            			binding = true; 
        				/*Map<String, Integer> map2 = OnlineBean.getMap();
        				System.out.println("微信登录");
        				System.out.println("DepartmentId："+userById.getDepartmentId());
						System.out.println("online："+map2.get(userById.getDepartmentId()));
						System.out.println("limit："+userById.getOnlineLimit());
						if(map2.get(userById.getDepartmentId()) != null) {
							if(map2.get(userById.getDepartmentId()) > userById.getOnlineLimit()) {
								//重定向有点问题,这个是 跳转到扫码登录页面
								//	response.sendRedirect(baseurls+projectname+"/demo.html");
								
							}else {
								map2.put(userById.getDepartmentId(), map2.get(userById.getDepartmentId())+1);
								OnlineBean.setMap(map2);
								userById.setIsonlineLimit(true);
							}
            			}*/
            		}else {//表示这个不是企业员工，但是还是可以登录
            			//request.getSession().setAttribute("loginUserstate", true);	
            			binding = true; 
            			
            		}
            	}else {//表示这个数据库中没有这个用户的信息
            	//boolean loginUserstate=	(boolean)request.getSession().getAttribute("loginUserstate")
            	//if((boolean) request.getSession().getAttribute("loginUserstate")!=true ) {//表示没有登录
            		//跳转到登录页面
            		//response.sendRedirect("http://localhost:8070/BZCX/login.html");
            		//将微信用户放到session中
            		request.getSession().setAttribute("snsUserInfo", snsUserInfo);
            		//request.getRequestDispatcher("/BZCX/userlogin.html").forward(request,response);
            		response.sendRedirect("http://bzc.shengfeisi.cn:8080/BZCX/userLogin.html"); 
            		
            	//}
            		
            		/*snsUserInfo.setStatus(1);
            		//--------------------这个是部门的id 不能设置成死的
            		snsUserInfo.setDepartmentId("depart-3a1281e9b1ec49b8acb93791f61e85b9");
            		user.insert(snsUserInfo, roleIds);
            		userById = user.getUserByQuery(quer);*/
            	}
            	//Object loginUserstate= request.getSession().getAttribute("loginUserstate");
            	if(binding) {
            		request.getSession().setAttribute("loginUserstate", true);	
            		URL = "http://bzc.shengfeisi.cn:8080"+URL.toString();//+snsUserInfo.getUnionid();
            		if(URL!=null) {
            			response.sendRedirect(URL.toString()); 
            		}
            	
            	
            	}
            	//String URL = (String) request.getSession().getAttribute("userUrl");
            //具体业务start
    
            	//request.getSession().setAttribute("loginUser", userById);
            	//request.getSession().setAttribute("resourceList", userById.getResourceList());
            	//request.getSession().setMaxInactiveInterval(3600);
        		/*for(ResourceVo resourceVo : userById.getResourceList()){
        			// 将权限信息放到session里面
        			request.getSession().setAttribute(resourceVo.getId(),"true");
        		}*/
           //具体业务end
        		/*System.out.println("loginUser ======不拦截==="+URL);
        		if(URL != null) {
        			operLogService.insert(super.getOperLog(request, "1001", URL.toString() , userById.getId()));
        			URL = "http://localhost:8070"+URL.toString();//+snsUserInfo.getUnionid();
        			response.sendRedirect(URL.toString()); 
        		}else {
        			response.sendRedirect("http://localhost:8070/BZCX/QRcode.html"); 
        		}*/
        }
    }  

    
  /*  @RequestMapping("xxxxx")
	public void accreditBack(String data, Model model,HttpServletRequest request,HttpServletResponse response) throws Exception{
		String code = request.getParameter("code");
		String publishId = request.getParameter("state");
		String contextPath = request.getContextPath();//获取项目名
	
		String url=contextPath+"/xxxx";
		if(!StringUtils.isEmpty(code)){
			WxCommoneUtils wxCommoneUtils =new WxCommoneUtils();
			//String openId = wxCommoneUtils.getOpenId(code);
			JSONObject wxUserInfo = wxCommoneUtils.getWxUserInfo(code);
			String openId = wxUserInfo.getString("openid");
			String headImgUrl = wxUserInfo.getString("headimgurl");//用户头像 
//			BASE64Encoder be =new  BASE64Encoder();
//			String encode = be.encode(headImgUrl.getBytes());
//			String string = encode.replaceAll(" ", "");
			url=url+"?"openId="+openId+"&headImgUrl="+headImgUrl;
			
		}
		response.setStatus(HttpServletResponse.SC_MOVED_PERMANENTLY);  
		response.setHeader("Location",url);
		 //清除缓存
		response.setHeader( "Connection", "close" );
		response.setHeader( "Pragma", "no-cache" );
		response.addHeader( "Cache-Control", "must-revalidate" );
		response.addHeader( "Cache-Control", "no-cache" );
		response.addHeader( "Cache-Control", "no-store" );
		response.setDateHeader("Expires", 0);
	}
    */
    /**
     * 获取网页授权凭证
     * 
     * @param appId 公众账号的唯一标识
     * @param appSecret 公众账号的密钥
     * @param code
     * @return WeixinAouth2Token
     */
    public static Oauth2Token getOauth2AccessToken(String appId, String appSecret, String code) {
        Oauth2Token wat = null;
        // 拼接请求地址
        String requestUrl = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";
        requestUrl = requestUrl.replace("APPID", appId);
        requestUrl = requestUrl.replace("SECRET", appSecret);
        requestUrl = requestUrl.replace("CODE", code);
        // 获取网页授权凭证
        com.alibaba.fastjson.JSONObject jsonObject = JSON.parseObject(NetUtil.get(requestUrl));
        if (null != jsonObject) {
            try {
                wat = new Oauth2Token();
                wat.setAccessToken(jsonObject.getString("access_token"));
                wat.setExpiresIn(jsonObject.getInteger("expires_in"));
                wat.setRefreshToken(jsonObject.getString("refresh_token"));
                wat.setOpenId(jsonObject.getString("openid"));
                wat.setScope(jsonObject.getString("scope"));
            } catch (Exception e) {
                wat = null;
                int errorCode = jsonObject.getInteger("errcode");
                String errorMsg = jsonObject.getString("errmsg");
                log.error("获取网页授权凭证失败 errcode:{} errmsg:{}", errorCode, errorMsg);
            }
        }
        return wat;
    }
    
    /**
     * 通过网页授权获取用户信息
     * 
     * @param accessToken 网页授权接口调用凭证
     * @param openId 用户标识
     * @return SNSUserInfo
     */
    public static R_UserDo getSNSUserInfo(String accessToken, String openId) {
    	R_UserDo snsUserInfo = null;
        // 拼接请求地址
        String requestUrl = "https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID";
        requestUrl = requestUrl.replace("ACCESS_TOKEN", accessToken).replace("OPENID", openId);
        // 通过网页授权获取用户信息
        com.alibaba.fastjson.JSONObject jsonObject =  JSON.parseObject(NetUtil.get(requestUrl));

        if (null != jsonObject) {
            try {
                snsUserInfo = new R_UserDo();
                // 用户的标识
                snsUserInfo.setOpenid(jsonObject.getString("openid"));
                // 昵称
                snsUserInfo.setNickname(jsonObject.getString("nickname"));
                // 性别（1是男性，2是女性，0是未知）
                snsUserInfo.setSex(jsonObject.getInteger("sex"));
                // 用户所在国家
                snsUserInfo.setCountry(jsonObject.getString("country"));
                // 用户所在省份
                snsUserInfo.setProvince(jsonObject.getString("province"));
                // 用户所在城市
                snsUserInfo.setCity(jsonObject.getString("city"));
                // 用户头像
                snsUserInfo.setIcon(jsonObject.getString("headimgurl"));
                // 用户特权信息
                List<String> list = JSON.parseArray(jsonObject.getString("privilege"),String.class);
                //snsUserInfo.setPrivilegeList(list);
                //与开放平台共用的唯一标识，只有在用户将公众号绑定到微信开放平台帐号后，才会出现该字段。
               // snsUserInfo.setUnionid(jsonObject.getString("unionid"));
            } catch (Exception e) {
                snsUserInfo = null;
                int errorCode = jsonObject.getInteger("errcode");
                String errorMsg = jsonObject.getString("errmsg");
                log.error("获取用户信息失败 errcode:{} errmsg:{}", errorCode, errorMsg);
            }
        }
        return snsUserInfo;
    }
   
    /**
     * URL编码（utf-8）
     * 
     * @param source
     * @return
     */
    public static String urlEncodeUTF8(String source) {
        String result = source;
        try {
            result = java.net.URLEncoder.encode(source, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }
    
    private static String byteToHex(final byte[] hash) {
        Formatter formatter = new Formatter();
        for (byte b : hash)
        {
            formatter.format("%02x", b);
        }
        String result = formatter.toString();
        formatter.close();
        return result;
    }

    private static String create_nonce_str() {
        return UUID.randomUUID().toString();
    }

    private static String create_timestamp() {
        return Long.toString(System.currentTimeMillis() / 1000);
    }
}