package cn.e3mall.service.content;

import java.util.List;

import cn.e3mall.common.pojo.DataGridResult;
import cn.e3mall.common.utils.E3Result;
import cn.e3mall.pojo.TbContent;

public interface ContentService {

	DataGridResult getContentListDataGrid(Long categoryId ,int page,int rows);
	
	E3Result addContent(TbContent content);
	
	E3Result deleteContent(String ids);
	
	List<TbContent> getContentListByCid(Long cid);
}
