package com.folkestone.bzcx.service.interact;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.folkestone.bzcx.bean.bean_do.interact.S_Valid_ConfirmDo;
import com.folkestone.bzcx.bean.bean_dto.common.EasyUISeparatPage;
import com.folkestone.bzcx.bean.bean_dto.common.Query;
import com.folkestone.bzcx.bean.bean_vo.interact.ValidConfirmVo;
import com.folkestone.bzcx.mapper.interact.SValidConfirmMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**
 * 标准意见反馈service
 * @author Administrator
 *
 */

@Service
public class ValidatService {

	@Autowired
	private SValidConfirmMapper sValidConfirmMapper;
	
	/**
	 * 标准意见反馈单的删除(可批量删除)
	 * @param validConfirmIds
	 */
	public void deleteByIds(String[] validConfirmIds) {
		for (String validConfirmId : validConfirmIds) {
			sValidConfirmMapper.deleteByIds(validConfirmId);
		}
	}
	
	public int deleteByStandId(String standardId) {
		return sValidConfirmMapper.deleteBystanIds(standardId);
	}

	/**
	 * 标准意见反馈单列表
	 * @param query
	 * @return
	 */
	public EasyUISeparatPage listByQuery(Query query) {
		Object ob = query.get("page");
		Integer rows = query.getRows();
		if(ob == null){
			ob = "1";
		}
		Integer page = new Integer(ob.toString());
		// 设置分页参数
		PageHelper.startPage(page, rows);
		// 执行查询
		List<ValidConfirmVo> listByQuery = sValidConfirmMapper.listByQuery(query);
		// 得到结果
		PageInfo<ValidConfirmVo> pageInfo = new PageInfo<ValidConfirmVo>(listByQuery);
		EasyUISeparatPage result = new EasyUISeparatPage(pageInfo.getTotal(), pageInfo.getList());
		return result;
	}

	/**
	 * 标准意见反馈单的查看
	 * @param validConfirmDo
	 * @return
	 */
	public int update(S_Valid_ConfirmDo validConfirmDo) {
		
		return sValidConfirmMapper.updateByValidConfirmId(validConfirmDo);
	}
	
	/**
	 * 提交标准意见反馈单
	 * @author Administrator
	 *
	 */
	public int addValida(S_Valid_ConfirmDo valid){
		return sValidConfirmMapper.insert(valid);
	}
	
	
	public EasyUISeparatPage getsByValid(Query query){
		// 设置分页参数
				PageHelper.startPage(1, 10);
				// 执行查询
				List<ValidConfirmVo> listByQuery = sValidConfirmMapper.OneByValid(query);
				// 得到结果
				PageInfo<ValidConfirmVo> pageInfo = new PageInfo<ValidConfirmVo>(listByQuery);
				EasyUISeparatPage result = new EasyUISeparatPage(pageInfo.getTotal(), pageInfo.getList());
				return result;
	}
	
}
