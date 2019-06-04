package com.folkestone.bzcx.service.backup;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.TimerTask;

import org.apache.commons.io.FileUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import com.folkestone.bzcx.bean.bean_do.backup.B_BackupDo;
import com.folkestone.bzcx.bean.bean_vo.dm.StandardVo;
import com.folkestone.bzcx.common.util.POIUtil;
import com.folkestone.bzcx.service.dm.StandardService;

/**
 * 标准题录备份
 * @author folkestone-6
 *
 */
public class AdminStandardTimerTaskSerivce extends TimerTask{

	public AdminStandardTimerTaskSerivce() {
		System.out.println("AdminStandardTimerTaskSerivce===========");
	}
	
	@Override
	public void run() {
		
		//备份操作
		Date date = new Date();
		System.out.println(date.toString());
		System.out.println("---------标准题录备份--------");
		WebApplicationContext wac = ContextLoader.getCurrentWebApplicationContext(); 
		StandardService standardService = (StandardService)wac.getBean("standardService");
		List<StandardVo> list = standardService.listAllStandard();
		String filePath = null;
		try {
			filePath = POIUtil.exportStandard("xlsx",list);
			String fileName = "题录.xlsx";
			File file = new File(filePath);
			HttpHeaders headers = new HttpHeaders();
			fileName = new String((fileName).getBytes("UTF-8"), "iso-8859-1");
			// 为了解决中文名称乱码问题
			headers.setContentDispositionFormData("attachment", fileName);
			headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
			new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file), headers, HttpStatus.OK);
		} catch (IOException e) {
			System.out.println("自动备份失败");
			e.printStackTrace();
		}
		//持久化备份类
		B_BackupDo backup = new B_BackupDo();
		backup.setCreateDate(new Date());
//		backup.setGroup(1);
		backup.setPath(filePath);
		BackupSerivce backupSerivce = (BackupSerivce)wac.getBean("backupSerivce");
		backupSerivce.insert(backup);
	}
	public static void main(String[] args) {
		
	}

}
