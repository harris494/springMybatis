package cn.harris.ssm.controller;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import cn.harris.ssm.controller.validation.ValidGroup1;
import cn.harris.ssm.exception.CustomException;
import cn.harris.ssm.po.ItemsCustom;
import cn.harris.ssm.po.ItemsQueryVo;
import cn.harris.ssm.service.ItemsService;

/**
 * 商品的Controller
 * 
 * @author harri
 *
 */

@Controller
// 为了对url进行分类管理，可以在这里定义根路径。最终访问url是根路径+子路径
@RequestMapping("/items")
public class ItemsController {

	@Autowired
	private ItemsService itemsService;

	// 商品分类
	// itemtypes表示最终将方法返回值放在request中的key
	@ModelAttribute("itemtypes")
	public Map<String, String> getItemTypes() {

		Map<String, String> itemTypes = new HashMap<String, String>();
		itemTypes.put("101", "数码");
		itemTypes.put("102", "母婴");

		return itemTypes;
	}

	@RequestMapping("/queryItems")
	public ModelAndView queryItems(HttpServletRequest request, ItemsQueryVo itemsQueryVo) throws Exception {
		System.out.println(request.getParameter("id"));
		// 调用service查找数据库，查询商品列表，这里使用静态数据模拟
		List<ItemsCustom> itemsList = itemsService.findItemsList(itemsQueryVo);

		// 返回ModelandView
		ModelAndView modelAndView = new ModelAndView();
		// 相当于request的setAttribute,在jsp页面中通过itemsList取数据
		modelAndView.addObject("itemsList", itemsList);
		// 指定视图
		// 下边的路径，如果在视图解析器中配置jsp路径的前缀和jsp的路径的后缀,可以修改为
		// modelAndView.setViewName("/WEB-INF/jsp/items/itemsList.jsp");
		modelAndView.setViewName("items/itemsList");

		return modelAndView;
	}

	// 商品信息修改页面显示
	// @RequestMapping("/editItems")
	// 限制http请求方法，可以post和get

	// @RequestMapping(value="/editItems",method=
	// {RequestMethod.POST,RequestMethod.GET})
	// public ModelAndView editItems() throws Exception {
	// // 调用service根据商品id查询商品信息
	// ItemsCustom itemsCustom = itemsService.findItemsById(1);
	//
	// // 返回ModelandView
	// ModelAndView modelAndView = new ModelAndView();
	// // 将商品信息放到model
	// modelAndView.addObject("itemsCustom", itemsCustom);
	// // 修改页面
	// modelAndView.setViewName("items/editItems");
	// return modelAndView;
	// }

	@RequestMapping(value = "/editItems", method = { RequestMethod.POST, RequestMethod.GET })
	// @RequestParam里边指定request传入参数名称和形参进行绑定。
	// required属性指定参数是否必须要传入
	// 通过defaultValue可以设置默认值，如果id参数没有传入，将默认值与参数绑定。
	public String editItems(Model model, @RequestParam(value = "id", required = true) Integer items_id)
			throws Exception {
		// 调用service根据商品id查询商品信息
		ItemsCustom itemsCustom = itemsService.findItemsById(items_id);
		//判断商品是否为空，根据id没有查询到商品，抛出异常，提示用户商品信息不存在
		if(itemsCustom == null) {
			throw new CustomException("your modified item is not exist!");
		}
		// 相当于modelAndView.addObject("itemsCustom", itemsCustom);
		model.addAttribute("items", itemsCustom);
		return "items/editItems";
	}
	
	//查询商品信息，输出json
	@RequestMapping("/itemsView/{id}")
	//itemsView/{id}里边的{id}表示占位符，通过@PathVariable获取占位符中的参数 或者传入@PathVariable指定名称中
	public @ResponseBody ItemsCustom itemsView(@PathVariable("id") Integer id) throws Exception{
		ItemsCustom itemsCustom = itemsService.findItemsById(id);
		return itemsCustom;
	}

