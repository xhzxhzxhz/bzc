package com.folkestone.bzcx.controller.front.userlogin;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.folkestone.bzcx.bean.bean_do.user.R_DepartmentDo;
import com.folkestone.bzcx.bean.bean_do.user.R_UserDo;
import com.folkestone.bzcx.bean.bean_dto.common.Query;
import com.folkestone.bzcx.bean.bean_dto.common.Result;
import com.folkestone.bzcx.bean.bean_vo.user.DepartmentVo;
import com.folkestone.bzcx.bean.bean_vo.user.UserVo;
import com.folkestone.bzcx.common.util.UUIDUtil;
import com.folkestone.bzcx.controller.base.BaseController;
import com.folkestone.bzcx.controller.front.userlogin.wechatinterface.SendMsgUtil;
import com.folkestone.bzcx.service.user.DepartmentService;
import com.folkestone.bzcx.service.user.UserService;

import redis.clients.jedis.Jedis;

/**
 * 
 * 登录接口
 * @author folkestone-6
 *
 */
@Controller
@RequestMapping(value="/userlogin")
public class login extends BaseController{
	@Autowired
	private DepartmentService departmentService;
	
	@Autowired
    private UserService userservice;
	/**
	 * 先判断这个企业用用户 还是个人用户
	 * @return
	 * @throws IOException 
	 */
	@ResponseBody
	@RequestMapping(value = "/login", method = {RequestMethod.POST,RequestMethod.GET})
	public Result login(String account,String password,String phone,String vCode,String type,HttpServletRequest req,HttpServletResponse respon) throws IOException{
		Result re = new Result(false);
		//将我们登录后的用户存放到Redis中 设置他的过期时间，当用户通过微信登录后我们就看可以获取他的用户信息 ，然后在去Redis中查询，当存在的时候，直接去登录，否则去用账号登录
		Object URL = req.getSession().getAttribute("userUrl");
		try {
			if(type!=null && !"".equals(type)) {
				if(type.equals("个人用户")) {//表示这个人是个人用户
					Query query = new Query();
					if(phone!=null) {
						query.put("phone", phone);
					}
					
					UserVo user=	userservice.findUserByTel(query);
					if(user!=null) {//表示有这个用户
						Jedis jedis = new Jedis("localhost");
						String code=	jedis.get(phone).toString();
						if(vCode!=null && code.equals(vCode)) {//表示登录成功
			        		String url=(String)	req.getSession().getAttribute("userUrl");
			        		//然后让他重定向到第一次请求的页面
			        		//respon.sendRedirect(url);
			        		String ss = req.getQueryString();
			        		System.out.println("ss:==============="+ss);
			        		System.out.println("url:==============="+url);
			        		//将这个用户更新到数据库中
			        		R_UserDo userDo=(R_UserDo) req.getSession().getAttribute("snsUserInfo");
			        		String openid=user.getOpenid();
			        		System.out.println("openid：+++++++++++++"+openid);
			        		if(userDo!=null) {
			        			userservice.update(userDo, null);
			        		}
			        		Query quer= new Query();
			        		quer.put("openid",openid);
			        		UserVo userById = userservice.getUserByQuery(quer);
			        		re.setCode(200);
			        		re.setMsg("验证通过！");
			        		if(URL!=null) {
			        			
			        			req.getSession().setAttribute("loginUser", userById);
			        			req.getSession().setAttribute("loginUserstate", true);
			        			URL = "http://bzc.shengfeisi.cn:8080"+URL.toString();//+snsUserInfo.getUnionid();
			        			//respon.sendRedirect(URL.toString());
			        			re.setCode(203);
				        		re.setData(URL.toString());
			        		}else {
			        			//respon.sendRedirect("http://bzc.shengfeisi.cn:8080/BZCX/QRcode.html"); 
			        			re.setCode(203);
				        		re.setData("http://bzc.shengfeisi.cn:8080/BZCX/QRcode.html");
			        		}
			        		
			        }else {
			        	//202 验证码错误
			        	re.setCode(202);
			        	re.setMsg("验证码错误，请求重新输入");
			        }
						return re;
					}else {//表示在数据库中没有这个用户，将这个添加到数据库库中
						
				        Jedis jedis = new Jedis("localhost");
				        String  code= jedis.get(phone);
				        System.out.println("code:+++++++++++++登录验证+++++++="+code);
				        if(code!=null) {
				        	if(vCode!=null && vCode.equals(code)) {//表示登录成功
					        	//将这个保存到数据库中
				        		R_UserDo us=		(R_UserDo) req.getSession().getAttribute("snsUserInfo");
					        	String userId=UUIDUtil.getUUID("user");
					        	us.setId(userId);
					        	us.setTel(phone);
					        	userservice.insert(us,null);
					        	String openid =us.getOpenid();
					        	Query quer= new Query();
				        		quer.put("openid",openid);
				        		UserVo userById = userservice.getUserByQuery(quer);
					        	req.getSession().setAttribute("loginUser", userById);
			        			req.getSession().setAttribute("loginUserstate", true);
					        	//然后重定向到首页
					        	if(URL!=null) {
					        		URL = "http://bzc.shengfeisi.cn:8080"+URL.toString();//+snsUserInfo.getUnionid();
				        			//respon.sendRedirect(URL.toString()); 
					        		re.setCode(203);
					        		re.setData(URL.toString());
				        		}else {
				        			//respon.sendRedirect("http://bzc.shengfeisi.cn:8080/BZCX/QRcode.html"); 
				        			re.setCode(203);
					        		re.setData("QRcode.html");
				        		}
					        	
					        	//String url=(String) req.getSession().getAttribute("userUrl");
					        	//重定向到刚才请求的地址
					        	//respon.sendRedirect(url);
					        	System.out.println("url:+++++++++++++++++++"+URL.toString());
					        	
					        }else {
					        	//202 验证码错误
					        	re.setCode(202);
					        	re.setMsg("验证码错误，请求重新输入");
					        }
				        }
				        
					}
				}	
				if(type.equals("企业用户")) {//表示这个是一个企业用户
					Query query = new Query();
					query.put("account", account);
					DepartmentVo logindepartment = departmentService.logindepartment(query);
					if(null != logindepartment) {//表示这个企业有这个账号，
					
							if(password.equals(logindepartment.getPassword())) {//验证成功
								//通过这个账号去查询这个人
								UserVo userVo=	userservice.findUserByAccount(account);
								if(userVo!=null) {//表示这个用户已经有这个的账号
								String  changeState= logindepartment.getChangeState();
								if(changeState!=null) {
									if(changeState.equals("2") ) {//表示这个以前登录过的
										//获取当前登录人的对象
										//UserVo user = getUser(req);
										R_UserDo user=(R_UserDo) req.getSession().getAttribute("snsUserInfo");
										R_UserDo us = new R_UserDo();
										//将这个企业和用户进行绑定，这样就会让这个用户是这个企业的
										user.setDepartmentId(logindepartment.getId());
										//us.setId(user.getId());
										
										//将数据库中的用户数据进行更新
										userservice.update(user, null);
									}
									//Object URL = req.getSession().getAttribute("userUrl");
									//int indexOf = URL.toString().lastIndexOf("/");
									//System.out.println(indexOf);
									//得到//后面的请求路劲
									//String substring = URL.toString().substring((indexOf+1), URL.toString().length());
									//将用户的请求参数，所属部门返回
									re.setMsg("登录成功");
									//表示登录成功、
						        	//然后重定向到首页
									
				        			req.getSession().setAttribute("loginUserstate", true);
									//URL = "http://bzc.shengfeisi.cn:8080"+URL.toString();
						        	//respon.sendRedirect(URL.toString());
						        	re.setCode(203);
									
									re.setData(URL.toString());
								}
						}else {//表示么有这个用户
							R_UserDo user=(R_UserDo) req.getSession().getAttribute("snsUserInfo");
							
							user.setDepartmentId(logindepartment.getId());
							//us.setId(user.getId());
							
							//添加用户
							userservice.insert(user, null);
							re.setMsg("登录成功");
							//表示登录成功、
				        	//然后重定向到首页
							req.getSession().setAttribute("loginUserstate", true);
							//URL = "http://bzc.shengfeisi.cn:8080"+URL.toString();
				        	//respon.sendRedirect(URL.toString());
				        	re.setCode(203);
					
							re.setData(URL.toString());
						}
						
							
						}else {//密码错误
							R_UserDo user=(R_UserDo) req.getSession().getAttribute("snsUserInfo");
							
							user.setDepartmentId(logindepartment.getId());
							//us.setId(user.getId());
							
							//将数据库中的用户数据进行更新
							
				        	re.setCode(202);
				        	//re.setResult(true);
				        	re.setMsg("密码错误");					
						}
					}else {/*//表示这个账号没有一个用户使用，当登录成功的时候，将这个账号绑定给这个用户
						if(password.equals(logindepartment.getPassword())) {//验证成功
								//将用户和公司的账号绑定起来
							R_UserDo user=(R_UserDo) req.getSession().getAttribute("snsUserInfo");
							user.setAccount(account);
							user.setPassword(password);
							//添加用户
							userservice.insert(user, null);
							//重定向到我们请求的页面
							respon.sendRedirect(URL.toString());
							re.setCode(200);
							re.setMsg("添加成功！");
						}else {//验证失败
							//201无效用户名，202.密码错误
							re.setCode(201);
							re.setMsg("密码错误");
							
						
						}
						*/
						//表示这个企业没有这个账号，就上他去申请账号
						re.setCode(500);
						re.setMsg("请申请有效账号");
						}
					}
				}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return re;
	}
	/**
	 * 发送短信
	 * @param phone
	 * @param vCode
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/sendMsg" ,method=RequestMethod.GET)
	public Result sendMsg(String phone,String type ,HttpServletRequest req,HttpServletResponse response) {
		Result result = new Result ();
		try {
			if(type!=null && !"".equals(type)) {
				if(type.equals("个人用户")) {//表示这个人是个人用户
						//然后就是给这个用户发送验证码 通过短信
						 //1、随机生成6位的验证码
			            //表示的是这个的首数字 不能为0
			            int num = (int) (Math.random() * 9 + 1);
			            //后面的则是 随机的生成5个随机数字，这个的原因是：阿里云通信  我们传过去的验证码  如果首字母为 0 则 不会识别 这个0
			            String randomNum = RandomStringUtils.randomNumeric(5);
			            //进行的是字符串的拼接 将这个生成的数字 拼接成字符串
			            randomNum = num + randomNum;
			        
			        //2、将生成的验证码保存到redis中三分钟
			        //redisTemplate.boundValueOps(phone).set(randomNum, 5L, TimeUnit.MINUTES);
			            Jedis jedis = new Jedis("localhost");
			            jedis.set(phone, randomNum, "NX", "EX", 60);
			        try {
						SendMsgUtil.sendMsg(phone, randomNum);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			      
			        String  code= jedis.get(phone);
			        String url=(String)	req.getSession().getAttribute("userUrl");
	        		//然后让他重定向到第一次请求的页面
	        		System.out.println("url:++++++++++++++++"+url);
	        		String ss = req.getQueryString();
	        		System.out.println("ss:==============="+ss);
	        		System.out.println("code:==============="+code);
			       result.setCode(200);
			       result.setMsg("发送成功");
					
				}
			}
		}catch(Exception e) {
			e.printStackTrace();
			result.setCode(500);
			result.setMsg("发送失败");
		}
		return result;
	}
	
	/**
	 * 编辑部门
	 * @param departmentId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/update", method = {RequestMethod.POST})
	public Result update(R_DepartmentDo department, HttpServletRequest req){
		Result result = new Result(false);
		try {
			int row = departmentService.update(department);
			if(row >= 1)
			{
				UserVo user = getUser(req);
				R_UserDo us = new R_UserDo();
				us.setDepartmentId(department.getId());
				us.setId(user.getId());
				int update = userservice.update(us, null);
				if(update > 0) {
					result.setResult(true);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
}
