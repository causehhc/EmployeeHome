package com.csi.emphome.demo.rest.user;

import com.csi.emphome.demo.service.user.UserService;
import com.csi.emphome.demo.service.user.dto.UserListQuery;
import com.csi.emphome.demo.service.user.dto.UserSearchData;
import com.csi.emphome.demo.service.user.dto.UserTemp;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping(value = "/vue-admin-template/user")
public class UserController {
    private final UserService userService;
    public UserController(UserService userService)
    {
        this.userService = userService;
    }
    /*显示*/
    @CrossOrigin
    @PostMapping(value = "/list")
    @ResponseBody
    public HashMap<String,Object> fetchList(@RequestBody UserListQuery data){
        return userService.fetchListFunc(data);
    }
    /*添加*/
    @CrossOrigin
    @PostMapping(value = "/create")
    @ResponseBody
    public HashMap<String, Object> createListItem(@RequestBody UserTemp data) {
        System.out.println("hello");
        System.out.println(data.getPassword());
        return userService.createListItemFunc(data);
    }
    /*修改*/
    @CrossOrigin
    @PostMapping(value = "/update")
    @ResponseBody
    public HashMap<String, Object>updateListItem(@RequestBody UserTemp data) {
        return userService.updateListItemFunc(data);
    }
    /*删除*/
    @CrossOrigin
    @PostMapping(value = "/delete")
    @ResponseBody
    public HashMap<String, Object> deleteListItem(@RequestBody List<UserTemp> data) {
        return userService.deleteListItemFunc(data);
    }
    /*搜索*/
    @CrossOrigin
    @PostMapping(value = "/listItem")
    @ResponseBody
    public HashMap<String, Object> fetchList(@RequestBody UserSearchData data) {
        return userService.fetchListItemFunc(data);
    }
    /*添加时检查用户名是否重复*/
    @CrossOrigin
    @PostMapping(value = "/check")
    @ResponseBody
    public HashMap<String, Object> checkSameName(@RequestBody UserTemp data) {
        return userService.checkSameNameFunc(data);
    }
}
