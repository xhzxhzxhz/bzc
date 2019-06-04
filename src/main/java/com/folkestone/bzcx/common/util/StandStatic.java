package com.folkestone.bzcx.common.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import com.folkestone.bzcx.bean.bean_do.dm.L_LawsDo;
import com.folkestone.bzcx.bean.bean_do.dm.S_StandardDo;
import com.folkestone.bzcx.bean.bean_vo.dm.LawsVo;
import com.folkestone.bzcx.bean.bean_vo.dm.StandardVo;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class StandStatic {
	
	private static String fileURl = ContantFinalUtil.BASE_PATH;
	
	private static String ftlname = ContantFinalUtil.STATIC_STANDARDNAME;
	/**
	 * 题录静态化生成
	 * @param fileURL
	 * @param htmlurl
	 * @throws IOException
	 * @throws TemplateException
	 */
	public static void state (S_StandardDo stand,String htmlurl) throws IOException, TemplateException {
		//1.创建配置类

				Configuration configuration=new Configuration(Configuration.getVersion());

				//2.设置模板所在的目录

				configuration.setDirectoryForTemplateLoading(new File(fileURl));

				//3.设置字符集

				configuration.setDefaultEncoding("utf-8");

				//4.加载模板"test.ftl"

				Template template = configuration.getTemplate(ftlname);

				//5.创建数据模型

				Map map=new HashMap();
				//标准号
				/*map.put("name", "aa");
				map.put("message", "assss");
				map.put("StandardNo",stand.getStandardNo());*/
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				if(stand.getStandardId() == null) {
					map.put("standardId","无");
				}else {
					map.put("standardId",stand.getStandardId());
				}
				if(stand.getStandardNo() == null) {
					map.put("StandardNo","无");
				}else {
					map.put("StandardNo",stand.getStandardNo());
				}
				//标准名称
				if(stand.getStandardCnName() == null) {
					map.put("StandardCnName","无");
				}else {
					map.put("StandardCnName",stand.getStandardCnName());
				}
				//起草单位
				if(stand.getDraftUnit() == null) {
					map.put("DraftUnit","无");
				}else {
					map.put("DraftUnit",stand.getDraftUnit());
				}
				//发布单位
				if(stand.getPubOrg() == null) {
					map.put("pubOrg","无");
				}else {
					map.put("pubOrg",stand.getPubOrg());
				}
				//发布日期
				if(stand.getPublishDate() == null) {
					map.put("publishDate","无");
				}else {
					map.put("publishDate",sdf.format(stand.getPublishDate()));
				}
				//实施日期
				if(stand.getImplementationDate() == null) {
					map.put("implementationDate","无");
				}else {
					map.put("implementationDate",sdf.format(stand.getImplementationDate()));
				}
				//归口单位
				if(stand.getBelongToUnit() == null) {
					map.put("belongToUnit","无");
				}else {
					map.put("belongToUnit",stand.getBelongToUnit());
				}
				//起草人
				if(stand.getDraftStaff() == null) {
					map.put("draftStaff","无");
				}else {
					map.put("draftStaff",stand.getDraftStaff());
				}
				//引用标准
				if(stand.getQuoteStandard() == null) {
					map.put("quoteStandard","无");
				}else {
					map.put("quoteStandard",stand.getQuoteStandard());
				}
				//代替标准
				if(stand.getReplaceStandard() == null) {
					map.put("replaceStandard","无");
				}else {
					map.put("replaceStandard",stand.getReplaceStandard());
				}
				//技术领域/适用范围
				if(stand.getTechnicalField() == null) {
					map.put("technicalField","无");
				}else {
					map.put("technicalField",stand.getTechnicalField());
				}
				if(stand.getStandardStatus() == null) {
					map.put("standardStatus","无");
				}else {
					//1：现行；2：暂行；3：废止
					if(stand.getStandardStatus() == 1) {
						map.put("standardStatus","现行");
					}else if(stand.getStandardStatus() == 2){
						map.put("standardStatus","暂行");
					}else if(stand.getStandardStatus() == 3) {
						map.put("standardStatus","废止");
					}
				}
				//原文链接
				//map.put("originaltext","aaa");
				
				
				//6.创建Writer对象
				
				Writer out =new FileWriter(new File(htmlurl));
				
				//7.输出

				template.process(map,out);

				//8.关闭Writer对象

				out.close();
	}
	
	
	/**
	 * 题录静态化生成
	 * @param fileURL
	 * @param htmlurl
	 * @throws IOException
	 * @throws TemplateException
	 */
	public static void state (StandardVo stand,String htmlurl) throws IOException, TemplateException {
		//1.创建配置类

				Configuration configuration=new Configuration(Configuration.getVersion());

				//2.设置模板所在的目录

				configuration.setDirectoryForTemplateLoading(new File(fileURl));

				//3.设置字符集

				configuration.setDefaultEncoding("utf-8");

				//4.加载模板"test.ftl"

				Template template = configuration.getTemplate(ftlname);

				//5.创建数据模型

				Map map=new HashMap();
				//标准号
				/*map.put("name", "aa");
				map.put("message", "assss");
				map.put("StandardNo",stand.getStandardNo());*/
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				if(stand.getStandardId() == null) {
					map.put("standardId","无");
				}else {
					map.put("standardId",stand.getStandardId());
				}
				if(stand.getStandardNo() == null) {
					map.put("StandardNo","无");
				}else {
					map.put("StandardNo",stand.getStandardNo());
				}
				//标准名称
				if(stand.getStandardCnName() == null) {
					map.put("StandardCnName","无");
				}else {
					map.put("StandardCnName",stand.getStandardCnName());
				}
				//起草单位
				if(stand.getDraftUnit() == null) {
					map.put("DraftUnit","无");
				}else {
					map.put("DraftUnit",stand.getDraftUnit());
				}
				//发布单位
				if(stand.getPubOrg() == null) {
					map.put("pubOrg","无");
				}else {
					map.put("pubOrg",stand.getPubOrg());
				}
				//发布日期
				if(stand.getPublishDate() == null) {
					map.put("publishDate","无");
				}else {
					map.put("publishDate","无");
				}
				//实施日期
				if(stand.getImplementationDate() == null) {
					map.put("implementationDate","无");
				}else {
					map.put("implementationDate","无");
				}
				//归口单位
				if(stand.getBelongToUnit() == null) {
					map.put("belongToUnit","无");
				}else {
					map.put("belongToUnit",stand.getBelongToUnit());
				}
				//起草人
				if(stand.getDraftStaff() == null) {
					map.put("draftStaff","无");
				}else {
					map.put("draftStaff",stand.getDraftStaff());
				}
				//引用标准
				if(stand.getQuoteStandard() == null) {
					map.put("quoteStandard","无");
				}else {
					map.put("quoteStandard",stand.getQuoteStandard());
				}
				//代替标准
				if(stand.getReplaceStandard() == null) {
					map.put("replaceStandard","无");
				}else {
					map.put("replaceStandard",stand.getReplaceStandard());
				}
				//技术领域/适用范围
				if(stand.getTechnicalField() == null) {
					map.put("technicalField","无");
				}else {
					map.put("technicalField",stand.getTechnicalField());
				}
				//标准状态
				if(stand.getStandardStatus() == null) {
					map.put("standardStatus","无");
				}else {
					map.put("standardStatus",stand.getStandardStatus());
					//1：现行；2：暂行；3：废止
				}
				//原文链接
				//map.put("originaltext","aaa");
				
				
				//6.创建Writer对象
				
				Writer out =new FileWriter(new File(htmlurl));
				
				//7.输出

				template.process(map,out);

				//8.关闭Writer对象

				out.close();
	}
	/**
	 * 法律的题录的静态化生成
	 * @param stand
	 * @param htmlurl
	 * @throws IOException
	 * @throws TemplateException
	 */
	public static void lawsState (L_LawsDo laws,String htmlurl) throws IOException, TemplateException {
		//1.创建配置类

				Configuration configuration=new Configuration(Configuration.getVersion());

				//2.设置模板所在的目录
				//我们可以将这点写死
				configuration.setDirectoryForTemplateLoading(new File("C:\\\\Users\\\\zqm\\\\Desktop"));

				//3.设置字符集

				configuration.setDefaultEncoding("utf-8");

				//4.加载模板"test.ftl"
				//这个也需要修改
				Template template = configuration.getTemplate("index.ftl");

				//5.创建数据模型

				Map map=new HashMap();
				//标准号
				/*map.put("name", "aa");
				map.put("message", "assss");
				map.put("StandardNo",stand.getStandardNo());*/
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				if(laws.getLawsId() == null) {
					map.put("lawsId","无");
				}else {
					map.put("lawsId",laws.getLawsId());
				}
				if(laws.getLawsNo() == null) {
					map.put("lawsNo","无");
				}else {
					map.put("lawsNo",laws.getLawsNo());
				}
				//标准名称
				if(laws.getLawsCnName() == null) {
					map.put("lawsCnName","无");
				}else {
					map.put("lawsCnName",laws.getLawsCnName());
				}
				//发布单位
				if(laws.getPubOrg() == null) {
					map.put("pubOrg","无");
				}else {
					map.put("pubOrg",laws.getPubOrg());
				}
				//发布日期
				if(laws.getPublishDate() == null) {
					map.put("publishDate","无");
				}else {
					map.put("publishDate",laws.getPublishDate());
				}
				//实施日期
				if(laws.getImplementationDate() == null) {
					map.put("implementationDate","无");
				}else {
					map.put("implementationDate",laws.getImplementationDate());
				}
				/**
				 * 效力等级
				 */
				if(laws.getEffectivenessLevel() == null) {
					map.put("effectivenessLevel","无");
				}else {
					map.put("effectivenessLevel",laws.getEffectivenessLevel());
					//1：现行；2：暂行；3：废止
				}
				//原文链接
				//map.put("originaltext","aaa");
				
				
				//6.创建Writer对象
				
				Writer out =new FileWriter(new File(htmlurl));
				
				//7.输出

				template.process(map,out);

				//8.关闭Writer对象

				out.close();
	}
	public static void lawsState (LawsVo laws,String htmlurl) throws IOException, TemplateException {
		//1.创建配置类

				Configuration configuration=new Configuration(Configuration.getVersion());

				//2.设置模板所在的目录
				//我们可以将这点写死
				configuration.setDirectoryForTemplateLoading(new File("C:\\\\Users\\\\zqm\\\\Desktop"));

				//3.设置字符集

				configuration.setDefaultEncoding("utf-8");

				//4.加载模板"test.ftl"
				//这个也需要修改
				Template template = configuration.getTemplate("index.ftl");

				//5.创建数据模型

				Map map=new HashMap();
				//标准号
				/*map.put("name", "aa");
				map.put("message", "assss");
				map.put("StandardNo",stand.getStandardNo());*/
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				if(laws.getLawsId() == null) {
					map.put("lawsId","无");
				}else {
					map.put("lawsId",laws.getLawsId());
				}
				if(laws.getLawsNo() == null) {
					map.put("lawsNo","无");
				}else {
					map.put("lawsNo",laws.getLawsNo());
				}
				//标准名称
				if(laws.getLawsCnName() == null) {
					map.put("lawsCnName","无");
				}else {
					map.put("lawsCnName",laws.getLawsCnName());
				}
				//发布单位
				if(laws.getPubOrg() == null) {
					map.put("pubOrg","无");
				}else {
					map.put("pubOrg",laws.getPubOrg());
				}
				//发布日期
				if(laws.getPublishDate() == null) {
					map.put("publishDate","无");
				}else {
					map.put("publishDate",laws.getPublishDate());
				}
				//实施日期
				if(laws.getImplementationDate() == null) {
					map.put("implementationDate","无");
				}else {
					map.put("implementationDate",laws.getImplementationDate());
				}
				/**
				 * 效力等级
				 */
				if(laws.getEffectivenessLevel() == null) {
					map.put("effectivenessLevel","无");
				}else {
					map.put("effectivenessLevel",laws.getEffectivenessLevel());
					//1：现行；2：暂行；3：废止
				}
				//原文链接
				//map.put("originaltext","aaa");
				
				
				//6.创建Writer对象
				
				Writer out =new FileWriter(new File(htmlurl));
				
				//7.输出

				template.process(map,out);

				//8.关闭Writer对象

				out.close();
	}
}
