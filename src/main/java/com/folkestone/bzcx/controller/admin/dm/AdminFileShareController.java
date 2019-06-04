package com.folkestone.bzcx.controller.admin.dm;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.folkestone.bzcx.bean.bean_do.dm.F_File_ShareDo;
import com.folkestone.bzcx.bean.bean_dto.common.EasyUISeparatPage;
import com.folkestone.bzcx.bean.bean_dto.common.Query;
import com.folkestone.bzcx.bean.bean_dto.common.Result;
import com.folkestone.bzcx.bean.bean_vo.dm.FileShareVo;
import com.folkestone.bzcx.bean.bean_vo.user.UserVo;
import com.folkestone.bzcx.common.listen.Progress;
import com.folkestone.bzcx.common.util.ContantFinalUtil;
import com.folkestone.bzcx.common.util.FileUtil;
import com.folkestone.bzcx.controller.base.BaseController;
import com.folkestone.bzcx.service.dm.FileShareService;
import com.folkestone.bzcx.service.user.OperLogService;
import com.folkestone.bzcx.service.user.UserService;

/**
 * 
 * 资源共享
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/admin/file")
public class AdminFileShareController extends BaseController{
	
	@Autowired
	private FileShareService fileshare;
	
	@Autowired
	private OperLogService operLogService;
	
	@Autowired
	private UserService user;
	
	@ResponseBody
	@RequestMapping(value ="/delefile", method = {RequestMethod.POST})
	public Result deleteFile(@RequestParam("fileId") String[] ids){
		Result result = new Result(true);
		int i = fileshare.deleteByIds(ids);
		if(i >= 1){
			result.setResult(true);
		}
		return result;
	}	
	@ResponseBody
	@RequestMapping(value ="/detail", method = {RequestMethod.POST})
	public Result getfile(@RequestParam("fileId") String ids){
		Result result = new Result(true);
		FileShareVo infoByid = fileshare.getInfoByid(ids);
		if(infoByid != null){
			result.setData(infoByid);
			result.setResult(true);
		}
		return result;
	}
	
	/**
	 * 文件上传
	 */
	@ResponseBody
	@RequestMapping(value="/upload",method = { RequestMethod.POST })
	public Result upload(@RequestParam(value="fileField") MultipartFile multi,F_File_ShareDo filedo,
			HttpServletRequest req){
		Result result = new Result(false);
		String type = "";
		try {
			UserVo user = super.getUser(req);
			F_File_ShareDo file = new F_File_ShareDo();
			if((multi.getOriginalFilename().indexOf(".")) > 0){
				type = multi.getOriginalFilename().substring(multi.getOriginalFilename().lastIndexOf(".")+1, multi.getOriginalFilename().length());
			}else{
				type = multi.getOriginalFilename();
			}
			file.setFileType(type);
			Map<String, String> fileInfo = new HashMap<String, String>();
			if(type.equalsIgnoreCase("txt") || type.equalsIgnoreCase("doc") || type.equalsIgnoreCase("docx") || type.equalsIgnoreCase("pdf")){
				fileInfo = FileUtil.readFile(multi);
				if(fileInfo == null){
					return result;
				}
				String string = fileInfo.get("filePath");
				String st[] = string.split("&");
				file.setFilePath(st[0]);
				if(filedo.getFileName() != null){
					file.setFileName(filedo.getFileName());
				}else{
					file.setFileName(st[1]);
				}
			}else{
				fileInfo = FileUtil.saveFile(multi);
				if(fileInfo == null){
					return result;
				}
				String string = fileInfo.get("filePath");
				file.setFilePath(string);
				if(filedo.getFileName() != null){
					file.setFileName(filedo.getFileName());
				}else{
					file.setFileName(fileInfo.get("fileName"));
				}
			}
				file.setComment(filedo.getComment());
				file.setFileSize(Integer.valueOf(multi.getSize()+""));
				file.setUploadDate(new Date());
				file.setUploadUser(user.getId());
				file.setStatus(Short.valueOf("1"));
				int row = fileshare.addfileInfo(file);
				if (row != 0) {
					result.setResult(true);
					operLogService.insert(super.getOperLog(req, "1019", "文件上传" ,file.getFileId()));
				}else{
					result.setResult(false);
				}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	/**
	 * 获得文件上传进度
	 * @param req
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/progress", method = {RequestMethod.POST, RequestMethod.GET})
	public Result fileUpload(HttpServletRequest request,HttpServletResponse resp) throws IOException {
		Result result = new Result(true);
		Progress  progress = (Progress)request.getSession().getAttribute("status");
		result.setData(progress);
		return result;
	}
	
	/**
	 * 
	 * 文件下载
	 * @param session
	 * @return
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value = "/downlod",method = { RequestMethod.GET,RequestMethod.POST })
    public Object testResponseEntity(@RequestParam("id") String id,HttpServletRequest req) throws IOException{
    	Result result = new Result(false);
		Query query = new Query();
		query.put("architectureId", id);
		//获得用户登录的信息
		UserVo attribute = (UserVo) req.getSession().getAttribute("loginUser");
		FileShareVo infoByid = fileshare.getInfoByid(id);
		if(infoByid != null && infoByid.getFilePath() != null) {
			//ContantFinalUtil.BASE_PATH
			String path = infoByid.getFilePath().toString().split("\\\\")[2];
			System.out.println(ContantFinalUtil.BASE_PATH + path);
			File file = new File(ContantFinalUtil.BASE_PATH + path);
			System.out.println(file.exists());
			if(!file.exists()) {
				result.setCode(10004);
				result.setMsg("文件不存在，请重新上传");
				return result;
			}
		}
		F_File_ShareDo fe = new F_File_ShareDo();
		fe.setLastOperDate(new Date());
		fe.setLastOperUser(attribute.getId());
		fe.setFileId(id);
		int changeInfo = fileshare.changeInfo(fe);
		if(changeInfo == 1){
			result.setCode(200);
			result.setResult(true);
		}
		try {
			String filePath = infoByid.getFilePath().toString().split("\\\\")[2];
			operLogService.insert(super.getOperLog(req, "1014", "文件下载" ,id));
			return super.download(filePath, infoByid.getFileName().toString()+"."+infoByid.getFileType());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
    }
    
    
	/**
	 * 通过id，文件名，文件描述，状态查询出列表
	 * @param param
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/list",method = { RequestMethod.POST })
	public EasyUISeparatPage getFileInfo(@RequestParam Map<String, Object> param){
		Query query = new Query(param);
		EasyUISeparatPage data = fileshare.getInfo(query);
		for (Object obj : data.getRows()) {
			FileShareVo fileshare = (FileShareVo) obj;
			UserVo userUpload = user.getUserById(fileshare.getUploadUser());
			fileshare.setUploadUser(userUpload.getAccount());
			if(fileshare.getAuditUser() != null){
				UserVo userAudit = user.getUserById(fileshare.getAuditUser());
				fileshare.setAuditUser(userAudit.getAccount());
			}
			if (fileshare.getLastOperUser() != null) {
				UserVo userLast = user.getUserById(fileshare.getLastOperUser());
				fileshare.setLastOperUser(userLast.getAccount());
			}
		}
		return data;
	}
	
	/**
	 * 
	 * 共享资源修改
	 * @param filesharedo
	 * @return
	 */
	 @ResponseBody
	 @RequestMapping(value = "/update",method = { RequestMethod.POST,RequestMethod.GET})
	public Result changeInfo(F_File_ShareDo filesharedo,String fileId,HttpServletRequest req){
		Result result = new Result(false);
		try {
			System.out.println(fileId);
			if(fileId != null) {
				filesharedo.setFileId(fileId);
			}
			int row = fileshare.changeInfo(filesharedo);
			if (row == 1) {
				result.setResult(true);
				operLogService.insert(super.getOperLog(req, "1020", "文件修改" ,filesharedo.getFileId()));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	 
	 /**
	  * 
	  * 修改多个资源状态
	 * @param filelist
	 * @returnBZCX/file/updates
	 */
	 @ResponseBody
	 @RequestMapping(value="/updatelistes",method = {RequestMethod.POST})
	 public Result changeStatus(@RequestParam String[] fileId,String status,HttpServletRequest req){
		 	Result result =  new Result(false);
		 	UserVo user = (UserVo) req.getSession().getAttribute("loginUser");
		 		try {
		 			for (String st : fileId) {
		 				F_File_ShareDo files = new F_File_ShareDo();
		 				files.setFileId(st);
		 				files.setAuditDate(new Date());
		 				files.setAuditUser(user.getId());
		 				files.setStatus(Short.valueOf(status));
		 				fileshare.changeInfo(files);
		 				operLogService.insert(super.getOperLog(req, "1020", "文件修改" ,st));
					}
						result.setResult(true);
				} catch (Exception e) {
					e.printStackTrace();
			}
			return result;
	 }
}
