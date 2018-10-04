package cn.harris.ssm.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.harris.ssm.po.ItemsCustom;

/**
 * json交互测试
 * @author harri
 *
 */
@Controller
public class jsonTest {
	//请求json串(商品信息)，输出json(商品信息)
	//@RequestBody将请求的商品信息的json串转成itemsCustom对象
	//@ResponseBody	将itemsCustom对象转成json串输出
	//不能同时两个url同时存在 会报错！！！ 但依旧不会进来。 慎用json转json吧	
	@RequestMapping("/requestJson")
	public @ResponseBody ItemsCustom requestJson(@RequestBody ItemsCustom itemsCustom) {
		//@ResponseBody将itemsCustom转成json输出
		return itemsCustom;
	}
	//请求key/value，输出json
	@RequestMapping("/responseJson")
	public @ResponseBody ItemsCustom responseJson( ItemsCustom itemsCustom) {
		//@ResponseBody将itemsCustom转成json输出
		System.out.println(itemsCustom.getId());
		return itemsCustom;
	}
}
