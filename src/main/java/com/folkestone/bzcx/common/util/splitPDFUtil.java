package com.folkestone.bzcx.common.util;

import java.io.FileOutputStream;
import java.util.ArrayList;

import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfCopy;
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfReader;

public class splitPDFUtil {
	/** 
     * 截取pdfFile的第from页至第end页，组成一个新的文件名 
     * @param pdfFile  需要分割的PDF
     * @param savepath  新PDF
     * @param from  起始页
     * @param end  结束页
     */  
    public static void splitPDFFile(String respdfFile,  String savepath, int from, int end) {  
        Document document = null;  
        PdfCopy copy = null;          
        try {  
            PdfReader reader = new PdfReader(respdfFile);            
            int n = reader.getNumberOfPages();            
            if(end==0){  
                end = n;  
            }  
            ArrayList<String> savepaths = new ArrayList<String>();  
            String staticpath = respdfFile.substring(0, respdfFile.lastIndexOf("\\")+1);  
            //String savepath = staticpath+ newFile;  
            savepaths.add(savepath);  
            document = new Document(reader.getPageSize(1));  
            copy = new PdfCopy(document, new FileOutputStream(savepaths.get(0)));  
            document.open();  
            for(int j=from; j<=end; j++) {  
                document.newPage();   
                PdfImportedPage page = copy.getImportedPage(reader, j);  
                copy.addPage(page);  
            }  
            document.close();  

        } catch (Exception e) {  
            e.printStackTrace();  
     
        }  
    }  

}
