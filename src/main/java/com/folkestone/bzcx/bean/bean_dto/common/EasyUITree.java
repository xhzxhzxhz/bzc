/**
 * author：smallking
 * 2017年10月31日 
 */
package com.folkestone.bzcx.bean.bean_dto.common;

import java.util.List;

/**
 * Describe：封住easyui树的实体类
 * 
 * @author smallking
 *
 *  2017年10月31日
 */
public class EasyUITree {
	/**
	 * 节点ID
	 */
	private String id;
	/**
	 * 节点名称
	 */
	private String text;
	/**
	 * 子节点
	 */
	private List<EasyUITree> children;
	/**
	 * 当前节点的状态
	 */
	private State state;

	private boolean checked;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public List<EasyUITree> getChildren() {
		return children;
	}
	public void setChildren(List<EasyUITree> children) {
		this.children = children;
	}
	public State getState() {
		if(state == null){
			state = new State();
		}
		return state;
	}
	public void setState(State state) {
		this.state = state;
	}
	public boolean getChecked() {
		if(children != null && children.size() >= 1){
			return false;
		}
		return checked;
	}
	public void setChecked(boolean checked) {
		this.checked = checked;
	}
	
}
