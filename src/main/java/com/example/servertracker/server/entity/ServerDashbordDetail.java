package com.example.servertracker.server.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "SERVER_DASHBORD_DETAIL")
@Entity
public class ServerDashbordDetail {
    @Id
    @SequenceGenerator(name="server_dashbord_detail_generator", sequenceName="SERVER_DASHBORD_DETAIL_SEQ", allocationSize=1)
    @GeneratedValue(strategy= GenerationType.SEQUENCE, generator="server_dashbord_detail_generator")
    private Long id;
    @Column(name = "DB_TABLE_SPACE_OCCUPY_PERC")
    private String dbTableSpaceOccupyPerc;
    @Column(name = "APP_SPACE_USED_PERC")
    private String appSpaceUsedPerc;
    @Column(name = "SERVER_CACHE_STATUS")
    private String serverCacheStatus;
    @Column(name = "SERVER_IP")
    private String serverIp;
    @Column(name = "LIVE_BILLING_CATALOG")
    private String liveBillingCatalog;
}
