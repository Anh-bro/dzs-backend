package com.example.dzs_demo.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.dzs_demo.entity.Mark;
import com.example.dzs_demo.entity.Passage;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MarkDao extends BaseMapper<Mark> {

}
