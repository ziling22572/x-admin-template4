package com.ziling.xadmin.pojo;

import com.ziling.xadmin.entity.*;
import com.ziling.xadmin.vo.UserDeptVO;
import com.ziling.xadmin.vo.UserRoleVO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author lingjinli
 * @date 2025年06月26日 17:13
 * @desc 用户基本信息
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserInfo extends User {
    //   用户角色信息列表
    private List<UserRoleVO> roles;

    //   用户角色信息列表
    private List<UserDeptVO> depts;

    //用户菜单
    private List<Menu> menus;

    public UserInfo(Long userId, String username,String avatar, String phone, List<UserRoleVO> roles, List<UserDeptVO> depts, List<Menu> menus) {
        this.roles = roles;
        this.depts = depts;
        this.menus = menus;
        this.setId(userId);
        this.setUsername(username);
        this.setAvatar(avatar);
        this.setPhone(phone);
        this.setRoles(roles);
        this.setDepts(depts);
        this.setMenus(menus);
    }
}
