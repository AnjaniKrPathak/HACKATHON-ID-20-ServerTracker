package com.example.servertracker.server.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
    public class DBConnectionInfo {
        private String serverName;
        private String serverId;
        private String url;
        private String userName;
        private String userPass;
        private String className;



}
