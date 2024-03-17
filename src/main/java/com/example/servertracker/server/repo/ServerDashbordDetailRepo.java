package com.example.servertracker.server.repo;

import com.example.servertracker.server.entity.ServerDashbordDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServerDashbordDetailRepo extends JpaRepository<ServerDashbordDetail,Long> {


    ServerDashbordDetail findByServerIp(String serverIp);
}
