package com.example.dzs_demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName(value = "note")
public class Note {

    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;

    @TableField(value = "aid")
    private Integer aid;

    @TableField(value = "orderid")
    private Integer orderid;

    @TableField(value = "content")
    private String content;

    @TableField(value = "username")
    private String username;

}
