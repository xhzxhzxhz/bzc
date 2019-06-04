package com.folkestone.bzcx.common.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.folkestone.bzcx.bean.bean_vo.type.StandardTypeVo;

/**
 * Describe：存储常量的类
 * 
 * @author smallking
 *
 *  2017年10月15日
 */
public class ContantFinalUtil{
	
	
	/**
	 * 题录静态化页面模板
	 */
	public static final String STATIC_STANDARDNAME = ResourceBundle.getBundle("filepath").getString("STATIC_STANDARDNAME");
	/**
	 * 项目名称
	 *//*
	public static final String projectname = ResourceBundle.getBundle("filepath").getString("projectname");
	
	
	*//**
	 * 跳转头部
	 *//*
	public static final String baseurl = ResourceBundle.getBundle("wx").getString("baseurl");
*/
	/**
	 * 静态化页面html地址
	 */
	public static final String STATIC_STANDARDURL = ResourceBundle.getBundle("filepath").getString("STATIC_STANDARDURL");
	
	/**
	 * 标准查询类型
	 */
	public static List<StandardTypeVo> standType = null;
	
	/**
	 * 微信的accessTokenUrl
	 */
	public static final String accessTokenUrl = ResourceBundle.getBundle("wx").getString("accessTokenUrl");
	
	/**
	 * 微信的apiTicketUrl
	 */
	public static final String apiTicketUrl = ResourceBundle.getBundle("wx").getString("apiTicketUrl");
	/**
	 * 微信的token
	 */
	public static final String TOKEN = ResourceBundle.getBundle("wx").getString("TOKEN");
	/**
	 * 微信的APPID
	 */
	public static final String WX_APPID = ResourceBundle.getBundle("wx").getString("APPID");
	/*
	 * 微信的Secret
	 */
	public static final String WX_SECRET = ResourceBundle.getBundle("wx").getString("SECRET");
	/*
	 * 微信的Secret
	 */
	public static final String WX_URL = ResourceBundle.getBundle("wx").getString("URL");
	/**
	 * Log打印对象
	 */
	public static final Logger LOG = Logger.getLogger(ContantFinalUtil.class);
	/**
	 * 管理员的角色ID
	 */
	public static final String ROLE_ADMIN_ID = "d03279784ca44c0fb5b82cad4722719f";
	/**
	 * 题录实体的属性映射map
	 */
	public static final Map<String, String> STANDARD_FIELD_MAP = new HashMap<String, String>();
	/**
	 * 法律题录实体的属性映射map
	 */
	public static final Map<String, String> LAWS_FIELD_MAP = new HashMap<String, String>();
	/**
	 * 新闻类别的映射map
	 */
	public static final Map<String, String> MAPPING = new HashMap<String, String>();
	/**
	 * 体系实体的映射map
	 */
	public static final Map<String, String> ARCHITECTURE_FIELD_MAP = new HashMap<String, String>();
	/**
	 * 存储文件的根路径
	 */
	public static final String BASE_PATH = ResourceBundle.getBundle("filepath").getString("BASE_PATH");
	
	
	
