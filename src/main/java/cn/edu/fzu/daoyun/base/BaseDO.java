package cn.edu.fzu.daoyun.base;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class BaseDO implements Serializable {
    public Integer id;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @NotNull
    public Date gmt_create;
    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date gmt_modified;
}
