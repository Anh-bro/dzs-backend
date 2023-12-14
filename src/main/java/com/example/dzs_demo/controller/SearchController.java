package com.example.dzs_demo.controller;

import com.example.dzs_demo.entity.Search;
import com.example.dzs_demo.service.SearchService;
import com.example.dzs_demo.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/search")
public class SearchController {

    @Autowired
    SearchService searchService;

    @RequestMapping(value = "getSearch",method = RequestMethod.GET)
    public String getSearch(@RequestParam("username") String username){
        List<Search> searchList = searchService.selectSearch(username);
        if(searchList.size()==0){
            return Result.data(searchList);
        }
        Integer length=10;
        if(searchList.size()<10){
            length=searchList.size();
        }
        List<Search> resultList=new ArrayList<Search>();
        List<Integer> timeList=new ArrayList<Integer>();
        for(int i=0;i<length;i++){
            //找出对象列表中出现次数最多的一个;
            Integer atime=1;
            String v=searchList.get(i).getSearchcontent();
            for(int j=0;j<length;j++){
                if(searchList.get(j).getSearchcontent().equals(v)){
                    atime++;
                }
            }
            timeList.add(atime);
        }
        Integer max=0;
        Integer ind=0;
        for(int i=0;i<timeList.size();i++){
            if(timeList.get(i)>max){
                max=timeList.get(i);
                ind=i;
            }
        }
        resultList.add(0, searchList.get(ind));
        for(int i=0;i<length;i++){
            boolean isIn=false;
            for(Search s:resultList){
                if(s.getSearchcontent()==searchList.get(i).getSearchcontent()){
                    isIn=true;
                }
            }
            if(!isIn){
                resultList.add(searchList.get(i));
            }
        }

        return Result.data(resultList).replace("searchcontent","value");
    }
}
