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
    public List<ServerDashbordDetail> getServerDashbordDetail(Long userId) {
        List<UserServerDetail> userServerDetails=userService.getUserServerBasedOnUserId(userId);
        List<ServerDashbordDetail> serverDashbordDetails=new ArrayList<>();
        serverDashbordDetails =dashbordDetrailRepo.findByServerIp("10.109.35.199");
        for(UserServerDetail userServerDetail:userServerDetails){
           // serverDashbordDetails =dashbordDetrailRepo.findByServerIp(userServerDetail.getServerIp());
        }
        return serverDashbordDetails;
    }
}
