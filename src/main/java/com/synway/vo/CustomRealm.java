package com.synway.vo;

import com.synway.dao.RoleMapper;
import com.synway.dao.UserMapper;
import com.synway.domain.Role;
import com.synway.domain.User;
import com.synway.service.RoleService;
import com.synway.service.UserService;
import com.synway.utils.JwtUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.Set;

/**
 * 实现用户授权验证和权限验证
 */
public class CustomRealm extends AuthorizingRealm {
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JWTToken;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        System.out.println("----------身份认证方法----------");
        String token = (String) authenticationToken.getCredentials();
        // 解密获得username，用于和数据库进行对比
        String username = JwtUtils.getUserName(token);
        String id = JwtUtils.getUserId(token);
        if (username == null || !JwtUtils.verify(token, username, id) || id == null) {
            throw new AuthenticationException("token认证失败！");
        }
        User user = userService.selectById(Integer.valueOf(id));
        if (user == null) {
            throw new AuthenticationException("该用户不存在！");
        }
        if (user.getState() == 1) {
            throw new AuthenticationException("该用户已被封号！");
        }
        return new SimpleAuthenticationInfo(token, token, "MyRealm");
    }


    /**
     * 只有当需要检测用户权限的时候才会调用此方法，例如checkRole,checkPermission之类的
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        System.out.println("————权限认证————");
        String userId = JwtUtils.getUserId(principals.toString());
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        //获得该用户角色
//        String role = userService.getRole(userId);
//        //每个角色拥有默认的权限
//        String rolePermission = roleService.getRolePermission(username);
        Role role = null;
        Set<String> permissionSet = new HashSet<>();
        Set<String> roleSet = new HashSet<>();
        if (userId != null) {
            role = roleService.getRoleAndPermission(Integer.parseInt(userId));
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
