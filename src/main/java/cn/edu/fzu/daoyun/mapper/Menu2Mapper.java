package cn.edu.fzu.daoyun.mapper;

import cn.edu.fzu.daoyun.entity.Menu;
import cn.edu.fzu.daoyun.entity.MenuDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface Menu2Mapper {



    @Select("<script> select * from menu2 where id in"+
    "<foreach  item='item' index='index' collection='ids' open='(' separator=',' close=')'> #{item} </foreach> </script>")
    public List<MenuDO> getMenusById(List<Integer> ids);

}
