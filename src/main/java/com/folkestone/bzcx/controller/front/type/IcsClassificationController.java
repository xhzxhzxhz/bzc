package com.folkestone.bzcx.controller.front.type;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.folkestone.bzcx.bean.bean_do.type.IcsClassificationDo;
import com.folkestone.bzcx.bean.bean_dto.common.Query;
import com.folkestone.bzcx.bean.bean_dto.common.Result;
import com.folkestone.bzcx.bean.bean_vo.type.IcsClassificationVo;
import com.folkestone.bzcx.controller.base.BaseController;
import com.folkestone.bzcx.service.type.IcsClassificationService;


@Controller
@RequestMapping(value = "/icstype")
public class IcsClassificationController extends BaseController{

	@Autowired
	private IcsClassificationService icsservice;
	private List<IcsClassificationVo> nodes= null;
	@ResponseBody
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	public Result listAll(){  //返回一个json  
		Result re = new Result(true);
		List<IcsClassificationVo> listAll = icsservice.listAll();
		re.setData(listAll);
		return re;
	}
	
	@ResponseBody
	@RequestMapping(value = "/listTree", method = RequestMethod.POST)
	public Result listTree(@RequestParam Map<String, Object> param){  //返回一个json  
		Result re = new Result(true);
		Query query = new Query(param);
		List<IcsClassificationVo> listAll = icsservice.listTree(query);
		re.setData(listAll);
		return re;
	}
	
