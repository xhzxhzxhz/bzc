package com.folkestone.bzcx.controller.admin.dm;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.pdfbox.exceptions.COSVisitorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;

import com.folkestone.bzcx.bean.bean_do.dm.File_LawsDO;
import com.folkestone.bzcx.bean.bean_do.dm.S_Std_FileDo;
import com.folkestone.bzcx.bean.bean_dto.common.EasyUISeparatPage;
import com.folkestone.bzcx.bean.bean_dto.common.Query;
import com.folkestone.bzcx.bean.bean_dto.common.Result;
import com.folkestone.bzcx.bean.bean_vo.dm.LawsVo;
import com.folkestone.bzcx.bean.bean_vo.dm.StandardVo;
import com.folkestone.bzcx.bean.bean_vo.dm.StdFileVo;
import com.folkestone.bzcx.bean.bean_vo.user.UserVo;
import com.folkestone.bzcx.common.listen.Progress;
import com.folkestone.bzcx.common.util.ContantFinalUtil;
import com.folkestone.bzcx.common.util.DateFormatUtil;
import com.folkestone.bzcx.common.util.FileUtil;
import com.folkestone.bzcx.common.util.Pdf2htmlEXUtil;
import com.folkestone.bzcx.common.util.PdfUtil;
import com.folkestone.bzcx.common.util.StringUtil;
import com.folkestone.bzcx.common.util.UUIDUtil;
import com.folkestone.bzcx.common.util.WordUtil;
import com.folkestone.bzcx.common.util.splitPDFUtil;
import com.folkestone.bzcx.controller.base.BaseController;
import com.folkestone.bzcx.service.dm.FileLawsService;
import com.folkestone.bzcx.service.dm.LawsService;
import com.folkestone.bzcx.service.dm.SolrService;
import com.folkestone.bzcx.service.dm.StandardFileService;
import com.folkestone.bzcx.service.dm.StandardService;
import com.folkestone.bzcx.service.user.OperLogService;
import com.itextpdf.text.pdf.PdfReader;

/**
 * Describe：处理标准原文的Controller
 * 
 * @author smallking
 *
 *         2017年11月4日
 */
@Controller
@RequestMapping(value = "/admin/stdfile")
public class AdminStandardFileController extends BaseController {

	@Autowired
	private StandardFileService standardFileService;
	@Autowired
	private SolrService solrService;
	@Autowired
	private OperLogService operLogService;
	@Autowired
	private StandardService standService;
	@Autowired
	private LawsService lawsService;
	@Autowired
	private FileLawsService fileLawsService;
	private int h = ContantFinalUtil.BASE_PATH.lastIndexOf("\\");
	// System.out.println("realPath:============"+ContantFinalUtil.BASE_PATH.toString());
	// System.out.println("h:=========="+h);
	private String realPath = ContantFinalUtil.BASE_PATH.substring(0, h);

	@ResponseBody
	@RequestMapping(value = "/copylist", method = { RequestMethod.POST, RequestMethod.GET })
	public void selectDemo(@RequestParam Map<String, Object> param, HttpServletRequest req)
			throws IOException, COSVisitorException {
		HttpSession session = req.getSession();
		// 碍眼也别注释掉
		session.setAttribute("a", "a");
		// 每个人只能看到自己部门上传的原文
		Query query = new Query(param);
		query.put("isback", "后台权限标记");
		List<StdFileVo> listStdFileQueryNoPage = standardFileService.listStdFileQueryNoPage(query);
		for (StdFileVo stdFileVo : listStdFileQueryNoPage) {
			if (null != stdFileVo.getPdfFile()) {
				File fil = new File("D:\\2\\1\\common\\cmaps" + stdFileVo.getPdfFile());
				cleanPdf pdf = new cleanPdf();
				if (fil.exists()) {
					System.out.println("D:\\2\\1\\common\\cmaps" + stdFileVo.getPdfFile());
					Boolean doIt = pdf.doIt("D:\\2\\1\\common\\cmaps" + stdFileVo.getPdfFile(),
							"D:\\2\\1\\common\\resources\\pdf\\" + stdFileVo.getStdFileName());
					if (doIt) {
						FileChannel input = new FileInputStream(fil).getChannel();
						FileChannel output = new FileOutputStream(
								new File("D:\\2\\1\\common\\resources\\pdf\\" + stdFileVo.getStdFileName()))
										.getChannel();
						output.transferFrom(input, 0, input.size());
						if (input != null) {
							input.close();
						}
						if (output != null) {
							output.close();
						}
					}
				}

			} else if (null != stdFileVo.getStdFilePath()) {

			}
		}
		System.out.println(listStdFileQueryNoPage.size());
	}

