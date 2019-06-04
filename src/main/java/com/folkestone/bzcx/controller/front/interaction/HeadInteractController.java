package com.folkestone.bzcx.controller.front.interaction;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.websocket.Session;

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
import com.folkestone.bzcx.common.websocket.WebSocket;
import com.folkestone.bzcx.controller.base.BaseController;
import com.folkestone.bzcx.service.interact.InteractService;
import com.folkestone.bzcx.service.user.OperLogService;
import com.folkestone.bzcx.service.user.UserService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 处理前台互动维护
 * 
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/interact")
public class HeadInteractController extends BaseController{
	@Autowired
	private InteractService interactService;
	
	@Autowired
	private OperLogService operLogService;
	
	@Autowired
	private UserService userservice;
	
	//用于轮询时发送消息
	private Session session;

	
	

	/**
	 * 查询意见反馈列表
	 * 
	 * @param param
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/list", method = { RequestMethod.POST})
	public EasyUISeparatPage select(@RequestParam Map<String, Object> param,HttpServletRequest req) {
		Query query = new Query(param);
		EasyUISeparatPage data = interactService.listFeedbackByQuery(query);
		Object obj = req.getSession().getAttribute("loginUser");
		return data;
	}

	
	/**
	 * 查询意见反馈详细列表
	 * 
	 * @param param
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/lists", method = { RequestMethod.POST })
	public EasyUISeparatPage listselect(@RequestParam Map<String, Object> param,HttpServletRequest req) {
		Query query = new Query(param);
		EasyUISeparatPage data = interactService.listReplyByQueryOrder(query);
		FeFeedbackVo feedbackById = new FeFeedbackVo();
		for (Object o : data.getRows()) {
			FeReplyVo replyvo = (FeReplyVo) o;
			feedbackById = interactService.getFeedbackById(replyvo.getFeedbackId());
		}
		data.put("FeFeedbackVo", feedbackById);
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
	 */
	@ResponseBody
	@RequestMapping(value = "/replyDetail", method = { RequestMethod.POST })
	public String replyDetail(String type, String feedbackId, String page,
			String rows,HttpServletRequest req) {
		JSONObject json = new JSONObject();
		JSONArray jsonArr = new JSONArray();
		List<FeReplyVo> replyList = selectReceiverUserByFeedbackId(feedbackId);// 得到留言的总条数，管理员和用户都有
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
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
				UserVo userById = userservice.getUserById(feRe.getReplyUser());
				replyJson.put("userName", userById.getAccount());// 管理员：admin
																// 普通用户：通过回复人id查询用户表得到用户名
				replyJson.put("content", feRe.getContent());
				replyJson.put("feedbackDate", sdf.format(new Date()));
				replyJson.put("side", "left");// 管理员：right 用户：left
				jsonArr.add(replyJson);
			}
		}
		json.put("rows", jsonArr);
		return json.toString();
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
	 * 用户留言
	 * @param param
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/message", method = { RequestMethod.POST })
	public Result LeavingMessage(Fe_FeedbackDo content,HttpServletRequest req){
		Result result = new Result(false);
		UserVo user = getUser(req);
		content.setUserId(user.getId());
		content.setUsername(user.getNickname());
		content.setStatus(Short.valueOf("1"));
		content.setFeedbackDate(new Date());
		int insert = interactService.insert(content);
		if(insert == 1){
			result.setResult(true);
			operLogService.insert(super.getOperLog(req, "5001", content+ "" ,content.getFeedbackId()));
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
//用于轮询发送消息
	@ResponseBody
	@RequestMapping(value = "/Socketmessage", method={RequestMethod.POST})
	public Result getSocketMessage(){
		Result result = new Result(true);
		WebSocket webs = new WebSocket();
		webs.onMessage( "interact" , session);
		return result;
	}
}