	// // 商品信息修改提交
	// @RequestMapping("/editItemsSubmit")
	// public ModelAndView editItemsSubmit() throws Exception {
	// //调用service更新商品信息，页面需要将商品信息传到此方法
	//
	//
	//
	// // 返回ModelandView
	// ModelAndView modelAndView = new ModelAndView();
	// modelAndView.setViewName("success");
	// return modelAndView;
	// }
	// 商品信息修改提交
	// 在需要校验的pojo前面添加@Validated,在需要校验的pojo后面添加BindingResult bindingResult接收校验出错信息
	// 注意：@Validated和BindingResult bindingResult是配对出现，并且形参顺序是固定的（一前一后）。
	// value= {ValidGroup1.class}指定使用ValidGroup1分组的校验
	//1、springmvc默认对pojo数据进行回显
	//pojo数据传入controller方法后，springmvc自动将pojo数据放到request域，key等于pojo类型（首字母小写）
	// @ModelAttribute可以指定pojo回显到页面在request中的key
	@RequestMapping("/editItemsSubmit")
	public String editItemsSubmit(Model model, HttpServletRequest request, Integer id,
			@ModelAttribute("items") @Validated(value = { ValidGroup1.class }) ItemsCustom itemsCustom,
			BindingResult bindingResult,
			MultipartFile itemPic	//接收商品图片
			) throws Exception {
		// 获取校验错误信息
		if (bindingResult.hasErrors()) {
			// 输出错误信息
			List<ObjectError> allErrors = bindingResult.getAllErrors();
			for (ObjectError objectError : allErrors) {
				System.out.println(objectError.getDefaultMessage());
			}
			// 将错误信息传到页面
			model.addAttribute("allErrors", allErrors);
			
			//可以直接使用model将提交的pojo回显
			model.addAttribute("items", itemsCustom);
			
			// 出错重新到商品修改页面
			return "items/editItems";
		}
		//原始名称
		String originalFilename = itemPic.getOriginalFilename();
		//上传图片
		if(itemPic != null && originalFilename!=null && originalFilename.length() > 0) {
			//存储图片的物理路径
			String picPath = "D:\\test\\pic\\";
			//新的图片名称
			String newFileName = UUID.randomUUID()+originalFilename.substring(originalFilename.lastIndexOf("."));
			//新图片
			File newFile = new File(picPath+newFileName);
			//将内存中的数据写入磁盘
			itemPic.transferTo(newFile);
			//将新图片名称写到itemCustom中
			itemsCustom.setPic(newFileName);
		
		}
		
		
		// 调用service更新商品信息，页面需要将商品信息传到此方法
		itemsService.updateItems(id, itemsCustom);
		// 重定向到商品查询列表
		// return "redirect:queryItems.action";
		// 页面转发
//		return "forward:queryItems.action";
		 return "success";
	}

	// 批量删除 商品信息
	@RequestMapping("/deleteItems")
	public String deleteItems(int[] items_id) throws Exception {
		// 调用service删除

		return "success";
	}

	// 批量修改商品页面，将商品信息查询出来，在页面中可以编辑商品信息
	@RequestMapping("/editItemsQuery")
	public ModelAndView editItemsQuery(HttpServletRequest request, ItemsQueryVo itemsQueryVo) throws Exception {
		// 调用service查找数据库，查询商品列表，这里使用静态数据模拟
		List<ItemsCustom> itemsList = itemsService.findItemsList(itemsQueryVo);

		// 返回ModelandView
		ModelAndView modelAndView = new ModelAndView();
		// 相当于request的setAttribute,在jsp页面中通过itemsList取数据
		modelAndView.addObject("itemsList", itemsList);

		modelAndView.setViewName("items/editItemsQuery");

		return modelAndView;
	}

	// 批量修改商品提交
	// 通过ItemsQueryVo接收批量提交的商品信息，将商品信息存储到itemsQueryVo中itemsList属性中。
	@RequestMapping("/editItemsAllSubmit")
	public String editItemsAllSubmit(ItemsQueryVo itemsQueryVo) throws Exception {
		return "success";
	}
}