	@ResponseBody
	@RequestMapping(value = "/checkupload", method = { RequestMethod.POST })
	public Result checkstdFileUpload(String standardNo, HttpServletRequest req) {
		Result re = new Result(false);
		UserVo userVo = super.getUser(req);
		/*
		 * String noTrim =
		 * StringUtil.StrCha(standardNo.substring(0,standardNo.lastIndexOf(".")));
		 * StandardVo standardByNoTrim = standService.getStandardByNoTrim(noTrim);
		 * if(standardByNoTrim != null) {
		 * if(standardByNoTrim.getUploadUnit().equals(userVo.getDepartmentId()) ||
		 * userVo.getDepartmentParent().equals("0")) { System.out.println("本公司");
		 * re.setMsg("本公司"); re.setResult(true); }else { re.setCode(900);
		 * re.setMsg("不能绑定该题录"); } }else { re.setResult(true); re.setMsg("无题录"); }
		 */
		if (userVo.getDepartmentParent().equals("0")) {
			re.setResult(true);
		} else {
			re.setCode(900);
			re.setMsg("没有权限上传原文");
		}
		return re;
	}

	/**
	 * 存储标准原文
	 * 
	 * @param req
	 * @param MRequset
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/upload", method = { RequestMethod.POST })
	public Result stdFileUpload(HttpServletRequest req, MultipartRequest MRequset) {
		long start2 = System.currentTimeMillis();
		Result result = new Result();
		List<MultipartFile> files = MRequset.getFiles("stdFile");
		UserVo userVo = super.getUser(req);
		for (MultipartFile stdFile : files) {
			// 判断
			// 先去标准题录中去查询是否存在这个标准题录，当存在，就进行下面的操作，当不存在，就返回一个提示让添加题录后在上传原文
			String standardNo = "";
			if (stdFile.getOriginalFilename().indexOf("##") > 1) {
				standardNo = stdFile.getOriginalFilename().split("##")[0].replace("_", "/");
			} else {
				String str = stdFile.getOriginalFilename();
				int index = str.lastIndexOf(".");
				str = str.substring(0, index);
				standardNo = str.replace("_", "/");

			}
			String noTrim = StringUtil.StrCha(standardNo);
			// 获取得到我们的标准题录信息
			StandardVo standardVo = standService.getStandardByNoTrim(noTrim);

			if (standardVo != null) {// 表示题录中有这个的数据
				// 第一步 先将文件存储起来
				Object obj = FileUtil.readFilePDFW(stdFile);
				if (obj == null) {
					continue;
				}
				String icsCode = standardVo.getIcsCode();
				String publishDate = standardVo.getPublishDate();
				String Status = standardVo.getStandardStatus();
				String pubOrg = (String) standardVo.getPubOrg();

				@SuppressWarnings("unchecked")
				Map<String, String> fileInfo = (HashMap<String, String>) obj;
				String type = stdFile.getOriginalFilename()
						.substring(stdFile.getOriginalFilename().lastIndexOf(".") + 1);
				// 现在指定的标准原文和标准题录关联的规则如下
				S_Std_FileDo stdFileDo = new S_Std_FileDo();
				stdFileDo.setStandardNo(standardNo);
				stdFileDo.setFileType(type);
				stdFileDo.setStdFileName(stdFile.getOriginalFilename());
				stdFileDo.setStdFileSize(Integer.valueOf(stdFile.getSize() + ""));
				stdFileDo.setUploadUnit(userVo.getDepartmentId());
				Boolean bo = false;
				if (type.equals("pdf") || type.equals("PDF")) {
					stdFileDo.setPdfFile(fileInfo.get("filePath"));
					try {
						stdFileDo.setPageNumber(Integer.valueOf(fileInfo.get("pageNum")));
					} catch (Exception e) {
						stdFileDo.setPageNumber(0);
					}
				} else if (type.equals("doc") || type.equals("docx")) {
					stdFileDo.setStdFilePath(fileInfo.get("filePath"));
					bo = true;
				}
				stdFileDo.setUploadDate(new Date());
				// 将当前上传的文件添加到标准原文表中，然后我们就可以进行切割和读取原文的信息
				int row = standardFileService.insert(stdFileDo, bo);
				operLogService.insert(super.getOperLog(req, "1007", stdFileDo.getStdFileId(), null));
				req.getSession().setAttribute("stdfileIdPdf", stdFileDo.getStdFileId());
				try {

					// 得到这个原文的文件路径，然后将其按照页码切割名称是原文件名+页码，
					String filePath = fileInfo.get("filePath");
					File file = new File(filePath);
					String fileName = file.getName();
					// String[] types=fileName.split(".");
					// int index = fileName.lastIndexOf(".");
					// String fileType = types[types.length-1];
					// 判读这个文件是PDF还是其他的格式，然后将Word格式转换为PDF格式，
					if (fileName.endsWith("docx") || fileName.endsWith("doc")) { // 当文件的类型是这两个的时候，我们就可以将其转化为PDF
						String targetPath = file.getAbsolutePath();
						// int h = targetPath.lastIndexOf(".");
						// 遍历 将我们切割后的进行拼接
						String[] paths = targetPath.split("\\.");
						// 遍历 进行字符串的拼接
						StringBuffer bufferPath = null;
						for (int i = 0; i <= paths.length - 1; i++) {
							if (i == 0) {
								bufferPath.append(paths[0]);
							} else {
								bufferPath.append("." + paths[i]);
							}
						}
						// targetPath = bufferPath+"\\new";
						WordUtil.wordToPdf(file.getAbsolutePath(), bufferPath.toString());
						// 读取新的文件
						// 不能通过.切割 ， 有可能是错误
						String[] names = file.getName().split("\\.");
						// 还是拼接
						StringBuffer newName = null;
						for (int i = 0; i <= names.length - 1; i++) {
							if (i == 0) {
								newName.append(names[0]);
							} else {
								newName.append("." + names[i]);
							}

						}
						String name = newName + ".pdf";

						String newFilePath = targetPath + name;
						// 读取转换后的文件
						File newFile = new File(newFilePath);
						// 将这个文件进行切割

						PdfReader reader = new PdfReader(newFile.getAbsolutePath());
						int n = reader.getNumberOfPages();
						// System.out.println("路径："+fileList.get(i).getPath());
						// splitPDFUtil.splitPDFFile(fileList.get(i).getPath(),
						// "C:\\Users\\zqm\\Desktop\\pdf"+fileList.get(i).getName()+"-"+j, j, j+1);
						for (int j = 1; j < n; j++) {
							StringBuffer pathName = null;
							String[] path = targetPath.split("\\");
							for (int i = 0; i <= path.length - 1; i++) {
								if (i == 0) {
									pathName.append(path[i]);
								} else {
									pathName.append("\\" + path[i]);
								}

							}
							// 新文件的路径
							String pdfName = pathName + "\\child" + newFile.getName();
							System.out.println("路径：" + pdfName);

							String endName = pdfName + "+" + j + ".pdf";
							splitPDFUtil.splitPDFFile(newFile.getAbsolutePath(), endName, j, j);
							// partitionPdfFile("F:\\\\pdf\\\\pdf\\\\2.pdf","F:\\pdf\\pdf\\4.pdf", 1,1);
						}
					}
					if (fileName.endsWith("pdf")) {

						PdfReader reader = new PdfReader(realPath + file.getPath());
						int n = reader.getNumberOfPages();
						String targetPath = realPath + file.getPath();
						// 遍历所有的页面
						for (int j = 1; j <= n; j++) {
							// 这里好像加了两层文件夹，多了一层，要把这一层去掉
							// 在原先的基础上添加一层新的
							int index = (realPath + file.getPath()).lastIndexOf("\\");
							String pathName = (realPath + file.getPath()).substring(0, index);
							// 获取这个题录的id,然后让其成为我们的新的名称
							String pdfName = pathName + "\\child\\" + stdFileDo.getStdFileId();
							System.out.println("切割后的新的路径和名称：" + pdfName);
							// 将这个名称切割
							// 我们定义的最终切割后的名称是endName 就是在上传后的文件路劲的最后一层文件夹添加一层，文件名和曾是标准原文id+ 页码数
							String endName = pdfName + "+" + j + ".pdf";
							long start = System.currentTimeMillis();
							splitPDFUtil.splitPDFFile(realPath + file.getPath(), endName, j, j);
							long end = System.currentTimeMillis();
							System.out.println("切割文件消耗的总的时间是：" + (end - start) / 1000);
							// 生成HTML文件
							long start1 = System.currentTimeMillis();

							Pdf2htmlEXUtil.pdf2html("F:\\shengfeisiTool\\software\\pdf2htmlEX-v1.0\\pdf2htmlEX.exe",
									endName, pathName + "\\html", stdFileDo.getStdFileId() + "+" + j);
							
							/*
							 * PdfDocument pdf = new PdfDocument(); //读取PDF文件 pdf.loadFromFile(endName);
							 * //将其转化成HTML文件 pdf.saveToFile(
							 * pathName+"\\html\\"+stdFileDo.getStdFileId()+"+"+j+".html", FileFormat.HTML);
							 */
							long end1 = System.currentTimeMillis();
							System.out.println("生成一个HTML消耗的总的时间是：" + (end1 - start1) / 1000);
						}

					}

					ArrayList<File> fileList = new ArrayList<File>();
					int index = (realPath + file.getPath()).lastIndexOf("\\");
					String pathName = (realPath + file.getPath()).substring(0, index);
					String dirPath = pathName + "\\child";
					long start1 = System.currentTimeMillis();
					getFiles(fileList, dirPath);
					// System.out.println("realPath+smalFile.getpath():============================"+realPath+smallFile.getPath());
					for (int y = 0; y < fileList.size(); y++) {
						// 将新的文件名称中通过-去截取然后得到前一部分，然后在比对当比对成功后，表示就这个原文件下的切割的子文件，然后将其添加到solr中
						File smallFile = fileList.get(y);
						String na = smallFile.getName().split(".pdf")[0];
						/*
						 * //表示 都是子元素
						 * System.out.println("smallFile.getAbsolutePath():============================"
						 * +smallFile.getAbsolutePath());
						 * System.out.println("realPath+smalFile.getpath():============================"
						 * +realPath+smallFile.getPath());
						 */
						String newPath = smallFile.getAbsolutePath().toString();
						String newName = smallFile.getName();
						int f = newName.lastIndexOf("+");
						String nm = newName.substring(0, f);

						if (stdFileDo.getStdFileId().equals(nm)) {// 表示是在当前的页面中
							// 将我们的pdf解析出来然后获取到pdf文件的内容
							Map<String, String> readPDFContent = PdfUtil.readPDFContent(smallFile.getAbsolutePath());
							// Map<String, Object> readPDFContent =
							// PdfUtil.ocrReadDpf(smallFile.getAbsolutePath());
							// 而如果我们使用OCR识别，就必须将存储在新的文件中的数据进行读取，然后将其放到
							// 将当前传入的文章 添加到solr中
							// 当前文章的对象
							String content = readPDFContent.get("content");
							String idstr = smallFile.getName().split(".pdf")[0];
							String[] ids = idstr.split("\\+");
							String page = ids[ids.length - 1];
							// 获取标题
							String standardCnName = standardVo.getStandardCnName().toString();

							solrService.addDocumentToSolr(stdFileDo.getStdFileId() + "+" + page, file.getName(),
									content, icsCode, publishDate, Status, standardCnName, pubOrg);
							long end1 = System.currentTimeMillis();
							System.out.println("添加一次数据到solr库中消耗的总的时间是：" + (end1 - start1) / 1000);
						}
					}

					if (row == 1) {
						// 还要初始化solr
						// 这个中只是添加的原文信息，但是我们的切割后的PDF文件没有添加进去
						long start = System.currentTimeMillis();
						solrService.addDocumentToSolr(stdFileDo.getStdFileId(), stdFile.getOriginalFilename(),
								fileInfo.get("content"), icsCode, publishDate, Status,
								standardVo.getStandardCnName().toString(), standardVo.getPubOrg().toString());
						long end = System.currentTimeMillis();
						System.out.println("添加一篇文章到solr中消耗的总的时间是：" + (end - start) / 1000);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				result.setCode(200);
				result.setMsg("上传成功！");
			} else {// 表示标准题录没有这个题录
					// 判断法律题录中是否有这个的数据，当存在，我们就将其取出来，通过法律文号
				if (noTrim != null) {
					LawsVo lawsDo = lawsService.findByLawsNoTrim(noTrim);
					// 获取法律的分类和我们的发布日期
					String icsCode = null;
					Date date = null;
					String lawsStatus = null;
					String publishDate = null;
					if (lawsDo != null) {
						icsCode = lawsDo.getIcsCode();
						date = lawsDo.getPublishDate();
						lawsStatus = lawsDo.getLawsStatus();

						if (date != null) {
							publishDate = DateFormatUtil.date2Str(date);
						}
					}
					// 判断这个是否为空
					if (lawsDo != null) {
						// 第一步 先将文件存储起来
						Object obj = FileUtil.readFilePDFW(stdFile);
						if (obj == null) {
							continue;
						}

						@SuppressWarnings("unchecked")
						Map<String, String> fileInfo = (HashMap<String, String>) obj;
						String type = stdFile.getOriginalFilename()
								.substring(stdFile.getOriginalFilename().lastIndexOf(".") + 1);
						// 现在指定的标准原文和标准题录关联的规则如下
						S_Std_FileDo stdFileDo = new S_Std_FileDo();
						String stdFileId = UUIDUtil.getUUID("S_S_F");
						stdFileDo.setStdFileId(stdFileId);
						stdFileDo.setStandardNo(lawsDo.getLawsNo());
						stdFileDo.setFileType(type);
						stdFileDo.setStdFileName(stdFile.getOriginalFilename());
						stdFileDo.setStdFileSize(Integer.valueOf(stdFile.getSize() + ""));
						stdFileDo.setUploadUnit(userVo.getDepartmentId());
						Boolean bo = false;
						if (type.equals("pdf") || type.equals("PDF")) {
							stdFileDo.setPdfFile(fileInfo.get("filePath"));
							try {
								stdFileDo.setPageNumber(Integer.valueOf(fileInfo.get("pageNum")));
							} catch (Exception e) {
								stdFileDo.setPageNumber(0);
							}
						} else if (type.equals("doc") || type.equals("docx")) {
							stdFileDo.setStdFilePath(fileInfo.get("filePath"));
							bo = true;
						}
						stdFileDo.setUploadDate(new Date());
						// 添加到标准原文中
						int row = standardFileService.insertLaws(stdFileDo, bo);
						// 将我们添加进去的标准原文的id去出来和我们的法律题录的id进行关联 通过第三张表
						String lawsId = lawsDo.getLawsId();
						stdFileId = stdFileDo.getStdFileId();
						File_LawsDO fileLaws = new File_LawsDO();
						String id = UUIDUtil.getUUID("fileLaws");
						fileLaws.setId(id);
						fileLaws.setLawsId(lawsId);
						fileLaws.setStdFileId(stdFileId);
						// 添加到关联表中
						fileLawsService.insert(fileLaws);
						operLogService.insert(super.getOperLog(req, "1007", stdFileDo.getStdFileId(), null));
						req.getSession().setAttribute("stdfileIdPdf", stdFileDo.getStdFileId());
						try {

							// 得到这个原文的文件路径，然后将其按照页码切割名称是原文件名+页码，
							String filePath = fileInfo.get("filePath");
							File file = new File(filePath);
							String fileName = file.getName();
							// String[] types=fileName.split(".");
							// int index = fileName.lastIndexOf(".");
							// String fileType = types[types.length-1];
							// 判读这个文件是PDF还是其他的格式，然后将Word格式转换为PDF格式，
							if (fileName.endsWith("docx") || fileName.endsWith("doc")) { // 当文件的类型是这两个的时候，我们就可以将其转化为PDF
								String targetPath = realPath + file.getPath();
								// int h = targetPath.lastIndexOf(".");
								// 遍历 将我们切割后的进行拼接
								String[] paths = targetPath.split("\\.");
								// 遍历 进行字符串的拼接
								StringBuffer bufferPath = null;
								for (int i = 0; i <= paths.length - 1; i++) {
									bufferPath.append(paths[i]);

								}
								// targetPath = bufferPath+"\\new";
								WordUtil.wordToPdf(realPath + file.getPath(), bufferPath.toString());
								// 读取新的文件
								// 不能通过.切割 ， 有可能是错误
								String[] names = file.getName().split("\\.");
								// 还是拼接
								StringBuffer newName = null;
								for (int i = 0; i <= names.length - 1; i++) {
									newName.append(names[i]);
								}
								String name = newName + ".pdf";

								String newFilePath = targetPath + name;
								// 读取转换后的文件
								File newFile = new File(newFilePath);
								// 将这个文件进行切割

								PdfReader reader = new PdfReader(newFile.getAbsolutePath());
								int n = reader.getNumberOfPages();
								// System.out.println("路径："+fileList.get(i).getPath());
								// splitPDFUtil.splitPDFFile(fileList.get(i).getPath(),
								// "C:\\Users\\zqm\\Desktop\\pdf"+fileList.get(i).getName()+"-"+j, j, j+1);
								for (int j = 1; j <= n; j++) {
/*									StringBuffer pathName = null;
									String[] path = targetPath.split("\\");
									for (int i = 0; i <= path.length - 1; i++) {
										if (i == 0) {
											pathName.append(path[i]);
										} else {
											pathName.append("\\" + path[i]);
										}

									}
									// 新文件的路径
									String pdfName = pathName + "\\child" + newFile.getName();
									System.out.println("路径：" + pdfName);
									
									String endName = pdfName + "+" + j + ".pdf";*/
									int index = (realPath + file.getPath()).lastIndexOf("\\");
									String pathName = (realPath + file.getPath()).substring(0, index);
									// 获取这个题录的id,然后让其成为我们的新的名称
									String pdfName = pathName + "\\child\\" + stdFileDo.getStdFileId();
									System.out.println("切割后的新的路径和名称：" + pdfName);
									// 将这个名称切割
									// 我们定义的最终切割后的名称是endName 就是在上传后的文件路劲的最后一层文件夹添加一层，文件名和曾是标准原文id+ 页码数
									String endName = pdfName + "+" + j + ".pdf";
									long start = System.currentTimeMillis();
									splitPDFUtil.splitPDFFile(realPath + file.getPath(), endName, j, j);
									long end = System.currentTimeMillis();
									System.out.println("切割文件消耗的总的时间是：" + (end - start) / 1000);
									splitPDFUtil.splitPDFFile(newFile.getAbsolutePath(), endName, j, j);
									// partitionPdfFile("F:\\\\pdf\\\\pdf\\\\2.pdf","F:\\pdf\\pdf\\4.pdf", 1,1);

									Pdf2htmlEXUtil.pdf2html("F:\\shengfeisiTool\\software\\pdf2htmlEX-v1.0\\pdf2htmlEX.exe",
											endName, pathName + "\\html", stdFileDo.getStdFileId() + "+" + j);

								}
							}
							if (fileName.endsWith("pdf")) {

							/*	System.out.println(
										"file.getPath()：================================" + realPath + file.getPath());
								// ContantFinalUtil.BASE_PATH;
								System.out.println("ContantFinalUtil.BASE_PATH ============="
										+ ContantFinalUtil.BASE_PATH + file.getPath().toString());
								System.out.println(
										"file.getName()：================================" + file.getName().toString());*/
								PdfReader reader = new PdfReader(realPath + file.getPath());
								int n = reader.getNumberOfPages();
								String targetPath = realPath + file.getPath();
								// int i = targetPath.lastIndexOf(".");
								// System.out.println("路径："+fileList.get(i).getPath());
								// splitPDFUtil.splitPDFFile(fileList.get(i).getPath(),
								// "C:\\Users\\zqm\\Desktop\\pdf"+fileList.get(i).getName()+"-"+j, j, j+1);
								// 遍历所有的页面
								for (int j = 1; j <=n; j++) {
								
									/*int index = (realPath + file.getPath()).lastIndexOf("\\");
									String pathName = (realPath + file.getPath()).substring(0, index);
								
									// 获取这个题录的id,然后让其成为我们的新的名称
									String pdfName = pathName + "\\child\\" + stdFileDo.getStdFileId();
									System.out.println("切割后的新的路径和名称：" + pdfName);

									// 我们定义的最终切割后的名称是endName 就是在上传后的文件路劲的最后一层文件夹添加一层，文件名和曾是标准原文id+ 页码数
									String endName = pdfName + "+" + j + ".pdf";
									splitPDFUtil.splitPDFFile(realPath + file.getPath(), endName, j, j);
									// partitionPdfFile("F:\\\\pdf\\\\pdf\\\\2.pdf","F:\\pdf\\pdf\\4.pdf", 1,1);
									// System.out.println("F:\\pdf\\splitPDF"+fileList.get(i).getName()+"-"+j);*/	
									int index = (realPath + file.getPath()).lastIndexOf("\\");
									String pathName = (realPath + file.getPath()).substring(0, index);
									// 获取这个题录的id,然后让其成为我们的新的名称
									String pdfName = pathName + "\\child\\" + stdFileDo.getStdFileId();
									System.out.println("切割后的新的路径和名称：" + pdfName);
									// 将这个名称切割
									// 我们定义的最终切割后的名称是endName 就是在上传后的文件路劲的最后一层文件夹添加一层，文件名和曾是标准原文id+ 页码数
									String endName = pdfName + "+" + j + ".pdf";
									long start = System.currentTimeMillis();
									splitPDFUtil.splitPDFFile(realPath + file.getPath(), endName, j, j);
									long end = System.currentTimeMillis();
									System.out.println("切割文件消耗的总的时间是：" + (end - start) / 1000);
									splitPDFUtil.splitPDFFile(realPath + file.getPath(), endName, j, j);
									// partitionPdfFile("F:\\\\pdf\\\\pdf\\\\2.pdf","F:\\pdf\\pdf\\4.pdf", 1,1);

									Pdf2htmlEXUtil.pdf2html("F:\\shengfeisiTool\\software\\pdf2htmlEX-v1.0\\pdf2htmlEX.exe",
											endName, pathName + "\\html", stdFileDo.getStdFileId() + "+" + j);
									}

							}

							ArrayList<File> fileList = new ArrayList<File>();
							int index = (realPath + file.getPath()).lastIndexOf("\\");
							String pathName = (realPath + file.getPath()).substring(0, index);
							String dirPath = pathName + "\\child";

							getFiles(fileList, dirPath);
							for (int y = 0; y < fileList.size(); y++) {
								// 将新的文件名称中通过-去截取然后得到前一部分，然后在比对当比对成功后，表示就这个原文件下的切割的子文件，然后将其添加到solr中
								File smallFile = fileList.get(y);
								String na = smallFile.getName().split(".pdf")[0];
								// int f=file.getName().lastIndexOf(".");
								// String fName= file.getName().substring(0, f);
								// int s=smallFile.getName().lastIndexOf(".");
								// String sn= smallFile.getName().substring(0, s);
								String newPath = smallFile.getAbsolutePath().toString();
								String newName = smallFile.getName();
								int f = newName.lastIndexOf("+");
								String nm = newName.substring(0, f);
								if (stdFileDo.getStdFileId().equals(nm)) {// 表示是在当前的页面中
									System.out.println(
											"切割后的名称是？：++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++"
													+ na);
									Map<String, String> readPDFContent = PdfUtil
											.readPDFContent(smallFile.getAbsolutePath());

									String content = readPDFContent.get("content");

									String idstr = smallFile.getName().split(".pdf")[0];
									String[] ids = idstr.split("\\+");
									String page = ids[ids.length - 1];

									solrService.addDocumentToSolr(stdFileDo.getStdFileId() + "+" + page, file.getName(),
											content, icsCode, publishDate, lawsStatus, lawsDo.getLawsCnName(),
											lawsDo.getPubOrg().toString());
								}
							}
							if (row == 1) {
								// 还要初始化solr
								// 这个中只是添加的原文信息，但是我们的切割后的PDF文件没有添加进去
								solrService.addDocumentToSolr(stdFileDo.getStdFileId(), stdFile.getOriginalFilename(),
										fileInfo.get("content"), icsCode, publishDate, lawsStatus,
										lawsDo.getLawsCnName(), lawsDo.getPubOrg().toString());

							}
						} catch (Exception e) {
							e.printStackTrace();
						}
						result.setCode(200);
						result.setMsg("上传成功！");
					} else {
						result.setCode(500);
						result.setMsg("请先添加题录后，再上传原文");
					}
				}
			}
		}
		long end2 = System.currentTimeMillis();
		System.out.println("上传一篇原文消耗的总的时间是：" + (end2 - start2) / 1000);
		return result;
	}

	/**
	 * 根据Query里的条件 查询标准原文
	 * 
	 * @param param
	 * @return
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value = "/list", method = { RequestMethod.POST, RequestMethod.GET })
	public EasyUISeparatPage select(@RequestParam Map<String, Object> param, HttpServletRequest req)
			throws IOException {
		HttpSession session = req.getSession();
		// 碍眼也别注释掉
		session.setAttribute("a", "a");
		// 每个人只能看到自己部门上传的原文
		Query query = new Query(param);
		query.put("isback", "后台权限标记");
		EasyUISeparatPage data = standardFileService.listStdFileByQuery(query);
		return data;
	}

	/**
	 * 根据ID查询标准原文
	 * 
	 * @param stdFileId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/detail", method = { RequestMethod.POST, RequestMethod.GET })
	public Result detail(String stdFileId) {
		Result result = new Result(true);
		StdFileVo stdFileVo = standardFileService.getStdFileById(stdFileId);
		result.setData(stdFileVo);
		return result;
	}

	/**
	 * 修改标准原文pdf
	 * 
	 * @param param
	 * @return
	 * @throws IOException
	 */
	/*
	 * @ResponseBody
	 * 
	 * @RequestMapping(value = "/updateFile", method = {RequestMethod.POST,
	 * RequestMethod.GET}) public Result updatefile(HttpServletRequest req,
	 * MultipartRequest MRequset,String stdFileId) throws IOException { Result
	 * result = new Result(false); System.out.println(stdFileId);
	 * List<MultipartFile> files = MRequset.getFiles("stdFile"); UserVo userVo =
	 * super.getUser(req); for(MultipartFile stdFile : files){ // 第一步 先将文件存储起来
	 * Object obj = FileUtil.readFile(stdFile); if(obj == null){ continue; }
	 * 
	 * @SuppressWarnings("unchecked") Map<String, String> fileInfo =
	 * (HashMap<String, String>)obj; System.out.println(fileInfo.get("filePath"));
	 * System.out.println(fileInfo.get("pageNum")); S_Std_FileDo stdFileDo = new
	 * S_Std_FileDo(); stdFileDo.setPageNumber(new
	 * Integer(fileInfo.get("pageNum")));
	 * stdFileDo.setPdfFile(fileInfo.get("filePath"));
	 * stdFileDo.setStdFileId(stdFileId); stdFileDo.setLastOperDate(new Date());
	 * stdFileDo.setLastOperUser(userVo.getId());
	 * //stdFileDo.setLastOperType(Short.valueOf("pdf")); try { int row
	 * =standardFileService.updatefile(stdFileDo);
	 * 
	 * if(row == 1){ req.getSession().setAttribute("stdfileIdPdf",
	 * stdFileDo.getStdFileId()); result.setResult(true); // 还要初始化solr
	 * solrService.addDocumentToSolr(stdFileDo.getStdFileId(),
	 * stdFileDo.getStdFileName() + "", fileInfo.get("content"));
	 * operLogService.insert(super.getOperLog(req, "1007", stdFileDo.getStdFileId(),
	 * null)); } } catch (Exception e) { e.printStackTrace(); } } return result; }
	 */
	/**
	 * 修改标准原文
	 * 
	 * @param param
	 * @return
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value = "/update", method = { RequestMethod.POST, RequestMethod.GET })
	public Result update(S_Std_FileDo stdFileDo, HttpServletRequest req) throws IOException {
		Result result = new Result(false);
		UserVo userVo = super.getUser(req);
		try {
			int row = standardFileService.update(stdFileDo, userVo);
			if (row == 1) {
				result.setResult(true);
			} else if (row == -1) {
				result.setMsg("没有权限修改");
				result.setCode(900);
			} else if (row == -2) {
				result.setMsg("不能绑定该题录标准");
				result.setCode(900);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 删除标准原文
	 * 
	 * @param param
	 * @return
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value = "/delete", method = { RequestMethod.POST, RequestMethod.GET })
	public Result delete(HttpServletRequest req, String[] stdFileIds) throws IOException {
		Result result = new Result(false);
		UserVo userVo = super.getUser(req);
		try {
			for (String stdFileId : stdFileIds) {
				int row = standardFileService.deleteById(stdFileId, true, userVo);
				if (row == 1) {
					result.setResult(true);
					operLogService.insert(super.getOperLog(req, "1008", stdFileId, stdFileId));
				} else if (row == -1) {
					result.setCode(900);
					result.setMsg("没有权限删除");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 获得文件上传进度
	 * 
	 * @param req
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/progress", method = { RequestMethod.POST, RequestMethod.GET })
	public Result fileUpload(HttpServletRequest request, HttpServletResponse resp) throws IOException {
		Result result = new Result(true);
		Progress progress = (Progress) request.getSession().getAttribute("status");
		result.setData(progress);
		return result;
	}

	/**
	 * 获得文件上传进度
	 * 
	 * @param req
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/filesession", method = { RequestMethod.POST, RequestMethod.GET })
	public Result getfileSession(HttpServletRequest req) {
		Result result = new Result(true);
		String attribute = (String) req.getSession().getAttribute("stdfileIdPdf");
		result.setData(attribute);
		return result;
	}

	/**
	 * 获得文件上传进度
	 * 
	 * @param req
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/cleanfilesession", method = { RequestMethod.POST, RequestMethod.GET })
	public Result cleanfileSession(HttpServletRequest req) {
		Result result = new Result(true);
		req.getSession().removeAttribute("stdfileIdPdf");
		return result;
	}

	private static void getFiles(ArrayList<File> fileList, String path) {
		File[] allFiles = new File(path).listFiles();
		for (int i = 0; i < allFiles.length; i++) {
			File file = allFiles[i];

			if (file.isFile()) {
				fileList.add(file);
			} else {
				getFiles(fileList, file.getAbsolutePath());
			}
		}
	}
}
