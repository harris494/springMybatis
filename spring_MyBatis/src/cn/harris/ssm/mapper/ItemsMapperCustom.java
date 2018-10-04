package cn.harris.ssm.mapper;

import cn.harris.ssm.po.Items;
import cn.harris.ssm.po.ItemsCustom;
import cn.harris.ssm.po.ItemsExample;
import cn.harris.ssm.po.ItemsQueryVo;

import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ItemsMapperCustom {
    //商品查询列表
	public List<ItemsCustom> findItemsList(ItemsQueryVo itemsQueryVo)throws Exception;
}