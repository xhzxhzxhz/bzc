package com.folkestone.bzcx.common.util;

import java.io.*;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;

/**
 * Describe：txt文件工具类，可将txt文件转成pdf，读取txt中的文字内容
 * 
 * @author smallking
 *
 *  2017年11月15日
 */
public class TxtUtil {
    private TxtUtil()
    {
    	
    }
    public static void txtToPdf(String filePath, String pdfPath) throws DocumentException, IOException {
        	//首先创建一个字体
            BaseFont bfChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
            Font FontChinese = new Font(bfChinese, 12, Font.NORMAL);
            String line = null;
            Document document;
            document = new Document(PageSize.A4, 50, 50, 50, 50);
            @SuppressWarnings("resource")
			BufferedReader in = new BufferedReader(new FileReader(filePath));
            PdfWriter.getInstance(document, new FileOutputStream(pdfPath));
            document.open();
            while ((line = in.readLine()) != null)
                document.add(new Paragraph(12, line, FontChinese));
            document.close();
    }
    
    /**
     * 读取txt文件的内容
     * @param filePath 想要读取的文件的绝对路径
     * @return 返回文件内容
     */
    public static String readTxt(String filePath){
        String fileContent = "";     
        try   
        {       
            File f = new File(filePath);      
            if(f.isFile()&&f.exists())  
            {       
                InputStreamReader read = new InputStreamReader(new FileInputStream(f),"utf-8");       
                BufferedReader reader = new BufferedReader(read);       
                String line;
                while ((line = reader.readLine()) != null)   
                {
                    fileContent += line;
                }
                read.close();      
            }
        } catch (Exception e)   
        {         
            e.printStackTrace();     
        }     
        return fileContent;   
    }  
} 