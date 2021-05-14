package cn.edu.fzu.daoyun.controller;


import cn.edu.fzu.daoyun.annotation.AnonymousGetMapping;
import cn.edu.fzu.daoyun.annotation.AnonymousPutMapping;
import cn.edu.fzu.daoyun.entity.Menu;
import cn.edu.fzu.daoyun.mapper.MenuMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping(value = "/menu")
public class MenuController {
    @Resource
    private MenuMapper menuMapper;

    @ResponseBody
    @AnonymousGetMapping(value = {"/role/{roleid}","/role"})
    public List getMenusByRoleId(@PathVariable(value = "roleid",required = false) Integer roleid) {
        List<Menu> Menus = null;
        List<HashMap> list = new ArrayList<>();
        if(roleid!=null) { Menus = this.menuMapper.getMenus(roleid + "|");}
        else{
            Menus = this.menuMapper.getAllMenus();
        }
        for (Menu m : Menus) {
            System.out.println(m);
            if (m.getParentid() == null) {
                HashMap hm = new HashMap();
                hm.put("icon", m.getIcon());
                hm.put("index", m.getUri());
                hm.put("title", m.getTitle());
                hm.put("id", m.getId());
                list.add(hm);
            }
        }
        for (int i = 0; i < list.size(); i++) {
            if(roleid!=null) Menus = menuMapper.getMenusWithParentIdAndroleid((Integer) list.get(i).get("id"),roleid+"|");
            else Menus = menuMapper.getMenusWithParentId((Integer) list.get(i).get("id"));
            if (Menus.size() > 0) list.get(i).put("subs", Menus);
        }
        return list;

    }


    @ResponseBody
    @AnonymousGetMapping(value = "")
    public List getMenu(@RequestParam("type") Integer type) {
        List<HashMap> list = new ArrayList<>();
        List<Menu> Menus = this.menuMapper.getMenus(type+"|");
        for (Menu m : Menus) {
            if (m.getParentid() == null){
                HashMap hm = new HashMap();
                hm.put("icon",m.getIcon());
                hm.put("index",m.getUri());
                hm.put("title",m.getTitle());
                hm.put("id",m.getId());
                list.add(hm);
            }
        }
        for(int i=0;i<list.size();i++) {
            Menus = menuMapper.getMenusWithParentIdAndroleid((Integer) list.get(i).get("id"),type+"|");
            if (Menus.size() > 0) list.get(i).put("subs", Menus);
        }
        return list;
    }




    @ResponseBody
    @AnonymousPutMapping(value = "")
    public ResponseEntity updateMenuPermission(@RequestBody HashMap map){
        Integer id = (Integer) map.get("id");
        List<Integer> permit = (List<Integer>) map.get("permit");
        List<Integer> nopermit = (List<Integer>) map.get("nopermit");
        System.out.println(id);
        System.out.println(permit);
        System.out.println(nopermit);
        HashMap res = new HashMap();
        for(int i=0;i<permit.size();i++){
            String roleid = this.menuMapper.getMenuPermission(permit.get(i)).getRoleid();
            if(roleid.contains(id+"|")) continue;
            else{
                roleid+=(id)+"|";
                this.menuMapper.updateMenuPermission(permit.get(i),roleid);
            }
        }
        for(int i=0;i<nopermit.size();i++){
            String roleid = this.menuMapper.getMenuPermission(nopermit.get(i)).getRoleid();
            System.out.println(roleid);
            System.out.println(roleid);
            System.out.println(roleid);
            System.out.println(roleid);
            System.out.println(roleid);
            System.out.println(roleid);
            System.out.println(roleid);
            System.out.println(roleid);

            if(!roleid.contains(id+"|")) continue;
            else{
                roleid = roleid.replace(id+"|","");
                this.menuMapper.updateMenuPermission(nopermit.get(i),roleid);
            }
        }

        return ResponseEntity.status(HttpStatus.OK).body(res);
    }
}