	/**
	 * 服务器上pdf阅读器的存储路径(供用户下载pdf阅读器)
	 */
	public static final String PDF_READER_PATH = ResourceBundle.getBundle("filepath").getString("PDF_READER_PATH");
	/**
	 * 导出题录的模板路径
	 */
	public static final String EXPORT_STANDARD_EXCEL = BASE_PATH + ResourceBundle.getBundle("filepath").getString("EXPORT_STANDARD_EXCEL");
	/**
	 * 导出体系的模板路径
	 */
	public static final String EXPORT_ARCHITECTURE_EXCEL = BASE_PATH + ResourceBundle.getBundle("filepath").getString("EXPORT_ARCHITECTURE_EXCEL");
	/**
	 * solr服务器的路径
	 */
	public static final String SOLR_URL = ResourceBundle.getBundle("solr").getString("SOLR_URL");
	/**
	 * Http请求参数过滤集合 凡是key出现在下列集合中的，都不算作检索历史词
	 */
	public static final List<String> PARAMTER_FILTER_LIST = new ArrayList<String>();
	/**
     * jackson 对象转json字符串
     */
	public static ObjectMapper objectMapper;
	/*--------------------------------------企标资源---------------------------------------------------*/
	/**
	 * 在资源表中搜索企标资源的ID
	 */
	public static final String SEARCH_QB_RIGHT = "res-9be4dab80e2e4c88a58e508e1f907b78";
	/**
	 * 在资源表中下载企标资源的ID
	 */
	public static final String DOWNLOAD_QB_RIGHT = "res-dccb3e31a96f44489136ef85ffd4fc12";
	/**
	 * 在资源表中在线预览企标资源的ID
	 */
	public static final String PREVIEW_QB_RIGHT = "res-64c0868ae11640ee9d0ccdfe064a599f";
	/*-----------------------------------------国标资源------------------------------------------------*/
	
