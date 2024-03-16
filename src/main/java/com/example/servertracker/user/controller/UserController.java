package com.example.servertracker.user.controller;

import com.example.servertracker.user.entity.UserDetail;
import com.example.servertracker.user.entity.UserServerDetail;
import com.example.servertracker.user.service.IUserService;
import org.apache.catalina.User;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
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
    @GetMapping("/getUserServerBasedOnUserId")
    public ResponseEntity<?> getUserServerDetailBasedOnUserId(@RequestParam Long userId ){
     List<UserServerDetail> userServerDetails=userService.getUserServerBasedOnUserId(userId);
        return new ResponseEntity<>(userServerDetails,HttpStatus.OK);
    }


    @PostMapping("/createUserWithCSV")
    public ResponseEntity<?> createUserWithCSV(@RequestParam ("file") MultipartFile file){

        List<String> response = new ArrayList<>();
        try {
            BufferedReader fileReader= new BufferedReader(new InputStreamReader(file.getInputStream()));
            CSVParser csvParser = new CSVParser(fileReader, CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());
            Iterable<CSVRecord> csvRecords = csvParser.getRecords();
            for(CSVRecord csvRecord : csvRecords){
                UserDetail user=new UserDetail();
                user.setName(csvRecord.get("name"));
                user.setEmail(csvRecord.get("email"));
                user.setProject(csvRecord.get("project"));
                user.setPassword("123456");


                try{
                    UserDetail userDetail= userService.addUserDetail(user);
                    response.add("Created User "+userDetail.getName()+"with id:"+userDetail.getEmail());
                }
                catch (Exception e){
                    response.add("Unable to created User "+e.getMessage());
                }
            }
        }catch (IOException e){

        }
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

}
