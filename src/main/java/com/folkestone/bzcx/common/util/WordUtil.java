package com.folkestone.bzcx.common.util;

import java.io.File;
import java.io.IOException;

import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.Dispatch;

public class WordUtil {
	private WordUtil()
	{
		
	}
	static final int wdDoNotSaveChanges = 0;// 不保存待定的更改。
	static final int wdFormatPDF = 17;// word转PDF 格式
	/**
	 * 将word转换成Pdf 
	 * @param filePath 带转换的word路径
	 * @param targetPath 存储转换成功的pdf的路径
	 * @return
	 * @throws IOException 
	 * @throws PDFSecurityException 
	 * @throws PDFException 
	 */
	public static synchronized void wordToPdf(String srcPath, String targetPath) throws  IOException {
		// 将word转为pdf
		File file = new File(targetPath);
		if(!file.exists())
		{
			System.out.println("Word转PDF开始启动...");
			long start = System.currentTimeMillis();
			ActiveXComponent app = null;
			try {
				app = new ActiveXComponent("Word.Application");
				app.setProperty("Visible", false);
				Dispatch docs = app.getProperty("Documents").toDispatch();
				System.out.println("打开文档：" + srcPath);
				Dispatch doc = Dispatch.call(docs, "Open", srcPath, false, true)
						.toDispatch();
				System.out.println("转换文档到PDF：" + targetPath);
				File tofile = new File(targetPath);
				if (tofile.exists()) {
					tofile.delete();
				}
				Dispatch.call(doc, "SaveAs", targetPath, wdFormatPDF);
				Dispatch.call(doc, "Close", false);
				long end = System.currentTimeMillis();
				System.out.println("转换完成，用时：" + (end - start) + "ms");
				
			} catch (Exception e) {
				System.out.println("Word转PDF出错：" + e.getMessage());
			} finally {
				if (app != null) {
					app.invoke("Quit", wdDoNotSaveChanges);
				}
				
			}
		}
	}
}
