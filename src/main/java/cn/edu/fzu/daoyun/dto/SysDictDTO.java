package cn.edu.fzu.daoyun.dto;

import cn.edu.fzu.daoyun.entity.SysDictDO;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;


@Data
@AllArgsConstructor
public class SysDictDTO {
    private SysDictDO dict;
    private List<SysDictDO> sub;
}
