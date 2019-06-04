package com.folkestone.bzcx.service.dm;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrQuery.ORDER;
import org.apache.solr.client.solrj.SolrRequest.METHOD;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.folkestone.bzcx.bean.bean_dto.dm.SolrSearchResult;
import com.folkestone.bzcx.common.util.ContantFinalUtil;
import com.folkestone.bzcx.service.user.QueryHistoryService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**
 * Describe：初始化数据到solr
 * 
 * @author smallking
 *
 *         2017年11月4日
 */
@Service
public class SolrService {
	@Autowired
	private StandardFileService standardFileService;
	@Autowired
	private QueryHistoryService queryHistoryService;

	/**
	 * 初始化数据到solr里面 建立solr索引
	 * 
	 * @param fileId
	 *            文件的ID
	 * @param fileName
	 *            文件的名称
	 * @param fileContent
	 *            文件的内容
	 * @param documentType
	 *            文件的类型 是个人文档 还是电子文档
	 */
	public void addDocumentToSolr(String fileId, String fileName, String fileContent, String icsCode,
			String publishDate, String status,String paranCnName,String pubOrg) {
		// 1.实例化对象,相当于在浏览器输入了url
		SolrServer server = new HttpSolrServer(ContantFinalUtil.SOLR_URL);
		// 2.新建doc对象
		SolrInputDocument doc = new SolrInputDocument();
		// 3.往doc中添加属性

		doc.addField("id", fileId);
		doc.addField("fileName", fileName);
		doc.addField("fileType", fileName.split("\\.")[1]);
		doc.addField("fileContent", fileContent);
		// doc.addField("fileClass", value);
		doc.addField("icsCode", icsCode);
		doc.addField("paramCnName", paranCnName);
		doc.addField("publishDate", publishDate);
		doc.addField("status", status);
		doc.addField("pubOrg", pubOrg);
		System.out.println(
				"sor库添加成功：：--------------------------------"+fileId+"------------------------------------------------------"
						+ fileId);
		try {
			server.add(doc);
			server.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 从solr中查询元素
	 * 
	 * @param idStr
	 * @param keyword
	 * @return
	 */
	public List<SolrSearchResult> selectContentFormSolr(HttpServletRequest pngse, String idStr, String keyword,
			Integer rows, Integer start) {
		List<SolrSearchResult> resultList = new ArrayList<SolrSearchResult>();
		// 1.实例化对象,相当于在浏览器输入了url
		SolrServer server = new HttpSolrServer(ContantFinalUtil.SOLR_URL);
		
		// 2. 设置查询
		SolrQuery query = new SolrQuery();
		// 设置排序规则,按照关键字出现的次数，降序排列
		query.setSort("termfreq(upFileContent,*" + keyword + "*)", ORDER.desc);
		// 设置查询条件
		String queryStr = "fileContent:" + keyword;
		
		query.set("fl", "fileName,id,fileContent,freq:termfreq(fileContent,*" + keyword + "*)");
		query.set("q", queryStr);
		// 设置过滤字段
		query.set("fq", idStr);
		// 每页展示的条数
		query.set("rows", rows);

		// 开始的页数
		query.set("start", start - 1);
		
				// 开启高亮
				query.setHighlight(true);
				// 高亮列 多个高亮列 空格隔断
				query.addHighlightField("fileContent fileName");
				// 前缀
				query.setHighlightSimplePre("<span style='color:red'>");
				// 后缀
				query.setHighlightSimplePost("</span>");
				// 执行查询,并接收查询结果
				query.setHighlightFragsize(50);
		
		
		
		
		QueryResponse response = new QueryResponse();
		try {
			response = server.query(query, METHOD.POST);
		} catch (SolrServerException e) {
			e.printStackTrace();
		}
		// 获取到结果中docs的内容
		SolrDocumentList list = response.getResults();
		SolrQuery querytotal = new SolrQuery();
		querytotal.setSort("termfreq(upFileContent,*" + keyword + "*)", ORDER.desc);
		querytotal.set("q", queryStr);
		querytotal.set("fq", idStr);
		querytotal.set("rows", Integer.MAX_VALUE);
		querytotal.set("start", 0);
		try {
			int size = server.query(querytotal, METHOD.POST).getResults().size();
			pngse.getSession().setAttribute("solrtotal", size);
		} catch (SolrServerException e) {
			e.printStackTrace();
		}
		// 获取查询结果中的高亮内容
		Map<String, Map<String, List<String>>> highMap = response.getHighlighting();
		if (list.size() > 0) {
			for (SolrDocument doc : list) {
				SolrSearchResult solrResult = new SolrSearchResult();
				solrResult.setFileId(doc.get("id") + "");
				//先从表准原文中去查询我们的数据
				//String fileId=doc.get("id").toString();
				//切割
				//StdFileVo stdFileVo = standardFileService.getStdFileById(doc.get("id") + "");
				/*if (stdFileVo != null) {
					String standNo = stdFileVo.getStandardNo();
					if (standNo != null) {
						solrResult.setStandardNo(standNo);
					}
					String standId = stdFileVo.getStandardId();
					if (standId != null) {
						solrResult.setStandardId(standId);
					}

					if (stdFileVo.getHtmlUrl() != null) {
						solrResult.setHtmlUrl(stdFileVo.getHtmlUrl());
					}*/
					  Object freq = doc.get("freq");
					  if(freq!=null) {
						  
						  solrResult.setFreq(freq.toString());
					  }
					
						  Map<String, List<String>> map2 = highMap.get(doc.get("id").toString());
							if (map2 != null && map2.size() > 0) {
								if (map2.get("fileName") != null && map2.get("fileName").size() > 0) {
									solrResult.setFileName(map2.get("fileName").get(0));
								} else {

									Object fileNameO = (Object) doc.get("fileName");
									if (fileNameO != null) {
										String fileName = fileNameO.toString();

										if (fileName != null) {
											solrResult.setFileName(doc.get("fileName").toString());
										}
									}

								}
							} else {
								Object fileNameO = (Object) doc.get("fileName");
								if (fileNameO != null) {
									String fileName = fileNameO.toString();

									if (fileName != null) {
										solrResult.setFileName(doc.get("fileName").toString());
									}
								}
							}

							if (map2 != null && map2.size() > 0) {
								if (map2.get("fileContent") != null && map2.get("fileContent").size() > 0) {
									solrResult.setFileContent(map2.get("fileContent").get(0));
								} else {
									Object con = doc.get("fileContent");
									if (con != null) {
										String fileContent = con.toString();
										if (fileContent != null) {
											solrResult.setFileContent(doc.get("fileContent").toString());
										}
									}

								}
							} else {
								Object con = doc.get("fileContent");
								if (con != null) {
									String fileContent = con.toString();
									if (fileContent != null) {
										solrResult.setFileContent(doc.get("fileContent").toString());
									}
								}

							}
					
					
					resultList.add(solrResult);
				}

			}
		
		return resultList;
	}

	/**
	 * 删除solr索引 fileId：solr索引的ID
	 * 
	 * @param keyword
	 * @param fileScope
	 */
	public void deleteSolrIndex(String fileId) {
		try {
			SolrServer server = new HttpSolrServer(ContantFinalUtil.SOLR_URL);
			server.deleteById(fileId);
			server.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 查询全文检索界面上显示的相关搜索和为你推荐的词汇
	 * 
	 * @param keyword
	 * @return
	 */
	public List<String> listRelationWord(String keyword) {
		PageHelper.startPage(1, 10);
		List<String> list = queryHistoryService.listRelationWord(keyword);
		PageInfo<String> pageInfo = new PageInfo<String>(list);
		return pageInfo.getList();
	}
	
	/**
	 * 从solr中查询元素
	 * 
	 * @param idStr
	 * @param keyword
	 * @return
	 */
	public List<SolrSearchResult> selectContentForm2Solr(HttpServletRequest pngse, String idStr, String keyword,
			Integer rows, Integer start) {
		List<SolrSearchResult> resultList = new ArrayList<SolrSearchResult>();
		// 1.实例化对象,相当于在浏览器输入了url
		SolrServer server = new HttpSolrServer(ContantFinalUtil.SOLR_URL);
		
		// 2. 设置查询
		SolrQuery query = new SolrQuery();
		// 设置排序规则,按照关键字出现的次数，降序排列
		query.setSort("termfreq(upFileContent,*" + keyword + "*)", ORDER.desc);
		// 设置查询条件
		String queryStr = "fileContent:" + keyword;
		
		query.set("fl", "fileName,id,fileContent,freq:termfreq(fileContent,*" + keyword + "*)");
		query.set("q", queryStr);
		// 设置过滤字段
		query.set("fq", idStr);
		// 每页展示的条数
		query.set("rows", rows);

		// 开始的页数
		query.set("start", start - 1);
		if(keyword!=null) {
			if(keyword.equals("*")) {
				query.setHighlight(false);
				// 高亮列 多个高亮列 空格隔断
			
				
			}else {
				// 开启高亮
				query.setHighlight(true);
				// 高亮列 多个高亮列 空格隔断
				query.addHighlightField("fileContent fileName");
				// 前缀
				query.setHighlightSimplePre("<span style='color:red'>");
				// 后缀
				query.setHighlightSimplePost("</span>");
				// 执行查询,并接收查询结果
				query.setHighlightFragsize(50);
			}
		}
		
		
		QueryResponse response = new QueryResponse();
		try {
			response = server.query(query, METHOD.POST);
		} catch (SolrServerException e) {
			e.printStackTrace();
		}
		// 获取到结果中docs的内容
		SolrDocumentList list = response.getResults();
		SolrQuery querytotal = new SolrQuery();
		querytotal.setSort("termfreq(upFileContent,*" + keyword + "*)", ORDER.desc);
		querytotal.set("q", queryStr);
		querytotal.set("fq", idStr);
		querytotal.set("rows", Integer.MAX_VALUE);
		querytotal.set("start", 0);
		try {
			int size = server.query(querytotal, METHOD.POST).getResults().size();
			pngse.getSession().setAttribute("solrtotal", size);
		} catch (SolrServerException e) {
			e.printStackTrace();
		}
		// 获取查询结果中的高亮内容
		Map<String, Map<String, List<String>>> highMap = response.getHighlighting();
		if (list.size() > 0) {
			for (SolrDocument doc : list) {
				SolrSearchResult solrResult = new SolrSearchResult();
				solrResult.setFileId(doc.get("id") + "");
				//先从表准原文中去查询我们的数据
				//String fileId=doc.get("id").toString();
				//切割
				//StdFileVo stdFileVo = standardFileService.getStdFileById(doc.get("id") + "");
				/*if (stdFileVo != null) {
					String standNo = stdFileVo.getStandardNo();
					if (standNo != null) {
						solrResult.setStandardNo(standNo);
					}
					String standId = stdFileVo.getStandardId();
					if (standId != null) {
						solrResult.setStandardId(standId);
					}

					if (stdFileVo.getHtmlUrl() != null) {
						solrResult.setHtmlUrl(stdFileVo.getHtmlUrl());
					}*/
					  Object freq = doc.get("freq");
					  if(freq!=null) {
						  
						  solrResult.setFreq(freq.toString());
					  }
					  if(keyword.equals("*")) {//表示我们的关键字为空
						  Object fileName= doc.get("fileName");
						  if(fileName!=null) {
							  solrResult.setFileName(fileName.toString());
						  }
						  Object fileContent= doc.get("fileContent");
						  if(fileContent!=null) {
							  solrResult.setFileContent(fileContent.toString());
						  }
					  }else {
						  Map<String, List<String>> map2 = highMap.get(doc.get("id").toString());
							if (map2 != null && map2.size() > 0) {
								if (map2.get("fileName") != null && map2.get("fileName").size() > 0) {
									solrResult.setFileName(map2.get("fileName").get(0));
								} else {

									Object fileNameO = (Object) doc.get("fileName");
									if (fileNameO != null) {
										String fileName = fileNameO.toString();

										if (fileName != null) {
											solrResult.setFileName(doc.get("fileName").toString());
										}
									}

								}
							} else {
								Object fileNameO = (Object) doc.get("fileName");
								if (fileNameO != null) {
									String fileName = fileNameO.toString();

									if (fileName != null) {
										solrResult.setFileName(doc.get("fileName").toString());
									}
								}
							}

							if (map2 != null && map2.size() > 0) {
								if (map2.get("fileContent") != null && map2.get("fileContent").size() > 0) {
									solrResult.setFileContent(map2.get("fileContent").get(0));
								} else {
									Object con = doc.get("fileContent");
									if (con != null) {
										String fileContent = con.toString();
										if (fileContent != null) {
											solrResult.setFileContent(doc.get("fileContent").toString());
										}
									}

								}
							} else {
								Object con = doc.get("fileContent");
								if (con != null) {
									String fileContent = con.toString();
									if (fileContent != null) {
										solrResult.setFileContent(doc.get("fileContent").toString());
									}
								}

							}
					  }
					
					resultList.add(solrResult);
				}

			}
		
		return resultList;
	}

}
