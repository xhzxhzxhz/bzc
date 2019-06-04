package com.folkestone.bzcx.controller.front.userlogin;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.folkestone.bzcx.bean.bean_dto.common.Result;
import com.folkestone.bzcx.common.util.ContantFinalUtil;

/**
 * 页面跳转
 * @author folkestone-6
 *
 */
@Controller
@RequestMapping(value = "/jump")
public class JumpView {
	
		private static String appid = ContantFinalUtil.WX_APPID;//"wx8c2232787ae62253";
	 	private final static String secret = ContantFinalUtil.WX_SECRET;
	    private final static String baseurl = ContantFinalUtil.WX_URL;
	    private final static String roleIds[] = {"3232bb649efd4d91b34135db754b5dab"}; 
	
	@ResponseBody
	@RequestMapping(value = "/address", method = {RequestMethod.POST,RequestMethod.GET})
	public Result list(HttpServletRequest request,HttpServletResponse response){ 
		Result re = new Result(true);
		Object URL = request.getSession().getAttribute("userUrl");
		System.out.println("-------------jump-------------"+URL);
		re.setData(URL);
		return re;
	}
	
	@ResponseBody
	@RequestMapping(value = "/url", method = {RequestMethod.POST,RequestMethod.GET})
	public Result jumpurl(HttpServletRequest request,HttpServletResponse response){ 
		Result re = new Result(true);
		String urlNameString = "https://open.weixin.qq.com/connect/oauth2/authorize?appid="+appid+"&redirect_uri="+baseurl+"&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect";
		re.setData(urlNameString);
		return re;
	}
	
	
}
