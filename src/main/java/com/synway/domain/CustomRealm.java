package com.synway.domain;

import com.synway.pojo.Role;
import com.synway.pojo.User;
import com.synway.service.RoleService;
import com.synway.service.UserService;
import com.synway.utils.JwtUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.Set;

/**
 * 实现用户授权验证和权限验证
 */
public class CustomRealm extends AuthorizingRealm {
    private static final Logger logger = LoggerFactory.getLogger(CustomRealm.class);

    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JWTToken;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) {
        logger.info("----------身份认证方法----------");
        String token = (String) authenticationToken.getCredentials();
        // 解密获得username，用于和数据库进行对比
        String username = JwtUtils.getUserName(token);
        int id = JwtUtils.getUserId(token);
        if (username == null || !JwtUtils.verify(token, username, id) || id == 0) {
            throw new IncorrectCredentialsException("token认证失败！");
        }
        User user = userService.selectById(id);
        if (user == null) {
            throw new IncorrectCredentialsException("用户密码不正确！");
        }
        if (user.getState() == 1) {
            throw new LockedAccountException("该用户已被封号！");
        }
        return new SimpleAuthenticationInfo(token, token, "MyRealm");
    }


    /**
     * 只有当需要检测用户权限的时候才会调用此方法，例如checkRole,checkPermission之类的
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        logger.info("————权限认证————");
        int userId = JwtUtils.getUserId(principals.toString());
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        //获得该用户角色
//        String role = userService.getRole(userId);
//        //每个角色拥有默认的权限
//        String rolePermission = roleService.getRolePermission(username);
        Role role = null;
        Set<String> permissionSet = new HashSet<>();
        Set<String> roleSet = new HashSet<>();
        if (userId != 0) {
            role = roleService.getRoleAndPermission(userId);
            //需要将 role, permission 封装到 Set 作为 info.setRoles(), info.setStringPermissions() 的参数
            roleSet.add(role.getRole());
            permissionSet.add(role.getPermission());
        }
        //设置该用户拥有的角色和权限
        info.setRoles(roleSet);
        info.setStringPermissions(permissionSet);
        return info;
    }

}
