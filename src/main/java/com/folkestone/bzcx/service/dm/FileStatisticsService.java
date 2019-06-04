package com.folkestone.bzcx.service.dm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.folkestone.bzcx.bean.bean_dto.common.Query;
import com.folkestone.bzcx.bean.bean_vo.dm.FileStatisticVo;
import com.folkestone.bzcx.mapper.dm.FileStatisticsMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**
 * 
 * 资源共享
 * 
 * @author Administrator
 *
 */
@Service
public class FileStatisticsService {

	@Autowired
	private FileStatisticsMapper fileStatisticsMapper;

	/**
	 * 把获得的值进行封装
	 * 
	 * @param query
	 * @return
	 */
	public Map<String, Object> StatisticAnalysis() {
		Map<String, Object> samap = new HashMap<String, Object>();
		samap.put("TodayAcess", fileStatisticsMapper.getTodayAcess()); // 今日访问量
		samap.put("CumulaAcess", fileStatisticsMapper.getCumulaAcess()); // 总访问量
		/*samap.put("ActiveUserNumber", fileStatisticsMapper.getActiveUserNumber());// 过去三十天活跃用户数
*/		samap.put("TotalUserNumber", fileStatisticsMapper.getTotalUserNumber());// 系统总用户数
		samap.put("TodayRetrieval", fileStatisticsMapper.getTodayRetrieval());// 今日检索次数
		samap.put("TotalRetrieval", fileStatisticsMapper.getTotalRetrieval());// 累计检索次数
		samap.put("StandardView", fileStatisticsMapper.getStandardView());// 标准查看次数
		samap.put("StandardDownload", fileStatisticsMapper.getStandardDownload());// 标准下载次数
		return samap;
	}

	// 1访问量排行
	public List<Object> SystemAccess() {
		return desshuzu(fileStatisticsMapper.getSystemAccess());
	}

