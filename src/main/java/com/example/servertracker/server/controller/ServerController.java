package com.example.servertracker.server.controller;

import com.example.servertracker.server.entity.*;
import com.example.servertracker.server.service.IServerService;
import com.example.servertracker.user.entity.UserServerDetail;
import com.example.servertracker.user.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.servertracker.server.entity.DBConnectionInfo;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;
import com.jcraft.jsch.*;

import static com.example.servertracker.server.repo.ConfigDataSource.getDBSpaceDetailsInfo;

@RestController
@RequestMapping("/server")
public class ServerController {
    @Autowired
    IUserService userService;
    @Autowired
    IServerService serverService;
    @PostMapping("/saveServerPocAMCacheDetail")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<?> savePocAMCacheStatus(@RequestBody ServerPocAmCacheDetail serverPocAmCacheDetail){


              ServerPocAmCacheDetail caheDetail= serverService.saveServerPocAMCachedetail(serverPocAmCacheDetail);
               return new ResponseEntity<>(caheDetail, HttpStatus.OK);

    }
    @PostMapping("/saveServerDbTableSpaceDetail")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<?> saveServerDbTableSpaceDetail(@RequestBody ServerDbTableSpaceDetail serverDbTableSpaceDetail){
        ServerDbTableSpaceDetail dbTableSpaceDetail=serverService.saveServerDbTableSpaceDetail(serverDbTableSpaceDetail);
        return new ResponseEntity<>(dbTableSpaceDetail,HttpStatus.OK);

    }
    @PostMapping("/saveServerAppSpaceDetail")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<?> saveServerAppSpaceDetail(@RequestBody ServerAppSpaceDetail serverAppSpaceDetail){
        ServerAppSpaceDetail appSpaceDetail=serverService.saveServerAppSpaceDetail(serverAppSpaceDetail);
        return new ResponseEntity<>(serverAppSpaceDetail,HttpStatus.OK);
    }
    @PostMapping("/saveServerDashbordDetail")
    @CrossOrigin(origins = "http://localhost:3000")
    public  ResponseEntity<?> saveServerDashbordDetail(@RequestBody ServerDashbordDetail dashbordDetail){
        ServerDashbordDetail serverDashbordDetail =serverService.saveServerDashbordDetail(dashbordDetail);
        return new ResponseEntity<>(serverDashbordDetail,HttpStatus.OK);
    }

	    private Map<String, DBConnectionInfo> getDBDetailsMap() {
        Map<String, DBConnectionInfo> dbDetailsMap = new HashMap<String, DBConnectionInfo>();
        List<UserServerDetail> userServerDetails = userService.getAllUserServer();

//        List<UserServer> userServers=userService.getAllUserServers();
        DBConnectionInfo dbConnectionInfo;
        int i = 0;
        for (UserServerDetail userServer : userServerDetails) {
            dbConnectionInfo = new DBConnectionInfo();
            dbConnectionInfo.setServerName(userServer.getServerIp());
            dbConnectionInfo.setServerId("" + userServer.getServerId());
            dbConnectionInfo.setUrl("jdbc:oracle:thin:@" + userServer.getServerIp() + ":1524/DBG195");
            dbConnectionInfo.setUserName(userServer.getDbUserName());
            dbConnectionInfo.setUserPass(userServer.getDbUserPassword());
            dbConnectionInfo.setClassName("oracle.jdbc.OracleDriver");

            dbDetailsMap.put(userServer.getServerIp(), dbConnectionInfo);
            i++;
            System.out.println("Getting Connection Seq No: " + i);
        }

        System.out.println("DBConnection Map :" + dbDetailsMap);
        return dbDetailsMap;
    }


