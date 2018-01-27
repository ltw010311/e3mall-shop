package cn.e3mall.service.content;

import java.util.List;

import cn.e3mall.common.pojo.EasyUITreeNode;
import cn.e3mall.common.utils.E3Result;

public interface ContentCatService {

	List<EasyUITreeNode> getContentCategoryList(Long parentId);
	
	E3Result addContentCategory(Long parentId,String name);
	
	E3Result updateContentCategory(Long id,String name);

	E3Result deleteContentCategory(Long id);
	
}
