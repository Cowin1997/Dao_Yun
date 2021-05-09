package cn.edu.fzu.daoyun.base;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Api
public class Page<T> implements Serializable {
    @ApiModelProperty("分页数据")
    private List<T> pageData;

    @ApiModelProperty("总条数")
    private Integer totalSize;

    @ApiModelProperty("总页数")
    private Integer totalPage;

}
