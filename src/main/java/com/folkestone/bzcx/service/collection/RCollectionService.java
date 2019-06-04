package com.folkestone.bzcx.service.collection;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.folkestone.bzcx.bean.bean_do.collection.RCollectionDo;
import com.folkestone.bzcx.bean.bean_dto.common.EasyUISeparatPage;
import com.folkestone.bzcx.bean.bean_dto.common.Query;
import com.folkestone.bzcx.bean.bean_vo.collection.RCollectionVo;
import com.folkestone.bzcx.mapper.collection.RCollectionMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Service
public class RCollectionService {

	@Autowired
	private RCollectionMapper collection;
	
	/**
	 * 不添加条件分页查询
	 * @param query
	 * @return
	 */
	/*public PageData listByQueryPage(Query query) {
		PageHelper.startPage(query.getPage(), query.getRows());
		PageInfo<RCollectionVo> pageInfo = new PageInfo<RCollectionVo>(collection.listByQuery(query));
		return new PageData(pageInfo.getTotal(), pageInfo.getList());
	}*/
	
	public EasyUISeparatPage listByQueryPage(Query query) {
		// 设置分页参数
				PageHelper.startPage(query.getPage(), query.getRows());
				// 执行查询
				List<RCollectionVo> listManageByQuery = collection.listByQuery(query);
				// 得到结果
				PageInfo<RCollectionVo> pageInfo = new PageInfo<RCollectionVo>(listManageByQuery);
				EasyUISeparatPage result = new EasyUISeparatPage(pageInfo.getTotal(), pageInfo.getList());
				return result;
	}
	
	/**
	 * 不添加条件分页查询
	 * @param query
	 * @return
	 */
	public List<RCollectionVo> listByQuery(Query query) {
		List<RCollectionVo> listByQuery = collection.listByQuery(query);
		return listByQuery;
	}
	
	public int insert(RCollectionDo record) {
		return collection.insert(record);
	}
	
	public int update(RCollectionDo record) {
		return collection.update(record);
	}
	
	public int delete(String collectionId) {
		return collection.delete(collectionId);
	}
	
	public int delete(String[] collectionId) {
		int j = 0;
		for (int i = 0; i < collectionId.length; i++) {
			int delete = collection.delete(collectionId[i]);
			if(delete > 0) {
				j++;
			}
		}
		return j;
	}
	/**
	 * 将我们的收藏的数据展示
	 * @param query
	 * @return
	 */
	public List<Map<String,Object>> show(Query query) {
		// 设置分页参数
		PageHelper.startPage(query.getPage(), query.getRows());
		// 执行查询
		List<Map<String,Object>> listManageByQuery = collection.show(query);
		// 得到结果
		
		// 得到结果
		/*PageInfo<String> pageInfo = new PageInfo<String>(listManageByQuery);
		EasyUISeparatPage result = new EasyUISeparatPage(pageInfo.getTotal(), pageInfo.getList());*/
		return listManageByQuery;
	}
	
}
