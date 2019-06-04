package com.folkestone.bzcx.common.util;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfReaderContentParser;
import com.itextpdf.text.pdf.parser.SimpleTextExtractionStrategy;
import com.itextpdf.text.pdf.parser.TextExtractionStrategy;

//import net.sourceforge.tess4j.ITesseract;
//import net.sourceforge.tess4j.Tesseract;

public class PdfUtil {
	
	private PdfUtil()
	{
		
	}
	/**
	 * 解析PDF 
	 * @param pdfPath
	 * @return
	 * @throws Exception
	 */
	public static Map<String, String> readPDFContent(String pdfPath) {
		Map<String, String> resultMap = new HashMap<String, String>();
		File file = new File(pdfPath);
		if(!file.exists()){
			return resultMap;
		}
		PdfReader reader = null;
		try {
			System.out.println("````````````"+pdfPath);
			reader = new PdfReader(pdfPath);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		PdfReaderContentParser parser = new PdfReaderContentParser(reader);
		StringBuffer buff = new StringBuffer();
		TextExtractionStrategy strategy;
		for (int i = 1; i <= reader.getNumberOfPages(); i++) {
			try {
				strategy = parser.processContent(i,
						new SimpleTextExtractionStrategy());
				buff.append(strategy.getResultantText());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		resultMap.put("content", buff.toString());
		resultMap.put("pageNum", reader.getNumberOfPages() + "");
		reader.close();
		return resultMap;
	}
	/**
	 * OCR识别读取文件
	 * @param pdfPath
	 * @param newPath
	 */
//	public static Map<String,Object> ocrReadDpf(String pdfPath) {
//		Map<String,Object> resultMap= new HashMap<String,Object>();
//		long start =System.currentTimeMillis();
//		File imageFile = new File(pdfPath);
//		System.out.println(imageFile);
//		
//		
//	        ITesseract instance = new Tesseract();  // JNA Interface Mapping
//	        // ITesseract instance = new Tesseract1(); // JNA Direct Mapping
////	        D:\tesserOCR\Tesseract-OCR\tessdata
//	        instance.setDatapath("F:\\pdf\\pdf\\tesserOCR\\Tesseract-OCR\\tessdata"); // path to tessdata directory
//	        instance.setLanguage("chi_sim");
//
//	        try {
//	           // String result = instance.doOCR(imageFile);
//	        	String result =instance.doOCR(imageFile);
//	           // System.out.println(result );
//	            resultMap.put("content", result);
//	            if(result!=null) {
//	            	resultMap.put("content", result);
//	            }else {
//	            	return null;
//	            }
//	            
//	    		System.out.println("结束时间====================Thu Apr 25 11:38:36 CST 2019==============================="+System.currentTimeMillis());
//	    		long end =System.currentTimeMillis();
//	    		System.out.println("消耗的总的时间是："+(end-start)/1000);
//	        } catch (Exception e) {
//	        	e.printStackTrace();
//	            System.err.println(e.getMessage());
//	        }
//	        return resultMap;
//	}
}
