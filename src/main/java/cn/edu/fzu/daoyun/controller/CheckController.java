package cn.edu.fzu.daoyun.controller;

import cn.edu.fzu.daoyun.annotation.AnonymousPostMapping;
import cn.edu.fzu.daoyun.base.Result;
import cn.edu.fzu.daoyun.constant.ResultCodeEnum;
import cn.edu.fzu.daoyun.entity.TaskDO;
import cn.edu.fzu.daoyun.mapper.CheckMapper;
import cn.edu.fzu.daoyun.query.CheckQuery;
import cn.edu.fzu.daoyun.utils.RedisUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

@Slf4j
@RestController
@Api(tags={"签到管理接口"})
@RequestMapping("/api/check")
public class CheckController {
    @Resource
    private CheckMapper checkMapper;
    @Resource
    private RedisUtils redisUtils;
    @AnonymousPostMapping("/task")
    @ApiOperation(value = "addTask",notes = "发起签到")
    public Result addTask(@RequestBody TaskDO task){
        task.setDisabled(true);
        Boolean aBoolean = this.checkMapper.addTask(task);
        this.checkMapper.updateTotalCheck(task.getCourse_cid());
        if(aBoolean){
            redisUtils.set(String.valueOf(task.getCourse_cid()),task,task.getTime_limit());
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    checkMapper.setTaskEnd(task.getId());
                }
            }, task.getTime_limit() * 1000);
            return Result.success(ResultCodeEnum.ADD_SUCCESS);
        }
        return Result.failure(ResultCodeEnum.ADD_FAILURE);
    }



    @AnonymousPostMapping("")
    @ApiOperation(value = "Check",notes = "学生签到")
    @Transactional
    public Result Check(@RequestBody CheckQuery query){
        Object o = this.redisUtils.get(String.valueOf(query.getClassId()));
        if(o!=null){ // 任务有效
            TaskDO task = (TaskDO) o;
            System.out.println(task.toString());
            query.setCreateTime(new Date());
            Integer checked = this.checkMapper.isChecked(task.getId(), query.getSid());
            if(checked>0) return Result.failure(ResultCodeEnum.HAS_CHECK);
            Boolean aBoolean = this.checkMapper.check(task.getId(), query);
            if(aBoolean) {
                Boolean bBoolean = this.checkMapper.updateStudentCheck(2, query.getSid(), task.getCourse_cid());
                if(bBoolean) return Result.success(ResultCodeEnum.CHECK_SUCCESS);
            }
            return Result.failure(ResultCodeEnum.CHECK_FAILURE);
        }
        return Result.failure(ResultCodeEnum.CHECK_TIMEOUT);
    }

}
