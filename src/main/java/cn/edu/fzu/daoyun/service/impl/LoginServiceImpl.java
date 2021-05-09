package cn.edu.fzu.daoyun.service.impl;

import cn.edu.fzu.daoyun.base.Result;
import cn.edu.fzu.daoyun.config.security.SmsAuthenticationToken;
import cn.edu.fzu.daoyun.constant.AccountEnum;
import cn.edu.fzu.daoyun.constant.ResultCodeEnum;
import cn.edu.fzu.daoyun.constant.RoleEnum;
import cn.edu.fzu.daoyun.dto.JwtUserDTO;
import cn.edu.fzu.daoyun.dto.StudentDTO;
import cn.edu.fzu.daoyun.dto.TeacherDTO;
import cn.edu.fzu.daoyun.entity.StudentDO;
import cn.edu.fzu.daoyun.entity.TeacherDO;
import cn.edu.fzu.daoyun.entity.UserAuthDO;
import cn.edu.fzu.daoyun.entity.UserDO;
import cn.edu.fzu.daoyun.exception.BadRequestException;
import cn.edu.fzu.daoyun.mapper.StudentMapper;
import cn.edu.fzu.daoyun.mapper.TeacherMapper;
import cn.edu.fzu.daoyun.mapper.UserMapper;
import cn.edu.fzu.daoyun.query.FastRegisterQuery;
import cn.edu.fzu.daoyun.query.LoginQuery;
import cn.edu.fzu.daoyun.query.PwdForgetQuery;
import cn.edu.fzu.daoyun.query.RegisterQuery;
import cn.edu.fzu.daoyun.service.OnlineUserService;
import cn.edu.fzu.daoyun.utils.JwtUtils;
import cn.edu.fzu.daoyun.utils.RedisUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
@Slf4j
@Service
@RequiredArgsConstructor
public class LoginServiceImpl {
    private final AuthenticationManager authenticationManager;
    private final RedisUtils redisUtils;
    private final JwtUtils jwtUtils;
    private final OnlineUserService onlineUserService;
    private final PasswordEncoder passwordEncoder;
    @Resource
    private UserMapper userMapper;
    @Resource
    private StudentMapper studentMapper;
    @Resource
    private TeacherMapper teacherMapper;

    /**
     *  通过用户名密码登录
     * @param loginQuery
     * @return
     */
    public String loginByLocal(LoginQuery loginQuery){
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginQuery.getIdentifier(), loginQuery.getCredential());
        String token = doLogin(authenticationToken);
        return token;
    }

    public String doLogin(AbstractAuthenticationToken authenticationToken){
        Authentication authentication = authenticationManager.authenticate(authenticationToken);
        // 存储安全上下文(security context)的信息
        SecurityContextHolder.getContext().setAuthentication(authentication);
        // 登录成功
        JwtUserDTO jwtUserDTO = (JwtUserDTO) authentication.getPrincipal();
        String token = jwtUtils.generate(jwtUserDTO.getUserDTO().getUser());
        // 保存在线信息
        onlineUserService.save(jwtUserDTO, token);
        // 踢掉之前已经登录的 token
        //**
        //**
        return token;
    }

    public String loginByPhone(LoginQuery loginQuery){
        SmsAuthenticationToken authenticationToken =
                new SmsAuthenticationToken(loginQuery.getIdentifier(), loginQuery.getCredential());
        String token = doLogin(authenticationToken);
        return token;
    }

    @Transactional
    public StudentDTO registStudent(RegisterQuery registerQuery){
        if(userMapper.selectUserAuthByIdentifier(registerQuery.getUsername())!=null)
            throw new BadRequestException("用户名已经注册存在");
        if(userMapper.selectUserAuthByIdentifier(registerQuery.getPhone())!=null)
            throw new BadRequestException("手机已经注册存在");
           UserDO user = new UserDO(null,new Date(),null,registerQuery.getUsername(),null, RoleEnum.STUDENT,true);
           this.userMapper.insertUser(user);
           log.info("registStudent add user id:" + user.getId());
           UserAuthDO userAuth = new UserAuthDO(null,new Date(),null,user.getId(),AccountEnum.LOCAL,registerQuery.getUsername(),passwordEncoder.encode(registerQuery.getPassword()));
           this.userMapper.insertUserAuth(userAuth);
           userAuth.setIdentifier(registerQuery.getPhone());
           userAuth.setIdentity_type(AccountEnum.PHONE);
           this.userMapper.insertUserAuth(userAuth);
           StudentDO student = new StudentDO();
           student.setSid(registerQuery.getIdNumber());
           student.setUser_id(user.getId());
           student.setGmt_create(new Date());
           this.studentMapper.insertStudent(student);
           return new StudentDTO(user);
    }

    @Transactional
    public TeacherDTO registTeacher(RegisterQuery registerQuery){
        if(userMapper.selectUserAuthByIdentifier(registerQuery.getUsername())!=null)
            throw new BadRequestException("用户名已经注册存在");
        if(userMapper.selectUserAuthByIdentifier(registerQuery.getPhone())!=null)
            throw new BadRequestException("手机已经注册存在");
        UserDO user = new UserDO(null,new Date(),null,registerQuery.getUsername(),null, RoleEnum.TEACHER,true);
        this.userMapper.insertUser(user);
        log.info("registStudent add user id:" + user.getId());
        UserAuthDO userAuth = new UserAuthDO(null,new Date(),null,user.getId(),AccountEnum.LOCAL,registerQuery.getUsername(),registerQuery.getPassword());
        this.userMapper.insertUserAuth(userAuth);
        userAuth.setIdentifier(registerQuery.getPhone());
        userAuth.setIdentity_type(AccountEnum.PHONE);
        this.userMapper.insertUserAuth(userAuth);
        TeacherDO teacher = new TeacherDO();
        teacher.setTid(registerQuery.getIdNumber());
        teacher.setUser_id(user.getId());
        teacher.setGmt_create(new Date());
        this.teacherMapper.insertTeacher(teacher);
        return new TeacherDTO(user);
    }


    public void fastRegist(FastRegisterQuery registerQuery){
        if(userMapper.selectUserAuthByIdentifier(registerQuery.getPhone())!=null)
            throw new BadRequestException("手机已经注册存在");
        UserDO user = new UserDO(null,new Date(),null,registerQuery.getPhone(),null, RoleEnum.UNKOWN,true);
        this.userMapper.insertUser(user);
        UserAuthDO userAuth = new UserAuthDO(null,new Date(),null,user.getId(),AccountEnum.PHONE,registerQuery.getPhone(),passwordEncoder.encode(registerQuery.getPhone()));
        this.userMapper.insertUserAuth(userAuth);
    }

    @Transactional
    public Boolean pwdReset(PwdForgetQuery query){
       return userMapper.updatePwd(query.getPhone(),passwordEncoder.encode(query.getNewPass()));
    }
}
