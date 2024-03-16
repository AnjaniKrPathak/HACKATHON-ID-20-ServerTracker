package com.example.servertracker.server.service;

import com.example.servertracker.server.entity.ServerAppSpaceDetail;
import com.example.servertracker.server.entity.ServerDashbordDetail;
import com.example.servertracker.server.entity.ServerDbTableSpaceDetail;
import com.example.servertracker.server.entity.ServerPocAmCacheDetail;
import com.example.servertracker.server.repo.ServerAppSpaceDetailRepo;
import com.example.servertracker.server.repo.ServerDashbordDetailRepo;
import com.example.servertracker.server.repo.ServerDbTableSpaceDetailRepo;
import com.example.servertracker.server.repo.ServerPocAMCacheDetailRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