	@ResponseBody
	@RequestMapping(value = "/listByQuery", method = RequestMethod.POST)
	public List<IcsClassificationVo> listByQuery(@RequestParam Map<String, Object> param){  //返回一个json  
		 Query query = new Query(param);
		return icsservice.listByQuery(query);
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/insert", method = RequestMethod.POST)
	public Result insert(IcsClassificationDo record){  //返回一个json  
		Result re = new Result(true);
		icsservice.insert(record);
		return re;
	}
	
	/**
	 * 树桩的展示我们分类表的数据
	 */
/*	@ResponseBody
	@RequestMapping(value = "/showTypeByTree", method = RequestMethod.POST)
	public Result showTypeByTree(){
		Result result= new Result();
		try {
			String data= null;
			//查询所有的资源
			nodes=icsservice.listAll();
			if(nodes!=null) {
				//创建json的树形结构
				 List<IcsClassificationVo> nodeTree = buildTree();
				 JSONArray jsonArray = new JSONArray();
				 //遍历这个集合
				for(IcsClassificationVo r : nodeTree) {
					JSONObject obj= new JSONObject();
					obj.put(r.getIcsName(), r);
					jsonArray.add(obj);
				}
				data=	jsonArray.toString();
				result.setCode(200);
				result.setData(data);
			}
		}catch(Exception e) {
			e.printStackTrace();
			result.setCode(500);
			result.setMsg("查询数据库失败，请检查数据的合法性");
		}
		return result;
	}
	
	
	 *//**
     * 构建树形结构
     * @return
     *//*
    public List<IcsClassificationVo> buildTree() {
        List<IcsClassificationVo> treeNodes = new ArrayList<IcsClassificationVo>();
        List<IcsClassificationVo> rootNodes = getRootNodes();
        for (IcsClassificationVo rootNode : rootNodes) {
            buildChildNodes(rootNode);
            treeNodes.add(rootNode);
        }
        return treeNodes;
    }

    *//**
     * 递归子节点
     * @param node
     *//*
    public void buildChildNodes(IcsClassificationVo node) {
        List<IcsClassificationVo> children = getChildNodes(node);
        if (!children.isEmpty()) {
            for(IcsClassificationVo child : children) {
                buildChildNodes(child);
            }
            node.setChildren(children);;
        }
    }

    *//**
     * 获取父节点下所有的子节点
     * @param pnode
     * @return
     *//*
    public List<IcsClassificationVo> getChildNodes(IcsClassificationVo pnode) {//传入父节点对象，如果为该父节点的子节点，则放入子节点集合中
        List<IcsClassificationVo> childNodes = new ArrayList<IcsClassificationVo>();
        for (IcsClassificationVo n : nodes){//从nodes中筛选所以为pnode的子节点//这个是数据库中所有的数据
        String icsId=	pnode.getIcsId();
       String parendid= n.getIcsParendid();
       if(icsId!=null && parendid!=null) {
    	   if (icsId.equals(parendid)) {
               childNodes.add(n);
           }
       }
          
        }
        return childNodes;
    }

    *//**
     * 判断是否为根节点
     * @param node
     * @return
     *//*
    public boolean rootNode(IcsClassificationVo node) {
        boolean isRootNode = true;
        for (IcsClassificationVo n : nodes){//从nodes中筛选所以父节点
        	 String icsId=	n.getIcsId();
             String parendid= node.getIcsParendid();
             if(icsId!=null &&parendid!=null) {
            	 if (parendid.equals(icsId)) {//判断传入的node对象中，他的上级成员编号还有没有node中的成员编号与之对应，如果没有，则为根节点
                     isRootNode= false;
                     break;
                 }
             }
           
        }
        return isRootNode;
    }

    *//**
     * 获取集合中所有的根节点
     * @return
     *//*
    public List<IcsClassificationVo> getRootNodes() {
        List<IcsClassificationVo> rootNodes = new ArrayList<IcsClassificationVo>();
        for (IcsClassificationVo n : nodes){
            if (rootNode(n)) {
                rootNodes.add(n);//把所以的根节点放入rootNodes集合中
            }
        }
        return rootNodes;
    }*/

	@ResponseBody
	@RequestMapping(value = "/showTypeByTree", method = RequestMethod.POST)
	public Result showResource() {
		Result result=new Result();
		try {
			String data= null;
			//查询所有的资源
			nodes=icsservice.listAll();
			if(nodes!=null&&nodes.size()>0) {
				//创建json的树形结构
				 List<IcsClassificationVo> nodeTree = buildTree();
				 JSONArray jsonArray = new JSONArray();
				 //遍历这个集合
				for(IcsClassificationVo r : nodeTree) {
					Map<String,Object> map= new HashMap<String,Object>();
					map.put("icsId", r.getIcsId());
					map.put("icsName", r.getIcsName());
					map.put("icsParendid", r.getIcsParendid());
					map.put("icsCode", r.getIcsCode());
					map.put("children", r.getChildren());
					map.put("type", r.getType());
					map.put("threeIcs", r.getThreeIcs());
					
					
					
					
					
					JSONObject obj= new JSONObject(map);
					//obj.put(r.getCdmc(), r);
					jsonArray.add(obj);
				}
				data=jsonArray.toString();
			}
			result.setCode(200);
			result.setData(data);
		}catch(Exception e) {
			e.printStackTrace();
			result.setCode(500);
			result.setMsg("数据库操作失误，请检查数据的合法性");
		}
		
		
		return result;
	}
	/**
     * 构建树形结构
     * @return
     */
    public List<IcsClassificationVo> buildTree() {
        List<IcsClassificationVo> treeNodes = new ArrayList<IcsClassificationVo>();
        List<IcsClassificationVo> rootNodes = getRootNodes();
        for (IcsClassificationVo rootNode : rootNodes) {
            buildChildNodes(rootNode);
            treeNodes.add(rootNode);
        }
        return treeNodes;
    }

    /**
     * 递归子节点
     * @param node
     */
    public void buildChildNodes(IcsClassificationVo node) {
        List<IcsClassificationVo> children = getChildNodes(node);
        if (!children.isEmpty()) {
            for(IcsClassificationVo child : children) {
                buildChildNodes(child);
            }
            node.setChildren(children);;
        }
    }

    /**
     * 获取父节点下所有的子节点
     * @param pnode
     * @return
     */
    public List<IcsClassificationVo> getChildNodes(IcsClassificationVo pnode) {//传入父节点对象，如果为该父节点的子节点，则放入子节点集合中
        List<IcsClassificationVo> childNodes = new ArrayList<IcsClassificationVo>();
        for (IcsClassificationVo n : nodes){//从nodes中筛选所以为pnode的子节点//这个是数据库中所有的数据
        	if(n!=null ) {
        	String icsId=	pnode.getIcsId();
        	String parentId=n.getIcsParendid();
        	 if (icsId.equals(parentId)) {
                 childNodes.add(n);
             }
        	}
           
        }
        return childNodes;
    }

    /**
     * 判断是否为根节点
     * @param node
     * @return
     */
    public boolean rootNode(IcsClassificationVo node) {
        boolean isRootNode = true;
        for (IcsClassificationVo n : nodes){//从nodes中筛选所以父节点
        	if(n!=null) {
        		 String icsId=	n.getIcsId();
                 String parendid= node.getIcsParendid();
                 if(icsId!=null &&parendid!=null) {
                	 if (parendid.equals(icsId)) {//判断传入的node对象中，他的上级成员编号还有没有node中的成员编号与之对应，如果没有，则为根节点
                         isRootNode= false;
                         break;
                     }
                 }
        	}
        	
           
        }
        return isRootNode;
    }

    /**
     * 获取集合中所有的根节点
     * @return
     */
    public List<IcsClassificationVo> getRootNodes() {
        List<IcsClassificationVo> rootNodes = new ArrayList<IcsClassificationVo>();
        for (IcsClassificationVo n : nodes){
        	if(n!=null) {
        		if (rootNode(n)) {
                    rootNodes.add(n);//把所以的根节点放入rootNodes集合中
                }
        	}
            
        }
        return rootNodes;
    }


	
	
}
