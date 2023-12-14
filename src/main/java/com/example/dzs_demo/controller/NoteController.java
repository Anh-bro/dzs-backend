package com.example.dzs_demo.controller;

import com.alibaba.fastjson2.JSON;
import com.example.dzs_demo.entity.Note;
import com.example.dzs_demo.service.IndexService;
import com.example.dzs_demo.service.NoteService;
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
@RequestMapping("/note")
public class NoteController {
    @Autowired
    NoteService noteService;

    @Autowired
    IndexService indexService;


    @RequestMapping(value = "/getNoteByAid",method = RequestMethod.GET)
    public String getNoteByAid(@RequestParam("aid")Integer aid,
                               @RequestParam("username")String username){
        List<Note> noteList = noteService.selectNoteByAid(aid,username);
        return Result.data(noteList);
    }

    //TODO
    @RequestMapping(value = "/getNote",method = RequestMethod.GET)
    public String getNoteBy(@RequestParam("username") String username){
        List<Note> noteList = noteService.selectNote(username);
        return Result.data(noteList);
    }
    @RequestMapping(value = "/getAllNote",method = RequestMethod.GET)
    public String getAllNote(@RequestParam("username") String username){
        List<Note> noteList = noteService.selectNote(username);
        System.out.println(noteList);
//        return  Result.data(noteList);
        String r= Result.data(noteList);
        Map map=JSON.parseObject(r, Map.class);

        List<Map<String,Object>> l=(List<Map<String,Object>>)map.get("data");
        for(int i=0;i<l.size();i++){
            Integer aid=(Integer)l.get(i).get("aid");
            if(indexService.getTitleById(aid)==null){
                return  Result.data(noteList);
            }
            String title=indexService.getTitleById(aid);
            l.get(i).put("title",title);
        }
        System.out.println(map.get("data"));

        return Result.data(l);

    }

    @RequestMapping(value = "/updateNote",method = RequestMethod.GET)
    public String updateNote(@RequestParam("aid")Integer aid,
                                @RequestParam("orderid")Integer orderid,
                                @RequestParam("content")String content,
                                @RequestParam("username")String username){
        val r = noteService.updateNote(aid, orderid, content ,username);
        return Result.data(r);
    }

    @RequestMapping(value = "/deleteNoteByAidOrderid",method = RequestMethod.GET)
    public String deleteNoteByAidOrderid(@RequestParam("aid")Integer aid,
                                         @RequestParam("orderid")Integer orderid){
        String r=noteService.deleteNotebyAidOrderid(aid,orderid);
        return Result.data(r);
    }
    @RequestMapping(value = "/deleteAllNoteByAid",method = RequestMethod.GET)
    public String deleteAllNoteByAid(@RequestParam("aid")Integer aid){
        String r=noteService.deleteAllNoteByAid(aid);
        System.out.println(r);
        return Result.data(r);
    }
}
