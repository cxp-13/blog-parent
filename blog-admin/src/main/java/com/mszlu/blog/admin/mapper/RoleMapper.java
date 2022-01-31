package com.mszlu.blog.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mszlu.blog.admin.pojo.Role;
import java.util.List;

public interface RoleMapper extends BaseMapper<Role> {

    List<Role> findRoleListByUserId(Long userId);
}