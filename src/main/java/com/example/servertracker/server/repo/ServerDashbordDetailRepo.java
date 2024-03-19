package com.example.servertracker.server.repo;

import com.example.servertracker.server.entity.ServerAppLiveStatusReport;
import com.example.servertracker.server.entity.ServerDashbordDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServerDashbordDetailRepo extends JpaRepository<ServerDashbordDetail,Long> {


    ServerDashbordDetail findByServerIp(String serverIp);
     @Query(
             value = "SELECT * FROM SERVER_DASHBORD_DETAIL u",
             nativeQuery = true)
    List<ServerAppLiveStatusReport> serverListStatusReport(Long userId);
}