	// 2标准分类统计
	public List<Object> StandardClassificalis() {
		List<Object> li = new ArrayList<Object>();
		for (int i = 0; i < fileStatisticsMapper.getStandardClassificalis().size() && i < 10; i++) {
			try {
				int b = Integer.parseInt(fileStatisticsMapper.getStandardClassificalis().get(i).getBegin());
				String a = fileStatisticsMapper.getStandardClassificalis().get(i).getEnd();
				List<Object> Mli = new ArrayList<Object>();
				// 标准类型：国标 G，企标Q，行标H 等。
					if(a.equals("Q")) {
						Mli.add("企标");
						Mli.add(b);
					}else if(a.equals("G")) {
						Mli.add("国标");
						Mli.add(b);
					}else if(a.equals("H")) {
						Mli.add("行标");
						Mli.add(b);
					}else {
						Mli.add("三化文件");
						Mli.add(b);
					}
				li.add(Mli);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return li;

	}

	// 3检索词热度排行
	public List<Object> SearchWordHeatlis(Query query) {
		// 设置分页参数
		PageHelper.startPage(query.getPage(), query.getRows());
		// 得到结果
		PageInfo<FileStatisticVo> pageInfo = new PageInfo<FileStatisticVo>(fileStatisticsMapper.getSearchWordHeatlis(query));
		return shuzus(pageInfo.getList());
	}

	// 4二级单位标准数量排行
	/*public List<Object> TwoLevelQuantitylis(Query query, String string) {
		if (string.equals("1")) {
			// 设置分页参数
			PageHelper.startPage(query.getPage(), query.getRows());
			// 得到结果
			PageInfo<FileStatisticVo> pageInfo = new PageInfo<FileStatisticVo>(fileStatisticsMapper.getTwoLevelQuantitylis(query));
			return  shuzus(pageInfo.getList());
		} else {
			// 设置分页参数
			PageHelper.startPage(query.getPage(), query.getRows());
			// 得到结果
			PageInfo<FileStatisticVo> pageInfo = new PageInfo<FileStatisticVo>( fileStatisticsMapper.getTwoLevelQuantitylis(query));
			return  shuzus(pageInfo.getList());
		}
	}*/

	// 5二级单位访问量排行
	/*public List<Object> TwoLevelAccesslis() {
		return shuzu(fileStatisticsMapper.getTwoLevelAccesslis());
	}
*/
	// 6二级单位用户数排行
	/*public List<Object> TwoLevelUserlist(Query query, String str) {
		if (str.equals("1")) {
			// 设置分页参数
			PageHelper.startPage(query.getPage(), query.getRows());
			// 得到结果
			PageInfo<FileStatisticVo> pageInfo = new PageInfo<FileStatisticVo>(fileStatisticsMapper.getTwoLevelUserlist(query));
			return  shuzus(pageInfo.getList());
		} else {
			// 设置分页参数
			PageHelper.startPage(query.getPage(), query.getRows());
			// 得到结果
			PageInfo<FileStatisticVo> pageInfo = new PageInfo<FileStatisticVo>(fileStatisticsMapper.getTwoLevelUserlist(query));
			return  shuzus(pageInfo.getList());
}

	}*/

	// 7标准使用率排行
	public List<Object> StandardUsage(Query query) {
		// 设置分页参数
	PageHelper.startPage(query.getPage(), query.getRows());
	// 得到结果
	PageInfo<FileStatisticVo> pageInfo = new PageInfo<FileStatisticVo>(fileStatisticsMapper.getStandardUsage());
	return  shuzus(pageInfo.getList());
	}

	// 8二级单位与检索词统计
	/*public List<Object> RetWords(Query query) {
		List<FileStatisticVo> list = fileStatisticsMapper.getRetWords();
		List<Object> li = new ArrayList<Object>();// 返回值
		List<Object> li1 = new ArrayList<Object>();// 公司名 与标准数量
		List<Integer> linum = null;
		List<Object> li2 = new ArrayList<Object>(); // 检索词
		List<Object> likey = new ArrayList<Object>(); // 检索词
		Map<String, Object> mapname = null;
		String name = "";
		String key = "";
		int k = 0;
		for (int i = 0; i < list.size(); i++) {
			li2.add(list.get(i).getEnd());
		}
		likey = quchong(li2); // 去重之后的检索词
		li.add(likey);

		for (int i = 0; i < list.size(); i++) {// 便利；数组 重新做json
			if (name.equals(list.get(i).getBegin())) {

				key = list.get(i).getEnd(); // 得到key 值 对比likey 放number
				k = shuzudingwei(likey, key);
				if (k >= linum.size() - 1) {
					for (int j = linum.size(); j < k; j++) {
						linum.add(0);
					}
					linum.add(Integer.parseInt(list.get(i).getSpare()));
				} else {

					linum.set(k, Integer.parseInt(list.get(i).getSpare()));
				}

				if (i == list.size() - 1) {
					mapname.put("data", linum); // 把name 放进map
					li1.add(mapname);
				}
			} else if (!name.equals(list.get(i).getBegin())) {
				if (linum != null) {
					mapname.put("data", linum); // 把name 放进map
					li1.add(mapname);
				}
				name = list.get(i).getBegin(); // 二级单位
				mapname = new HashMap<String, Object>();
				mapname.put("name", name); // 把name 放进map
				linum = new ArrayList<Integer>();
				// 做一个数列对比
				// 传key过去 得到一个index
				key = list.get(i).getEnd();
				k = 0;
				k = shuzudingwei(likey, key);
				if (k == 0) {
					linum.add(Integer.parseInt(list.get(i).getSpare()));
				} else {
					for (int j = linum.size(); j < k; j++) {
						linum.add(0);
					}
					linum.add(Integer.parseInt(list.get(i).getSpare()));
				}
			}
		}
		li.add(li1);
		return li;
	}
*/

	private int shuzudingwei(List<Object> likey, String key) {
		for (int i = 0; i < likey.size(); i++) {
			if (key.equals(likey.get(i))) {
				return i;
			}
		}
		return -1;

	}
	private List<Object> quchong(List<Object> list) {
		Set<Object> set = new HashSet<Object>();
		List<Object> newList = new ArrayList<Object>();
		for (Object cd : list) {
			if (set.add(cd)) {
				newList.add(cd);
			}
		}
		return newList;
	}
	private List<Object> shuzus(List<FileStatisticVo> list) {
		List<Object> li = new ArrayList<Object>();
		List<Object> li1 = new ArrayList<Object>();
		List<Object> li2 = new ArrayList<Object>();
		for (int i = 0; i < list.size() && i < 10; i++) {
			li1.add(list.get(i).getEnd());
			li2.add(Integer.valueOf(list.get(i).getBegin()));
		}
		li.add(li1);
		li.add(li2);
		return li;
	}

	private List<Object> shuzustwo(List<FileStatisticVo> list) {
		List<Object> li = new ArrayList<Object>();
		List<Object> li1 = new ArrayList<Object>();
		List<Object> li2 = new ArrayList<Object>();
		for (int i = 0; i < list.size(); i++) {
			li1.add(list.get(i).getEnd());
			li2.add(Integer.valueOf(list.get(i).getBegin()));
		}
		li.add(li1);
		li.add(li2);
		return li;
	}

	public List<Object> desshuzu(List<FileStatisticVo> list) {
		List<Object> li1 = new ArrayList<Object>();
		for (int i = list.size()-1; i >0 && i> list.size()-21; i--) {
			List<Object> li = new ArrayList<Object>();
			li.add(list.get(i).getEnd());
			li.add(Integer.parseInt(list.get(i).getBegin()));
			li1.add(li);
		}
		return li1;
	}
	
	public List<Object> shuzu(List<FileStatisticVo> list) {
		List<Object> li1 = new ArrayList<Object>();
		for (int i = 0; i < list.size() && i < 10; i++) {
			List<Object> li = new ArrayList<Object>();
			li.add(list.get(i).getEnd());
			li.add(Integer.parseInt(list.get(i).getBegin()));
			li1.add(li);
		}
		return li1;
	}
	public Map<String, List<FileStatisticVo>> ExcelTest() {
		Query query = new Query();
		Map<String, List<FileStatisticVo>> SystemAccess = new HashMap<String, List<FileStatisticVo>>();
		SystemAccess.put("TodayAcess", fileStatisticsMapper.getTodayAcess()); // 今日访问量
		SystemAccess.put("CumulaAcess", fileStatisticsMapper.getCumulaAcess()); // 总访问量
		/*SystemAccess.put("ActiveUserNumber", fileStatisticsMapper.getActiveUserNumber());*/// 过去三十天活跃用户数
		SystemAccess.put("TotalUserNumber", fileStatisticsMapper.getTotalUserNumber());// 系统总用户数
		SystemAccess.put("TodayRetrieval", fileStatisticsMapper.getTodayRetrieval());// 今日检索次数
		SystemAccess.put("TotalRetrieval", fileStatisticsMapper.getTotalRetrieval());// 累计检索次数
		SystemAccess.put("StandardView", fileStatisticsMapper.getStandardView());// 标准查看次数
		SystemAccess.put("StandardDownload", fileStatisticsMapper.getStandardDownload());// 标准下载次数
		/*SystemAccess.put("1", fileStatisticsMapper.getSystemAccess());*/ // 1访问量排行
		SystemAccess.put("2", fileStatisticsMapper.getStandardClassificalis()); // 2标准分类统计
		SystemAccess.put("3", fileStatisticsMapper.getSearchWordHeatlis(query)); // 3检索词热度排行
		/*SystemAccess.put("4", fileStatisticsMapper.getTwoLevelQuantitylis(query)); // 4二级单位标准数量排行
		SystemAccess.put("5", fileStatisticsMapper.getTwoLevelUserlist(query)); // 6二级单位用户数排行
		SystemAccess.put("6", fileStatisticsMapper.getTwoLevelAccesslis()); */// 5二级单位访问量排行
		SystemAccess.put("7", fileStatisticsMapper.getStandardUsage()); // 7标准使用率排行
		/*SystemAccess.put("8", fileStatisticsMapper.getRetWords()); */// 8二级单位与检索词统计
		return SystemAccess;

	}
}
