package cn.edu.fzu.daoyun.service.impl;

import cn.edu.fzu.daoyun.base.Page;
import cn.edu.fzu.daoyun.dto.SysDictDTO;
import cn.edu.fzu.daoyun.entity.SysDictDO;
import cn.edu.fzu.daoyun.mapper.SysDictMapper;
import cn.edu.fzu.daoyun.service.SysDictService;
import io.swagger.models.auth.In;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class SysDictServiceImpl implements SysDictService {
    @Resource
    private SysDictMapper sysDictMapper;

    @Override
    public List<SysDictDO> getDictParentList(Integer from, Integer to) {
        return sysDictMapper.getDictParentList(from,to);
    }

    @Override
    public List<SysDictDO> getSubDictList(Integer parentId) {
        return sysDictMapper.getSubDictList(parentId);
   }

    @Override
    public Page<SysDictDTO> getDictPage(Integer page, Integer size) {
        Integer from = (page - 1) * size;
        Integer to = page * size;
        Integer totalSize = this.sysDictMapper.getSysDictTotal(); //总条数
        Integer totalPage = (int) Math.ceil((double) totalSize / size); //总页数
        List<SysDictDTO> list = new ArrayList<>();
        List<SysDictDO> dictParentList = this.getDictParentList(from, to);
        for (SysDictDO dict:dictParentList) {
            List<SysDictDO> subDictList = this.getSubDictList(dict.getId());
            list.add(new SysDictDTO(dict,subDictList));
        }
        return new Page<>(list, totalSize, totalPage);
    }


    public Integer addParentDict(SysDictDO dict){
        sysDictMapper.addParentDict(dict);
        return dict.getId();
    }

    public Integer addSubDict(SysDictDO dict){
        sysDictMapper.addSubDict(dict);
        return dict.getId();
    }

    public Boolean deleteParentDict(Integer id){
        return sysDictMapper.deleteParentDict(id);
    }

    public Boolean updateParentDict(SysDictDO dict){
        return sysDictMapper.updateParentDict(dict);
    }
    public Boolean updateSubDict(SysDictDO dict){
        return sysDictMapper.updateSubDict(dict);
    }
}
