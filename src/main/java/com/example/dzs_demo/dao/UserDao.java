package com.example.dzs_demo.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.dzs_demo.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserDao extends BaseMapper<User> {
}
