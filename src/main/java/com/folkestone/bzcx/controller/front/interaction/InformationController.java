package com.folkestone.bzcx.controller.front.interaction;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.folkestone.bzcx.bean.bean_do.interact.RInformationDo;
import com.folkestone.bzcx.bean.bean_dto.common.Result;
import com.folkestone.bzcx.bean.bean_vo.user.UserVo;
import com.folkestone.bzcx.service.interact.RInformationService;
/**
 * 企业服务
 * @author zqm
 *
 */
@Controller
@RequestMapping("/serinformation")
public class InformationController {
	
	@Autowired
	private RInformationService informate;
	
	
	@ResponseBody
	@RequestMapping(value = "/insert", method = { RequestMethod.POST })
	public Result insert(HttpServletRequest req,RInformationDo record) {
		Result re = new Result(false);
		UserVo user = (UserVo) req.getSession().getAttribute("loginUser");
		record.setInformationUserid(user.getId());
		record.setInformationDate(new Date());
		int insert = informate.insert(record);
		if(insert > 0) {
			re.setResult(true);
		}
		return re;
	}
	
}
