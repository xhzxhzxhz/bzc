
package com.folkestone.bzcx.service.dm;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.folkestone.bzcx.bean.bean_do.dm.L_LawsDo;
import com.folkestone.bzcx.bean.bean_do.dm.S_StandardDo;
import com.folkestone.bzcx.bean.bean_do.dm.S_Std_FileDo;
import com.folkestone.bzcx.bean.bean_dto.common.EasyUISeparatPage;
import com.folkestone.bzcx.bean.bean_dto.common.Query;
import com.folkestone.bzcx.bean.bean_vo.dm.LawsVo;
import com.folkestone.bzcx.bean.bean_vo.dm.StandardVo;
import com.folkestone.bzcx.bean.bean_vo.dm.StdFileVo;
import com.folkestone.bzcx.bean.bean_vo.user.UserVo;
import com.folkestone.bzcx.common.util.ContantFinalUtil;
import com.folkestone.bzcx.common.util.StringUtil;
import com.folkestone.bzcx.controller.base.BaseController;
import com.folkestone.bzcx.mapper.dm.FileLawsMapper;
import com.folkestone.bzcx.mapper.dm.LawsMapper;
import com.folkestone.bzcx.mapper.dm.SStandardMapper;
import com.folkestone.bzcx.mapper.dm.SStdFileMapper;
import com.folkestone.bzcx.mapper.user.RDepartmentMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**
 * Describe：标准原文service
 * 
 * @author smallking
 *
 *         2017年11月4日
 */
@Service
public class StandardFileService extends BaseController {
	@Autowired
	private SStdFileMapper stdFileMapper;
	@Autowired
	private StandardService standardService;
	@Autowired
	private SolrService solrService;
	@Autowired
	private RDepartmentMapper deparmapper;
	@Autowired
	private SStandardMapper standMapper;
	@Autowired
	private LawsMapper lawsMapper;
	@Autowired 
	private FileLawsMapper fileLawsMapper;
	@Autowired
	private LawsService lawsService;
	/**
	 * 插入一个原文 现在原文的名称为:Q_SY00011##测试分割标准1.txt这种的。需要分割得到标准号 如果再上传一个标准名称为
	 * Q_SY00011##测试分割标准2.txt的原文。按照后上传的一个为准
	 * 
	 * @param stdFileDo
	 * @return 返回一个map 当不是新增的原文的话就会
	 *         去标准题录中去查找，返回题录信息，和修改的条数，当是新增的话，就需要去添加标准题录，然后将结果返回，而且添加的时候，只是添加了一个标准名称和标准号
	 */
	S_Std_FileDo stdFileDo;
	StdFileVo stdFileVo;

