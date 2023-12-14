package com.example.dzs_demo.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.dzs_demo.dao.NoteDao;
import com.example.dzs_demo.entity.Index;
import com.example.dzs_demo.entity.Note;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {
    @Autowired
    NoteDao noteDao;

    public List<Note> getAllData(){
        return noteDao.selectList(null);
    }
    public List<Note> selectNote(String username){
        List<Note> noteList = noteDao.selectList(new QueryWrapper<Note>().like("username",username));
        System.out.println(noteList);
        return noteList;
    }

    public List<Note> selectNoteByAid(Integer aid,String username){
        List<Note> noteList = noteDao.selectList(new QueryWrapper<Note>().like("aid", aid));
        return noteList;
    }


    public String  updateNote(Integer aid,Integer orderid,String content,String username){
        if(content.equals("")){
            //TODO
            this.deleteNotebyAidOrderid(aid,orderid);
            return "delete";
        }
        Note note = noteDao.selectOne(new QueryWrapper<Note>().eq("aid", aid).eq("orderid", orderid));
        if(note==null){
            Note n=new Note();
            n.setAid(aid);
            n.setContent(content);
            n.setOrderid(orderid);
            n.setUsername(username) ;
            noteDao.insert(n);
            return "add";
        }else{
            note.setContent(content);
            noteDao.update(note,new QueryWrapper<Note>().eq("aid", aid).eq("orderid", orderid));
            return "update";
        }
    }

    public String deleteNotebyAidOrderid(Integer aid,Integer orderid){
        noteDao.delete(new QueryWrapper<Note>().eq("aid",aid).eq("orderid",orderid));
        return "ok";
    }
    public String deleteAllNoteByAid(Integer aid){
        noteDao.delete(new QueryWrapper<Note>().eq("aid",aid));
        return "ok";
    }


    public void insertAllData(List<Note> noteList) {
        noteDao.delete(null);
        for(Note i : noteList){
            noteDao.insert(i);
        }
    }
}
