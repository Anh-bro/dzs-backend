package com.example.dzs_demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName(value = "mark")
public class Mark {
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;

    @TableField(value = "aid")
    private Integer aid;

    @TableField(value = "username")
    private String username;

    @TableField(value = "markdate")
    private Date date;
}
