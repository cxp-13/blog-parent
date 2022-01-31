package com.mszlu.blog.admin.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class PermissionVo {

    private Long id;

    private String name;

    private String path;

    private String description;
}
