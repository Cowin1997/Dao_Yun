package cn.edu.fzu.daoyun.service.impl;

import cn.edu.fzu.daoyun.base.Page;
import cn.edu.fzu.daoyun.entity.SysParamDO;
import cn.edu.fzu.daoyun.mapper.SysParamMapper;
import cn.edu.fzu.daoyun.service.SysParamService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class SysParamServiceImpl implements SysParamService {
    @Resource
    private SysParamMapper sysParamMapper;

    public Page<SysParamDO> getSysParamList(Integer page, Integer size){
        Integer from = (page - 1) * size;
        Integer to = page * size;
        Integer totalSize = this.sysParamMapper.getSysParamTotal(); //总条数
        Integer totalPage = (int) Math.ceil((double) totalSize / size); //总页数
        List<SysParamDO> sysParamList = this.sysParamMapper.getSysParamList(from, to);
        Page pageResult = new Page(sysParamList, totalSize, totalPage);
        return pageResult;
    }


    public Boolean delSysParam(Integer id){
        return sysParamMapper.delSysParam(id);
    }



    public Boolean addSysParam(SysParamDO param){
        param.setGmt_create(new Date());
        return  this.sysParamMapper.addSysParam(param);
    }
    public Boolean updateSysParam(SysParamDO param){
        param.setGmt_modified(new Date());
        return  this.sysParamMapper.updateSysParam(param);
    }
}
