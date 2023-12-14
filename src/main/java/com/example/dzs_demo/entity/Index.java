package com.example.dzs_demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "indexinfo")
public class Index {

    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;

    @TableField(value = "pid")
    private Integer pid;

    @TableField(value = "level")
    private Integer level;

    @TableField(value = "name")
    private String name;
}
