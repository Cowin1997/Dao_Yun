package cn.edu.fzu.daoyun.service.impl;

import cn.edu.fzu.daoyun.base.Page;
import cn.edu.fzu.daoyun.entity.CollegeDO;
import cn.edu.fzu.daoyun.entity.MajorDO;
import cn.edu.fzu.daoyun.entity.SchoolDO;
import cn.edu.fzu.daoyun.mapper.OrganizationMapper;
import cn.edu.fzu.daoyun.service.OrganizationService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class OrganizationServiceImpl implements OrganizationService {
    @Resource
    private OrganizationMapper organizationMapper;


    public Page<SchoolDO> getSchoolList(Integer page, Integer size){ //获取学校分页列表
        Integer from = (page - 1) * size;
        Integer to = page * size;
        List<SchoolDO> schoolList = this.organizationMapper.getSchoolList(from, to);
        Integer totalSize = this.organizationMapper.getSchoolTotal(); //总条数
        Integer totalPage = (int) Math.ceil((double) totalSize / size); //总页数
        Page pageResult = new Page(schoolList, totalSize, totalPage);
        return pageResult;
    }

    public SchoolDO getSchool(Integer schCode){ //根据学校代码获取学校明细
        SchoolDO school = this.organizationMapper.getSchool(schCode);
        return school;
    }


    public Page<CollegeDO> getCollegeList(Integer schCode , Integer page, Integer size){ //根据学校代码,获取学校相关专业
        Integer from = (page - 1) * size;
        Integer to = page * size;
        List<CollegeDO> collegeList = this.organizationMapper.getCollegeList(schCode,from, to);
        Integer totalSize = this.organizationMapper.getCollegeTotal(schCode); //总条数
        Integer totalPage = (int) Math.ceil((double) totalSize / size); //总页数
        Page pageResult = new Page(collegeList, totalSize, totalPage);
        return pageResult;
    }
    //根据学校代码和学院代码,获取学院详细信息
    public CollegeDO getCollege(Integer schCode,Integer colCode){
        CollegeDO college = this.organizationMapper.getCollege(schCode,colCode);
        return college;
    }

    //根据学校代码和学院代码,获取专业列表信息
    public Page<MajorDO> getMajorList(Integer colCode, Integer page, Integer size){
        Integer from = (page - 1) * size;
        Integer to = page * size;
        List<MajorDO> majorList = this.organizationMapper.getMajorList(colCode,from, to);
        Integer totalSize = this.organizationMapper.getMajorTotal(colCode); //总条数
        Integer totalPage = (int) Math.ceil((double) totalSize / size); //总页数
        Page pageResult = new Page(majorList, totalSize, totalPage);
        return pageResult;
    }
    //根据学院代码和专业代码,获取专业信息
    public MajorDO getMajor(Integer colCode,Integer majCode){
        MajorDO major = this.organizationMapper.getMajor(colCode,majCode);
        return major;
    }

}
