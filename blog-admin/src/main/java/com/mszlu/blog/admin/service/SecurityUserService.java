package com.mszlu.blog.admin.service;

import com.mszlu.blog.admin.mapper.PermissionMapper;
import com.mszlu.blog.admin.mapper.RoleMapper;
import com.mszlu.blog.admin.pojo.Admin;
import com.mszlu.blog.admin.pojo.Permission;
import com.mszlu.blog.admin.pojo.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class SecurityUserService implements UserDetailsService {
    @Autowired
    private AdminService adminService;
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private PermissionMapper permissionMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("username----->" + username);
        ArrayList<GrantedAuthority> grantedAuthorities = new ArrayList<>();

        Admin admin = adminService.findAdminByUsername(username);
        System.out.println(admin);
        if (admin == null) {
            return null;
        }
        List<Role> roles = roleMapper.findRoleListByUserId(admin.getId());
        System.out.println(roles);
        for (Role role : roles) {
            grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_"+role.getRoleKeyword()));
            List<Permission> permissions = permissionMapper.findPermissionByRole(role.getId());
            for (Permission permission : permissions) {
                grantedAuthorities.add(new SimpleGrantedAuthority(permission.getPermissionKeyword()));
            }
        }
        System.out.println(grantedAuthorities);
        UserDetails userDetails = new User(username, admin.getPassword(), grantedAuthorities);

        return userDetails;
    }
}
