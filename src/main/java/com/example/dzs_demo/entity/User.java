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
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "userlogin")
public class User {

    @TableId(value = "username",type= IdType.ASSIGN_ID)
    private String username;

    @TableField(value = "password")
    private String password;
    @TableField(value="name")
    private String name;
    @TableField(value = "logintime")
    private Date logintime;
    @TableField(value = "lastaid")
    private Integer lastaid;

    public User(String username, String password, String name) {
        this.setUsername(username);
        this.setName(name);
        this.setPassword(password);
    }
}
