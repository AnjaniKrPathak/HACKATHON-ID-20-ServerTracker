package com.example.servertracker.server.service;

import com.example.servertracker.server.entity.*;
import com.example.servertracker.server.repo.*;
import com.example.servertracker.user.entity.UserServerDetail;
import com.example.servertracker.user.service.IUserService;
import com.jcraft.jsch.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

@Service
public class ServerServiceImpl implements IServerService{
    @Autowired
    ServerPocAMCacheDetailRepo serverPocAMCacheDetailRepo;
    @Autowired
    ServerDbTableSpaceDetailRepo spaceDetailRepo;
    @Autowired
    ServerAppSpaceDetailRepo appSpaceDetailRepo;
    @Autowired
    ServerDashbordDetailRepo dashbordDetrailRepo;
    @Autowired
    IUserService userService;
    @Autowired
    JdbcTemplate jdbcTemplate;
    @Autowired
    IServerDao dashbordDao;

    @Override
    public ServerPocAmCacheDetail saveServerPocAMCachedetail(ServerPocAmCacheDetail serverPocAmCacheDetail) {
        serverPocAMCacheDetailRepo.saveAndFlush(serverPocAmCacheDetail);
        return serverPocAmCacheDetail;
    }

    @Override
    public ServerPocAmCacheDetail getServerPocAMStatus(String serverIp) {
        ServerPocAmCacheDetail serverPocAmCacheDetail;
        String sql = "\n" +
                "select o.name \"Cache_Name\",o.object_id \"Cache_Object\" , lv.value \"cacheStatus\", to_date(d.date_value,'dd-mm-yy') \"Created_When\" from nc_objects o , nc_params p, nc_list_values " +
                "lv,nc_params d where parent_id =9158663745313788273 and o.object_id = p.object_id and p.attr_id =9158663977813788559 and p.list_value_id =lv.list_value_id   and d.object_id =o.object_id and d.attr_id = 62    order by name desc fetch first 1 rows only";
        List<ServerPocAmCacheDetail> serverPocAmStatus = jdbcTemplate.query(
                sql,
                new BeanPropertyRowMapper(ServerPocAmCacheDetail.class));
        serverPocAmCacheDetail= new ServerPocAmCacheDetail();
        List<ServerPocAmCacheDetail> serverPocAmStatusList = new ArrayList<>();
        for (ServerPocAmCacheDetail sv : serverPocAmStatus) {
            serverPocAmCacheDetail.setCacheStatus(sv.getCacheStatus());
            serverPocAmCacheDetail.setServerIp(serverIp);
            serverPocAmCacheDetail.setCacheObject(sv.getCacheObject());
            serverPocAmCacheDetail.setCreatedWhen(sv.getCreatedWhen());
            serverPocAmCacheDetail.setCacheName(sv.getCacheName());

            serverPocAmStatusList.add(serverPocAmCacheDetail);

        }

        System.out.println("POC AM Cache Details: "+serverPocAmStatusList);
        return serverPocAmStatusList.get(0);
    }

    @Override
    public List<ServerAppLiveStatusReport> getServerListStatusReport(Long userId) {
        List<ServerAppLiveStatusReport> statusReport=dashbordDao.serverListStatusReport(userId);
        return statusReport;
    }

    @Override
    public List<ServerAppSpaceReport> appStatusReprot(Long userId) {
        return dashbordDao.appStatusSpaceReport(userId);
    }

    @Override
    public List<ServerAppDbSpaceReport> dbStatusReprot(Long userId) {
        return dashbordDao.dbSpaceServerReport(userId);
    }

    @Override
    public ServerDbTableSpaceDetail saveServerDbTableSpaceDetail(ServerDbTableSpaceDetail serverDbTableSpaceDetail) {
        return  spaceDetailRepo.saveAndFlush(serverDbTableSpaceDetail);
    }

    @Override
    public ServerAppSpaceDetail saveServerAppSpaceDetail(ServerAppSpaceDetail serverAppSpaceDetail) {
        return appSpaceDetailRepo.saveAndFlush(serverAppSpaceDetail);
    }

    @Override
    public ServerDashbordDetail saveServerDashbordDetail(ServerDashbordDetail dashbordDetail) {
        return dashbordDetrailRepo.save(dashbordDetail);

    }

