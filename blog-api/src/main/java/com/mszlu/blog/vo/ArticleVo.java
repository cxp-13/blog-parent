package com.mszlu.blog.vo;
//
//import com.mszlu.blog.dao.pojo.ArticleBody;
//import com.mszlu.blog.dao.pojo.Category;
import com.mszlu.blog.dao.pojo.SysUser;
import com.mszlu.blog.dao.pojo.Tag;
import lombok.Data;

import java.util.List;

@Data
public class ArticleVo {

    private Long id;

    private String title;

    private String summary;

    private Integer commentCounts;

    private Integer viewCounts;

    private Integer weight;
    /**
     * 创建时间
     */
    private String createDate;

    private String author;

    private ArticleBodyVo body;

    private List<TagVo> tags;

    private CategoryVo categorys;

}