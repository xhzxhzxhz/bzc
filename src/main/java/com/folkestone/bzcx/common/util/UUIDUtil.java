package com.folkestone.bzcx.common.util;

import java.util.UUID;

/**
 * Describe：生成UUID的工具类
 * 
 * @author smallking
 *
 *  2017年10月30日
 */
public class UUIDUtil {
	/**
	 * 生成UUID的公用方法。其中prefix为主键的前缀。
	 * 例如：用户的主键为 "user"-UUID, 文件的主键为"file"-UUID
	 * @param prefix
	 * @return
	 */
	public static String getUUID(String prefix){
		return prefix + "-" + UUID.randomUUID().toString().replaceAll("-", "");
	}
	
	private UUIDUtil(){
		
	}
}
