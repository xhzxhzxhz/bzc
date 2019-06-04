package com.folkestone.bzcx.controller.admin.interaction;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.folkestone.bzcx.bean.bean_do.interact.Fe_FeedbackDo;
import com.folkestone.bzcx.bean.bean_do.interact.Fe_ReplyDo;
import com.folkestone.bzcx.bean.bean_dto.common.EasyUISeparatPage;
import com.folkestone.bzcx.bean.bean_dto.common.Query;
import com.folkestone.bzcx.bean.bean_dto.common.Result;
import com.folkestone.bzcx.bean.bean_vo.interact.FeFeedbackVo;
import com.folkestone.bzcx.bean.bean_vo.interact.FeReplyVo;
import com.folkestone.bzcx.bean.bean_vo.user.UserVo;
import com.folkestone.bzcx.controller.base.BaseController;
import com.folkestone.bzcx.service.interact.InteractService;
import com.folkestone.bzcx.service.user.OperLogService;
import com.folkestone.bzcx.service.user.UserService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 处理后台互动维护
 * 
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/admin/interact")
public class InteractController extends BaseController{
	@Autowired
	private InteractService interactService;
	
	@Autowired
	private OperLogService operLogService;
	
	@Autowired
	private UserService userservice;

	/**
	 * 反馈修改
	 * 
	 * @param
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/update", method = { RequestMethod.POST })
	public Result update(Fe_FeedbackDo feedbackDo) {
		Result result = new Result(false);
		try {
			int row = interactService.update(feedbackDo);
			if (row == 1) {
				result.setResult(true);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 反馈状态的修改，将未读状态标记为已读状态
	 * 
	 * @param status
	 *            '1：未读；2：已读；
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/updateStatus", method = { RequestMethod.POST })
	public Result updateStatus(String[] ids) {
		Result result = new Result(false);
		try {
			for (String id : ids) {
				Fe_FeedbackDo fDo = new Fe_FeedbackDo();
				fDo.setStatus(Short.valueOf("2"));
				fDo.setFeedbackId(id);
				interactService.update(fDo);
			}
			result.setResult(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 查询单条意见反馈
	 * 
	 * @param architectureId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/detail", method = { RequestMethod.POST })
	public String detail(String feedbackId) {
		return feedbackId;
		/*
		 * { "rows":[
		 * {"userName":"admin1","content":"这是管理员说的一段话","feedbackDate":
		 * "2017-11-03","side":"left"},
		 * {"userName":"admin2","content":"这是管理员说的一段话","feedbackDate":
		 * "2017-11-03","side":"left"},
		 * {"userName":"user1","content":"这是用户1说的一段话","feedbackDate":
		 * "2017-11-03","side":"right"},
		 * {"userName":"admin3","content":"这是管理员说的一段话","feedbackDate":
		 * "2017-11-03","side":"left"},
		 * {"userName":"user2","content":"这是用户2说的一段话","feedbackDate":
		 * "2017-11-03","side":"right"},
		 * {"userName":"admin4","content":"这是管理员说的一段话","feedbackDate":
		 * "2017-11-03","side":"left"} ] }
		 */

