package com.ocean.service;

import com.ocean.pojo.Category;
import com.ocean.util.Page;

import java.util.List;

public interface CategoryService {

	List<Category> list();
	int total();
	List<Category> list(Page page);
	void deleteAll();
	void addTwo();
}
