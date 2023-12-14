package com.example.dzs_demo.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.dzs_demo.dao.SearchDao;
import com.example.dzs_demo.entity.Passage;
import com.example.dzs_demo.entity.Search;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
@Service
public class SearchService {
    @Autowired
    SearchDao searchDao;

    public List<Search> getAllData(){
        return searchDao.selectList(null);
    }
    public List<Search> selectSearch(String username){
        List<Search> searchList = searchDao.selectList(new QueryWrapper<Search>().like("username",username));
        Collections.reverse(searchList);
        return searchList;
    }
    public void addSearch(String content,String username){
        Search search=new Search();
        search.setSearchcontent(content);
        search.setUsername(username);
        searchDao.insert(search);

    }

    public void insertAllData(List<Search> searchList) {
        searchDao.delete(null);
        for(Search i : searchList){
            searchDao.insert(i);
        }
    }
//    public List<Passage> selectPassageByAid(Integer aid) {
//
//    }
}
