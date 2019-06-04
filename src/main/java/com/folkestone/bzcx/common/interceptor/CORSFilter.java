package com.folkestone.bzcx.common.interceptor;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletResponse;

/*自定义拦截器 用于给每个都加上跨域的头*/
@WebFilter(filterName = "CORSFilter", urlPatterns = "/*")

public class CORSFilter implements Filter {
	  @Override  
	    public void init(FilterConfig arg0) throws ServletException {  
	          
	    }  
	 
	    @Override  
	    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)  
	            throws IOException, ServletException {
	    	/**
	    	 *  response.setHeader("Access-Control-Allow-Origin", "*"); //解决跨域访问报错 
				response.setHeader("Access-Control-Allow-Methods", "POST, PUT, GET, OPTIONS, DELETE"); 
				response.setHeader("Access-Control-Max-Age", "3600"); //设置过期时间 
				response.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept, client_id, uuid, Authorization"); 
				response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // 支持HTTP 1.1. 
				response.setHeader("Pragma", "no-cache"); // 支持HTTP 1.0. response.setHeader("Expires", "0"); 

	    	 */
	    	/**
	    	 *  res.setHeader("Access-Control-Allow-Origin", "*");
				res.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
				res.setHeader("Access-Control-Max-Age", "3600");
				res.setHeader("Access-Control-Allow-Headers", "x-requested-with");
	    	 */
	        HttpServletResponse response = (HttpServletResponse) res;  
	        response.setHeader("Access-Control-Allow-Origin", "*");    
	        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");    
	        response.setHeader("Access-Control-Max-Age", "3600");    
	        response.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With,x-requested-with, Content-Type, Accept, client_id, uuid, Authorization");  
	        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // 支持HTTP 1.1. 
			response.setHeader("Pragma", "no-cache"); // 支持HTTP 1.0. response.setHeader("Expires", "0"); 
			response.setHeader("Access-Control-Allow-Headers", "x-requested-with");   
	        chain.doFilter(req, res);   
	    }  
	  
	  
	    @Override  
	    public void destroy() {  
	        // TODO Auto-generated method stub  
	          
	    }  
	  
}