		/*
		 * JSONObject json = new JSONObject(); JSONArray jsonArr = new
		 * JSONArray(); List<Fe_ReplyDo> replyList = new
		 * ArrayList<Fe_ReplyDo>(); for (Fe_ReplyDo reply: replyList) {
		 * JSONObject replyJson = new JSONObject(); replyJson.put("userName",
		 * );//管理员：admin 普通用户：通过回复人id查询用户表得到用户名 replyJson.put("content",
		 * reply.getContent()); replyJson.put("feedbackDate", reply);
		 * replyJson.put("side", "right");//管理员：right 用户：left
		 * jsonArr.add(replyJson); } json.put("rows", jsonArr); return
		 * json.toString();
		 */
		/*
		 * Result result = new Result(true); FeFeedbackVo feedbackVo =
		 * interactService.getFeedbackById(feedbackId);
		 * result.setData(feedbackVo); return result;
		 */
	}

	/**
	 * 查询意见反馈列表
	 * 
	 * @param param
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/list", method = { RequestMethod.POST })
	public EasyUISeparatPage select(@RequestParam Map<String, Object> param) {
		Query query = new Query(param);
		EasyUISeparatPage data = interactService.listFeedbackByQuery(query);
		return data;
	}

	
	/**
	 * 查询反馈回复相互留言 管理员视角
	 * 
	 * @param type
	 *            1=系统管理员；2=管理人员；3=普通人员
	 * @param feedbackId
	 *            反馈ID
	 * @param page
	 *            当前页
	 * @param rows
	 *            页条数
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping(value = "/replyDetail", method = { RequestMethod.POST })
	public void replyDetail(String type, String feedbackId, String page,
			String rows,HttpServletRequest req,HttpServletResponse respon) throws IOException {
		super.setCharSet(respon);
		JSONObject json = new JSONObject();
		JSONArray jsonArr = new JSONArray();
		List<FeReplyVo> replyList = selectReceiverUserByFeedbackId(feedbackId);// 得到留言的总条数，管理员和用户都有
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		//把feeback的第一个留言加进去
		JSONObject replyJso = new JSONObject();
		FeFeedbackVo userv = interactService.getFeedbackById(feedbackId);
		UserVo userByIdd = userservice.getUserById(userv.getUserId());
		replyJso.put("userName", userByIdd.getAccount());
		replyJso.put("feedbackDate", userv.getFeedbackDate());
		replyJso.put("content", userv.getContent());
		replyJso.put("side", "left");
		jsonArr.add(replyJso);
		// 回复人是管理员的话，把管理员的回复的内容全都筛选出来
		for (FeReplyVo feRe : replyList) {
			if (feRe.getReplyUser().equals("user-5590cf2e466b427787153f7dcb4ec60c")) {
				JSONObject replyJson = new JSONObject();
				UserVo userById = userservice.getUserById("user-5590cf2e466b427787153f7dcb4ec60c");
				replyJson.put("userName", userById.getAccount()); // 管理员：admin
														// 普通用户：通过回复人id查询用户表得到用户名
				replyJson.put("content", feRe.getContent());
				replyJson.put("feedbackDate", sdf.format(new Date()));
				replyJson.put("side", "right");// 管理员：right 用户：left
				jsonArr.add(replyJson);
			} else {
				JSONObject replyJson = new JSONObject();
				
				replyJson.put("userName", userByIdd.getAccount());// 管理员：admin
																// 普通用户：通过回复人id查询用户表得到用户名
				replyJson.put("content", feRe.getContent());
				replyJson.put("feedbackDate", sdf.format(new Date()));
				replyJson.put("side", "left");// 管理员：right 用户：left
				jsonArr.add(replyJson);
			}
		}
		json.put("rows", jsonArr);
			respon.getWriter().write(json.toString());
	}

	/**
	 * 查询反馈回复列表
	 * 
	 * @param param
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/replyList", method = { RequestMethod.POST })
	public EasyUISeparatPage replyList(@RequestParam Map<String, Object> param) {
		Query query = new Query(param);
		EasyUISeparatPage data = interactService.listReplyByQuery(query);
		return data;
	}

	/**
	 * 根据反馈ID查询回复表对象
	 * 
	 * @param feedbackId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/selectReceiverUserByFeedbackId", method = { RequestMethod.POST })
	public List<FeReplyVo> selectReceiverUserByFeedbackId(String feedbackId) {
		List<FeReplyVo> feReplyVo = interactService
				.selectReceiverUserByFeedbackId(feedbackId);
		return feReplyVo;
	}

	/**
	 * 保存意见反馈(保存用户的第一次说话，那么如何判断是用户第一次说话？？)
	 * 通过FE_FEEDBACK表，使得每个用户的USER_ID对应一个FEEDBACK_ID，当用户发送意见的时候就查询有没有FEEDBACK_ID
	 * 
	 * @param architectureDo
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/insert", method = { RequestMethod.POST })
	public Result insert(String feedbackId, String content, String type,
			HttpServletRequest req) {
		Result result = new Result(false);
		// 此表只保存用户第一次说话（普通人员）
		if (type.equals("3")) {
			// 获取当前登录用户
			// Object obj = req.getSession().getAttribute("loginUser");
			// Qx_RyzhbDo user = (Qx_RyzhbDo) obj;
			Fe_FeedbackDo feedbackDo = new Fe_FeedbackDo();
			feedbackDo.setFeedbackDate(new Date());
			feedbackDo.setStatus(Short.valueOf("0"));
			// feedbackDo.setUserId(user.getRyzhid());
			feedbackDo.setUserId("");
			try {
				int row = interactService.insert(feedbackDo);
				if (row == 1) {
					result.setResult(true);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
	/**
	 * 用户第一次留言
	 * @param param
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/message", method = { RequestMethod.POST })
	public Result LeavingMessage(String content,HttpServletRequest req){
		Result result = new Result(false);
		System.out.println(content);
		UserVo user = (UserVo) req.getSession().getAttribute("loginUser");
		Fe_FeedbackDo fe =new Fe_FeedbackDo();
		fe.setUserId(user.getId());
		fe.setStatus(Short.valueOf("1"));
		fe.setContent(content);
		fe.setFeedbackDate(new Date());
		int insert = interactService.insert(fe);
		if(insert == 1){
			result.setResult(true);;
		}
		return result;
	}
	
	/**
	 * 保存意见反馈回复（管理员和用户相互留言） 
	 * 
	 * @param feedbackId
	 *            反馈ID
	 * @param content
	 *            反馈内容
	 * @param type
	 *            管理类型 1=系统管理员；2=管理人员；3=普通人员
	 * @param req
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/insertReply", method = { RequestMethod.POST })
	public Result insertReply(String feedbackId, String content,
			HttpServletRequest req, String UserId) {
		try {
		Result result = new Result(false);
		Fe_ReplyDo replyDo = new Fe_ReplyDo();
		Fe_FeedbackDo feedbackDo = new Fe_FeedbackDo();
		//先拿到登录的用户信息、
		UserVo user = (UserVo) req.getSession().getAttribute("loginUser");//拿到的是当前登录的人的信息
		if (user.getId().equals("user-5590cf2e466b427787153f7dcb4ec60c")) {
			replyDo.setReplyUser(user.getId());//回复人id
			replyDo.setReceiverUser(UserId);//被回复人id，这里是普通用户id
			replyDo.setFeedbackId(feedbackId);
			replyDo.setContent(content);
			replyDo.setReplyDate(new Date());
			int row = interactService.insertReply(replyDo);
			
			//这里是把回复的消息更新到FE_FEEDBACK这张表，负责显示消息
			feedbackDo.setStatus(Short.valueOf("2"));
			feedbackDo.setFeedbackDate(new Date());
			feedbackDo.setFeedbackId(feedbackId);
			int rows = interactService.update(feedbackDo);
			if (row == 1 && rows == 1) {
				result.setResult(true);
				operLogService.insert(super.getOperLog(req, "1023", "反馈回复" ,UserId));
			}
		}else{
			//如果是用户就保存到FEREPLY这张表里
			//这里是把回复的消息更新到FE_FEEDBACK这张表，负责显示消息
				//把管理员显示的数据修改为未读，并更新显示数据
				//feedbackDo.setContent(content);
				feedbackDo.setStatus(Short.valueOf("1"));
				feedbackDo.setFeedbackDate(new Date());
				feedbackDo.setFeedbackId(feedbackId);
				int update = interactService.update(feedbackDo);
				//这里主要是负责保存留言历史记录
				replyDo.setFeedbackId(feedbackId);
				replyDo.setReplyUser(user.getId());//回复人id
				replyDo.setReceiverUser("user-5590cf2e466b427787153f7dcb4ec60c");//被回复人id，这里是管理员id                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                          
				replyDo.setContent(content);
				replyDo.setReplyDate(new Date());
				int row = interactService.insertReply(replyDo);
				if (row == 1 && update==1) {
					result.setResult(true);
				}
		}
		return result;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 删除意见反馈（可批量删除）
	 * 
	 * @param ids
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/delete", method = { RequestMethod.POST })
	public Result delete(String[] feedbackId,HttpServletRequest req) {
		Result result = new Result(false);
		try {
			interactService.deleteByIds(feedbackId);
			result.setResult(true);
			for (String string : feedbackId) {
				operLogService.insert(super.getOperLog(req, "1022", "意见删除" ,string));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 前台显示
	 * 
	 * @param ids
	 * @return
	 */
	/*@ResponseBody
	@RequestMapping(value = "/show", method = { RequestMethod.POST })
	public Result headShow(String[] feedbackId,HttpServletRequest req) {
		Result result = new Result(false);
		try {
			if(interactService.headShow(feedbackId) > 0){
				result.setResult(true);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}*/
}
