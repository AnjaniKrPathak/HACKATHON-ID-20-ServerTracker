package com.example.servertracker.user.controller;

import com.example.servertracker.user.entity.UserDetail;
import com.example.servertracker.user.service.IUserService;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
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
}
