package com.example.servertracker.server.service;

import com.example.servertracker.server.entity.ServerPocAmCacheDetail;
import com.example.servertracker.server.repo.ServerPocAMCacheDetailRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServerServiceImpl implements IServerService{
    @Autowired
    ServerPocAMCacheDetailRepo serverPocAMCacheDetailRepo;


    @Override
    public ServerPocAmCacheDetail saveServerPocAMCachedetail(ServerPocAmCacheDetail serverPocAmCacheDetail) {
        serverPocAMCacheDetailRepo.saveAndFlush(serverPocAmCacheDetail);
        return serverPocAmCacheDetail;
    }
}
