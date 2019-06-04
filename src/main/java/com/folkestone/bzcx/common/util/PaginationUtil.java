package com.folkestone.bzcx.common.util;

import java.util.ArrayList;
import java.util.List;

import com.folkestone.bzcx.bean.bean_dto.common.EasyUISeparatPage;

/**
 * Describe：一些特殊地方，需要手动分页
 * 
 * @author smallking
 *
 *  2017年11月23日
 */
public class PaginationUtil {
	/**
	 * 手动分页
	 * @param dataList 需要分页的数据
	 * @param start 从第几页开始 最小值为 1
	 * @param limit 一页多少个
	 * @return
	 */
	public static EasyUISeparatPage pagination(List<?> dataList, int start, int limit) {
		EasyUISeparatPage result = new EasyUISeparatPage();
		List<Object> resultList = new ArrayList<Object>();
		if(start < 1){
			start = 1;
		}
		if(limit < 1){
			limit = 1;
		}
		int a = (start - 1) * limit;
		int b = start * limit;
		if(a >= dataList.size()){
			a = dataList.size();
		}
		if(b >= dataList.size()){
			b = dataList.size();
		}
		for(int i = a; i < b; i ++){
			try {
				resultList.add(dataList.get(i));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		result.setTotal(Long.valueOf(dataList.size()));
		result.setData(resultList);
		return result;
	}
}
