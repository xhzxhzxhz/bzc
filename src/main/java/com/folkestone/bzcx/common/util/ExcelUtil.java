package com.folkestone.bzcx.common.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.hssf.util.Region;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import com.folkestone.bzcx.bean.bean_vo.dm.FileStatisticVo;

/**
 * 报表导出工具类
 * 
 * @author JYLiu
 * @巴黎的雨季 本工具是根据POI对Excel2003进行报表导出 本工具类可根据模板进行Excel的导出 并且可根据提供的模板Sheet页进行复制
 *        从而实现多个Sheet页的需求 使用本工具类时,如果需求是每个Sheet页中的数据都不一致,但是表格样式和模板都一样
 *        那么只需要在实际情况中根据 sql 来查询要添加的数据源 (只需更改数据源即可)
 * 
 */
public class ExcelUtil {

	/**
	 * 根据模板导出报表，可导出多个Sheet页
	 * 
	 * @param 导出的Excel文件名
	 * @param 模板路径
	 *            (全路径)
	 * @param 数据源
	 * @param 返回请求
	 * @param 生成的Sheet页的名称集合
	 * @param 数据源中Map集合的key值
	 *            (key值对应的value值顺序要列名顺序一致)
	 * @param 开始
	 *            循环写入数据 的行数(从第几行开始写入数据)
	 */
	public static void ExcelByModel(String ExcelName, String ModelURl, Map<String, List<FileStatisticVo>> dataSource,
			HttpServletResponse response, String[] sheetNames) throws Exception {

		// 设置导出Excel报表的导出形式
		response.setContentType("application/vnd.ms-excel");
		// 设置导出Excel报表的响应文件名
		String fileName = new String(ExcelName.getBytes("utf-8"), "ISO-8859-1");
		response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xls");
		// 创建一个输出流
		OutputStream fileOut = response.getOutputStream();
		// 读取模板文件路径
		File file = new File(ModelURl);
		FileInputStream fins = new FileInputStream(file);
		POIFSFileSystem fs = new POIFSFileSystem(fins);
		// 读取Excel模板
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFWorkbook Nwb = new HSSFWorkbook(fs);
		// 设置边框样式
		HSSFCellStyle style = wb.createCellStyle();
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		// 设置边框样式的颜色
		style.setBottomBorderColor(HSSFColor.BLACK.index);
		style.setLeftBorderColor(HSSFColor.BLACK.index);
		style.setRightBorderColor(HSSFColor.BLACK.index);
		style.setTopBorderColor(HSSFColor.BLACK.index);
		// 模板页
		HSSFSheet sheetModel = null;
		// 新建的Sheet页
		HSSFSheet newSheet = null;
		// 创建行
		HSSFRow row = null;
		// 创建列
		HSSFCell cell = null;

		// 循环建立Sheet页
		for (int i = 0; i < sheetNames.length; i++) {
			// 读取模板中模板Sheet页中的内容
			sheetModel = Nwb.getSheet(sheetNames[i]);
			// 设置新建Sheet的页名
			newSheet = wb.createSheet(sheetNames[i]);
			// 将模板中的内容复制到新建的Sheet页中
			copySheet(wb, Nwb, sheetModel, newSheet, sheetModel.getFirstRowNum(), sheetModel.getLastRowNum());
			// excel 模板页 新建的Sheet页 模板页的开始 模板页的结束
			int num = 3;
			if (i == 0) {
				dataSource.get("0");
				// 给新的excel 赋值
				// 获取到新建Sheet页中的第一行为其中的列赋值 getNumericCellValue()
				// 注意 合并的单元格也要按照合并前的格数来算
				row = newSheet.getRow(4);
				row.getCell(4).setCellValue(((FileStatisticVo) dataSource.get("TodayAcess").get(0)).getBegin());
				row.getCell(8).setCellValue(((FileStatisticVo) dataSource.get("CumulaAcess").get(0)).getBegin());
				row.getCell(12).setCellValue(((FileStatisticVo) dataSource.get("ActiveUserNumber").get(0)).getBegin());
				row.getCell(16).setCellValue(((FileStatisticVo) dataSource.get("TotalUserNumber").get(0)).getBegin());
				// 获取模板中的第二列，并赋值
				row = newSheet.getRow(10);
				// 注意 合并的单元格也要按照合并前的格数来算
				row.getCell(4).setCellValue(((FileStatisticVo) dataSource.get("TodayRetrieval").get(0)).getBegin());
				row.getCell(8).setCellValue(((FileStatisticVo) dataSource.get("TotalRetrieval").get(0)).getBegin());
				row.getCell(12).setCellValue(((FileStatisticVo) dataSource.get("StandardView").get(0)).getBegin());
				row.getCell(16).setCellValue(((FileStatisticVo) dataSource.get("StandardDownload").get(0)).getBegin());
			} else if (i == 8) {
				for (FileStatisticVo item : dataSource.get(String.valueOf(i))) {
					// 循环遍历,新建行 (3,0)
					row = newSheet.createRow(num);
					// 判断有多少列数据
					// 设置每列的数据 设置每列的样式 设置每列的值
					cell = row.createCell(0);
					cell.setCellStyle(style);
					cell.setCellValue(item.getBegin());
					cell = row.createCell(1);
					cell.setCellStyle(style);
					cell.setCellValue(item.getEnd());
					cell = row.createCell(2);
					cell.setCellStyle(style);
					cell.setCellValue(item.getSpare());
					num++;
				}
			} else {
				for (FileStatisticVo item : dataSource.get(String.valueOf(i))) {
					// 循环遍历,新建行 (3,0)
					row = newSheet.createRow(num);
					// 判断有多少列数据
					// 设置每列的数据 设置每列的样式 设置每列的值
					cell = row.createCell(1);
					cell.setCellStyle(style);
					cell.setCellValue(item.getBegin());
					cell = row.createCell(0);
					cell.setCellStyle(style);
					cell.setCellValue(item.getEnd());
					num++;
				}

			}

		}
		// break 加break可以测试只添加一个Sheet页的情况
		// 写入流
		wb.write(fileOut);
		fileOut.flush();
		// 关闭流
		fileOut.close();
	}

