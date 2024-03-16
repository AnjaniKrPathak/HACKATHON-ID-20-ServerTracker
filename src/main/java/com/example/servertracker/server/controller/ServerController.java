package com.example.servertracker.server.controller;

import com.example.servertracker.server.entity.*;
import com.example.servertracker.server.service.IServerService;
import com.example.servertracker.user.entity.UserServerDetail;
import com.example.servertracker.user.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.*;

import static com.example.servertracker.server.repo.ConfigDataSource.getDBFlatOfferingDetails;
@RestController
@RequestMapping("/server")
public class ServerController {
    @Autowired
    IUserService userService;
    @Autowired
    IServerService serverService;
    @PostMapping("/saveServerPocAMCacheDetail")
    public ResponseEntity<?> savePocAMCacheStatus(@RequestBody ServerPocAmCacheDetail serverPocAmCacheDetail){


              ServerPocAmCacheDetail caheDetail= serverService.saveServerPocAMCachedetail(serverPocAmCacheDetail);
               return new ResponseEntity<>(caheDetail, HttpStatus.OK);

    }
    @PostMapping("/saveServerDbTableSpaceDetail")
    public ResponseEntity<?> saveServerDbTableSpaceDetail(@RequestBody ServerDbTableSpaceDetail serverDbTableSpaceDetail){
        ServerDbTableSpaceDetail dbTableSpaceDetail=serverService.saveServerDbTableSpaceDetail(serverDbTableSpaceDetail);
        return new ResponseEntity<>(dbTableSpaceDetail,HttpStatus.OK);

    }
    @PostMapping("/saveServerAppSpaceDetail")
    public ResponseEntity<?> saveServerAppSpaceDetail(@RequestBody ServerAppSpaceDetail serverAppSpaceDetail){
        ServerAppSpaceDetail appSpaceDetail=serverService.saveServerAppSpaceDetail(serverAppSpaceDetail);
        return new ResponseEntity<>(serverAppSpaceDetail,HttpStatus.OK);
    }
    @PostMapping("/saveServerDashbordDetail")
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
        while (iterator.hasNext()) {
            Map.Entry<String, DBConnectionInfo> entry = (Map.Entry<String, DBConnectionInfo>) iterator.next();
            String key = entry.getKey();
            DBConnectionInfo value = entry.getValue();
            result = result.append("Server Name: ")
                    .append(value.getServerName())
                    .append(getDBFlatOfferingDetails(value.getUrl(), value.getUserName()));

        }
        System.out.println(" Final Result: " + result);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }


    @GetMapping("/getAllDBServer/{serverIdAddress}")
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
                    .append(getDBFlatOfferingDetails(value.getUrl(),value.getUserName()));

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
    public  ResponseEntity<?>  getServerHealth(){
        List<UserServerDetail> userServerDetails=userService.getAllUserServer();
        RestTemplate restTemplate = new RestTemplate();
        StringBuilder result= new StringBuilder();
        ResponseEntity<String> response;
        String statusServer="";
        for(UserServerDetail userServer:userServerDetails){
            String ipAddress=userServer.getServerIp();
            String loginURL="http://"+ipAddress+":"+userServer.getAppServerPort()+"/login.jsp";
            System.out.println(" loginURL: "+loginURL);

            try {
                response = restTemplate.getForEntity(loginURL, String.class);
                restTemplate.getForEntity(loginURL, String.class);
                statusServer = "" + response.getStatusCode().is2xxSuccessful();


                System.out.println("Status of Server:"+statusServer);
                if (response.getStatusCode().is2xxSuccessful()) {
                    System.out.println("Server is up and running.");
                } else {
                    System.out.println("Server is not responding or experiencing issues.");
                }

            }catch (Exception e){
                System.out.println("Unable to reach to server: "+loginURL);
                statusServer="Unable to Reach";
//               result=result.append(loginURL+" Unable to reach");
            }
            result=result.append(loginURL+": "+statusServer).append("\n");
        }

        System.out.println(" Final Result: "+result);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
    @GetMapping("/getDashbordDetail")
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

        return new ResponseEntity<>(dashbordDetailList,HttpStatus.OK);

    }
    @GetMapping("/serverLiveStatusReport")
    public ResponseEntity<?> getServerLiveStatusReport(@RequestParam Long userId){
        List<ServerAppLiveStatusDetail> serverAppLiveStatusDetails=new ArrayList<>();
        serverAppLiveStatusDetails.add(new ServerAppLiveStatusDetail(10,6,4));
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


}
