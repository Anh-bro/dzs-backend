package com.example.dzs_demo.controller;

import com.alibaba.fastjson2.JSON;
import com.example.dzs_demo.entity.Passage;
import com.example.dzs_demo.service.IndexService;
import com.example.dzs_demo.service.PassageService;
import com.example.dzs_demo.service.SearchService;
import com.example.dzs_demo.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/passage")
public class PassageController {
    @Autowired
    PassageService passageService;
    @Autowired
    IndexService indexService;
    @Autowired
    SearchService searchService;

    @RequestMapping(value = "/getPassage",method = RequestMethod.GET)
    public String getPassage(@RequestParam("aid")Integer aid){
        List<Passage> passageList = passageService.selectPassageByAid(aid);
        return Result.data(passageList);
    }
    @RequestMapping(value = "/deletePassageContent",method = RequestMethod.GET)
    public String deletePassageContent(@RequestParam("aid")Integer aid,
                                 @RequestParam("orderid")Integer orderid){
        return Result.data(passageService.deletePassageByAidOrderid(aid,orderid));
    }

    @RequestMapping(value = "/addPassageText",method = RequestMethod.GET)
    public String addPassageText(@RequestParam("aid")Integer aid,
                                 @RequestParam("level")String level,
                                 @RequestParam("content")String content){
//        passageService.
        List<Passage> passageList = passageService.selectPassageByAid(aid);
        Integer max=0;
        for(int i=0;i<passageList.size();i++){
            if(passageList.get(i).getOrderid()>max){
                max=passageList.get(i).getOrderid();
            }
        }
        return Result.data(passageService.addPassageText(aid, max+1, level, content));
    }
    @RequestMapping(value = "/addPassageTextUp",method = RequestMethod.GET)
    public String addPassageTextUp(@RequestParam("aid")Integer aid,
                                 @RequestParam("level")String level,
                                 @RequestParam("content")String content,
                                   @RequestParam("xuanzhongorderid")Integer orderid){
        return Result.data(passageService.addPassageTextUp(aid,level,content,orderid));
    }

    @RequestMapping(value = "/addPassageImages",method = RequestMethod.GET)
    public String addPassageImages(@RequestParam("aid")Integer aid){
        List<Passage> passageList = passageService.selectPassageByAid(aid);
        return Result.data(passageList);
    }

    @RequestMapping(value = "/searchPassage",method = RequestMethod.GET)
    public String searchPassage(@RequestParam("input")String input,
                                @RequestParam("username")String username){
        List<Passage> resultList=new ArrayList<Passage>();
        List<Passage> passageList = passageService.getAllData();
        for(int i=0;i<passageList.size();i++){
            if(passageList.get(i).getContent().contains(input)){
                resultList.add(passageList.get(i));
            }
        }

        String r= Result.data(resultList);
        Map map= JSON.parseObject(r, Map.class);
        //将aid level转成中文
        List<Map<String,Object>> l=(List<Map<String,Object>>)map.get("data");
        for(int i=0;i<l.size();i++){
            Integer aid=(Integer)l.get(i).get("aid");
            String title=indexService.getTitleById(aid);
            l.get(i).put("title",title);
            if(l.get(i).get("level").equals("paragraph")){
                l.get(i).put("levelname","正文");
            }else if(l.get(i).get("level").equals("title1")){
                l.get(i).put("levelname","一级标题");
            }else if(l.get(i).get("level").equals("title2")){
                l.get(i).put("levelname","二级标题");
            }

        }
        System.out.println(input);
        System.out.println(username);
        searchService.addSearch(input,username);

        return Result.data(l);

    }
}
