package cn.e3mall.common.pojo;

import java.io.Serializable;

public class EasyUITreeNode implements Serializable {

	private long id;	//父节点的id
	private String text;	//父节点的名称
	private String state;	//如果节点下雨子节点，应该是closed，如没有就应该是open
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	
}
