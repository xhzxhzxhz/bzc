package com.folkestone.bzcx.common.util;

/**
 * 处理路径的工具
 * @author folkestone-6
 *
 */
public class PathUntil {
	
	private static int lastposition;

	/*xxx/xxx
	 * xxxx.xxx.xxx
	 * 位置在最后的
	 * 和
	 * 
	 * */
	/**
	 * 切割路径
	 * 
	 * @param str
	 * @param cutting
	 * @param isfront 需要后面的false
	 * @return
	 */
	public static String cutPathindexof(String str,String cutting,boolean isfront) {
		lastposition = str.lastIndexOf(cutting);
		String Handle = null;
		if(isfront) {
			if(lastposition > 0) {
				Handle = str.substring(0, lastposition);
			}
		}else {
			if(lastposition > 0) {
				Handle = str.substring(lastposition+1, str.length());
			}
		}
		return Handle;
	}
	
	
}
