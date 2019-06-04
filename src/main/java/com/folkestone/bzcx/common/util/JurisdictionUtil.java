package com.folkestone.bzcx.common.util;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.folkestone.bzcx.bean.bean_dto.common.Query;

public class JurisdictionUtil {
	
	public static Map<String, Object> searchJurisdiction(HttpServletRequest req,Map<String, Object> param){
		if(null != req.getSession().getAttribute(ContantFinalUtil.SEARCH_QB_RIGHT) || "".equals(req.getSession().getAttribute(ContantFinalUtil.SEARCH_QB_RIGHT) )){
			param.put("searcQB", "have");
		}
		if(null != req.getSession().getAttribute(ContantFinalUtil.SEARCH_GB_RIGHT) || "".equals(req.getSession().getAttribute(ContantFinalUtil.SEARCH_GB_RIGHT) )){
			param.put("searcGB", "have");
		}
		if(null != req.getSession().getAttribute(ContantFinalUtil.SEARCH_HB_RIGHT) || "".equals(req.getSession().getAttribute(ContantFinalUtil.SEARCH_HB_RIGHT) )){
			param.put("searcHB", "have");
		}
		if(null != req.getSession().getAttribute(ContantFinalUtil.SEARCH_SB_RIGHT) || "".equals(req.getSession().getAttribute(ContantFinalUtil.SEARCH_SB_RIGHT) )){
			param.put("searcSB", "have");
		}
		return param;
	}
	
	public static Query searchJurisdiction(HttpServletRequest req,Query query){
		if(null != req.getSession().getAttribute(ContantFinalUtil.SEARCH_QB_RIGHT) || "".equals(req.getSession().getAttribute(ContantFinalUtil.SEARCH_QB_RIGHT) )){
			query.put("searcQB", "have");
		}
		if(null != req.getSession().getAttribute(ContantFinalUtil.SEARCH_GB_RIGHT) || "".equals(req.getSession().getAttribute(ContantFinalUtil.SEARCH_GB_RIGHT) )){
			query.put("searcGB", "have");
		}
		if(null != req.getSession().getAttribute(ContantFinalUtil.SEARCH_HB_RIGHT) || "".equals(req.getSession().getAttribute(ContantFinalUtil.SEARCH_HB_RIGHT) )){
			query.put("searcHB", "have");
		}
		if(null != req.getSession().getAttribute(ContantFinalUtil.SEARCH_SB_RIGHT) || "".equals(req.getSession().getAttribute(ContantFinalUtil.SEARCH_SB_RIGHT) )){
			query.put("searcSB", "have");
		}
		return query;
	}
	
	public static Query downlJurisdiction(HttpServletRequest req,Query query){
		if(null != req.getSession().getAttribute(ContantFinalUtil.DOWNLOAD_QB_RIGHT) || "".equals(req.getSession().getAttribute(ContantFinalUtil.SEARCH_QB_RIGHT) )){
			query.put("searcQB", "have");
		}
		if(null != req.getSession().getAttribute(ContantFinalUtil.DOWNLOAD_GB_RIGHT) || "".equals(req.getSession().getAttribute(ContantFinalUtil.SEARCH_GB_RIGHT) )){
			query.put("searcGB", "have");
		}
		if(null != req.getSession().getAttribute(ContantFinalUtil.DOWNLOAD_HB_RIGHT) || "".equals(req.getSession().getAttribute(ContantFinalUtil.SEARCH_HB_RIGHT) )){
			query.put("searcHB", "have");
		}
		if(null != req.getSession().getAttribute(ContantFinalUtil.DOWNLOAD_SB_RIGHT) || "".equals(req.getSession().getAttribute(ContantFinalUtil.SEARCH_SB_RIGHT) )){
			query.put("searcSB", "have");
		}
		return query;
	}
	
	public static Query previewJurisdiction(HttpServletRequest req,Query query){
		if(null != req.getSession().getAttribute(ContantFinalUtil.SEARCH_QB_RIGHT) || "".equals(req.getSession().getAttribute(ContantFinalUtil.SEARCH_QB_RIGHT) )){
			query.put("searcQB", "have");
		}
		if(null != req.getSession().getAttribute(ContantFinalUtil.SEARCH_GB_RIGHT) || "".equals(req.getSession().getAttribute(ContantFinalUtil.SEARCH_GB_RIGHT) )){
			query.put("searcGB", "have");
		}
		if(null != req.getSession().getAttribute(ContantFinalUtil.SEARCH_HB_RIGHT) || "".equals(req.getSession().getAttribute(ContantFinalUtil.SEARCH_HB_RIGHT) )){
			query.put("searcHB", "have");
		}
		if(null != req.getSession().getAttribute(ContantFinalUtil.SEARCH_SB_RIGHT) || "".equals(req.getSession().getAttribute(ContantFinalUtil.SEARCH_SB_RIGHT) )){
			query.put("searcSB", "have");
		}
		return query;
	}
	
	public static boolean downlJurisdiction(HttpServletRequest req,String type){
		System.out.println(req.getSession().getAttribute(type));
		if(null != req.getSession().getAttribute(type)){
			return true;
		}
		/*if(null != req.getSession().getAttribute(ContantFinalUtil.SEARCH_QB_RIGHT)){
			return true;
		}
		if(null != req.getSession().getAttribute(ContantFinalUtil.SEARCH_QB_RIGHT)){
			return true;
		}
		if(null != req.getSession().getAttribute(ContantFinalUtil.SEARCH_QB_RIGHT)){
			return true;
		}*/
		return false;
	}
	
	public static Boolean previewJurisdiction(HttpServletRequest req,String type){
		if(null != req.getSession().getAttribute(type)){
			return true;
		}
		return false;
	}
}
