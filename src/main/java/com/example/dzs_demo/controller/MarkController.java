package com.example.dzs_demo.controller;

import com.alibaba.fastjson2.JSON;
import com.example.dzs_demo.entity.Mark;
import com.example.dzs_demo.entity.Note;
import com.example.dzs_demo.service.IndexService;
import com.example.dzs_demo.service.MarkService;
import com.example.dzs_demo.utils.Result;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/mark")
public class MarkController {
    @Autowired
    MarkService markService;
    @Autowired
    IndexService indexService;

    @RequestMapping(value = "/getAllMark",method = RequestMethod.GET)
    public String getAllMark(@RequestParam("username") String username){
        List<Mark> markList = markService.getAllMark(username);
        System.out.println(markList);
        String r= Result.data(markList);
        Map map= JSON.parseObject(r, Map.class);

        List<Map<String,Object>> l=(List<Map<String,Object>>)map.get("data");
        for(int i=0;i<l.size();i++){
            Integer aid=(Integer)l.get(i).get("aid");
            if(indexService.getTitleById(aid)==null){
                return  Result.data(markList);
            }
            String title=indexService.getTitleById(aid);
            l.get(i).put("title",title);
        }
        System.out.println(map.get("data"));

        return Result.data(l);

    }

    @RequestMapping(value = "/addmark",method = RequestMethod.GET)
    public String addmark(@RequestParam("aid")Integer aid,
                             @RequestParam("username")String username){
        markService.addMark(aid,username);
        return Result.data("ok");
    }
    @RequestMapping(value = "/ismark",method = RequestMethod.GET)
    public String ismark(@RequestParam("aid")Integer aid,
                          @RequestParam("username")String username){
        return markService.isMark(aid,username).toString();
    }
    @RequestMapping(value = "/deletemark",method = RequestMethod.GET)
    public String deletemark(@RequestParam("aid")Integer aid,
                         @RequestParam("username")String username){
        return markService.deleteMark(aid,username);
    }
}
