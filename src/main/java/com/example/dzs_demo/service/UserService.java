package com.example.dzs_demo.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.example.dzs_demo.dao.NoteDao;
import com.example.dzs_demo.dao.SearchDao;
import com.example.dzs_demo.dao.UserDao;
import com.example.dzs_demo.entity.Note;
import com.example.dzs_demo.entity.Search;
import com.example.dzs_demo.entity.User;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class UserService {

    @Autowired
    UserDao userDao;
    @Autowired
    SearchDao searchDao;
    @Autowired
    NoteDao noteDao;

    public List<User> getAllData(){
        return userDao.selectList(null);
    }
    public void addUser(String username,String password,String name){
        userDao.insert(new User(username,password,name));
    }

    public User selectUserByName(String username){
        User user = userDao.selectById(username);
        return user;
    }

    public String updateUserInfo(String user,String username,String password,String name){
        User u = new User();
        u.setName(name);
        u.setPassword(password);
        u.setUsername(username);
        if (user.equals(username)){
            userDao.update(u,new UpdateWrapper<User>().eq("username",username));
        }else{
            userDao.delete(new QueryWrapper<User>().eq("username",user));
            userDao.insert(u);
            searchDao.update(null,new UpdateWrapper<Search>().eq("username",user).set("username",username));
            noteDao.update(null,new UpdateWrapper<Note>().eq("username",user).set("username",username));
        }

        return "ok";
    }
    public void updateUserLoginTime(String username){
        userDao.update(null,new UpdateWrapper<User>().eq("username",username).set("logintime",new Date()));
    }
    public Date getUserLoginTime(String username){
        return userDao.selectOne(new QueryWrapper<User>().eq("username",username)).getLogintime();
    }
    public Integer getUserLastAid(String username){
        User user = userDao.selectOne(new QueryWrapper<User>().eq("username", username));
        if(user==null){
            return -1;
        }else{
            return user.getLastaid();
        }
    }
    public String setUserLastAid(String username,Integer aid){
        User user = userDao.selectOne(new QueryWrapper<User>().eq("username", username));
        user.setLastaid(aid);
        userDao.update(user,new QueryWrapper<User>().eq("username",username));
        return "ok";
    }


    public void insertAllData(List<User> userList) {
        userDao.delete(null);
        for(User i : userList){
            userDao.insert(i);
        }
    }
}
