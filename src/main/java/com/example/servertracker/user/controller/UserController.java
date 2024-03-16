package com.example.servertracker.user.controller;

import com.example.servertracker.user.entity.UserDetail;
import com.example.servertracker.user.entity.UserServerDetail;
import com.example.servertracker.user.service.IUserService;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    IUserService userService;
    @GetMapping("/test")
    public String testUser(){
        return "Test User";

    }
    @PostMapping("/addUSer")
    public ResponseEntity<?> addUser(@RequestBody UserDetail userDetail){

      UserDetail ud= userService.addUserDetail(userDetail);
        Map<String,Object> map=new LinkedHashMap<String,Object>();
        if(ud.getEmail()!=null){



            map.put("statusCode", HttpStatus.OK.value());
            map.put("data",ud);
            return new ResponseEntity<>(map, HttpStatus.OK);
        }
        else {
            map.clear();
            map.put("status",0);
            map.put("message","Server Not Added");
            return new ResponseEntity<>(map, HttpStatus.NOT_FOUND);
        }



    }
    @PutMapping("/updateUser")
    public ResponseEntity<?> updateUser(@RequestBody UserDetail userDetail){

        UserDetail ud= userService.updateUserDetail(userDetail);
        Map<String,Object> map=new LinkedHashMap<String,Object>();
        if(ud.getEmail()!=null){



            map.put("statusCode", HttpStatus.OK.value());
            map.put("data",ud);
            return new ResponseEntity<>(map, HttpStatus.OK);
        }
        else {
            map.clear();
            map.put("status",0);
            map.put("message","Server Not Added");
            return new ResponseEntity<>(map, HttpStatus.NOT_FOUND);
        }

    }
    @PostMapping("/addUserServer")
    public ResponseEntity<?> addUserServer(@RequestBody UserServerDetail userServerDetail){
        UserServerDetail userServerDetail1=userService.addUserServerDetail(userServerDetail);
        Map<String,Object> map=new LinkedHashMap<String,Object>();
        if(userServerDetail.getServerIp()!=null){



            map.put("statusCode", HttpStatus.OK.value());
            map.put("data",userServerDetail1);
            return new ResponseEntity<>(map, HttpStatus.OK);
        }
        else {
            map.clear();
            map.put("status",0);
            map.put("message","Server Not Added");
            return new ResponseEntity<>(map, HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/getAllUserServer")
    public List<UserServerDetail> getUserServerDetail(){
        List<UserServerDetail> userServerDetails=userService.getAllUserServer();
        return userServerDetails;
    }
    @GetMapping("/getUserServerBasedOnUserId/{userId}")
    public ResponseEntity<?> getUserServerDetailBasedOnUserId(@PathVariable Long userId ){
     List<UserServerDetail> userServerDetails=userService.getUserServerBasedOnUserId(userId);
        return new ResponseEntity<>(userServerDetails,HttpStatus.OK);
    }


}
