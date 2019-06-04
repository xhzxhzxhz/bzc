package com.folkestone.bzcx.common.util;

import java.lang.reflect.Field;

/**
 * Describe：将Vo转成Do
 * 
 * @author smallking
 *
 *  2017年10月31日
 */
public class ConvertUtil {
	
	public static void convert(Object voObj, Object doObj) throws IllegalArgumentException, IllegalAccessException {
		Field[] voFields = voObj.getClass().getDeclaredFields();
		Field[] doFields = doObj.getClass().getDeclaredFields();
		for(Field voField : voFields)
		{
			voField.setAccessible(true);
			for(Field doField : doFields){
				doField.setAccessible(true);
				if(doField.getName().equals(voField.getName()) && voField.get(voObj) != null){
					doField.set(doObj, voField.get(voObj));
				}
			}
		}
	}
}
