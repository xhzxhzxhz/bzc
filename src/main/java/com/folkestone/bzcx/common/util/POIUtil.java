package com.folkestone.bzcx.common.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.folkestone.bzcx.bean.bean_do.dm.A_Architecture_NodeDo;
import com.folkestone.bzcx.bean.bean_do.dm.L_LawsDo;
import com.folkestone.bzcx.bean.bean_do.dm.S_StandardDo;
import com.folkestone.bzcx.bean.bean_vo.dm.ArchitectureNodeVo;
import com.folkestone.bzcx.bean.bean_vo.dm.LawsVo;
import com.folkestone.bzcx.bean.bean_vo.dm.StandardVo;

/**
 * Describe：通过poi读表格，写表格。
 * 
 * @author smallking
 *
 *         2017年11月2日
 */
public class POIUtil {

	/**
	 * 标准体系导出
	 * 
	 * @param excelType
	 *            是xls 还是xlsx
	 * @param rowsList
	 *            需要填充到表格里面的数据
	 * @return
	 * @throws IOException
	 */
	public static String exportArchitecture(String excelType, List<ArchitectureNodeVo> rowsList) throws IOException {
		Workbook wb = null;
		ArchitectureNodeVo a = new ArchitectureNodeVo();
		a.getArchitectureId();
		System.out.println("size----" + rowsList.size());
		OutputStream os = new FileOutputStream(ContantFinalUtil.EXPORT_STANDARD_EXCEL);
		try {
			if ("xls".equals(excelType)) {
				wb = new HSSFWorkbook();
			} else if ("xlsx".equals(excelType)) {
				wb = new XSSFWorkbook();
			} else {
				throw new Exception("当前文件不是excel文件");
			}
			Sheet sheet = wb.createSheet("sheet1");
			sheet.setDefaultColumnWidth(12); // 样式对象
			CellStyle style = wb.createCellStyle();
			style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);// 垂直
			style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 居中
			// 先创建表头
			// 通过反射的到列名
			Method[] methods = ArchitectureNodeVo.class.getMethods();
			Field[] fields = ArchitectureNodeVo.class.getDeclaredFields();
			// 再循环数据
			for (int i = 0; i < rowsList.size() + 1; i++) {
				// 数据对象实体
				ArchitectureNodeVo nodeVo;
				if (i != 0) {
					nodeVo = rowsList.get(i - 1);
				} else {
					nodeVo = rowsList.get(i);
				}
				Row row = sheet.createRow(i);
				int h = 0;
				for (Field field : fields) {
					field.setAccessible(true);
					String columName = ContantFinalUtil.ARCHITECTURE_FIELD_MAP.get(field.getName());
					if (columName != null) {
						Cell cell = row.createCell(h);
						row.setRowStyle(style);
						// 先创建表头
						if (i == 0) {
							cell.setCellValue(columName);
						} else {
							// 再循环数据
							Object value = null;
							for (int k = 0; k < methods.length; k++) {
								if (("get" + field.getName()).toLowerCase()
										.equals(methods[k].getName().toLowerCase())) {
									value = methods[k].invoke(nodeVo);
								}
							}
							if (value == null) {
								cell.setCellValue("");
							} else {
								cell.setCellValue(value + "");
							}
						}
						h++;
					}
				}
			}
			wb.write(os);
			return ContantFinalUtil.EXPORT_STANDARD_EXCEL;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			os.close();
		}
		return null;
	}

	/**
	 * 导出题录
	 * 
	 * @param excelType
	 *            xls或者是 xlsx
	 * @param rowsList
	 *            待写入的数据
	 * @return
	 * @throws IOException
	 */
	public static String exportStandard(String excelType, List<?> rowsList) throws IOException {
		Workbook wb = null;
		System.out.println(ContantFinalUtil.EXPORT_ARCHITECTURE_EXCEL);
		OutputStream os = new FileOutputStream(ContantFinalUtil.EXPORT_ARCHITECTURE_EXCEL);
		try {
			if ("xls".equals(excelType)) {
				wb = new HSSFWorkbook();
			} else if ("xlsx".equals(excelType)) {
				wb = new XSSFWorkbook();
			} else {
				throw new Exception("当前文件不是excel文件");
			}
			Sheet sheet = wb.createSheet("sheet1");
			sheet.setDefaultColumnWidth(12); // 样式对象
			CellStyle style = wb.createCellStyle();
			style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);// 垂直
			style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 居中
			// 通过反射的到列名和方法
			Method[] methods = StandardVo.class.getMethods();
			Field[] fields = StandardVo.class.getDeclaredFields();
			// 先生成表头
			Row headRow = sheet.createRow(0);
			int h = 0;
			for (Field field : fields) {
				field.setAccessible(true);
				String columName = ContantFinalUtil.STANDARD_FIELD_MAP.get(field.getName());
				if (columName != null) {
					Cell cell = headRow.createCell(h);
					headRow.setRowStyle(style);
					cell.setCellValue(columName);
					h++;
				}
			}
			for (int i = 0; i < rowsList.size(); i++) {
				// 数据对象实体
				StandardVo standardVo = (StandardVo) rowsList.get(i);
				Row row = sheet.createRow(i + 1);
				int h1 = 0;
				for (Field field : fields) {
					field.setAccessible(true);
					String columName = ContantFinalUtil.STANDARD_FIELD_MAP.get(field.getName());
					if (columName != null) {
						Cell cell = row.createCell(h1);
						row.setRowStyle(style);
						// 再循环数据
						Object value = null;
						for (int k = 0; k < methods.length; k++) {
							if (("get" + field.getName()).toLowerCase().equals(methods[k].getName().toLowerCase())) {
								value = methods[k].invoke(standardVo);
							}
						}
						if (value == null) {
							cell.setCellValue("");
						} else {
							cell.setCellValue(value + "");
						}
						h1++;
					}
				}
			}
			wb.write(os);
			return ContantFinalUtil.EXPORT_ARCHITECTURE_EXCEL;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			os.close();
		}
		return null;
	}

	/**
	 * 解析excel，返回题录对象集合
	 * 
	 * @param fileFullPath
	 * @return
	 */
	public static List<?> importStandard(String fileFullPath) {
		List<S_StandardDo> standardVoList = new ArrayList<S_StandardDo>();
		List<String> li = new ArrayList<String>();
		String str = "";
		try {
			File file = new File(fileFullPath);
			InputStream inputStream = new FileInputStream(file);
			// 将文件的输入流转换成Workbook
			Workbook wb = WorkbookFactory.create(inputStream);
			// 获得第一个工作表
			Sheet sheet = wb.getSheetAt(0);
			int rowNum = sheet.getLastRowNum() + 1;
			for (int i = 1; i < rowNum; i++) {
				S_StandardDo standardDo = new S_StandardDo();
				Row row = sheet.getRow(i);
				int cellNum = row.getLastCellNum();
				for (int j = 0; j <= 43; j++) {
					try {
						Cell cell = row.getCell(j);
						if (cell == null) {
							if (j == 0) {
								file.delete();
								str = "对不起,第" + (i + 1) + "行" + "标准号为空,导入失败";
								li.add(str);
								return li;
							}
							if (j == 1) {
								file.delete();
								str = "对不起,第" + (i + 1) + "行" + "标准中文名称为空,导入失败";
								li.add(str);
								return li;
							}
							if (j == 18) {
								file.delete();
								str = "对不起,第" + (i + 1) + "行" + "标准类型为空,导入失败";
								li.add(str);
								return li;
							}
							if (j == 41) {
								file.delete();
								str = "对不起,第" + (i + 1) + "行" + "体系代码为空,导入失败";
								li.add(str);
								return li;
							}
							continue;
						}
						cell.setCellType(Cell.CELL_TYPE_STRING);
						String cellValue = cell.getStringCellValue();
						if (cellValue == null || cellValue.equals("")) {
							cellValue = null;
						}
						// 第一列是标准号
						if (j == 0) {
							standardDo.setStandardNo(cellValue);
						}
						// 第二列是标准中文名称
						if (j == 1) {
							standardDo.setStandardCnName(cellValue);
							if (cellValue.length() > 0) {
								String substring = Pinyin.isChineseChar(cellValue);
								standardDo.setPinyin(Pinyin.getPinYinHeadChar(substring).toUpperCase());
							}
						}
						// 第三列是标准英文名
						if (j == 2) {
							standardDo.setStandardEnName(cellValue);
						}
						// 第四列是标准原文名称
						if (j == 3) {
							standardDo.setStandardFileName(cellValue);
						}
						// 第五列是发布单位
						if (j == 4) {
							standardDo.setPubOrg(cellValue);
						}
						// 第六列是采用关系
						if (j == 5) {
							standardDo.setAdoptRelation(cellValue);
						}
						// 第七列是代替标准
						if (j == 6) {
							standardDo.setReplaceStandard(cellValue);
						}
						// 第八列是被代替标准
						if (j == 7) {
							standardDo.setReplacedStandard(cellValue);
						}
						// 第九列是引用标准
						if (j == 8) {
							standardDo.setQuoteStandard(cellValue);
						}
						// 第十列是修改件
						if (j == 9) {
							standardDo.setModifyObject(cellValue);
						}
						// 第十一列是补充件
						if (j == 10) {
							standardDo.setSupplementaryObject(cellValue);
						}
						// 第十二列是发布日期
						if (j == 11) {
							if (cellValue.indexOf("-") >= 0) {
								standardDo.setPublishDate(
										DateFormatUtil.str2Date(StringUtil.trim(cellValue), "yyyy-MM-dd"));
							}
						}
						// 第十三列是实施日期
						if (j == 12) {
							if (cellValue.indexOf("-") >= 0) {
								standardDo.setImplementationDate(
										DateFormatUtil.str2Date(StringUtil.trim(cellValue), "yyyy-MM-dd"));
							}
						}
						// 第十四列是确认日期
						if (j == 13) {
							if (cellValue.indexOf("-") >= 0) {
								standardDo.setConfirmDate(
										DateFormatUtil.str2Date(StringUtil.trim(cellValue), "yyyy-MM-dd"));
							}
						}
						// 第十五列是截止日期
						if (j == 14) {
							if (cellValue.indexOf("-") >= 0) {
								standardDo
										.setEndDate(DateFormatUtil.str2Date(StringUtil.trim(cellValue), "yyyy-MM-dd"));
							}
						}
						// 第十六列是废止日期
						if (j == 15) {
							if (cellValue.indexOf("-") >= 0) {
								standardDo.setInvalidDate(
										DateFormatUtil.str2Date(StringUtil.trim(cellValue), "yyyy-MM-dd"));
							}
						}
						// 第十七归口单位
						if (j == 16) {
							standardDo.setBelongToUnit(cellValue);
						}
						// 第十八列是起草单位
						if (j == 17) {
							standardDo.setDraftUnit(cellValue);
						}
						// 第十九列是标准类型
						if (j == 18) {
							standardDo.setStandardType(cellValue);
						}
						// 第二十列是标准水平
						if (j == 19) {
							standardDo.setStandardLevel2(cellValue);
						}
						// 第二十一列是中文主题词
						if (j == 20) {
							standardDo.setThemeCnName(cellValue);
						}
						// 第二十二列是英文主题词
						if (j == 21) {
							standardDo.setThemeEnName(cellValue);
						}
						// 第二十三列中文自由词
						if (j == 22) {
							standardDo.setFreeCnName(cellValue);
						}
						// 第二十四列是语言
						if (j == 23) {
							cellValue = (ContantFinalUtil.STANDARD_FIELD_MAP.get(StringUtil.trim(cellValue)));
							if (cellValue != null) {
								standardDo.setLanguage(Short.valueOf(cellValue));
							} else {
								standardDo.setLanguage(null);
							}
						}
						// 第二十五列是国别
						if (j == 24) {
							standardDo.setCountry(cellValue);
						}
						// 第二十六列是国际标准分类法
						if (j == 25) {
							standardDo.setInternationClass(cellValue);
						}
						// 第二十七列是中国标准分类法
						if (j == 26) {
							standardDo.setInternationClass(cellValue);
						}
						// 第二十八列是标准级别
						if (j == 27) {
							cellValue = StringUtil.trim(cellValue);
							if (cellValue != null) {
								standardDo.setStandardLevel(Short.valueOf(cellValue));
							} else {
								standardDo.setStandardLevel(null);
							}
						}
						// 第二十九列是立项日期
						if (j == 28) {
							standardDo.setProjectStartDate(
									DateFormatUtil.str2Date(StringUtil.trim(cellValue), "yyyy-MM-dd"));
						}
						// 第三十列是技术领域
						if (j == 29) {
							standardDo.setTechnicalField(cellValue);
						}
						// 第三十一列是起草人员或者专家
						if (j == 30) {
							standardDo.setDraftStaff(cellValue);
						}
						// 第三十二列是联系方式
						if (j == 31) {
							standardDo.setContactWay(cellValue);
						}
						// 第三十三列是管辖权
						if (j == 32) {
							standardDo.setJurisdiction(cellValue);
						}
						// 第三十四列是译文
						if (j == 33) {
							standardDo.setTranslation(cellValue);
						}
						// 第三十五列是UDC号
						if (j == 34) {
							standardDo.setUdc(cellValue);
						}
						// 第三十六列是页数
						if (j == 35) {
							cellValue = StringUtil.trim(cellValue);
							if (cellValue != null) {
								standardDo.setPageNumber(Integer.valueOf(cellValue));
							} else {
								standardDo.setPageNumber(null);
							}
						}
						// 第三十七列是出版社
						if (j == 36) {
							standardDo.setPublisher(cellValue);
						}
						// 第三十八列是出版时间
						if (j == 37) {
							standardDo.setPubDate(DateFormatUtil.str2Date(StringUtil.trim(cellValue), "yyyy-MM-dd"));
						}
						// 第三十九列是书号
						if (j == 38) {
							standardDo.setIsbn(cellValue);
						}
						// 第四十列是摘要
						if (j == 39) {
							standardDo.setDigest(cellValue);
						}
						// 第四十一列是标准状态
						if (j == 40) {
							cellValue = ContantFinalUtil.STANDARD_FIELD_MAP.get(StringUtil.trim(cellValue));
							if (cellValue != null) {
								standardDo.setStandardStatus(Short.valueOf(cellValue));
							} else {
								standardDo.setStandardStatus(null);
							}
						}
						// 第四十二列是体系代码
						if (j == 41) {
							standardDo.setNodeCode(cellValue);
						}
						if (j == 42) {
							standardDo.setIcsCode(cellValue);
						}
						if (j == 43) {
							standardDo.setCompetentDepartment(cellValue);
						}
					} catch (Exception e) {
					}
				}
				standardVoList.add(standardDo);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return standardVoList;
	}

	/**
	 * 解析excel文件 返回节点对象集合 nodelist
	 * 
	 * @param filePath
	 * @return
	 * @throws IOException
	 * @throws InvalidFormatException
	 */
	public static List<A_Architecture_NodeDo> importNode(String filePath) throws InvalidFormatException, IOException {
		List<A_Architecture_NodeDo> nodeList = new ArrayList<A_Architecture_NodeDo>();
		File file = new File(filePath);
		InputStream inputStream = new FileInputStream(file);
		// 将文件的输入流转换成Workbook
		Workbook wb = WorkbookFactory.create(inputStream);
		// 获得第一个工作表
		Sheet sheet = wb.getSheetAt(0);
		int rowNum = sheet.getLastRowNum() + 1;
		for (int i = 1; i < rowNum; i++) {
			A_Architecture_NodeDo node = new A_Architecture_NodeDo();
			Row row = sheet.getRow(i);
			short cellNum = row.getLastCellNum();
			for (int j = 0; j < cellNum; j++) {
				Cell cell = row.getCell(j);
				cell.setCellType(Cell.CELL_TYPE_STRING);
				String cellValue = cell.getStringCellValue();
				if (cellValue == null || cellValue.equals("")) {
					cellValue = null;
				}
				if (j == 0) {
					node.setNodeCnName(cellValue);
				}
				if (j == 1) {
					node.setNodeCode(cellValue);
				}
			}
			nodeList.add(node);
		}
		return nodeList;
	}

	/**
	 * 解析excel，返回题录对象集合
	 * 
	 * @param fileFullPath
	 * @return
	 */
	public static List<?> importLaws(String fileFullPath) {
		List<L_LawsDo> LawsVoList = new ArrayList<L_LawsDo>();
		List<String> li = new ArrayList<String>();
		String str = "";
		try {
			File file = new File(fileFullPath);
			InputStream inputStream = new FileInputStream(file);
			// 将文件的输入流转换成Workbook
			Workbook wb = WorkbookFactory.create(inputStream);
			// 获得第一个工作表
			Sheet sheet = wb.getSheetAt(0);
			// 获取这个工作表中的总页码
			int rowNum = sheet.getLastRowNum() + 1;
			for (int i = 1; i < rowNum; i++) {
				L_LawsDo lawsDo = new L_LawsDo();
				//在我们存储的时候存储了单元格数次数据，而且里面的id是同一个id ，表示 我们需要在这里将我们的数据，遍历了n次，创建了
				Row row = sheet.getRow(i);
				// 获取每一行中的单元格的数量
				int cellNum = row.getLastCellNum();
				for (int j = 0; j < cellNum; j++) {
					try {
						Cell cell = row.getCell(j);
						if (cell == null) {
							if (j == 0) {
								file.delete();
								str = "对不起,第" + (i + 1) + "行" + "法律文号为空,导入失败";
								li.add(str);
								return li;
							}
							if (j == 1) {
								file.delete();
								str = "对不起,第" + (i + 1) + "行" + "法律中文名称为空,导入失败";
								li.add(str);
								return li;
							}
							if (j == 7) {
								file.delete();
								str = "对不起,第" + (i + 1) + "行" + "法律类型为空,导入失败";
								li.add(str);
								return li;
							}
							if (j == 16) {
								file.delete();
								str = "对不起,第" + (i + 1) + "行" + "分类编码为空,导入失败";
								li.add(str);
								return li;
							}
							continue;
						}
						cell.setCellType(Cell.CELL_TYPE_STRING);
						String cellValue = cell.getStringCellValue();
						if (cellValue == null || cellValue.equals("")) {
							cellValue = null;
						}
						// 第一列是法律文号号
						if (j == 0) {
							lawsDo.setLawsNo(cellValue);
						
						}
						// 第二列是法律中文名称
						if (j == 1) {
							lawsDo.setLawsCnName(cellValue);
							if (cellValue.length() > 0) {
								String substring = Pinyin.isChineseChar(cellValue);
								lawsDo.setPingYing(Pinyin.getPinYinHeadChar(substring).toUpperCase());
							}
							
						}
						// 第三列是标准英文名
						if (j == 2) {
							lawsDo.setLawsEnName(cellValue);
							
						}
						// 第四列是发布单位
						if (j == 3) {
							lawsDo.setPubOrg(cellValue);
							
						}
						// 第五列是发布日期
						if (j == 4) {
							if (cellValue.indexOf("-") >= 0) {
								lawsDo.setPublishDate(
										DateFormatUtil.str2Date(StringUtil.trim(cellValue), "yyyy-MM-dd"));
							}
						
						}
						// 第六列是实施日期
						if (j == 5) {
							if (cellValue.indexOf("-") >= 0) {
								lawsDo.setImplementationDate(
										DateFormatUtil.str2Date(StringUtil.trim(cellValue), "yyyy-MM-dd"));
							}
						
						}
						// 第七列是法律类型
						if (j == 6) {
							lawsDo.setLawsType(cellValue);
							
						}
						// 第八列是语言
						if (j == 7) {
							cellValue = (ContantFinalUtil.LAWS_FIELD_MAP.get(StringUtil.trim(cellValue)));
							if (cellValue != null) {
								lawsDo.setLanguage(Integer.valueOf(cellValue));
							} else {
								lawsDo.setLanguage(null);
							}
						
						}
						// 第九列是htmlUrl
						if (j == 8) {
							lawsDo.setHtmlUrl(cellValue);
							
						}
						// 第十列是法律效力
						if (j == 9) {
							lawsDo.setEffectivenessLevel(cellValue);
							
							
						}
						// 第十一列是title标题
						if (j == 10) {
							lawsDo.setTitle(cellValue);
						
						}
						// 第十二列是法律的水平
						if (j == 11) {
							if(cellValue!=null) {
								lawsDo.setLawsLevel(Integer.valueOf(cellValue));
								
							}

							

						}
						// 第十三列是译文
						if (j == 12) {
							lawsDo.setTranslation(cellValue);
							
						}
						// 第十四列是文件页数
						if (j == 13) {
							cellValue = StringUtil.trim(cellValue);
							if (cellValue != null) {
								lawsDo.setPageNumber(Integer.valueOf(cellValue));
							} else {
								lawsDo.setPageNumber(null);
							}
						
						}
						// 第十五列是摘要
						if (j == 14) {
							lawsDo.setDigest(cellValue);
						
						}
						// 第十六列是分类编码
						if (j == 15) {
							lawsDo.setIcsCode(cellValue);
						
						}
						// 第十七拼音
						if (j == 16) {
							lawsDo.setPingYing(cellValue);
							
						}
						// 第十八列是文章内容
						if (j == 17) {
							lawsDo.setContent(cellValue);
						
						}
						// 第十九列是法律文号去除特殊符号
						if (j == 18) {
							lawsDo.setLawsNoTrim(cellValue);
						
						}
						// 第二十列是法律的状态
						if (j == 19) {
							lawsDo.setLawsStatus(cellValue);
						
						}
						// 第二十一列是上传单位
						if (j == 20) {
							lawsDo.setUploadUnit(cellValue);
							
						}
						// 第二十一列是标准原文名称
						if (j == 21) {
							lawsDo.setLawsFileName(cellValue);
							
						}


						

					} catch (Exception e) {
						e.printStackTrace();
						
					}

				}
				LawsVoList.add(lawsDo);
			}

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return LawsVoList;
	}
	/**
	 * 导出题录 将我们的法律题录导出到
	 * 
	 * @param excelType
	 *            xls或者是 xlsx
	 * @param rowsList
	 *            待写入的数据
	 * @return
	 * @throws IOException
	 */
	public static String exportLaws(String excelType, List<?> rowsList) throws IOException {
		Workbook wb = null;
		System.out.println(ContantFinalUtil.EXPORT_ARCHITECTURE_EXCEL);
		OutputStream os = new FileOutputStream(ContantFinalUtil.EXPORT_ARCHITECTURE_EXCEL);
		try {
			if ("xls".equals(excelType)) {
				wb = new HSSFWorkbook();
			} else if ("xlsx".equals(excelType)) {
				wb = new XSSFWorkbook();
			} else {
				throw new Exception("当前文件不是excel文件");
			}
			Sheet sheet = wb.createSheet("sheet1");
			sheet.setDefaultColumnWidth(12); // 样式对象
			CellStyle style = wb.createCellStyle();
			style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);// 垂直
			style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 居中
			// 通过反射的到列名和方法
			Method[] methods = LawsVo.class.getMethods();
			Field[] fields = LawsVo.class.getDeclaredFields();
			// 先生成表头
			Row headRow = sheet.createRow(0);
			int h = 0;
			for (Field field : fields) {
				field.setAccessible(true);
				String columName = ContantFinalUtil.LAWS_FIELD_MAP.get(field.getName());
				if (columName != null) {
					Cell cell = headRow.createCell(h);
					headRow.setRowStyle(style);
					cell.setCellValue(columName);
					h++;
				}
			}
			for (int i = 0; i < rowsList.size(); i++) {
				// 数据对象实体
				LawsVo lawsVo = (LawsVo) rowsList.get(i);
				Row row = sheet.createRow(i + 1);
				int h1 = 0;
				for (Field field : fields) {
					field.setAccessible(true);
					String columName = ContantFinalUtil.LAWS_FIELD_MAP.get(field.getName());
					if (columName != null) {
						Cell cell = row.createCell(h1);
						row.setRowStyle(style);
						// 再循环数据
						Object value = null;
						for (int k = 0; k < methods.length; k++) {
							if (("get" + field.getName()).toLowerCase().equals(methods[k].getName().toLowerCase())) {
								value = methods[k].invoke(lawsVo);
							}
						}
						if (value == null) {
							cell.setCellValue("");
						} else {
							cell.setCellValue(value + "");
						}
						h1++;
					}
				}
			}
			wb.write(os);
			return ContantFinalUtil.EXPORT_ARCHITECTURE_EXCEL;
		} catch (Exception e) {
			e.printStackTrace();
			return "导出失败";
		} finally {
			os.close();
		}
		
	}
	
	

}