package com.folkestone.bzcx.common.interceptor;




import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.folkestone.bzcx.bean.bean_vo.user.UserVo;
import com.folkestone.bzcx.common.util.ContantFinalUtil;

/**
 * 拦截请求，匹配权限 记录操作日志的一个拦截器
 * @author smallking
 *
 */
public class RightInterceptor implements HandlerInterceptor {
	
	  private final static String appid = ContantFinalUtil.WX_APPID;//"wx8c2232787ae62253";
	  private final static String baseurl = ContantFinalUtil.WX_URL;
	
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
	}

	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		
	}

	public boolean preHandle(HttpServletRequest request, HttpServletResponse response,Object handler)
			throws Exception {
		String requestURI = request.getRequestURI();
		System.out.println("---------------right interceptor" + requestURI + "-----------------");
		// 静态资源直接放行
		String ss = request.getQueryString();
		if(requestURI.contains(".") && !requestURI.endsWith(".html")){
			return true;
		}
		if(requestURI.contains("userLogin.html") ){
			return true;
		}
		if(requestURI.contains("BZCX/userlogin/") ){
			return true;
		}
		
		// 免检的url放行
		if(ContantFinalUtil.PASS_URL_LIST.contains(requestURI)){
			return true;
		}
		// 拦截没有登录的请求
		Object attribute = request.getSession().getAttribute("loginUserstate");
		UserVo user = null;
		
		if(attribute != null){
			Object obj = request.getSession().getAttribute("loginUser");
			user = (UserVo)obj;
		}
		if(user == null){
			if(requestURI.endsWith(".html")){
				request.getSession().setAttribute("userUrl", requestURI);
			}
			//request.getRequestDispatcher("/BZCX/wx/weixinLogin");
			if(requestURI.startsWith("/BZCX/admin")){
				response.sendRedirect("/BZCX/admin/login.html");
			}else if(requestURI.equals("/BZCX/") && ss != null){
				response.sendRedirect("/BZCX/wx/weixinLogin?"+ss);
			}else{
	        	//String urlNameString = "https://open.weixin.qq.com/connect/oauth2/authorize?appid="+appid+"&redirect_uri="+baseurl+"&response_type=code&scope=snsapi_login,snsapi_userinfo&state=STATE&connect_redirect=1#wechat_redirect";
				//String urlNameString = "https://open.weixin.qq.com/connect/oauth2/authorize?appid="+appid+"&redirect_uri=localhost:8070/BZCX/wx/weixinLogin&response_type=code&scope=snsapi_login,snsapi_userinfo&state=STATE&connect_redirect=1#wechat_redirect";
				String urlNameString = "https://open.weixin.qq.com/connect/oauth2/authorize?appid="+appid+"&redirect_uri="+baseurl+"&response_type=code&scope=snsapi_login,snsapi_userinfo&state=STATE&connect_redirect=1#wechat_redirect";
				System.out.println(urlNameString);
				response.sendRedirect(urlNameString);
			}
			
			System.out.println("请先登录");
			System.out.println("--------------拦截的url" + requestURI + "-----------------");
			return false;
		}
		return true;
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
}
