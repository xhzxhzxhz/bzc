package com.folkestone.bzcx.common.aspect;

import org.aspectj.lang.JoinPoint;

import com.folkestone.bzcx.common.util.ContantFinalUtil;

public class ExceptionLog {

	public void doThrowing(JoinPoint joinPoint,Throwable ex)
	{
		// 得到出现异常的方法的参数列表
		String paramListStr = "";
		for (int i = 0; i < joinPoint.getArgs().length; i++) {
			paramListStr += ("参数" + i + 1 + "--" + joinPoint.getArgs()[i] + ";   ");
		}
		/*
		 // 得到当前用户信息
		 RequestAttributes ra = RequestContextHolder.getRequestAttributes();
		 ServletRequestAttributes sra = (ServletRequestAttributes) ra;
		 HttpServletRequest request = sra.getRequest();
		 RightUser user = (RightUser)request.getSession().getAttribute("user");
		 */
		// ContantFinalUtil.LOG.error("------------------------exception info start------------------------");
		// ContantFinalUtil.LOG.error("当前用户:" + user.toString);
		ContantFinalUtil.LOG.error("执行的方法:" + joinPoint.toLongString());
		ContantFinalUtil.LOG.error("参数列表:" + paramListStr);
		ContantFinalUtil.LOG.error("异常种类:" + ex.getClass().getName());
		ContantFinalUtil.LOG.error("异常详细:" + ex);
		ContantFinalUtil.LOG.error("------------------------exception info end------------------------");
		ContantFinalUtil.LOG.error(" ");
	}
}
