package com.llh.webserver.pojo;

import com.llh.webserver.model.BasicModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * SimplePageQueryVO 简单的分页查询参数查询类
 * <p>
 * 简单查询都是查询模型类的字段。
 * <p>
 * CreatedAt: 2020-05-21 21:42
 *
 * @author llh
 */
@Data
@Accessors(chain = true)
@ApiModel("简单的分页查询参数查询类。")
public class SimplePageQueryVO<T extends BasicModel> {
    @ApiModelProperty(value = "页数",required = true)
    private Integer pageNumber;
    @ApiModelProperty(value = "每页数量",required = true)
    private Integer pageSize;
    @ApiModelProperty("排序字段")
    private String orderField;
    @ApiModelProperty("排序方式。asc or desc")
    private String orderType;
    @ApiModelProperty("查询条件。简单查询都是查询模型类的字段。")
    private T model;

}
