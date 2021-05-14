package cn.edu.fzu.daoyun.mapper;


import cn.edu.fzu.daoyun.entity.Menu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface MenuMapper {

    @Select("select * from menu where roleid like '%${roleid}%' order by ord;")
    public List<Menu> getMenus(String roleid);

    @Select("select * from menu where parentid is null;")
    public List<Menu> getAllMenus();


    @Select("select * from menu order by id;")
    public List<Menu> getAllMenusOrderByIdAsc();

    @Select("select * from menu order by id;")
    public List<Menu> getAllMenusByOrderByIdAsc();

    @Select("select * from menu where parentid=#{parentid} order by ord;")
    public List<Menu> getMenusWithParentId(Integer parentid);


    @Select("select * from menu where parentid=#{parentid} and roleid like '%${roleid}%' order by ord;")
    public List<Menu> getMenusWithParentIdAndroleid (Integer parentid, String roleid);
    @Select("select * from menu where id=#{mid};")
    public Menu getMenuPermission(Integer mid);
    @Update("update menu set roleid=#{roleid} where id=#{mid};")
    public Boolean updateMenuPermission(Integer mid,String roleid);
}
