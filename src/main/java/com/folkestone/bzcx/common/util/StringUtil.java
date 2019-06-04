package com.folkestone.bzcx.common.util;

/**
 * Describe：处理一些字符串操作
 * 
 * @author smallking
 *
 *  2017年11月1日
 */
public class StringUtil {

	/**
	 * 字符串非空验证
	 * @param str
	 * @return
	 */
	public static boolean isEmpty(String str){
		if(str == null || str.trim().equals("") || str.equals("null") || str.equals("undefined")){
			return true;
		}
		return false;
	}
	/**
	 * @param cellValue
	 * @return
	 */
	public static String trim(String str) {
		if(str == null){
			return null;
		}
		return str.trim();
	}
	
	public static String StrCha(String str){
		 StringBuffer sb = new StringBuffer();
	    	String standardNoTrim =str.replaceAll("[^a-zA-Z0-9]", "").replaceAll("^", "");
	        char[] c = standardNoTrim.toCharArray();
	    	  for(int i=0;i<c.length;i++){
	    	   if(c[i]>=97){
	    	    sb.append((c[i]+"").toUpperCase());
	    	   }else {
				   sb.append(c[i]);
			   }
	    	  }
	    	  return sb.toString();
	}
}
