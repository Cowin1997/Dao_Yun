package cn.edu.fzu.daoyun.service.impl;

import cn.edu.fzu.daoyun.dto.RoleDTO;
import cn.edu.fzu.daoyun.entity.RoleDO;
import cn.edu.fzu.daoyun.mapper.RoleMapper;
import cn.edu.fzu.daoyun.service.RoleService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {
    @Resource
    private RoleMapper roleMapper;

    /**
     *  查询全部角色
     * @return
     */
    @Override
    public List<RoleDTO> getAll() {
        List<RoleDTO> allRoleDTO = new ArrayList<>();
        List<RoleDO> allRoleDO = this.roleMapper.getAll();
        for (RoleDO role:allRoleDO){
            RoleDTO roleDTO = new RoleDTO();
            BeanUtils.copyProperties(role,roleDTO);
            allRoleDTO.add(roleDTO);
        }
        return allRoleDTO;
    }

    @Override
    public Boolean addRole(RoleDO role) {
        Date create = new Date();
        role.setGmt_create(create);
        role.setGmt_modified(create);
        return this.roleMapper.addRole(role);
    }

    /**
     * 根据角色id删除角色
     * 成功返回true,失败返回false
     *
     * @param roleId
     */
    @Override
    public Boolean delRole(Integer roleId) {
        return this.roleMapper.delRole(roleId);
    }

    /**
     *  根据角色id更新角色信息
     * 成功返回true,失败返回false
     *
     * @param role
     */
    @Override
    public Boolean updRole(RoleDO role) {
        Date modified = new Date();
        role.setGmt_create(modified);
        role.setGmt_modified(modified);
        return this.roleMapper.updRole(role);
    }


}
