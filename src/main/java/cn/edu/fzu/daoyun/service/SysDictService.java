package cn.edu.fzu.daoyun.service;

import cn.edu.fzu.daoyun.base.Page;
import cn.edu.fzu.daoyun.dto.SysDictDTO;
import cn.edu.fzu.daoyun.entity.SysDictDO;

import java.util.List;

public interface SysDictService {

    public List<SysDictDO> getDictParentList(Integer from , Integer to);

    public List<SysDictDO> getSubDictList(Integer parentId);

    public Page<SysDictDTO> getDictPage(Integer from , Integer to);
}
