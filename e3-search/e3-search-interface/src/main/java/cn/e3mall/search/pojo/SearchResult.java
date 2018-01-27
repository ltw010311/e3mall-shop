package cn.e3mall.search.pojo;

import java.io.Serializable;
import java.util.List;

import cn.e3mall.common.pojo.SearchItem;

public class SearchResult implements Serializable {

	private long totalPages; //总的页数
	private long recourdCount; //商品总的记录数
	private List<SearchItem> itemList; //商品列表
	public long getTotalPages() {
		return totalPages;
	}
	public void setTotalPages(long totalPages) {
		this.totalPages = totalPages;
	}
	public long getRecourdCount() {
		return recourdCount;
	}
	public void setRecourdCount(long recourdCount) {
		this.recourdCount = recourdCount;
	}
	public List<SearchItem> getItemList() {
		return itemList;
	}
	public void setItemList(List<SearchItem> itemList) {
		this.itemList = itemList;
	}
	
}
