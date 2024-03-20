package com.example.servertracker.server.service;

import com.example.servertracker.server.entity.*;
import com.example.servertracker.user.entity.UserServerDetail;

import java.util.HashMap;
import java.util.List;

public interface IServerService {

    ServerPocAmCacheDetail saveServerPocAMCachedetail(ServerPocAmCacheDetail serverPocAmCacheDetail);

    ServerDbTableSpaceDetail saveServerDbTableSpaceDetail(ServerDbTableSpaceDetail serverDbTableSpaceDetail);

    ServerAppSpaceDetail saveServerAppSpaceDetail(ServerAppSpaceDetail serverAppSpaceDetail);

    ServerDashbordDetail saveServerDashbordDetail(ServerDashbordDetail dashbordDetail);

    HashMap<String, ServerAppSpaceDetail> getUserServerOSInfo(List<UserServerDetail> userServers);

    List<ServerDashbordDetail> getServerDashbordDetail(Long userId);

    ServerPocAmCacheDetail getServerPocAMStatus(String serverIp);

   List<ServerAppLiveStatusReport> getServerListStatusReport(Long userId);

    List<ServerAppSpaceReport> appStatusReprot(Long userId);

    List<ServerAppDbSpaceReport> dbStatusReprot(Long userId);
}
