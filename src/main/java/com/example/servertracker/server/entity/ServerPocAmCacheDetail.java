package com.example.servertracker.server.entity;

import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "SERVER_POC_AM_CACHE_DETAIL")
public class ServerPocAmCacheDetail {
    @Id

    private Long id;
    private  String serverIp;
    private String cacheObject;
    private String cacheName;
    private String cacheStatus;
    private String createdWhen;
}
