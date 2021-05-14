package cn.edu.fzu.daoyun.service.impl;

import cn.edu.fzu.daoyun.base.Page;
import cn.edu.fzu.daoyun.entity.StudentDO;
import cn.edu.fzu.daoyun.exception.BadRequestException;
import cn.edu.fzu.daoyun.mapper.StudentMapper;
import cn.edu.fzu.daoyun.service.StudentService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {
    @Resource
    private StudentMapper studentMapper;


    /**
     * 根据学号获取学生信息
     *
     * @param sid
     * @return
     */
    @Override
    public StudentDO getStudentBySid(Integer sid) {
        return this.studentMapper.selectBySid(sid);
    }

    /**
     * 根据学号删除学生(*解绑了账号绑定的学生)
     *
     * @param sid
     */
    @Override
    public Boolean deleteStudentBySid(Integer sid) {
        return this.deleteStudentBySid(sid);
    }

    /**
     * 更新学生信息
     *
     * @param student
     */
    @Override
    public Boolean updateStudentBySid(StudentDO student) {
        student.setGmt_modified(new Date());
        return this.studentMapper.updateStudent(student);
    }

    /**
     * 添加学生(* 必须先添加账号)
     *
     * @param student
     */
    @Override
    public Boolean addStudent(StudentDO student) {
        StudentDO s = this.getStudentBySid(student.getSid());
        if(s!=null) throw new BadRequestException("添加学生的学号已存在");
        student.setGmt_create(new Date());
        return this.addStudent(student);
    }

    /**
     * 根据组织信息查询所有学生
     */
    @Override
    public Page<StudentDO> getStudentPageByOrg(Integer sch, Integer col, Integer maj, Integer page, Integer size) {
        Integer from = (page-1)*size;
        Integer to = page * size;
        Integer totalSize = this.studentMapper.getTotalBySch_Col_Maj(sch,col,maj);
        Integer totalPage = (int) Math.ceil((double) totalSize / size);
        List<StudentDO> pageData = this.studentMapper.getStudentBySch_Col_Maj(sch, col, maj, from, to);
        return new Page<>(pageData,totalSize,totalPage);
    }


}
