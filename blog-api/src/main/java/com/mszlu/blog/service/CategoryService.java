package com.mszlu.blog.service;

import com.mszlu.blog.vo.CategoryVo;
import com.mszlu.blog.vo.Result;
import org.springframework.stereotype.Repository;


public interface CategoryService {

    CategoryVo findCategoryById(Long id);

    Result findAll();
}