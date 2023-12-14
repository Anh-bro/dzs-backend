package com.example.dzs_demo.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.example.dzs_demo.dao.PassageDao;
import com.example.dzs_demo.entity.Passage;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
public class PassageService {
    @Autowired
    PassageDao passageDao;


    public String deletePassageByAidOrderid(Integer aid,Integer orderid){
        Passage p= passageDao.selectOne(new QueryWrapper<Passage>().like("aid",aid).like("orderid",orderid));
        if(p.getLevel().equals("img")){
            String temp[]=p.getContent().split("/");
            String name=temp[temp.length-1].split("_")[0];
            String path = System.getProperty("user.dir")+"/static/img/";
            File file = new File(path+name);
            boolean deleted = file.delete();
            System.out.println(deleted);
        }
        if(p.getLevel().equals("vid")){
            String temp[]=p.getContent().split("/");
            String name=temp[temp.length-1].split("_")[0];
            String path = System.getProperty("user.dir")+"/static/img/";
            File file = new File(path+name);
            boolean deleted = file.delete();
            System.out.println(deleted);
        }
        passageDao.delete(new QueryWrapper<Passage>().like("aid",aid).like("orderid",orderid));
        return "sucess";
    }

    public List<Passage> selectPassageByAid(Integer aid) {
        List<Passage> passageList=passageDao.selectList(new QueryWrapper<Passage>().like("aid",aid));
        System.out.println(passageList);
        Collections.sort(passageList);
        return passageList;
    }
    public List<Passage> getAllData() {
        List<Passage> passageList=passageDao.selectList(null);
        return passageList;
    }
    public String addPassageText(Integer aid,Integer orderid,String level,String content){
        Passage passage = new Passage();
        passage.setAid(aid);
        passage.setOrderid(orderid);
        passage.setLevel(level);
        passage.setContent(content);
        passageDao.insert(passage);
        return "sucess";
    }
    public String addPassageTextUp(Integer aid,String level,String content,Integer xuanzhongorderid){
        Passage passage = new Passage();
        passage.setAid(aid);
        passage.setOrderid(xuanzhongorderid);

        List<Passage> passageList = passageDao.selectList(new QueryWrapper<Passage>().eq("aid", aid).ge("orderid", xuanzhongorderid));
        for (int i = 0; i < passageList.size(); i++) {
            passageDao.update(null,new UpdateWrapper<Passage>().eq("aid",aid).eq("orderid",passageList.get(i).getOrderid()).set("orderid",passageList.get(i).getOrderid()+1));
        }

        passage.setLevel(level);
        passage.setContent(content);
        passageDao.insert(passage);
        return "sucess";
    }

    public void insertAllData(List<Passage> passageList) {
        passageDao.delete(null);
        for(Passage i : passageList){
            passageDao.insert(i);
        }
    }
}
