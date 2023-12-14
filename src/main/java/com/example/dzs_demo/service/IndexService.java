package com.example.dzs_demo.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.segments.MergeSegments;
import com.example.dzs_demo.dao.IndexDao;
import com.example.dzs_demo.entity.Index;
import com.example.dzs_demo.entity.User;
import com.sun.media.sound.RIFFInvalidDataException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class IndexService {

    @Autowired
    IndexDao indexDao;

    public String insertAllData(List<Index> l){
        indexDao.delete(null);
        for(Index i : l){
            indexDao.insert(i);
        }
        return "ok";
    }
    public List<Index> getAllData(){
        return indexDao.selectList(null);
    }
    public List<Map<String, Object>> selectIndex() {
        List<Index> indexList = indexDao.selectList(null);
        List<Map<String, Object>> level1 = new ArrayList<>();
        List<Map<String, Object>> level2 = new ArrayList<>();
        List<Map<String, Object>> level3 = new ArrayList<>();
        indexList.forEach(item -> {
            List<Map<String, Object>> t = new ArrayList<>();
            Map<String, Object> m = new HashMap<>();
            m.put("id", item.getId());
            m.put("pid", item.getPid());
            m.put("label", item.getName());
            m.put("children", t);
            if (item.getLevel() == 1) {
                level1.add(m);
            } else if (item.getLevel() == 2) {
                level2.add(m);
            } else {
                level3.add(m);
            }
        });
        for (int i = 0; i < level3.size(); i++) {      //对叶子节点遍历
            for (int j = 0; j < level2.size(); j++) {   //
                if (level2.get(j).get("id") == level3.get(i).get("pid")) {
                    List<Map<String, Object>> obj = (List<Map<String, Object>>) level2.get(j).get("children");
                    obj.add(level3.get(i));
                }
            }
        }
        for (int i = 0; i < level2.size(); i++) {
            for (int j = 0; j < level1.size(); j++) {
                if (level1.get(j).get("id") == level2.get(i).get("pid")) {
                    List<Map<String, Object>> obj = (List<Map<String, Object>>) level1.get(j).get("children");
                    obj.add(level2.get(i));
                }
            }
        }
        System.out.println(level1);
        return level1;
    }

    public List<Index> selectOriginalIndex(){
        List<Index> indexList = indexDao.selectList(null);
        return indexList;
    }
    public Integer selectAllIndex(){
        List<Index> indexList = indexDao.selectList(null);
        Integer max=0;
        for(int i=0;i<indexList.size();i++){
            if(indexList.get(i).getId()>max){
                max=indexList.get(i).getId();
            }
        }
        return max;
    }
    public String deleteIndexById(Integer id){
        indexDao.deleteById(id);
        return "sucess";
    }
    public String getTitleById(Integer id){
        Index index = indexDao.selectOne(new QueryWrapper<Index>().eq("id", id));
        return index.getName();
    }

    public String addIndexNode(Integer id,Integer pid,String label){
        if(id!=pid) {
            Index index = indexDao.selectOne(new QueryWrapper<Index>().eq("id", pid));
            Integer level = index.getLevel() + 1;
            indexDao.insert(new Index(id,pid,level, label));
        }
        else {
            indexDao.insert(new Index(id,pid,1, label));
        }
//        System.out.println(level);

        return "sucess";
    }
}
