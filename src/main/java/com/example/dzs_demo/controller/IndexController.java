package com.example.dzs_demo.controller;

import com.alibaba.fastjson2.JSONObject;
import com.example.dzs_demo.entity.Index;
import com.example.dzs_demo.entity.Passage;
import com.example.dzs_demo.service.IndexService;
import com.example.dzs_demo.service.PassageService;
import com.example.dzs_demo.utils.Result;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/index")
public class IndexController {

    @Autowired
    IndexService indexService;
    @Autowired
    PassageService passageService;

    @RequestMapping(value = "/getIndex",method = RequestMethod.GET)
    public String getIndex(){
        List<Map<String, Object>> index=indexService.selectIndex();
        return Result.data(index);
    }
    @RequestMapping(value = "/getOriginalIndex",method = RequestMethod.GET)
    public String getOriginalIndex(){
        List<Index> index=indexService.selectOriginalIndex();

        return Result.data(index);

    }
    @RequestMapping(value = "/getIndexNum",method = RequestMethod.GET)
    public String getIndexNum(){
        Integer resultNum = indexService.selectAllIndex();
        return Result.data(resultNum);
    }

    @RequestMapping(value = "/addIndexNode",method = RequestMethod.POST)
    public String addIndexNode(@RequestBody Map<String,String> node){
        System.out.println(node);
        Integer id=Integer.valueOf(node.get("id"));
        Integer pid=Integer.valueOf(node.get("pid"));
        String label=node.get("label");
        String r=indexService.addIndexNode(id,pid,label);
        return Result.data(r);
    }

    @RequestMapping(value = "/deleteIndexNode",method = RequestMethod.GET)
    public String deleteIndexNode(@RequestParam("id")Integer id){
        List<Passage> passageList = passageService.selectPassageByAid(id);
        for(int i=0;i<passageList.size();i++){
            if(passageList.get(i).getLevel().equals("img")){
                passageService.deletePassageByAidOrderid(passageList.get(i).getAid(),passageList.get(i).getOrderid());
            }
        }

        String r=indexService.deleteIndexById(id);
        return Result.data(r);
    }

//    @RequestMapping(value = "/getTitleById",method = RequestMethod.GET)
//    public String getTitleById(@RequestParam("id") Integer id){
//        indexService.getTitleById(id);
//
//        return Result.data(index);
//
//    }
}
