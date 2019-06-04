package com.folkestone.bzcx.common.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.POIXMLDocument;
import org.apache.poi.POIXMLTextExtractor;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.springframework.web.multipart.MultipartFile;
public class FileUtil {
	/***
	 * 保存文件到服务器磁盘上
	 * @param mFile
	 * @return
	 */
	public static synchronized Map<String, String> saveFile(MultipartFile mFile){
		try {
			Map<String, String> fileInfo = new HashMap<String, String>();
			String originFIleName = mFile.getOriginalFilename().toLowerCase();
			String fileName = System.currentTimeMillis() + originFIleName.substring(originFIleName.lastIndexOf("."));
			File file = new File(ContantFinalUtil.BASE_PATH + fileName);
			if(!file.exists())
			{
				file.mkdirs();
			}
			// 先把file存起来 
			mFile.transferTo(file);
			fileInfo.put("filePath", file.getAbsolutePath());
			fileInfo.put("fileName", fileName);
			return fileInfo;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static 	Map<String, String>  readFile(MultipartFile mFile){
		Map<String, String> fileInfo = saveFile(mFile);
		String filePath = fileInfo.get("filePath");
		String fileName = fileInfo.get("fileName");
		System.out.println(mFile.getSize());
		String suffix = filePath.substring(filePath.lastIndexOf(".") + 1);
		
	 	String pdfPath = filePath.substring(0, filePath.lastIndexOf(".")) + ".pdf";
		try {
				if(suffix.equalsIgnoreCase("doc") || suffix.equalsIgnoreCase("docx")){
					// word转PDF 方便预览
					WordUtil.wordToPdf(filePath, pdfPath);
				}
				if(suffix.equalsIgnoreCase("txt")){
					// 读取txt里面的内容 添加solr索引的时候需要用到
					TxtUtil.txtToPdf(filePath, pdfPath);
					String content = TxtUtil.readTxt(filePath);
					fileInfo.put("content", content);
					fileInfo.put("pageNum", "1");
				}
				if(suffix.equalsIgnoreCase("pdf")){
					pdfPath = filePath;
				}
				// 读取pdf里面的信息和pdf的页数
				fileInfo.putAll(PdfUtil.readPDFContent(pdfPath));
			} catch (Exception e) {
				e.printStackTrace();
			}
			fileInfo.put("filePath", filePath + "&" + fileName);
			return fileInfo;	
	}
	
	public static 	Map<String, String>  readFilePDFW(MultipartFile mFile){
		String fileName = mFile.getOriginalFilename();
			int index = fileName.lastIndexOf(".");  
			String type  = fileName.substring(index+1,fileName.length());
		Map<String, String> fileInfo = saveFileMkdirs(mFile,type,true);
		String filePath = fileInfo.get("filePath");
		 String content="";
		try {
				if(type.equalsIgnoreCase("txt")){
					 content = TxtUtil.readTxt(filePath);
					fileInfo.put("pageNum", "1");
				}
				if(type.equalsIgnoreCase("pdf")){
					// 读取pdf里面的信息和pdf的页数
					String pageNum ="";
					try {
						Map<String, String> readPDFContent = PdfUtil.readPDFContent(filePath);
						pageNum = readPDFContent.get("pageNum");
						content = readPDFContent.get("content");
					} catch (Exception e) {
						pageNum="";
						System.out.println("失败的PDF"+fileName);
					}
					//fileInfo.
					fileInfo.put("pageNum",pageNum);
				}
	            if (type.endsWith("docx") || type.endsWith("doc")) {  
	            	try {
	                OPCPackage opcPackage = POIXMLDocument.openPackage(filePath);  
	                POIXMLTextExtractor extractor = new XWPFWordExtractor(opcPackage);  
	                content = extractor.getText(); 
	            	} catch (Exception e) {
	    				InputStream is = new FileInputStream(new File(filePath));  
			            WordExtractor ex = new WordExtractor(is);  
			            content = ex.getText(); 
	    			}
	              
	            } 
			
			} catch (Exception e) {
				e.printStackTrace();
			}
		   content= replaceLongStr(content, "\n", " ");
		   content= replaceLongStr(content, "\t", " ");
		   content= replaceLongStr(content, "     ", " ");
		   	fileInfo.put("filePath", filePath.replace(ContantFinalUtil.BASE_PATH, "\\"));
			 fileInfo.put("content", content);
			return fileInfo;	
	}
	/***
	 * 保存文件到服务器磁盘上  份文件夹
	 * @param mFile
	 * @return
	 */
	public static synchronized Map<String, String> saveFileMkdirs(MultipartFile mFile,String mkdirs){
		try {
			Map<String, String> fileInfo = new HashMap<String, String>();
			String originFIleName = mFile.getOriginalFilename().toLowerCase();
			String fileName = System.currentTimeMillis() + originFIleName.substring(originFIleName.lastIndexOf("."));
			File file = new File(ContantFinalUtil.BASE_PATH +mkdirs+"\\"+fileName);
			if(!file.exists())
			{
				file.mkdirs();
			}
			// 先把file存起来 
			mFile.transferTo(file);
			fileInfo.put("fileName", fileName);
			fileInfo.put("filePath", file.getAbsolutePath());
			return fileInfo;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/***
	 * 保存文件到服务器磁盘上  份文件夹
	 * @param mFile
	 * @return
	 */
	public static synchronized Map<String, String> saveFileMkdirs(MultipartFile mFile,String mkdirs,Boolean oldname){
		try {
			Map<String, String> fileInfo = new HashMap<String, String>();
			String originFIleName = mFile.getOriginalFilename().toLowerCase();
			String fileName = "";
			if(oldname) {
				fileName = PathUntil.cutPathindexof(originFIleName, ".", true).toUpperCase()+"."+mkdirs;
			}else {
				fileName = System.currentTimeMillis() + originFIleName.substring(originFIleName.lastIndexOf("."));
			}
			File file = new File(ContantFinalUtil.BASE_PATH +mkdirs+"\\"+fileName);
			if(!file.exists())
			{
				file.mkdirs();
			}
			// 先把file存起来 
			mFile.transferTo(file);
			fileInfo.put("fileName", fileName);
			fileInfo.put("filePath", file.getAbsolutePath());
			return fileInfo;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	public static String replaceLongStr(String str, String fromStr, String toStr) {
		StringBuffer result = new StringBuffer();
		if (str != null && !str.equals("")) {
			while (str.indexOf(fromStr) >= 0) {
				result.append(str.substring(0, str.indexOf(fromStr)));
				result.append(toStr);
				str = str.substring(str.indexOf(fromStr) + fromStr.length(), str.length());
			}
			result.append(str);
		}
		return result.toString();
	}

	
}
