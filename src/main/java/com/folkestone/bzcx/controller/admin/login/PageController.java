package com.folkestone.bzcx.controller.admin.login;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


/**
 * Describe：登录页跳转
 * 
 * @author smallking
 *
 *  2017年11月10日
 */
@Controller
public class PageController {
	@RequestMapping(value = "/admin", method = {RequestMethod.POST, RequestMethod.GET})
	public String pageAdmin(HttpServletRequest req){
		Object obj = req.getSession().getAttribute("loginUser");
		if(obj == null){
			System.out.println("未登录，跳转登录页");
			return "redirect:/admin/login.html";
		}else{
			return "redirect:/admin/index.html";
		}
	}
}