    @Override
    public List<ServerDashbordDetail> getServerDashbordDetail(Long userId) {
        List<UserServerDetail> userServerDetails=userService.getUserServerBasedOnUserId(userId);
        System.out.println("user Server Details "+userServerDetails.size());
        List<ServerDashbordDetail> serverDashbordDetails=new ArrayList<>();
       // serverDashbordDetails =dashbordDetrailRepo.findByServerIp("10.109.35.199");
        List<ServerDashbordDetail> serverDashbordDetailList=new ArrayList<>();
        for(UserServerDetail userServerDetail:userServerDetails){
            System.out.println("Server IP"+userServerDetail.getServerIp());

            ServerDashbordDetail serverDashbordDetail1 =dashbordDetrailRepo.findByServerIp(userServerDetail.getServerIp());
            serverDashbordDetailList.add(serverDashbordDetail1);
            System.out.println("Dashbord Details Size"+serverDashbordDetails.size());
        }
        return serverDashbordDetailList;
    }

    @Override
    public HashMap<String, ServerAppSpaceDetail> getUserServerOSInfo(List<UserServerDetail> userServers) {
        HashMap<String,ServerAppSpaceDetail> serverOsInfoMap=new HashMap<>();
        List<ServerAppSpaceDetail> serverSpaces=new ArrayList<>();
        System.out.println(" APP Server Info: "+serverOsInfoMap);
        ServerAppSpaceDetail serverAppSpaceDetail=null;
        for(UserServerDetail u1:userServers){

            try {

                serverAppSpaceDetail = puttyConnection("netcrk", u1.getServerIp(), 22, "crknet");

                //Saving data
                saveServerAppSpaceDetail(serverAppSpaceDetail);
                serverOsInfoMap.put(u1.getServerIp(), serverAppSpaceDetail);
            } catch (Exception e) {
//                e.printStackTrace();
                //Add status to Unix box and set as inactive
                System.out.println("*****Application Server NOT RESPONDNG****: "+u1.getServerIp());
            }
        }
        if(null != serverAppSpaceDetail)
            System.out.println(" APP Server Info Return:"+serverAppSpaceDetail.getServerIp()+","+serverAppSpaceDetail.getUsedPerc());

        System.out.println(" APP Server Info Return: "+serverOsInfoMap);
        return serverOsInfoMap;
    }

    public static ServerAppSpaceDetail puttyConnection(String userName, String hostName, int port, String password)
            throws JSchException, IOException {
        JSch jsch = new JSch();
        String command = "df -h";
        /*String command ="df -h | grep \"u02\"";*/
        List<ServerAppSpaceDetail> serverAppSpaceDetailList = new ArrayList<>();
        Session session = jsch.getSession(userName, hostName, port);

        // Set the password for authentication (you can use other methods for authentication)
        session.setPassword(password);

        // Avoid checking host key
        Properties config = new Properties();
        config.put("StrictHostKeyChecking", "no");
        session.setConfig(config);

        // Establish the connection
        session.connect();

        // Open a channel
        Channel channel = session.openChannel("exec");

        // Set the command to be executed
        ((ChannelExec) channel).setCommand(command);

        // Connect the channel
        channel.connect();

        // Read the output of the command
        InputStream inputStream = channel.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String[] st=new String[reader.readLine().length()];
        HashMap<String, String> hm= new HashMap<String, String>();
        String splitLine[];
        String line;
        System.out.println(reader.readLine());
        int i=0;
        ServerAppSpaceDetail serverAppSpaceDetail = new ServerAppSpaceDetail();;
        while ((line = reader.readLine()) != null) {
//            serverAppSpaceDetail=new ServerAppSpaceDetail();
            splitLine=line.split("\\s+");

            if(splitLine[0].substring(splitLine[0].lastIndexOf("/")+1,splitLine[0].length()).equals("vmdisk2-u02")){
                serverAppSpaceDetail.setFileSystem(splitLine[0].substring(splitLine[0].lastIndexOf("/")+1,splitLine[0].length()));
                serverAppSpaceDetail.setTotalSize(splitLine[1]);
                serverAppSpaceDetail.setUsed(splitLine[2]);
                serverAppSpaceDetail.setAvail(splitLine[3]);
                serverAppSpaceDetail.setUsed(splitLine[4]);
                serverAppSpaceDetail.setServerIp(hostName);

                serverAppSpaceDetail.setMountedOn(splitLine[5]);


//                serverAppSpaceDetailList.add(serverAppSpaceDetail);
            }
        }
        // Disconnect the channel and session when done
        channel.disconnect();
        session.disconnect();

        if(null != serverAppSpaceDetail)
            System.out.println(" puttyConnection hostName Return: "+hostName+">>>"+ serverAppSpaceDetail.getFileSystem()+","+serverAppSpaceDetail.getAvail());
        return serverAppSpaceDetail;
    }
}
