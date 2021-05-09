package cn.edu.fzu.daoyun.dto;

import cn.edu.fzu.daoyun.entity.UserDO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class TeacherDTO {
    private UserDO account;
}
