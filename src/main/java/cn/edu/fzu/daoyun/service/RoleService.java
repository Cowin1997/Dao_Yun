package cn.edu.fzu.daoyun.service;

import cn.edu.fzu.daoyun.dto.RoleDTO;
import cn.edu.fzu.daoyun.entity.RoleDO;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface RoleService {
    /**
     *  查询全部角色
     * @return
     */
    public List<RoleDTO> getAll();


    /**
     *  添加角色
     */

    public Boolean addRole(RoleDO role);


    /**
     *  根据角色id删除角色
     *  成功返回true,失败返回false
     */
    public Boolean delRole(Integer roleId);


    /**
     *     更新角色信息
     *     成功返回true,失败返回false
     */

    public Boolean updRole(RoleDO role);
}
