package cn.edu.fzu.daoyun.service;

import cn.edu.fzu.daoyun.base.Page;
import cn.edu.fzu.daoyun.entity.CollegeDO;
import cn.edu.fzu.daoyun.entity.MajorDO;
import cn.edu.fzu.daoyun.entity.SchoolDO;

public interface OrganizationService {
    public Page<SchoolDO> getSchoolList(Integer page, Integer size);

    public SchoolDO getSchool(Integer schCode);

    public Page<CollegeDO> getCollegeList(Integer schCode , Integer page, Integer size);

    public CollegeDO getCollege(Integer schCode,Integer colCode);

    public Page<MajorDO> getMajorList(Integer colCode, Integer page, Integer size);

    public MajorDO getMajor(Integer colCode,Integer majCode);

}
