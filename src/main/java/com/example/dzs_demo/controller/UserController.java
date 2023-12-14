package com.example.dzs_demo.controller;

import com.example.dzs_demo.entity.User;
import com.example.dzs_demo.service.IndexService;
import com.example.dzs_demo.service.UserService;
import com.example.dzs_demo.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;
    @Autowired
    IndexService indexService;

    @GetMapping("/updateUserInfo")
    public String updateUserInfo(@RequestParam("username")String username,
                                 @RequestParam("password")String password,
                                 @RequestParam("name")String name,
                                 @RequestParam("user")String user){
        if(userService.selectUserByName(username)!=null){
            return Result.error(500,"账户名重复");
        }
        userService.updateUserInfo(user,username,password,name);
        return Result.data("ok");
    }

    @PostMapping("/addUser")
    public String addUser(@RequestBody Map<String,String> userform){
        System.out.println(userform);
        String username=userform.get("username");
        String password=userform.get("password");
        String name=userform.get("name");
        User user = userService.selectUserByName(username);
        if(user!=null){
            return Result.error(500,"账户名重复");
        }
        userService.addUser(username,password,name);
        User u = userService.selectUserByName(username);
        if(u!=null){
            return Result.data("ok");
        }else{
            return Result.error(500,"添加失败");
        }
    }

    @RequestMapping(value = "/isServerOpen",method = RequestMethod.GET)
    public String isServerOpen(){
        return Result.data("ok");
    }
    @GetMapping("/getUserInfo")
    public String getUserInfo(@RequestParam("username") String username ){
        User user = userService.selectUserByName(username);
        if(user==null){
            return Result.error(500,"查无此人");
        }
        return Result.data(user);
    }
    @RequestMapping(value = "/getUserLastAid",method = RequestMethod.GET)
    public String getUserLastAid(@RequestParam("username") String username){
        return Result.data(userService.getUserLastAid(username));
    }
    @RequestMapping(value = "/getUserLastRead",method = RequestMethod.GET)
    public String getUserLastRead(@RequestParam("username") String username){
        if(userService.getUserLastAid(username)!=null)
            return Result.data(indexService.getTitleById(userService.getUserLastAid(username)));
        else
            return "暂无";
    }
    @RequestMapping(value = "/setUserLastAid",method = RequestMethod.GET)
    public String setUserLastAid(@RequestParam("username") String username,
                                 @RequestParam("aid") Integer aid){
        return Result.data(userService.setUserLastAid(username,aid));
    }

    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public String login(@RequestBody Map<String,String> userform){
        System.out.println(userform);
        String username=userform.get("username");
        String password=userform.get("password");
//        if(username.equals("admin")&&password.equals("admin")){
//            return Result.data(new  User("admin","admin","管理员"));
//        }
        if (password == null || password.equals("") || username == null || username.equals("")) {
            // 说明用户账号或密码，不允许登录
            return Result.error(403,"请输入账户或密码");
        }
        User u=userService.selectUserByName(username);
        if (u == null) {
            // 说明该用户从来没登录过，需要在User表中添加该用户
            return Result.error(403,"账户不存在，请联系后台人员");

        } else {
            if(u.getPassword().equals(password)){
                u.setLogintime(userService.getUserLoginTime(username));
                userService.updateUserLoginTime(username);
                System.out.println(u);
                return Result.data(u);
            }
            else {
                return Result.error(403,"密码不正确");
            }
        }
    }


}
