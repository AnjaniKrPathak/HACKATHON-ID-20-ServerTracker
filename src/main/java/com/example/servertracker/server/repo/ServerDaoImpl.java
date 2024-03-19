package com.example.servertracker.server.repo;

import com.example.servertracker.server.entity.ServerAppLiveStatusReport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ServerDaoImpl implements IServerDao {
    @Autowired
    JdbcTemplate jdbcTemplate;


    @Override
    public List<ServerAppLiveStatusReport> serverListStatusReport(Long userId) {

        String sql =    "select app_server_status \"serverStaus\", count(*) \"count\" from SERVER_DASHBORD_DETAIL sd, user_server_detail usd where sd.server_ip = usd.server_ip and user_id =1\n" +
                "group by sd.app_server_status" ;
        List<ServerAppLiveStatusReport> appLiveStatusReports = jdbcTemplate.query(sql,
                BeanPropertyRowMapper.newInstance(ServerAppLiveStatusReport.class));

        return appLiveStatusReports;


    }
}
