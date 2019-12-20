package com.ocean.controller;


import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.ocean.pojo.Category;
import com.ocean.service.CategoryService;
import com.ocean.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

// 告诉spring mvc这是一个控制器类
@Controller
@RequestMapping("")
public class CategoryController {
	@Autowired
	CategoryService categoryService;

	@RequestMapping("listCategory")
	public ModelAndView listCategory(Page page){
		ModelAndView mav = new ModelAndView();
//		PageHelper.offsetPage(page.getStart(),5);
//		List<Category> cs= categoryService.list();
//		int total = (int) new PageInfo<>(cs).getTotal();
		List<Category> cs= categoryService.list(page);
		int total = categoryService.total();
		
		page.caculateLast(total);
		
		// 放入转发参数
		mav.addObject("cs", cs);
		// 放入jsp路径
		mav.setViewName("listCategory");
		return mav;
	}

	@RequestMapping("listCategoryPagehelper")
	public ModelAndView listCategoryPagehelper(Page page){
		ModelAndView mav = new ModelAndView();
		PageHelper.offsetPage(page.getStart(),5);
		List<Category> cs= categoryService.list();
		int total = (int) new PageInfo<>(cs).getTotal();

		page.caculateLast(total);

		// 放入转发参数
		mav.addObject("cs", cs);
		// 放入jsp路径
		mav.setViewName("listCategory");
		return mav;
	}

	@RequestMapping("/test")
	public String test(){
		categoryService.deleteAll();
		categoryService.addTwo();
		return "ok";
	}

	@ResponseBody
	@RequestMapping("/submitCategory")
	public String submitCategory(@RequestBody Category category) {
		System.out.println("SSM接受到浏览器提交的json，并转换为Category对象:"+category);
		return "ok";
	}

	@ResponseBody
	@RequestMapping("/getOneCategory")
	public String getOneCategory() {
		Category c = new Category();
		c.setId(100);
		c.setName("第100个分类");
		JSONObject json= new JSONObject();
		json.put("category", JSONObject.toJSON(c));
		return json.toJSONString();
	}
	@ResponseBody
	@RequestMapping("/getManyCategory")
	public String getManyCategory() {
		List<Category> cs = new ArrayList<>();
		for (int i = 0; i < 10; i++) {
			Category c = new Category();
			c.setId(i);
			c.setName("分类名称:"+i);
			cs.add(c);
		}

		return JSONObject.toJSON(cs).toString();
	}

}