	public int insert(S_Std_FileDo stdFileDo, Boolean bo) {
		// 插入之前按照还需要再查查当前系统是否有这篇原文对应的题录
		String noTrim = StringUtil.StrCha(stdFileDo.getStandardNo());
		StandardVo standardVo = standardService.getStandardByNoTrim(noTrim);
		String std = "";
		if (standardVo == null) {
			std = stdFileDo.getStandardNo();
			String standardCnName = "";
			if (((String) stdFileDo.getStdFileName()).indexOf("##") > 1) {
				standardCnName = ((String) stdFileDo.getStdFileName()).split("##")[1];
			} else {
				standardCnName = (String) stdFileDo.getStdFileName();
			}
			// 如果当前的标准题录不存在 那么需要执行插入暂时只插入标准号 置空标准号
			try {
				S_StandardDo standardDo = new S_StandardDo();
				standardDo.setStandardNo(stdFileDo.getStandardNo());
				// standardDo.setStandardCnName(standardCnName);
				standardDo.setUploadUnit((String) stdFileDo.getUploadUnit());
				// TODO 插入标准体系
				standardService.insert(standardDo);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		} else {
			
			std = standardVo.getStandardNo();
		}
		// 插入之前 先看看这篇原文是不是已经存在, 看do 里面路径是否存在

		StdFileVo fileVo = stdFileMapper.getStdFileByFileName(std);
		int row = -1;
		// 不是空的 就更新 空的删除
		if (fileVo != null) {
			if (((String) fileVo.getStdFileName()).indexOf("##") > 1) {
				stdFileDo.setStdFileName(fileVo.getStdFileName());
			}

			stdFileDo.setStdFileId(fileVo.getStdFileId());
			stdFileDo.setStandardNo(std);
			row = stdFileMapper.update(stdFileDo);
		} else {
			stdFileDo.setStandardNo(std);
			row = stdFileMapper.insert(stdFileDo);
		}

		return row;
	}
	public int insertLaws(S_Std_FileDo stdFileDo, Boolean bo) {
		// 插入之前按照还需要再查查当前系统是否有这篇原文对应的题录
		String noTrim = StringUtil.StrCha(stdFileDo.getStandardNo());
		LawsVo standardVo = lawsMapper.findByLawsNoTrim(noTrim);
		String std = "";
		if (standardVo == null) {
			std = stdFileDo.getStandardNo();
			String lawsCnName = "";
			if (((String) stdFileDo.getStdFileName()).indexOf("##") > 1) {
				lawsCnName = ((String) stdFileDo.getStdFileName()).split("##")[1];
			} else {
				lawsCnName = (String) stdFileDo.getStdFileName();
			}
			// 如果当前的标准题录不存在 那么需要执行插入暂时只插入标准号 置空标准号
			try {
				L_LawsDo standardDo = new L_LawsDo();
				standardDo.setLawsNo(stdFileDo.getStandardNo());
				// standardDo.setStandardCnName(standardCnName);
				standardDo.setUploadUnit((String) stdFileDo.getUploadUnit());
				// TODO 插入标准体系
				lawsService.insert(standardDo);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		} else {
			
			std = standardVo.getLawsNo();
		}
		// 插入之前 先看看这篇原文是不是已经存在, 看do 里面路径是否存在

		StdFileVo fileVo = stdFileMapper.getStdFileByFileName(std);
		int row = -1;
		// 不是空的 就更新 空的删除
		if (fileVo != null) {
			if (((String) fileVo.getStdFileName()).indexOf("##") > 1) {
				stdFileDo.setStdFileName(fileVo.getStdFileName());
			}

			stdFileDo.setStdFileId(fileVo.getStdFileId());
			stdFileDo.setStandardNo(std);
			row = stdFileMapper.update(stdFileDo);
		} else {
			stdFileDo.setStandardNo(std);
			row = stdFileMapper.insert(stdFileDo);
		}

		return row;
	}
	/**
	 * 删除一篇原文
	 * 
	 * @param stdFileId
	 * @return
	 */
	public int deleteById(String stdFileId, boolean delet, UserVo user) {
		// 先查询原文删除时的单位
		StdFileVo stdFileVo = stdFileMapper.getStdFileById(stdFileId);
		int flag= fileLawsMapper.delete(stdFileId);
		// 查询所登录的用户是本公司或者总公司stdFileVo.getUploadUnit().equals(user.getDepartmentId()) ||
		if (user.getDepartmentParent().equals("0")) {
			int row = stdFileMapper.delteById(stdFileId);
			
			if (row == 1) {
				// 删除成功之后，在删除本地原文文件和solr索引
				if (delet && stdFileVo != null) {
					String path = (String) stdFileVo.getStdFilePath();
					if (!StringUtil.isEmpty(path)) {
						String filePath = ContantFinalUtil.BASE_PATH + path;
						File file = new File(filePath);
						file.delete();
					}
					if (!StringUtil.isEmpty(stdFileVo.getPdfFile())) {
						String pdfPath = ContantFinalUtil.BASE_PATH + stdFileVo.getPdfFile();
						String ph = pdfPath.replace("\\\\", "\\");
						File files = new File(ph);
						System.out.println("pdfPath:" + ph);
						files.delete();
						// 通过我们的标准原文id去将我们切割后的页码进行删除
						String newPath = "pdf\\child";
						// 这个是一个文件夹的地址
						String dirPath = ContantFinalUtil.BASE_PATH + newPath;
						// 读取这个文件夹
						ArrayList<File> fileList = new ArrayList<File>();

						getFiles(fileList, dirPath);
						for (File filed : fileList) {
							if (filed.getName().contains(stdFileId)) {
								filed.delete();
							}
						}
						String htmlPath = "pdf\\html";
						String dirPathHtml = ContantFinalUtil.BASE_PATH + htmlPath;
						// 读取这个文件夹
						ArrayList<File> fileList2 = new ArrayList<File>();
						getFiles(fileList2, dirPathHtml);
						for (File filed : fileList2) {
							if (filed.getName().contains(stdFileId)) {
								filed.delete();
								String name = filed.getName().split(".html")[0];
								solrService.deleteSolrIndex(name);

							}
						}
					}
				}
				solrService.deleteSolrIndex(stdFileId);
			}
			return row;
		}
		// 没有权限返回-1
		return -1;
	}

	/**
	 * 按照条件，查询标准原文集合
	 * 
	 * @param query
	 * @return
	 */
	public EasyUISeparatPage listStdFileByQuery(Query query) {
		// 设置分页参数
		PageHelper.startPage(query.getPage(), query.getRows());
		// 执行查询
		List<StdFileVo> listManageByQuery = stdFileMapper.listStdFileByQuery(query);
		// 得到结果
		PageInfo<StdFileVo> pageInfo = new PageInfo<StdFileVo>(listManageByQuery);
		EasyUISeparatPage data = new EasyUISeparatPage(pageInfo.getTotal(), pageInfo.getList());
		return data;
	}

	/**
	 * 查询标准原文 不分页
	 * 
	 * @param query
	 * @return
	 */
	public List<StdFileVo> listStdFileQueryNoPage(Query query) {
		return stdFileMapper.listStdFileByQuery(query);
	}

	/**
	 * 查询单个标准原文的详情
	 * 
	 * @param stdFileId
	 * @return
	 */
	public StdFileVo getStdFileById(String stdFileId) {
		return stdFileMapper.getStdFileById(stdFileId);
	}

	/**
	 * 修改标准原文，就是将我们数据库中的原文数据进行修改，同时修改我们的solr库中的数据，主要改的是solr库中的分类，状态，和发布日期等，
	 * 
	 * @param stdFileDo
	 * @return
	 */
	public int update(S_Std_FileDo stdFileDo, UserVo user) {
		// 先查询原文上传时的单位
		StdFileVo stdFileById = stdFileMapper.getStdFileById(stdFileDo.getStdFileId());
		// 查询所登录的用户是本公司或者总公司
		if (stdFileById.getUploadUnit().equals(user.getDepartmentId()) || user.getDepartmentParent().equals("0")) {
			// 修改之前，先查询stdFileDo中所所对应的题录是否已经有了对应的PDF或者Txt原文了
			// 如果所对应的题录之前已经上传了原文，则将以前题录的原文换成现在设置的原文
			String noTrim = StringUtil.StrCha(stdFileDo.getStandardNo());
			// 查询当前原文对应的题录
			StandardVo standardByNoTrim = standMapper.getStandardByNoTrim(noTrim);
			if (standardByNoTrim != null) {
				if (!standardByNoTrim.getUploadUnit().equals(user.getDepartmentId())
						|| !user.getDepartmentParent().equals("0")) {
					return -2;
				}
			} else {// 然后去法律法规中去查
				LawsVo lawsVo = lawsMapper.findLawsByStdFileId(stdFileDo.getStdFileId());
				if (!lawsVo.getUploadUnit().equals(user.getDepartmentId()) || !user.getDepartmentId().equals("0")) {
					return -2;
				}
			}
			Query query = new Query();
			query.put("standardNo", stdFileDo.getStandardNo());
			EasyUISeparatPage data = listStdFileByQuery(query);
			if (data.getRows() != null && data.getRows().size() >= 1) {
				// 将之前的原文所对应的题录设置为空
				for (Object obj : data.getRows()) {
					StdFileVo stdFileVo = (StdFileVo) obj;
					if (stdFileVo.getStdFileId().equals(stdFileDo.getStdFileId())) {
						continue;
					}
					String Stdfilename = (String) stdFileDo.getStdFileName();
					String fileType = Stdfilename.substring(Stdfilename.lastIndexOf("\\.") + 1);
					boolean flag1 = stdFileVo.getFileType().equals("doc") || stdFileVo.getFileType().equals("docx")
							|| stdFileVo.getFileType().equals("pdf");
					boolean flag2 = fileType.equals("doc") || fileType.equals("docx") || fileType.equals("pdf");
					if (stdFileVo.getFileType().equals(stdFileDo.getFileType()) || (flag1 && flag2)) {
						S_Std_FileDo changeDo = new S_Std_FileDo();
						changeDo.setStdFileId(stdFileVo.getStdFileId());
						changeDo.setStandardNo("");
						stdFileMapper.update(changeDo);
					}
				}
			}
			return stdFileMapper.update(stdFileDo);
		}
		// 如果没有权限修改的话返回-1
		return -1;
	}

	/**
	 * 通过标准号去查询标准原文
	 * 
	 * @param standardNo
	 * @return
	 */
	public StdFileVo findStandFileByStdNo(String standardNo) {
		// TODO Auto-generated method stub
		return stdFileMapper.findStdFileByStdNo(standardNo);
	}

	/**
	 * 通过法律法规id去查询标准原文
	 * 
	 * @param lawsId
	 */
	public StdFileVo findStdFileByLawsId(String lawsId) {
		// TODO Auto-generated method stub
		return stdFileMapper.findStdFileByLawsNo(lawsId);
	}

	/**
	 * 通过标准原文id去查询我们的法律题录
	 * 
	 * @param stdFileId
	 * @return
	 */
	public LawsVo findLawsByStdFileId(String stdFileId) {
		// TODO Auto-generated method stub
		return lawsMapper.findLawsByStdFileId(stdFileId);
	}

	/**
	 * 通过id去修改标准原文的次数
	 * 
	 * @param stdFileDo2
	 */
	public int updateById(S_Std_FileDo stdFileDo2) {
		// TODO Auto-generated method stub
		return stdFileMapper.update(stdFileDo2);
	}

	/**
	 * 获取浏览次数最多的文章
	 * 
	 * @return
	 */
	public List<StdFileVo> recommend() {
		// TODO Auto-generated method stub
		return stdFileMapper.recommend();
	}

	/**
	 * 读取文件夹
	 * 
	 * @param fileList
	 * @param path
	 */
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

	public StdFileVo findById(String stdFileId) {
		// TODO Auto-generated method stub
		
		return stdFileMapper.findById(stdFileId);
	}
}
