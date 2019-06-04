package com.folkestone.bzcx.controller.front.userlogin;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.folkestone.bzcx.service.user.UserService;



 
@Controller
@RequestMapping(value = "/token")
public class UserInfo {
	
	private UserService userService;
	
	@ResponseBody
	@RequestMapping(value = "/loginurl", method = {RequestMethod.POST,RequestMethod.GET})
	public void list(@RequestParam Map<String, Object> param,HttpServletRequest request){  //返回一个json  
		request.getSession().setAttribute("logincode", 1);
	}
	
	 /*@GetMapping("/wx/wxLogin")
	    public void doWxLogin (HttpServletRequest request, HttpServletResponse response) {
	 
	        try {
	            userService.doWxLogin(request, response);
	        } catch (Exception e) {
	            e.printStackTrace();
	            //相应的处理
	        }
	 
	    }*/
}
