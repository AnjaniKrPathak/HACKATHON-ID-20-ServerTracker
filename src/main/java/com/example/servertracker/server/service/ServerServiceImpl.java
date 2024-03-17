package com.example.servertracker.server.service;

import com.example.servertracker.server.entity.ServerAppSpaceDetail;
import com.example.servertracker.server.entity.ServerDashbordDetail;
import com.example.servertracker.server.entity.ServerDbTableSpaceDetail;
import com.example.servertracker.server.entity.ServerPocAmCacheDetail;
import com.example.servertracker.server.repo.ServerAppSpaceDetailRepo;
import com.example.servertracker.server.repo.ServerDashbordDetailRepo;
import com.example.servertracker.server.repo.ServerDbTableSpaceDetailRepo;
import com.example.servertracker.server.repo.ServerPocAMCacheDetailRepo;
import com.example.servertracker.user.entity.UserServerDetail;
import com.jcraft.jsch.*;
import com.example.servertracker.user.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
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


    @Override
    public ServerPocAmCacheDetail saveServerPocAMCachedetail(ServerPocAmCacheDetail serverPocAmCacheDetail) {
        serverPocAMCacheDetailRepo.saveAndFlush(serverPocAmCacheDetail);
        return serverPocAmCacheDetail;
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

	@Override
    public List<ServerDashbordDetail> getServerDashbordDetail(Long userId) {
        List<UserServerDetail> userServerDetails=userService.getUserServerBasedOnUserId(userId);
        List<ServerDashbordDetail> serverDashbordDetails=new ArrayList<>();
        serverDashbordDetails =dashbordDetrailRepo.findByServerIp("10.109.35.199");
        for(UserServerDetail userServerDetail:userServerDetails){
           // serverDashbordDetails =dashbordDetrailRepo.findByServerIp(userServerDetail.getServerIp());
        }
        return serverDashbordDetails;
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
