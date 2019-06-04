package com.folkestone.bzcx.common.util;

public class Dictionaries {

	public static String dictionaries(String standard) {
		int indexOf = standard.indexOf(" ");
		if(indexOf > 0) {
			//如果是有空格的话，就把截取空格前面的
			standard = standard.split(" ")[0];
			String standardNoTrim = standardNoTrim(standard);
			return standardNoTrim;
		}else {
			String[] stand = standard.split("-");
			return standardNoTrim(stand);
		}
	}
	
	public static String standardNoTrim(String standard) {
		 StringBuffer sb = new StringBuffer();
		 	String standardNoTrim =standard.replaceAll("[^a-zA-Z0-9]", "").replaceAll("^", "");
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
	
	public static String standardNoTrim(String[] standard) {
		 StringBuffer sb = new StringBuffer();
		 sb.append(standard[0]);
		 sb.append(standard[1]);
		 sb.append(standard[2]);
		 sb.append(standard[3]);
		return sb.toString();
	}
	
}
