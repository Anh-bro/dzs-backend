package com.example.dzs_demo.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.dzs_demo.dao.MarkDao;
import com.example.dzs_demo.dao.NoteDao;
import com.example.dzs_demo.entity.Index;
import com.example.dzs_demo.entity.Mark;
import com.example.dzs_demo.entity.Note;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class MarkService {

    @Autowired
    MarkDao markDao;
    public List<Mark> getAllData(){
        return markDao.selectList(null);
    }
    public String insertAllData(List<Mark> l){
        markDao.delete(null);
        for(Mark i : l){
            markDao.insert(i);
        }
        return "ok";
    }
    public List<Mark>  getAllMark(String username){
        return markDao.selectList(new QueryWrapper<Mark>().eq("username", username));

    }

    public String  addMark(Integer aid,String username){
        Mark mark =new Mark();
        mark.setAid(aid);
        mark.setUsername(username);
        mark.setDate(new Date());
        markDao.insert(mark);
        return "add";
    }
    public String  deleteMark(Integer aid,String username){
        markDao.delete(new QueryWrapper<Mark>().eq("aid",aid).eq("username",username));
        return "success";
    }

    public Boolean  isMark(Integer aid,String username){
        List<Mark> marks = markDao.selectList(new QueryWrapper<Mark>().like("aid", aid).like("username", username));
        if(marks.size()==1){
            return true;
        }else{
            return false;
        }
    }
}
