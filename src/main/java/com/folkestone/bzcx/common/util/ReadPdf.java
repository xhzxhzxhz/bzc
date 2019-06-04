package com.folkestone.bzcx.common.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Date;

//import net.sourceforge.tess4j.ITesseract;
//import net.sourceforge.tess4j.Tesseract;

public class ReadPdf {
	/**
	 * OCR识别方法
	 */
//	public static void ocrReadDpf(String pdfPath,String newPath) {
//		long start =System.currentTimeMillis();
//		File imageFile = new File(pdfPath);
//		System.out.println(imageFile);
//		 FileOutputStream fos = null;
//		try {
//			fos = new FileOutputStream(newPath);
//		} catch (FileNotFoundException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
//	        ITesseract instance = new Tesseract();  // JNA Interface Mapping
//	        // ITesseract instance = new Tesseract1(); // JNA Direct Mapping
////	        D:\tesserOCR\Tesseract-OCR\tessdata
//	        instance.setDatapath("F:\\pdf\\pdf\\tesserOCR\\Tesseract-OCR\\tessdata"); // path to tessdata directory
//	        instance.setLanguage("chi_sim");
//
//	        try {
//	           // String result = instance.doOCR(imageFile);
//	        	String result =instance.doOCR(imageFile);
//	            System.out.println(result );
//	            fos.write(result.getBytes());
//	            Date date2= new Date();
//	    		System.out.println("结束时间====================Thu Apr 25 11:38:36 CST 2019==============================="+System.currentTimeMillis());
//	    		long end =System.currentTimeMillis();
//	    		System.out.println("消耗的总的时间是："+(end-start)/1000);
//	        } catch (Exception e) {
//	        	e.printStackTrace();
//	            System.err.println(e.getMessage());
//	        }
//	}
}
