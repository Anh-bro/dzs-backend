package com.example.dzs_demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName(value = "search")
public class Search implements Comparable<Search>{
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;

    @TableField(value = "content")
    private String searchcontent;
    @TableField(value = "username")
    private String username;

    @Override
    public int compareTo( Search o) {
        if(this.getId()>o.getId())
        {
            return 1;
        }
        else if(this.getId()<o.getId())
        {
            return -1;
        }
        else
        {
            return 0;
        }
    }
}
