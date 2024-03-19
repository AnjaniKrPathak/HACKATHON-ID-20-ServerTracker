package com.example.servertracker.server.repo;

import com.example.servertracker.server.entity.ServerAppLiveStatusReport;

import java.util.List;

public interface IServerDao {
    List<ServerAppLiveStatusReport> serverListStatusReport(Long userId);
}
