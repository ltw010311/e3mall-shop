package cn.e3mall.content.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.e3mall.common.pojo.EasyUITreeNode;
import cn.e3mall.common.utils.E3Result;
import cn.e3mall.mapper.TbContentCategoryMapper;
import cn.e3mall.pojo.TbContentCategory;
import cn.e3mall.pojo.TbContentCategoryExample;
import cn.e3mall.pojo.TbContentCategoryExample.Criteria;
import cn.e3mall.service.content.ContentCatService;
/**
 * 内容管理Service
 * @author LiuTaiwen
 *
 */
@Service
public class ContentCatServiceImpl implements ContentCatService {

	@Autowired 
	private TbContentCategoryMapper contentCategoryMapper;
	@Override
	public List<EasyUITreeNode> getContentCategoryList(Long parentId) {
		//1.创建一个Example对象
		TbContentCategoryExample example = new TbContentCategoryExample();
		//2.设置查询条件
		Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(parentId);
		//3.执行查询
		List<TbContentCategory> list = contentCategoryMapper.selectByExample(example);
		//4.把内容列表转换为节点列表
		List<EasyUITreeNode> easyUITreeNodes = new ArrayList<>();
		for (TbContentCategory contentCategory : list) {
			EasyUITreeNode easyUITreeNode = new EasyUITreeNode();
			easyUITreeNode.setId(contentCategory.getId());
			easyUITreeNode.setText(contentCategory.getName());
			easyUITreeNode.setState(contentCategory.getIsParent()?"closed":"open");
			//添加 到节点列表
			easyUITreeNodes.add(easyUITreeNode);
		}
		//返回节点列表
		return easyUITreeNodes;
	}
	
	@Override
	public E3Result addContentCategory(Long parentId, String name) {
		//1.创建TbContentCategory对象
		TbContentCategory category = new TbContentCategory();
		//2.补全对象的属性
		category.setParentId(parentId);
		category.setName(name);
		//状态。可选值:1(正常),2(删除)
		category.setStatus(1);
		category.setSortOrder(1);
		//排列序号
		category.setIsParent(false);
		category.setCreated(new Date());
		category.setUpdated(new Date());
		//插入数据
		contentCategoryMapper.insert(category);
		//查询父节点的信息
		TbContentCategory contentCategory = contentCategoryMapper.selectByPrimaryKey(parentId);
		//判断父节点的is_parent是否为1，若不为1，置为1.
		if(!contentCategory.getIsParent()){
			contentCategory.setIsParent(true);
			//更新到数据库中
			contentCategoryMapper.updateByPrimaryKey(contentCategory);
		}
		return E3Result.ok(category);
	}

	@Override
	public E3Result updateContentCategory(Long id, String name) {
		//1.创建TbContentCateforyMapper对象
		TbContentCategory contentCategory = new TbContentCategory();
		//2.设置属性值
		contentCategory.setId(id);
		contentCategory.setName(name);
		//3.执行更新
		contentCategoryMapper.updateByPrimaryKeySelective(contentCategory);
		return E3Result.ok();
	}

	@Override
	public E3Result deleteContentCategory(Long id) {
		//1.查询节点信息
		TbContentCategory contentCategory = contentCategoryMapper.selectByPrimaryKey(id);
		//2.判断该节点是否为父节点
		if(!contentCategory.getIsParent()){
			contentCategoryMapper.deleteByPrimaryKey(id);
			
			
			
			/*TbContentCategory parent = contentCategoryMapper.selectByPrimaryKey(contentCategory.getParentId());
			if(!parent.getIsParent()){
				parent.setIsParent(false);
				//更新到数据库中
				contentCategoryMapper.updateByPrimaryKey(parent);
			}*/
		}else{
			String msg = "请先删除子选项后,在删除该节点！";
			return E3Result.build(200, msg);
		}
		return E3Result.ok();
	}

}
