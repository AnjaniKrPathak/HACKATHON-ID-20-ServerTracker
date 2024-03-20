package com.example.servertracker.server.repo;

import com.example.servertracker.server.entity.ServerAppDbSpaceReport;
import com.example.servertracker.server.entity.ServerAppLiveStatusReport;
import com.example.servertracker.server.entity.ServerAppSpaceReport;

import java.util.List;

public interface IServerDao {
    List<ServerAppLiveStatusReport> serverListStatusReport(Long userId);

    List<ServerAppSpaceReport> appStatusSpaceReport(Long userId);

    List<ServerAppDbSpaceReport> dbSpaceServerReport(Long userId);
}
