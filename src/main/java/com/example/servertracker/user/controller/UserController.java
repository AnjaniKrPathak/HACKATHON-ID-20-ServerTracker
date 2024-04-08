package com.example.servertracker.user.controller;

import com.example.servertracker.server.entity.ServerDashbordDetail;
import com.example.servertracker.server.service.IServerService;
import com.example.servertracker.user.dto.UserDto;
import com.example.servertracker.user.entity.UserDetail;
import com.example.servertracker.user.entity.UserRole;
import com.example.servertracker.user.entity.UserServerDetail;
import com.example.servertracker.user.service.IUserService;
import jakarta.validation.Valid;
import org.apache.catalina.User;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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
    @Autowired
    IServerService serverService;

    @GetMapping("/test")
    public String testUser() {
        return "Test User";

    }

    @PostMapping("/addUSer")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<?> addUser(@RequestBody UserDetail userDetail) {
        Map<String, Object> map = new LinkedHashMap<String, Object>();
        UserDetail existedUser=userService.findUserByEmail(userDetail.getEmail());
        if(existedUser !=null && existedUser.getEmail()!=null &&!existedUser.getEmail().isEmpty()){
            map.clear();
            map.put("status", HttpStatus.FORBIDDEN.value());
            map.put("message", "User Not Added "+existedUser.getEmail() +" Already Exist");
            return new ResponseEntity<>(map, HttpStatus.NOT_ACCEPTABLE);

        }
        UserDetail ud = userService.addUserDetail(userDetail);

        if (ud.getEmail() != null ) {


            map.put("statusCode", HttpStatus.OK.value());
            map.put("data", ud);
            return new ResponseEntity<>(map, HttpStatus.OK);
        } else {
            map.clear();
            map.put("status", 0);
            map.put("message", "User Not Added ");
            return new ResponseEntity<>(map, HttpStatus.NOT_FOUND);
        }


    }

    @PutMapping("/updateUser")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<?> updateUser(@RequestBody UserDetail userDetail) {

        UserDetail ud = userService.updateUserDetail(userDetail);
        Map<String, Object> map = new LinkedHashMap<String, Object>();
        if (ud.getEmail() != null) {


            map.put("statusCode", HttpStatus.OK.value());
            map.put("data", ud);
            return new ResponseEntity<>(map, HttpStatus.OK);
        } else {
            map.clear();
            map.put("status", 0);
            map.put("message", "Server Not Added");
            return new ResponseEntity<>(map, HttpStatus.NOT_FOUND);
        }

    }

    @PostMapping("/addUserServer")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<?> addUserServer(@RequestBody UserServerDetail userServerDetail,@RequestParam Long userId) {
        Map<String, Object> map = new LinkedHashMap<String, Object>();

        List<UserServerDetail> userServerDetails = userService.getUserServerBasedOnUserId(userId);




        for(UserServerDetail serverDetail:userServerDetails){
            if(serverDetail.getServerIp().equals(userServerDetail.getServerIp())){
                map.clear();
                map.put("status", HttpStatus.FORBIDDEN.value());
                map.put("message", "Server : " + serverDetail.getServerIp()+ " Already added for this User.");
                return new ResponseEntity<>(map, HttpStatus.NOT_FOUND);
            }

        }
        UserServerDetail userServerDetail1 = userService.addUserServerDetail(userServerDetail,userId);


        if (userServerDetail.getServerIp() != null) {


            map.put("statusCode", HttpStatus.OK.value());
            map.put("data", userServerDetail1);
            return new ResponseEntity<>(map, HttpStatus.OK);
        } else {
            map.clear();
            map.put("status", 0);
            map.put("message", "Server Not Added");
            return new ResponseEntity<>(map, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/getAllUserServer")
    @CrossOrigin(origins = "http://localhost:3000")
    public List<UserServerDetail> getUserServerDetail() {
        List<UserServerDetail> userServerDetails = userService.getAllUserServer();
        return userServerDetails;
    }

    

    @GetMapping("/getUserServerBasedOnUserId")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<?> getUserServerDetailBasedOnUserId(@RequestParam Long userId) {
        List<UserServerDetail> userServerDetails = userService.getUserServerBasedOnUserId(userId);
        return new ResponseEntity<>(userServerDetails, HttpStatus.OK);
    }


    @PostMapping("/createUserWithCSV")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<?> createUserWithCSV(@RequestParam("file") MultipartFile file) {

        List<String> response = new ArrayList<>();
        try {
            BufferedReader fileReader = new BufferedReader(new InputStreamReader(file.getInputStream()));
            CSVParser csvParser = new CSVParser(fileReader, CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());
            Iterable<CSVRecord> csvRecords = csvParser.getRecords();
            for (CSVRecord csvRecord : csvRecords) {
                UserDetail user = new UserDetail();
                user.setName(csvRecord.get("name"));
                user.setEmail(csvRecord.get("email"));
                user.setProject(csvRecord.get("project"));
                user.setPassword("123456");


                try {
                    UserDetail userDetail = userService.addUserDetail(user);
                    response.add("Created User " + userDetail.getName() + "with id:" + userDetail.getEmail());
                } catch (Exception e) {
                    response.add("Unable to create User " + e.getMessage());
                }
            }
        } catch (IOException e) {

        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/createUserServerWithCSV")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<?> createServerWithCSV(@RequestParam("file") MultipartFile file, @RequestParam Long userId) throws IOException {

        Map<String, Object> mapAddMultipleServer = new LinkedHashMap<String, Object>();
        BufferedReader fileReader = new BufferedReader(new InputStreamReader(file.getInputStream()));
        CSVParser csvParser = new CSVParser(fileReader, CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());
        Iterable<CSVRecord> csvRecords = csvParser.getRecords();
        for (CSVRecord csvRecord : csvRecords) {
            UserServerDetail userServerDetail = new UserServerDetail();
            userServerDetail.setDbServerPort(csvRecord.get("DbServerPort"));
            userServerDetail.setAppServerPort(csvRecord.get("AppServerPort"));
            userServerDetail.setAppUserPassword(csvRecord.get("AppUserPassword"));
            userServerDetail.setAppUserPassword(csvRecord.get("AppUserName"));
            userServerDetail.setAppUserPassword(csvRecord.get("DbUserName"));
            userServerDetail.setAppUserPassword(csvRecord.get("DbUserPassword"));
            userServerDetail.setUser(new UserDetail(Long.parseLong(csvRecord.get("User"))));

            userServerDetail.SetServerIp(csvRecord.get("ServerIp"));


            try {
                UserServerDetail serverDetail = userService.addUserServerDetail(userServerDetail,userId);
                //response.add("Created Server  " +serverDetail + "with id:" + serverDetail.getServerIp());
                mapAddMultipleServer.put("statusCode", HttpStatus.OK.value());
                mapAddMultipleServer.put("data", serverDetail);
                return new ResponseEntity<>(mapAddMultipleServer, HttpStatus.OK);
            } catch (Exception e) {
                mapAddMultipleServer.clear();
                mapAddMultipleServer.put("status", 0);
                mapAddMultipleServer.put("message", "Server Not Added");
                return new ResponseEntity<>(mapAddMultipleServer, HttpStatus.NOT_FOUND);
            }

        }
        return null;
    }

    @GetMapping("/findUserByEmail")
    public ResponseEntity<?> findUserByEmail(@RequestParam String email){
        UserDetail userDetail=userService.findUserByEmail(email);
        return new ResponseEntity<>(userDetail,HttpStatus.OK);
    }
    @GetMapping("/findRoleByName")
    public ResponseEntity<?> findRoleByRoleName(@RequestParam String roleName){
        UserRole userRole=userService.findRoleByRoleName(roleName);
        return new ResponseEntity<>(userRole,HttpStatus.OK);
    }

    @GetMapping("/findUserByUserNameAndPssword")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<?> findUserByUserNameAndPassword(@RequestParam String userName,@RequestParam String password){
        UserDetail userDetail=userService.findUserByEmailAndPassword(userName,password);
        System.out.println("User Detail "+userDetail);
        Map<String, Object> map = new LinkedHashMap<String, Object>();
        if (userDetail!= null) {


            map.put("statusCode", HttpStatus.OK.value());
            map.put("data", userDetail);
            return new ResponseEntity<>(map, HttpStatus.OK);
        } else {
            map.clear();
            map.put("statusCode", HttpStatus.NOT_FOUND.value());
            map.put("message", "User Not Found");
            return new ResponseEntity<>(map, HttpStatus.OK);
        }

    }









}
