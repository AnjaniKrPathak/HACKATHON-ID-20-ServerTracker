package com.example.servertracker.server.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data

@AllArgsConstructor
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
}
