package cn.e3mall.item.pojo;

import cn.e3mall.pojo.TbItem;
/**
 * 商品中没有images，只有image，需要子页面中展示中获取到所有的图片，可以继承TbItem类，得到它中的属性
 * @author LiuTaiwen
 *
 */
public class Item extends TbItem {

	//处理传入页面中的图片
	public String[] getImages(){
		String image2 = this.getImage();
		//得到图片的字符串，判断它是否为空
		if(image2 != null & !"".equals(image2)){
			return image2.split(",");
		}
		return null;
	}

	public Item() { }
	//有参构造，继承了父类，就应将它中的属性都继承
	public Item(TbItem tbItem){
		this.setId(tbItem.getId());
		this.setTitle(tbItem.getTitle());
		this.setSellPoint(tbItem.getSellPoint());
		this.setPrice(tbItem.getPrice());
		this.setNum(tbItem.getNum());
		this.setBarcode(tbItem.getBarcode());
		this.setImage(tbItem.getImage());
		this.setCid(tbItem.getCid());
		this.setStatus(tbItem.getStatus());
		this.setCreated(tbItem.getCreated());
		this.setUpdated(tbItem.getUpdated());
	}
	
}