    @GetMapping("/getAllDBServer")
    @CrossOrigin(origins = "http://localhost:3000")
//    public List<UserServerDetail> getUserServerDetail(){
    public ResponseEntity<?> getDBServerDetail() throws Exception {
        Map<String, DBConnectionInfo> hashMap = getDBDetailsMap();
        StringBuilder result = new StringBuilder();

        // Get the set of entries from the HashMap
        Set<Map.Entry<String, DBConnectionInfo>> entrySet = hashMap.entrySet();

        // Create an iterator for the entry set
        Iterator iterator = entrySet.iterator();

        // Iterate through the entries using a while loop
        System.out.println("Iterating HashMap without functional programming:" + hashMap);
        result = new StringBuilder();
        List<ServerDbTableSpaceDetail> serverDbTableSpaceDetailList = new ArrayList<>();
        ServerDbTableSpaceDetail serverDbTableSpaceDetail = null;
        while (iterator.hasNext()) {
            Map.Entry<String, DBConnectionInfo> entry = (Map.Entry<String, DBConnectionInfo>) iterator.next();
            String key = entry.getKey();
            DBConnectionInfo value = entry.getValue();
            serverService.saveServerDbTableSpaceDetail(getDBSpaceDetailsInfo(value.getUrl(), value.getUserName(), value.getServerName()));
            System.out.println(" Server Name: " + value.getServerName());
            System.out.println(" Server ID: " + value.getServerId());
        }

        result.append("Success");
        System.out.println(" Final Result: " + result);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }


    @GetMapping("/getAllDBServer/{serverIdAddress}")
    @CrossOrigin(origins = "http://localhost:3000")
//    public List<UserServerDetail> getUserServerDetailByServerIP(@PathVariable String serverIdAddress){
    public ResponseEntity<?> getUserServerDetailByServerIP(@PathVariable String serverIdAddress) throws Exception {
        StringBuilder  result=new StringBuilder();
        if(serverIdAddress == null){
            result.append("Server IP address is null");
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }

        String ipAddress[]=serverIdAddress.split(",");
        List<UserServerDetail> serverList = new ArrayList<UserServerDetail>();
        UserServerDetail serverInfo;
        for (int i=0;i<ipAddress.length;i++){
            serverInfo = new UserServerDetail();
            serverInfo.SetServerIp(ipAddress[i]);
            serverInfo.SetDbUserName("U32_C5_6400");
            serverInfo.SetDbUserPassword("U32_C5_6400");

            System.out.println("Server IP address: "+ipAddress[i]);

            serverList.add(serverInfo);
        }

        Map<String, DBConnectionInfo> hashMap=getDBDetailsMapByIPAddress(serverList);


        // Get the set of entries from the HashMap
        Set<Map.Entry<String, DBConnectionInfo>> entrySet = hashMap.entrySet();

        // Create an iterator for the entry set
        Iterator iterator = entrySet.iterator();

        // Iterate through the entries using a while loop
        System.out.println("Iterating HashMap without functional programming:"+hashMap);
        result=new StringBuilder();
        while (iterator.hasNext()) {
            Map.Entry<String, DBConnectionInfo> entry = (Map.Entry<String, DBConnectionInfo>) iterator.next();
            String key = entry.getKey();
            DBConnectionInfo value = entry.getValue();
            result=result.append("Server Name: ")
                    .append(value.getServerName())
                    .append(getDBSpaceDetailsInfo(value.getUrl(),value.getUserName(), value.getServerName()));

        }
        System.out.println(" Final Result: "+result);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }


    private Map<String, DBConnectionInfo> getDBDetailsMapByIPAddress(List<UserServerDetail> serverList) {
        Map<String, DBConnectionInfo> dbDetailsMap = new HashMap<String, DBConnectionInfo>();
//        List<UserServerDetail> userServerDetails = userService.getAllUserServer();

//        List<UserServer> userServers=userService.getAllUserServers();
        DBConnectionInfo dbConnectionInfo;
        int i = 0;
        for (UserServerDetail userServer : serverList) {
            dbConnectionInfo = new DBConnectionInfo();
            dbConnectionInfo.setServerName(userServer.getServerIp());
            dbConnectionInfo.setServerId("" + userServer.getServerId());
            dbConnectionInfo.setUrl("jdbc:oracle:thin:@" + userServer.getServerIp() + ":1524/DBG195");
            dbConnectionInfo.setUserName(userServer.getDbUserName());
            dbConnectionInfo.setUserPass(userServer.getDbUserPassword());
            dbConnectionInfo.setClassName("oracle.jdbc.OracleDriver");

            dbDetailsMap.put(userServer.getServerIp(), dbConnectionInfo);
            i++;
            System.out.println("Getting Connection Seq No: " + i);
        }

        System.out.println("DBConnection Map :" + dbDetailsMap);
        return dbDetailsMap;
    }

