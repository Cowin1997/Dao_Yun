package cn.edu.fzu.daoyun.dto;


import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
public class MenuDTO  implements Serializable {
    private List<MenuDTO> children;
    private String name;
    private String title;
    private String component;
    private String icon;
    private String path;
    private Integer parent_id;
    private Integer id;
}
