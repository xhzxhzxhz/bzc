package com.folkestone.bzcx.bean.bean_dto.online;

import java.util.HashMap;
import java.util.Map;

public class OnlineBean{
	
	public static Map<String, Integer> map = new HashMap<>();
	
	public static Map<String, Integer> getMap() {
		return map;
	}

	public static void setMap(Map<String, Integer> map) {
		OnlineBean.map = map;
	}

}
