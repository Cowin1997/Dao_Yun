package cn.edu.fzu.daoyun.entity;

import cn.edu.fzu.daoyun.base.BaseDO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MenuDO extends BaseDO implements Serializable {
    private Integer parent_id;
    private Integer sub_count;
    private Integer type;
    private String title;
    private String name;
    private String component;
    private Integer menu_sort;
    private String icon;
    private String path;
    private String permission;
    private Integer creator;
    private Integer reviser;
}
