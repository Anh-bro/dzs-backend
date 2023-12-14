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
@TableName(value = "passage")
public class Passage implements Comparable<Passage>{

    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;

    @TableField(value = "aid")
    private Integer aid;

    @TableField(value = "orderid")
    private Integer orderid;

    @TableField(value = "level")
    private String level;

    @TableField(value = "content")
    private String content;

    public int compareTo(Passage o)
    {
        if(this.getOrderid()>o.getOrderid())
        {
            return 1;
        }
        else if(this.getOrderid()<o.getOrderid())
        {
            return -1;
        }
        else
        {
            return 0;
        }
    }
}
