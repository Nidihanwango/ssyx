package com.atguigu.ssyx.acl.helper;

import com.atguigu.ssyx.model.acl.Permission;
import java.util.ArrayList;
import java.util.List;

/**
 * 菜单管理辅助工具类
 */
public class PermissionHelper {
    /**
     * 构造树形结构
     */
    public static List<Permission> build(List<Permission> allPermission) {
        List<Permission> tree = new ArrayList<>();
        // 1.找出root节点
        for (Permission permission : allPermission) {
            if (permission.getPid() == 0){
                permission.setLevel(1);
                // 2.找出root节点的子节点，并赋值
                tree.add(findChildren(permission, allPermission));
            }
        }
        // 3.返回结果
        return tree;
    }

    private static Permission findChildren(Permission parent, List<Permission> allPermission) {
        parent.setChildren(new ArrayList<>());
        for (Permission p : allPermission) {
            if (p.getPid().longValue() == parent.getId().longValue()) {
                p.setLevel(parent.getLevel() + 1);
                parent.getChildren().add(findChildren(p, allPermission));
            }
        }
        return parent;
    }
}


