	/**
	 * 在资源中搜素国标资源id
	 */
	public static final String SEARCH_GB_RIGHT = "res-d609527a5c004c2b8c54fb0b8ec6ac22";
	/**
	 * 在资源表中下载国标资源的ID
	 */
	public static final String DOWNLOAD_GB_RIGHT = "res-f52d021d436f44778e864c50b34d0e3f";
	/**
	 * 在资源表中在线预览国标资源的ID
	 */
	public static final String PREVIEW_GB_RIGHT = "res-c0e94e2b8aa646dfbbe1ea9f06bdaa30";
	/*-----------------------------------------行标资源------------------------------------------------*/
	/**
	 * 在资源表中在线预览行标资源的ID
	 */
	public static final String PREVIEW_HB_RIGHT = "res-ba1896134aeb4570b824a6a61dfa1589";
	/**
	 * 在资源表中在线下载行标资源的ID
	 */
	public static final String DOWNLOAD_HB_RIGHT = "res-a2c4eefe637a416f8d9cf02a7815f60a";
	/**
	 * 在资源表中在线检索行标资源的ID
	 */
	public static final String SEARCH_HB_RIGHT = "res-74c593fb40524c129847662f7fee419c";
	/*-----------------------------------------三化资源------------------------------------------------*/
	/**
	 * 在资源表中在线预览三化文件资源的ID
	 */
	public static final String PREVIEW_SB_RIGHT = "res-966da91e76354f998ae1f4344e340a68";
	/**
	 * 在资源表中在线下载三化文件资源的ID
	 */
	public static final String DOWNLOAD_SB_RIGHT = "res-0149e15cbc3b46c0815d66e167d4d68b";
	/**
	 * 在资源表中在线检索三化文件资源的ID
	 */
	public static final String SEARCH_SB_RIGHT = "res-d6bcaf75c41e488b8e0e1e65ed40405b";
	/**
	 * 不用检查登录的资源url
	 */
	public static final ArrayList<String> PASS_URL_LIST = new ArrayList<String>();
	/**
	 * 系统最基础的角色的集合 这些角色只能修改 不能删除
	 */
	public static final ArrayList<String> BASE_ROLE_LIST = new ArrayList<String>();
	static
	{
		objectMapper = new ObjectMapper();
	    objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
	}
	static{
		STANDARD_FIELD_MAP.put("standardNo", "标准号");
		STANDARD_FIELD_MAP.put("standardCnName", "标准中文名");
		STANDARD_FIELD_MAP.put("standardEnName", "标准英文名");
		STANDARD_FIELD_MAP.put("standardFileName", "标准原文名称");
		STANDARD_FIELD_MAP.put("pubOrg", "发布单位");
		STANDARD_FIELD_MAP.put("adoptRelation", "采用关系");
		STANDARD_FIELD_MAP.put("replaceStandard", "代替标准");
		STANDARD_FIELD_MAP.put("replacedStandard", "被代替标准");
		STANDARD_FIELD_MAP.put("quoteStandard", "引用标准");
		STANDARD_FIELD_MAP.put("modifyObject", "修改件");
		STANDARD_FIELD_MAP.put("supplementaryObject", "补充件");
		STANDARD_FIELD_MAP.put("publishDate", "发布日期");
		STANDARD_FIELD_MAP.put("implementationDate", "实施日期");
		STANDARD_FIELD_MAP.put("confirmDate", "确认日期");
		STANDARD_FIELD_MAP.put("endDate", "截止日期");
		STANDARD_FIELD_MAP.put("invalidDate", "废止日期");
		STANDARD_FIELD_MAP.put("belongToUnit", "归口单位");
		STANDARD_FIELD_MAP.put("draftUnit", "起草单位");
		STANDARD_FIELD_MAP.put("standardType", "标准类型");
		STANDARD_FIELD_MAP.put("standardLevel2", "标准水平");
		STANDARD_FIELD_MAP.put("themeCnName", "中文主题词");
		STANDARD_FIELD_MAP.put("themeEnName", "英文主题词");
		STANDARD_FIELD_MAP.put("freeCnName", "中文自由词");
		STANDARD_FIELD_MAP.put("language", "语言");
		STANDARD_FIELD_MAP.put("country", "国别");
		STANDARD_FIELD_MAP.put("internationClass", "国际标准分类法");
		STANDARD_FIELD_MAP.put("chineseClass", "中国标准分类法");
		STANDARD_FIELD_MAP.put("standardLevel", "标准级别");
		STANDARD_FIELD_MAP.put("projectStartDate", "立项日期");
		STANDARD_FIELD_MAP.put("technicalField", "技术领域");
		STANDARD_FIELD_MAP.put("draftStaff", "起草人员或者专家");
		STANDARD_FIELD_MAP.put("contactWay", "联系方式");
		STANDARD_FIELD_MAP.put("translation", "译文");
		STANDARD_FIELD_MAP.put("jurisdiction", "管辖权");
		STANDARD_FIELD_MAP.put("udc", "UDC号");
		STANDARD_FIELD_MAP.put("pageNumber", "页数");
		STANDARD_FIELD_MAP.put("publisher", "出版社");
		STANDARD_FIELD_MAP.put("pubDate", "出版时间");
		STANDARD_FIELD_MAP.put("isbn", "书号");
		STANDARD_FIELD_MAP.put("digest", "摘要");
		STANDARD_FIELD_MAP.put("standardStatus", "标准状态");
		STANDARD_FIELD_MAP.put("nodeCode", "体系代码");
		STANDARD_FIELD_MAP.put("中文", "1");
		STANDARD_FIELD_MAP.put("英文", "2");
		STANDARD_FIELD_MAP.put("其他", "3");
		STANDARD_FIELD_MAP.put("现行", "1");
		STANDARD_FIELD_MAP.put("暂行", "2");
		STANDARD_FIELD_MAP.put("废止", "3");
		
		MAPPING.put("newsCate-1", "通知公告");
		MAPPING.put("newsCate-2", "昆仑能源标准动态");
		MAPPING.put("newsCate-3", "中国石油标准动态");
		MAPPING.put("newsCate-4", "国家标准动态");
		MAPPING.put("newsCate-5", "行业标准动态");
		MAPPING.put("newsCate-6", "其他信息");
		MAPPING.put("newsStatus-0", "正常");
		MAPPING.put("newsStatus-1", "下架");
		MAPPING.put("newsHot-0", "否");
		MAPPING.put("newsHot-1", "是");
		MAPPING.put("stdFileLanguage-1", "中文");
		MAPPING.put("stdFileLanguage-2", "英文");
		MAPPING.put("stdFileLanguage-3", "其他");
		
		ARCHITECTURE_FIELD_MAP.put("nodeCode", "体系代码");
		ARCHITECTURE_FIELD_MAP.put("nodeCnName", "体系名称");
		
		PARAMTER_FILTER_LIST.add("relation");
		PARAMTER_FILTER_LIST.add("pSize");
		PARAMTER_FILTER_LIST.add("cPage");
		PARAMTER_FILTER_LIST.add("rows");
		PARAMTER_FILTER_LIST.add("page");
		PARAMTER_FILTER_LIST.add("hasImg");
		PARAMTER_FILTER_LIST.add("moudle");
		PARAMTER_FILTER_LIST.add("order");
		PARAMTER_FILTER_LIST.add("sort");
		PARAMTER_FILTER_LIST.add("standardCas");
		PARAMTER_FILTER_LIST.add("standardStatus");
		PARAMTER_FILTER_LIST.add("nodeId");
		PARAMTER_FILTER_LIST.add("isSecond");
		PARAMTER_FILTER_LIST.add("standardType");
		PARAMTER_FILTER_LIST.add("searcQB");
		PARAMTER_FILTER_LIST.add("searcGB");
		PARAMTER_FILTER_LIST.add("searcHB");
		PARAMTER_FILTER_LIST.add("searcSB");
		
		PASS_URL_LIST.add("/BZCX/admin/login.html");
		//PASS_URL_LIST.add("/BZCX/login.html");
		PASS_URL_LIST.add("/BZCX/newsDetail.html");
		PASS_URL_LIST.add("/BZCX/common.html");
		PASS_URL_LIST.add("/BZCX/help.html");
		PASS_URL_LIST.add("/BZCX/login/in");
		PASS_URL_LIST.add("/BZCX/login/out");
		PASS_URL_LIST.add("/BZCX/login/user/out");
		PASS_URL_LIST.add("/BZCX/login/check");
		PASS_URL_LIST.add("/BZCX/registe/in");
		PASS_URL_LIST.add("/BZCX/news/list");
		PASS_URL_LIST.add("/BZCX/history/hotword");
		PASS_URL_LIST.add("/BZCX/share/list");
		PASS_URL_LIST.add("/BZCX/department/second/unit");
		PASS_URL_LIST.add("/BZCX/newsList.html");
		PASS_URL_LIST.add("/BZCX/newsDetail.html");
		PASS_URL_LIST.add("/BZCX/newsCenter.html");
		PASS_URL_LIST.add("/BZCX/news/catelist");
		PASS_URL_LIST.add("/BZCX/news/updown");
		PASS_URL_LIST.add("/BZCX/news/listPic");
		PASS_URL_LIST.add("/BZCX/news/newsInfo");
		PASS_URL_LIST.add("/BZCX/news/search");
		PASS_URL_LIST.add("/BZCX/mobile/index.html");
		PASS_URL_LIST.add("/BZCX/register.html");
		PASS_URL_LIST.add("/BZCX/wx/authorize");
		PASS_URL_LIST.add("/BZCX/wx/weixinLogin");
		PASS_URL_LIST.add("/BZCX/jump/address");
		PASS_URL_LIST.add("/BZCX/wechat/wx_token");
		PASS_URL_LIST.add("/BZCX/wechat/hello");
		PASS_URL_LIST.add("/MP_verify_HNpKbiCaYwEvH8yI.txt");
		
		BASE_ROLE_LIST.add("22cc843a3a6d4589bfbcdd230aabec0d");
		BASE_ROLE_LIST.add("3679c3d118cd46a989cffc9189f76b5f");
		BASE_ROLE_LIST.add("60f88226bc1d46c5bc15c0936b969079");
		BASE_ROLE_LIST.add("c049b950e0624221b45d452249054b8f");
		BASE_ROLE_LIST.add("3232bb649efd4d91b34135db754b5dab");
		BASE_ROLE_LIST.add("2513f93db5b04e29abe44a297f9a62c0");
		BASE_ROLE_LIST.add("d03279784ca44c0fb5b82cad4722719f");
		/**
		 * <result column="LAWS_ID" property="lawsId" jdbcType="VARCHAR" />
		<result column="LAWS_NO" property="lawsNo" jdbcType="VARCHAR" />
		<result column="LAWS_CN_NAME" property="lawsCnName" jdbcType="VARCHAR" />
		<result column="LAWS_EN_NAME" property="lawsEnName" jdbcType="VARCHAR" />
		<result column="PUB_ORG" property="pubOrg" jdbcType="VARCHAR" />
		<result column="PUBLISH_DATE" property="publishDate" jdbcType="TIMESTAMP" />
		<result column="IMPLEMENTATION_DATE" property="implementationDate"
			jdbcType="TIMESTAMP" />
		<result column="LAWS_TYPE" property="lawsType" jdbcType="VARCHAR" />
		<!-- <result column="THEME_CN_NAME" property="themeCnName" jdbcType="VARCHAR" />
		<result column="THEME_EN_NAME" property="themeEnName" jdbcType="VARCHAR" /> -->
		<result column="LANGUAGE" property="language" jdbcType="INTEGER" />
		<result column="HTMLURL" property="htmlUrl" jdbcType="VARCHAR" />
		<result column="EFFECTIVENESS_LEVEL" property="effectivenessLevel"
			jdbcType="VARCHAR" />
		<result column="TITLE" property="title" jdbcType="VARCHAR" />
		<result column="LAWS_LEVEL" property="lawsLevel" jdbcType="INTEGER" />
		<result column="TRANSLATION" property="translation" jdbcType="VARCHAR" />
		<result column="PAGE_NUMBER" property="pageNumber" jdbcType="INTEGER" />
		<result column="DIGEST" property="digest" jdbcType="VARCHAR" />
		<result column="ICS_CODE" property="icsCode" jdbcType="VARCHAR" />
		<result column="PINGYING" property="pingYing" jdbcType="VARCHAR" />
		<result column="CONTENT" property="content" jdbcType="VARCHAR" />
		<result column="LAWS_NO_TRIM" property="lawsNoTrim" jdbcType="VARCHAR" />
		<result column="UPLOAD_UNIT" property="uploadUnit" jdbcType="VARCHAR" />
		<result column="LAWS_FILE_NAME" property="lawsFileName"
			jdbcType="VARCHAR" />
		<result column="LAWS_STATUS" property="lawsStatus"
			jdbcType="VARCHAR" />	
		 */
		
		LAWS_FIELD_MAP.put("lawsNo","法律文号");
		LAWS_FIELD_MAP.put("lawsCnName","法律中文名称");
		LAWS_FIELD_MAP.put("lawsEnName","法律英文名称");
		LAWS_FIELD_MAP.put("pubOrg","发布单位");
		LAWS_FIELD_MAP.put("publishDate","发布日期");
		LAWS_FIELD_MAP.put("implementationDate","实施日期");
		LAWS_FIELD_MAP.put("lawsType","法律类型");
		LAWS_FIELD_MAP.put("language","语种");
		LAWS_FIELD_MAP.put("htmlUrl","htmlUrl");
		LAWS_FIELD_MAP.put("effectivenessLevel","效力级别");
		LAWS_FIELD_MAP.put("title","标题");
		LAWS_FIELD_MAP.put("lawsLevel","法律水平");
		LAWS_FIELD_MAP.put("translation","译文");
		LAWS_FIELD_MAP.put("pageNumber","页码数量");
		LAWS_FIELD_MAP.put("digest","摘要");
		LAWS_FIELD_MAP.put("icsCode","分类编码");
		LAWS_FIELD_MAP.put("pingYing","拼音");
		LAWS_FIELD_MAP.put("content","内容");
		LAWS_FIELD_MAP.put("lawsNoTrim","文号");
		LAWS_FIELD_MAP.put("lawsStatus","法律状态");
		LAWS_FIELD_MAP.put("uploadUnit","上传单位");
		LAWS_FIELD_MAP.put("lawsFileName","原文名称");
	
		
	}
	private ContantFinalUtil()
	{
		
	}
}
