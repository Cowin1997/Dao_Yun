package cn.edu.fzu.daoyun.service.impl;

import cn.edu.fzu.daoyun.constant.RoleEnum;
import cn.edu.fzu.daoyun.dto.MenuDTO;
import cn.edu.fzu.daoyun.dto.UserDTO;
import cn.edu.fzu.daoyun.entity.*;
import cn.edu.fzu.daoyun.mapper.*;
import cn.edu.fzu.daoyun.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {
    @Resource
    private UserAuthMapper userAuthMapper;
    @Resource
    private UserMapper userMapper;
    @Resource
    private RoleMapper roleMapper;
    @Resource
    private StudentMapper studentMapper;
    @Resource
    private TeacherMapper teacherMapper;
    @Resource
    private PermissionMapper permissionMapper;
    @Resource
    private Menu2Mapper menu2Mapper;

    /**
     *  根据 id 查询用户基本信息
     */
    @Override
    public UserDO getUserById(Integer id) {
        return userMapper.selectUserById(id);
    }

    /*
     *  根据 identifier 查询详细的用户信息
     */
    @Override
    public UserDTO getUserDetailByIdentifier(String identifier,String type) {
        UserDTO userDTO = new UserDTO();
        // 获取 登录凭证信息(user_auth)
        UserAuthDO userAuth = userAuthMapper.selectByIdentifier(identifier,type);
        if(userAuth==null) return null;
        userDTO.setUserAuth(userAuth);
        // 获取相关的用户信息(user)
        UserDO user = userMapper.selectUserById(userAuth.getUser_id());
        if(user==null) return null;
        userDTO.setUser(user);
        // 用户相关的角色信息
        RoleDO role = roleMapper.getRoleById(user.getRole_id());
        if(role==null) return null;
        userDTO.setUserRole(role);
        // 获取个人信息(学生)
        if(user.getRole_id() == RoleEnum.STUDENT){
            StudentDO studentInfo = this.studentMapper.getStudentByUserId(user.getId());
            userDTO.setInfo(studentInfo);
        }
        //  获取个人信息(教师)
        if(user.getRole_id() == RoleEnum.TEACHER){
            TeacherDO teacherInfo = this.teacherMapper.getTeacherByUserId(user.getId());
            userDTO.setInfo(teacherInfo);
        }
        // 获取角色路由
        List<MenuDTO> permitList = new ArrayList<>();
        List<Integer> permitMenuIdList = this.permissionMapper.getPermission(user.getRole_id());
        List<MenuDO> permitMenuDoList = this.menu2Mapper.getMenusById(permitMenuIdList);
        System.out.println(permitList.toString());
        // 转成DTO
        for (MenuDO m:permitMenuDoList) {
            MenuDTO menu = new MenuDTO();
            BeanUtils.copyProperties(m,menu);
            permitList.add(menu);
        }
        // permitList 转成树
        List<MenuDTO> tree = generateMenuTree(permitList);
        //
        userDTO.setMenus(tree);

        return userDTO;
    }




    public  List<MenuDTO>  generateMenuTree(List<MenuDTO> menuList){
        List<MenuDTO> trees = new ArrayList<>();
        Set<Integer> ids = new HashSet<>();
        for (MenuDTO menuDTO : menuList) {
            if (menuDTO.getParent_id() == null) { // 父级
                trees.add(menuDTO);
            }
            for (MenuDTO it : menuList) {
                if (menuDTO.getId().equals(it.getParent_id())) {
                    if (menuDTO.getChildren() == null) {
                        menuDTO.setChildren(new ArrayList<>());
                    }
                    menuDTO.getChildren().add(it);
                    ids.add(it.getId());
                }
            }
        }
        return trees;

    }

}