    @GetMapping("/checkServerHealth")
    @CrossOrigin(origins = "http://localhost:3000")
    public  ResponseEntity<?>  getServerHealth(){
        List<UserServerDetail> userServerDetails=userService.getAllUserServer();
        RestTemplate restTemplate = new RestTemplate();
        StringBuilder result= new StringBuilder();
        ResponseEntity<String> response;
        String statusServer="";
        ServerDashbordDetail serverDashbordDetail=null;
        for(UserServerDetail userServer:userServerDetails){
            String ipAddress=userServer.getServerIp();
            serverDashbordDetail=new ServerDashbordDetail();
            serverDashbordDetail.setServerIp(ipAddress);
            Boolean isActive=false;

            String loginURL="http://"+ipAddress+":"+userServer.getAppServerPort()+"/login.jsp";
            System.out.println(" loginURL: "+loginURL);
            try {
                response = restTemplate.getForEntity(loginURL, String.class);
                restTemplate.getForEntity(loginURL, String.class);
                statusServer = "" + response.getStatusCode().is2xxSuccessful();


                System.out.println("Status of Server:"+statusServer);
                if (response.getStatusCode().is2xxSuccessful()) {
                    System.out.println("Server is up and running.");
                    isActive=true;
                } else {
                    System.out.println("Server is not responding or experiencing issues.");
                }

            }catch (Exception e){
                System.out.println("Unable to reach to server: "+loginURL);
                statusServer="Unable to Reach";
//               result=result.append(loginURL+" Unable to reach");
            }
            result=result.append(loginURL+": "+statusServer).append("\n");
            if(isActive){
                serverDashbordDetail.setAppServerStatus("Active");
            }else{
                serverDashbordDetail.setAppServerStatus("In-Active");
            }

            //Save Server status
            serverService.saveServerDashbordDetail(serverDashbordDetail);
        }


//        saveServerDashbordDetail(ServerDashbordDetail dashbordDetail) {
        System.out.println(" Final Result: "+result);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/getAllDBServer/user/{userId}")
    public ResponseEntity<?> getUserServerDetailByUserId(@PathVariable String userId) throws Exception {
        StringBuilder  result=new StringBuilder();
        if(userId == null){
            result.append("Server IP address is null :"+userId);
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }

        List<UserServerDetail> userServerDetails=userService.getUserServerBasedOnUserId(Long.parseLong(userId));
        //Just for testing
     /*   List<UserServerDetail> userServerDetails=new ArrayList<>();
            UserServerDetail us=new UserServerDetail();
            us.SetServerIp("10.109.35.199");
            us.SetDbUserName("U32_C5_6400");
            us.SetDbUserPassword("U32_C5_6400");
        userServerDetails.add(us);
        System.out.println(" Value Hard Coded for user: "+userId);*/


        Map<String, DBConnectionInfo> hashMap=getDBDetailsMapByIPAddress(userServerDetails);
        // Get the set of entries from the HashMap
        Set<Map.Entry<String, DBConnectionInfo>> entrySet = hashMap.entrySet();

        // Create an iterator for the entry set
        Iterator iterator = entrySet.iterator();

        // Iterate through the entries using a while loop
        System.out.println("Iterating HashMap without functional programming:"+hashMap);
        result=new StringBuilder();
        while (iterator.hasNext()) {
            Map.Entry<String, DBConnectionInfo> entry = (Map.Entry<String, DBConnectionInfo>) iterator.next();
            String key = entry.getKey();
            DBConnectionInfo value = entry.getValue();
            serverService.saveServerDbTableSpaceDetail(getDBSpaceDetailsInfo(value.getUrl(), value.getUserName(), value.getServerName()));
            System.out.println(" Server Name: " + value.getServerName());
            System.out.println(" Server ID: " + value.getServerId());
        }

        result.append("Success");
        System.out.println(" Final Result: " + result);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/serverOsSpaceInfo")
    public ResponseEntity<?> getOSInfo(){
        List<UserServerDetail> userServerDetails=userService.getAllUserServer();
//        System.out.println(" getOSInfo: "+userServerDetails);
        HashMap<String, ServerAppSpaceDetail> serverOSInfoMap= serverService.getUserServerOSInfo(userServerDetails);

        //updating DB
//        serverOSInfoMap.forEach((key, value)->serverService.saveServerAppSpaceDetail((ServerAppSpaceDetail) value));

        Map<String,Object> map=new LinkedHashMap<String,Object>();
        System.out.println(" APP Server Info: "+serverOSInfoMap);
        if(!serverOSInfoMap.isEmpty()){
            map.put("status", 1);
            map.put("data",serverOSInfoMap);
            return new ResponseEntity<>(map, HttpStatus.OK);
        }
        else {
            map.clear();
            map.put("status",0);
            map.put("message","No Data Found");
            return new ResponseEntity<>(map, HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/getDashbordDetail")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<?> getServerDashbordDetail(@RequestParam Long userId){
        List<ServerDashbordDetail> dashbordDetailList =serverService.getServerDashbordDetail(userId);
        Map<String,Object> serverDashbordDetailMap=new LinkedHashMap<String,Object>();
        if(!dashbordDetailList.isEmpty()){



            serverDashbordDetailMap.put("statusCode", HttpStatus.OK.value());
            serverDashbordDetailMap.put("data",dashbordDetailList);
            return new ResponseEntity<>(serverDashbordDetailMap, HttpStatus.OK);
        }
        else {
            serverDashbordDetailMap.clear();
            serverDashbordDetailMap.put("status",0);
            serverDashbordDetailMap.put("message","Server Not Added");
            return new ResponseEntity<>(serverDashbordDetailMap, HttpStatus.NOT_FOUND);
        }



    }
    @GetMapping("/serverLiveStatusReport")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<?> getServerLiveStatusReport(@RequestParam Long userId){
        List<ServerAppLiveStatusReport> serverAppLiveStatusDetails=new ArrayList<>();
        serverAppLiveStatusDetails.add(new ServerAppLiveStatusReport(10,6,4));
        Map<String,Object> mapLiveStatus=new LinkedHashMap<String,Object>();
        if(!serverAppLiveStatusDetails.isEmpty()){



            mapLiveStatus.put("statusCode", HttpStatus.OK.value());
            mapLiveStatus.put("data",serverAppLiveStatusDetails);
            return new ResponseEntity<>(mapLiveStatus, HttpStatus.OK);
        }
        else {
            mapLiveStatus.clear();
            mapLiveStatus.put("status",0);
            mapLiveStatus.put("message","Server Not Added");
            return new ResponseEntity<>(mapLiveStatus, HttpStatus.NOT_FOUND);
        }

    }
    @GetMapping("/appSpaceStatusReport")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<?> getAppStatusReport(@RequestParam Long userId){
        List<ServerAppSpaceReport> serverAppLiveStatusDetails=new ArrayList<>();
        serverAppLiveStatusDetails.add(new ServerAppSpaceReport(10,6,3,1));
        Map<String,Object> mapAppSpaceReport=new LinkedHashMap<String,Object>();
        if(!serverAppLiveStatusDetails.isEmpty()){



            mapAppSpaceReport.put("statusCode", HttpStatus.OK.value());
            mapAppSpaceReport.put("data",serverAppLiveStatusDetails);
            return new ResponseEntity<>(mapAppSpaceReport, HttpStatus.OK);
        }
        else {
            mapAppSpaceReport.clear();
            mapAppSpaceReport.put("status",0);
            mapAppSpaceReport.put("message","Server Not Added");
            return new ResponseEntity<>(mapAppSpaceReport, HttpStatus.NOT_FOUND);
        }

    }

    @GetMapping("/dbSpaceStatusReport")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<?> getDbStatusReport(@RequestParam Long userId){
        List<ServerAppDbSpaceReport> serverAppDbSpaceReports=new ArrayList<>();
        serverAppDbSpaceReports.add(new ServerAppDbSpaceReport(10,2,5,3));
        Map<String,Object> mapDbSpaceReport=new LinkedHashMap<String,Object>();
        if(!serverAppDbSpaceReports.isEmpty()){



            mapDbSpaceReport.put("statusCode", HttpStatus.OK.value());
            mapDbSpaceReport.put("data",serverAppDbSpaceReports);
            return new ResponseEntity<>(mapDbSpaceReport, HttpStatus.OK);
        }
        else {
            mapDbSpaceReport.clear();
            mapDbSpaceReport.put("status",0);
            mapDbSpaceReport.put("message","Server Not Added");
            return new ResponseEntity<>(mapDbSpaceReport, HttpStatus.NOT_FOUND);
        }

    }

}
