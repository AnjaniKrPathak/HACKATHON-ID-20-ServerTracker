package com.example.servertracker.server.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data

@AllArgsConstructor
@NoArgsConstructor
@Table(name = "SERVER_POC_AM_CACHE_DETAIL")
@Entity
public class ServerPocAmCacheDetail {
    @Id
    @NonNull
    @SequenceGenerator(name="server_poc_am_cache_generator", sequenceName="SERVER_POC_AM_CACHE_SEQ", allocationSize=1)
    @GeneratedValue(strategy= GenerationType.SEQUENCE, generator="server_poc_am_cache_generator")
    @Column(name = "ID")
    private Long id;
    private  String serverIp;
    @Column(name ="CACHE_OBJECT" )
    private String cacheObject;
    @Column(name = "CACHE_NAME")
    private String cacheName;
    @Column(name = "CACHE_STATUS")
    private String cacheStatus;
    @Column(name = "CREATED_WHEN")
    private String createdWhen;

    public String getServerIp() {
        return serverIp;
    }

    public void setServerIp(String serverIp) {
        this.serverIp = serverIp;
    }

    public String getCacheObject() {
        return cacheObject;
    }

    public void setCacheObject(String cacheObject) {
        this.cacheObject = cacheObject;
    }

    public String getCacheName() {
        return cacheName;
    }

    public void setCacheName(String cacheName) {
        this.cacheName = cacheName;
    }

    public String getCacheStatus() {
        return cacheStatus;
    }

    public void setCacheStatus(String cacheStatus) {
        this.cacheStatus = cacheStatus;
    }

    public String getCreatedWhen() {
        return createdWhen;
    }

    public void setCreatedWhen(String createdWhen) {
        this.createdWhen = createdWhen;
    }

}
