package com.example.dzs_demo.controller;

import com.alibaba.fastjson2.JSON;
import com.example.dzs_demo.dao.SearchDao;
import com.example.dzs_demo.entity.*;
import com.example.dzs_demo.service.*;
import com.example.dzs_demo.utils.MyUtils;
import com.example.dzs_demo.utils.Result;
import com.example.dzs_demo.utils.ZipUtils;
import com.fasterxml.jackson.databind.ser.std.RawSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.xml.crypto.Data;
import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static com.example.dzs_demo.utils.MyUtils.*;

@RestController
public class FileUploadController {
    @Autowired
    PassageService passageService;
    @Autowired
    IndexService indexService;
    @Autowired
    NoteService noteService;
    @Autowired
    SearchService searchService;
    @Autowired
    UserService userService;
    @Autowired
    MarkService markService;

    @GetMapping("/exportData")
    public String exportData() throws IOException{
        Date date=new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd-hh-mm");
        String t=formatter.format(date);
        File f=new File(System.getProperty("user.dir")+"\\"+t+".zip");

        List<Index> allIndexData = indexService.getAllData();
        MyUtils.ObjectToJson(allIndexData,"Index.json");
        List<Passage> allPassageData = passageService.getAllData();
        MyUtils.ObjectToJson(allPassageData,"Passage.json");
        List<Note> allNoteData = noteService.getAllData();
        MyUtils.ObjectToJson(allNoteData,"Note.json");
        List<Search> allSearchData = searchService.getAllData();
        MyUtils.ObjectToJson(allSearchData,"Search.json");
        List<User> allUserData = userService.getAllData();
        MyUtils.ObjectToJson(allUserData,"User.json");
        List<Mark> allMarkData = markService.getAllData();
        MyUtils.ObjectToJson(allMarkData,"Mark.json");

        ZipUtils.toZip(System.getProperty("user.dir")+"/static",
                new FileOutputStream(f),
                true);
//        List<Index> indexList = JSON.parseArray(jsonFIleToJsonString(System.getProperty("user.dir") + "/JSONData/index.json"), Index.class);
//        System.out.println(indexList);
        return Result.data("导出成功，导出的数据包存放至"+f.getAbsolutePath());
    }
    @PostMapping ("/importData")
    public String importData(MultipartFile dataPack) throws IOException{
        String filename="";
        try{
            if(dataPack.getOriginalFilename().endsWith(".zip")){
                filename="datapack.zip";
            }else{
                return Result.error(500,"文件类型不对");
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
            return Result.error(500,"文件为空");
        }

        File file=new File(System.getProperty("user.dir")+"/"+filename);
        try{
            dataPack.transferTo(file);
            ZipUtils.unZip(file.getAbsolutePath(),System.getProperty("user.dir")+"/");
        }catch (Exception e){
            System.out.println(e);
            return Result.error(500,"解压时出错，请联系后台人员");
        }


        try {
            List<Index> indexList = JSON.parseArray(jsonFIleToJsonString(System.getProperty("user.dir") + "/static/JSONData/index.json"), Index.class);
            List<Note> noteList = JSON.parseArray(jsonFIleToJsonString(System.getProperty("user.dir") + "/static/JSONData/note.json"), Note.class);
            List<Passage> passageList = JSON.parseArray(jsonFIleToJsonString(System.getProperty("user.dir") + "/static/JSONData/passage.json"), Passage.class);
            List<Search> searchList = JSON.parseArray(jsonFIleToJsonString(System.getProperty("user.dir") + "/static/JSONData/search.json"), Search.class);
            List<User> userList = JSON.parseArray(jsonFIleToJsonString(System.getProperty("user.dir") + "/static/JSONData/user.json"), User.class);
            List<Mark> markList = JSON.parseArray(jsonFIleToJsonString(System.getProperty("user.dir") + "/static/JSONData/mark.json"), Mark.class);
            markService.insertAllData(markList);
            indexService.insertAllData(indexList);
            noteService.insertAllData(noteList);
            passageService.insertAllData(passageList);
            searchService.insertAllData(searchList);
            userService.insertAllData(userList);
        }catch (Exception e){
            System.out.println(e.getMessage());
            return Result.error(500,"导入数据库时出错");
        }

        return Result.data("ok");
//        String userpath = System.getProperty("user.dir")+"\\";
//        File file=new File(userpath+"datapack.zip");
//        FileSystemUtils.deleteRecursively(file);
//
//        System.out.println(dataPack.isEmpty());
//        String filename="";
//
//        if(dataPack.getOriginalFilename().endsWith(".zip")){
//            filename=dataPack.getOriginalFilename();
//        }
//
//        try{
//            dataPack.transferTo(file);
//            ZipUtils.unZip(file.getAbsolutePath(),userpath+"datapack");
//        }catch (Exception e) {
//            System.out.println(e);
//            return Result.error(500, "fail");
//        }
//        file.delete();
//        return Result.data("ok");
    }


    @PostMapping("/upload")
    public String up(MultipartFile photo,
                     @RequestParam("aid")Integer aid,
                     @RequestParam("description")String str,
                     @RequestParam("xuanzhongorderid")Integer orderid
                     ) throws IOException{
//        System.out.println();
//        System.out.println(photo.getOriginalFilename());
        String path = System.getProperty("user.dir")+"/static/img/";
        File dir = new File(path);
        // 如果不存在则创建目录
        if(!dir.exists()){
            dir.mkdirs();
        }
        System.out.println(dir.getAbsolutePath());
        String filename="";
        if(photo.getOriginalFilename().endsWith(".png")){
            filename=getRandomString()+".png";
        } else if (photo.getOriginalFilename().endsWith(".jpg")) {
            filename=getRandomString()+".jpg";
        } else if(photo.getOriginalFilename().endsWith(".mp4")){
            filename=getRandomString()+".mp4";
        }
        File file=new File(path+filename);
        System.out.println(file.getAbsolutePath());
        try{
            photo.transferTo(file);
        }catch (Exception e){
            System.out.println(e);
            return Result.error(500,"fail");
        }
        List<Passage> passageList = passageService.selectPassageByAid(aid);
        Integer max=0;
        for(int i=0;i<passageList.size();i++){
            if(passageList.get(i).getOrderid()>max){
                max=passageList.get(i).getOrderid();
            }
        }
        if(photo.getOriginalFilename().endsWith(".mp4")){
            passageService.addPassageText(aid,max+1,"vid","/api/static/img/"+filename+"_"+str);
        }else{
            passageService.addPassageText(aid, max+1, "img", "/api/static/img/"+filename+"_"+str);
        }
        return Result.data("ok");
    }
}