	/**
	 * 
	 * @param Excel工作簿对象
	 * @param 模板Sheet页
	 * @param 新建Sheet页
	 * @param 模板页的第一行
	 * @param 模板页的最后一行
	 */
	@SuppressWarnings("deprecation")
	private static void copySheet(HSSFWorkbook wb, HSSFWorkbook Nwb, HSSFSheet fromsheet, HSSFSheet newSheet,
			int firstrow, int lasttrow) {
		// 复制一个单元格样式到新建单元格
		if ((firstrow == -1) || (lasttrow == -1) || lasttrow < firstrow) {
			return;
		}
		// 复制合并的单元格
		Region region = null;
		for (int i = 0; i < fromsheet.getNumMergedRegions(); i++) {
			region = fromsheet.getMergedRegionAt(i);
			if ((region.getRowFrom() >= firstrow) && (region.getRowTo() <= lasttrow)) {
				newSheet.addMergedRegion(region);
			}
		}
		HSSFRow fromRow = null;
		HSSFRow newRow = null;
		HSSFCell newCell = null;
		HSSFCell fromCell = null;
		// 设置列宽
		for (int i = firstrow; i < lasttrow; i++) {
			fromRow = fromsheet.getRow(i);
			if (fromRow != null) {
				for (int j = fromRow.getLastCellNum(); j >= fromRow.getFirstCellNum(); j--) {
					int colnum = fromsheet.getColumnWidth((short) j);
					if (colnum > 100) {
						newSheet.setColumnWidth((short) j, (short) colnum);
					}
					if (colnum == 0) {
						newSheet.setColumnHidden((short) j, true);
					} else {
						newSheet.setColumnHidden((short) j, false);
					}
				}
				break;
			}
		}

		// 复制行并填充数据
		for (int i = 0; i < lasttrow + 1; i++) {
			fromRow = fromsheet.getRow(i);
			if (fromRow == null) {
				continue;
			}
			newRow = newSheet.createRow(i - firstrow);
			newRow.setHeight(fromRow.getHeight());
			for (int j = fromRow.getFirstCellNum(); j < fromRow.getPhysicalNumberOfCells(); j++) {
				fromCell = fromRow.getCell((short) j);
				if (fromCell == null) {
					continue;
				}
				newCell = newRow.createCell((short) j);
				HSSFCellStyle cStyle = wb.createCellStyle();
				cStyle.cloneStyleFrom(fromCell.getCellStyle());
				newCell.setCellStyle(cStyle);

				int cType = fromCell.getCellType();
				newCell.setCellType(cType);
				switch (cType) {
				case HSSFCell.CELL_TYPE_STRING:
					newCell.setCellValue(fromCell.getRichStringCellValue());
					break;
				case HSSFCell.CELL_TYPE_NUMERIC:
					newCell.setCellValue(fromCell.getNumericCellValue());
					break;
				case HSSFCell.CELL_TYPE_FORMULA:
					newCell.setCellValue(fromCell.getCellFormula());
					break;
				case HSSFCell.CELL_TYPE_BOOLEAN:
					newCell.setCellValue(fromCell.getBooleanCellValue());
					break;
				case HSSFCell.CELL_TYPE_ERROR:
					newCell.setCellValue(fromCell.getErrorCellValue());
					break;
				default:
					newCell.setCellValue(fromCell.getRichStringCellValue());
					break;
				}
			}
		}
	}

}