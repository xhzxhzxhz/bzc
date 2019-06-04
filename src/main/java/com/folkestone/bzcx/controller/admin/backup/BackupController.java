package com.folkestone.bzcx.controller.admin.backup;



import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.folkestone.bzcx.bean.bean_dto.common.EasyUISeparatPage;
import com.folkestone.bzcx.bean.bean_dto.common.Query;
import com.folkestone.bzcx.controller.base.BaseController;
import com.folkestone.bzcx.service.backup.BackupSerivce;

/**
 * 备份control
 * @author folkestone-6
 *
 */
@Controller
@RequestMapping(value = "/admin/detail")
public class BackupController extends BaseController {
	
	@Autowired
	private BackupSerivce backupSerivce; 
	
	/**
	 * 按类别查询对应的备份
	 */
	@ResponseBody
	@RequestMapping(value = "/selectByGroup", method={RequestMethod.POST} )
	public EasyUISeparatPage backup(@RequestParam Map<String, Object> param,Integer group) {
		Query query = new Query(param);
		EasyUISeparatPage result = backupSerivce.selectByGroup(query,group);
		return result;
	}
	
}
